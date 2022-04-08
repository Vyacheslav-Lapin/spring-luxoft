package ru.vlapin.trainings.springluxoft.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import ru.vlapin.trainings.springluxoft.model.AbstractAccount;
import ru.vlapin.trainings.springluxoft.model.CheckingAccount;
import ru.vlapin.trainings.springluxoft.model.Client;
import ru.vlapin.trainings.springluxoft.service.storage.ClientRepository;

import org.springframework.stereotype.Component;

public interface BankReportService {

    int getNumberOfBankClients();

    int getAccountsNumber();

    @SuppressWarnings("unused") List<Client> getClientsSorted();

    double getBankCreditSum();

    Map<String, List<Client>> getClientsByCity();
}

@Component
@RequiredArgsConstructor
class BankReportServiceImpl implements BankReportService {

    ClientRepository repository;

    @Override
    public int getNumberOfBankClients() {
        return repository.getAll().size();
    }

    @Override
    public int getAccountsNumber() {
        return (int) repository.getAll()
                         .stream()
                         .mapToLong(c -> c.getAccounts().size()).sum();
    }

    @Override
    public List<Client> getClientsSorted() {
        return repository.getAll()
                   .stream()
                   .sorted(Comparator.comparing(Client::getName))
                   .collect(Collectors.toList());
    }

    @Override
    public double getBankCreditSum() {
        return repository.getAll().stream()
                   .flatMap(c -> c.getAccounts().stream())
                   .filter(a -> a.getClass() == CheckingAccount.class)
                   .mapToDouble(AbstractAccount::getBalance)
                   .filter(b -> b < 0)
                   .sum();
    }

    @Override
    public Map<String, List<Client>> getClientsByCity() {
        return repository.getAll().stream()
                   .collect(Collectors.groupingBy(Client::getCity));
    }
}
