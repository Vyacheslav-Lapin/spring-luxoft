package ru.vlapin.trainings.springluxoft.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.vlapin.trainings.springluxoft.exceptions.AccountNumberLimitException;
import ru.vlapin.trainings.springluxoft.exceptions.ActiveAccountNotSet;
import ru.vlapin.trainings.springluxoft.service.storage.ClientRepository;

@Data
@Slf4j
@NoArgsConstructor
@RequiredArgsConstructor
public class Client {
    long id;
    @NonNull
    String name;
    List<AbstractAccount> accounts = new ArrayList<>(2);
    AbstractAccount activeAccount;
    @NonNull
    Gender gender;
    String city;
    ClientRepository repository;

    public synchronized double getBalance() {
        if (!checkIfActiveAccountSet())
            throw new ActiveAccountNotSet(name);
        return activeAccount.getBalance();
    }

    public synchronized void deposit(double amount) {

        if (!checkIfActiveAccountSet())
            throw new ActiveAccountNotSet(name);

        activeAccount.deposit(amount);
        repository.update(this);
    }

    public synchronized void withdraw(double amount) {

        if (!checkIfActiveAccountSet())
            throw new ActiveAccountNotSet(name);

        activeAccount.withdraw(amount);
        repository.update(this);
    }

    private boolean checkIfActiveAccountSet() {
        return activeAccount != null;
    }

    public void removeAccount(Class<?> type) {

        accounts = accounts.stream()
                .filter(a -> a.getClass() != type)
                .collect(Collectors.toList());

        repository.update(this);
    }

    public <T extends AbstractAccount> T getAccount(Class<T> type) {
        for (AbstractAccount account : accounts)
            if (account.getClass().equals(type))
                //noinspection unchecked
                return (T) account;
        return null;
    }

    public void addAccount(AbstractAccount account) throws AccountNumberLimitException {

        if (accounts.size() >= 2) {
            throw new AccountNumberLimitException();
        }

        if (account != null) {
            accounts.add(account);
        }
    }

    public void setDefaultActiveAccountIfNotSet() {

        if (activeAccount == null && accounts != null && !accounts.isEmpty()) {

            AbstractAccount account = getAccount(CheckingAccount.class);

            if (account == null)
                account = accounts.iterator().next();

            activeAccount = account;

            repository.update(this);

            log.info("Default account set for {}: {}", name, activeAccount.getClass().getSimpleName());
        }
    }

    private StringBuilder getSimpleInfoBuilder() {
        StringBuilder builder = new StringBuilder();

        builder.append("\nClient: ")
                .append(name)
                .append("\nGender: ")
                .append(getGender());

        return builder;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Gender {

        MALE("Mr"),
        FEMALE("Ms"),
        UNDEFINED("");

        final String salutation;
    }

}
