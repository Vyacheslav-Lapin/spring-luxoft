package ru.vlapin.trainings.springluxoft.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.val;
import ru.vlapin.trainings.springluxoft.exceptions.AccountNotFoundException;
import ru.vlapin.trainings.springluxoft.exceptions.ClientNotFoundException;
import ru.vlapin.trainings.springluxoft.model.AbstractAccount;
import ru.vlapin.trainings.springluxoft.model.CheckingAccount;
import ru.vlapin.trainings.springluxoft.model.Client;
import ru.vlapin.trainings.springluxoft.model.SavingAccount;
import ru.vlapin.trainings.springluxoft.service.storage.ClientRepository;

import org.springframework.stereotype.Component;

import static ru.vlapin.trainings.springluxoft.model.Client.Gender.*;

public interface Banking {

    Client addClient(Client client);

    Client getClient(String name);

    List<Client> getClients();

    @SuppressWarnings("unused")
    void deleteClient(Client client);

    AbstractAccount createAccount(Client client, Class<?> type);

    void updateAccount(Client c, AbstractAccount account);

    @SuppressWarnings("unused")
    AbstractAccount getAccount(Client client, Class<?> type);

    @SuppressWarnings("unused")
    List<AbstractAccount> getAllAccounts();

    List<AbstractAccount> getAllAccounts(Client client);

    void transferMoney(Client from, Client to, double amount);
}

@Component
@RequiredArgsConstructor
public
class BankingImpl implements Banking {

    ClientRepository repository;

    @Override
    public Client addClient(Client c) {

        Client created = repository.add(c);
        c.setRepository(repository);

        return created;
    }

    @Override
    public Client getClient(String name) {

        Client foundClient = repository.getBy(name);

        if (foundClient != null) {

            return foundClient;
        }

        throw new ClientNotFoundException(name);
    }

    @Override
    public List<Client> getClients() {
        return repository.getAll();
    }

    @Override
    public void deleteClient(Client c) {
        repository.remove(c.getId());
    }

    @Override
    public AbstractAccount createAccount(Client c, Class<?> type) {
        AbstractAccount account = null;
        Client client = repository.get(c.getId());
        if (client != null) {
            account = new SavingAccount(0);
            if (type == CheckingAccount.class)
                account = new CheckingAccount(0);
            client.addAccount(account);
            repository.update(c);
        }
        return account;
    }

    @Override
    public void updateAccount(Client c, AbstractAccount account) {

        Client clientToUpdate = repository.get(c.getId());

        if (clientToUpdate != null) {

            clientToUpdate.removeAccount(account.getClass());
            clientToUpdate.addAccount(account);

            repository.update(c);
        }
    }

    @Override
    public AbstractAccount getAccount(Client c, Class<?> type) {
        return c.getAccounts().stream()
                   .filter(a -> a.getClass() == type)
                   .findFirst()
                   .orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public List<AbstractAccount> getAllAccounts() {

        return repository.getAll()
                   .stream()
                   .flatMap(c -> c.getAccounts().stream())
                   .collect(Collectors.toList());
    }

    @Override
    public List<AbstractAccount> getAllAccounts(Client c) {

        return new ArrayList<>(repository.get(c.getId()).getAccounts());
    }

    @Override
    public void transferMoney(Client from, Client to, double amount) {

        from.withdraw(amount);
        to.deposit(amount);
    }

    /**
     * Method that creates a few clients and initializes them with sample values
     */
    @PostConstruct
    private void init() {

        Arrays.stream("Jonny Bravo:MALE:1000:1000, Adam Budzinsky:MALE:1500, Anna Smith:FEMALE".split(", "))
            .map(spec -> spec.split(":"))
            .map(spec -> {

                val client = new Client(spec[0], valueOf(spec[1]));

                if (spec.length > 2) {
                    client.addAccount(
                        new SavingAccount(
                            Integer.parseInt(spec[2])));
                    if (spec.length > 3) {
                        client.addAccount(new CheckingAccount(
                            Integer.parseInt(spec[3])
                        ));
                    }
                }
                return client;
            })
            .forEach(this::addClient);
    }

}
