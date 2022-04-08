package ru.vlapin.trainings.springluxoft;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.vlapin.trainings.springluxoft.model.AbstractAccount;
import ru.vlapin.trainings.springluxoft.model.CheckingAccount;
import ru.vlapin.trainings.springluxoft.model.Client;
import ru.vlapin.trainings.springluxoft.model.SavingAccount;
import ru.vlapin.trainings.springluxoft.service.BankReportService;
import ru.vlapin.trainings.springluxoft.service.Banking;
import ru.vlapin.trainings.springluxoft.service.BankingImpl;
import ru.vlapin.trainings.springluxoft.service.storage.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@SpringJUnitConfig(locations = "classpath:application-context.xml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BankApplicationTask2Tests {

    private static final String[] CLIENT_NAMES =
        {"Jonny Bravo", "Adam Budzinski", "Anna Smith"};

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    Banking banking;

    @Autowired
    ClientRepository repository;

    @Autowired
    BankReportService bankReport;

    @Test
    void repositoryBeanConfiguration() {
        assertNotNull(repository, "repository bean should be configured");
    }

    @Test
    void bankingBeanConfiguration() {
        assertNotNull(banking, "banking bean should be configured");
        assertTrue((banking instanceof BankingImpl), "storage should be instantiated with BankingImpl class");
    }

    @Test
    void bankReportConfiguration() {
        assertNotNull(bankReport, "bankReport bean should be configured");
    }

    @Test
    void initializationClient1() {
        Client client = banking.getClient(CLIENT_NAMES[0]);
        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[0]);

        assertEquals(2, client.getAccounts().size());
    }

    @Test
    void client1SavingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        AbstractAccount account = client.getAccount(SavingAccount.class);

        assertNotNull(account,
                      client.getName() + "should have "
                          + SavingAccount.class.getSimpleName() + " account");

        assertEquals(1000, account.getBalance());
    }

    @Test
    void client1CheckingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        CheckingAccount account = client.getAccount(CheckingAccount.class);

        assertNotNull(account,
                      client.getName() + "should have "
                          + CheckingAccount.class.getSimpleName() + " account");

        assertEquals(0, account.getBalance());
        assertEquals(1000, account.getOverdraft());
    }

    @Test
    void initializationClient2() {
        Client client = banking.getClient(CLIENT_NAMES[1]);
        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[1]);

        assertEquals(1, client.getAccounts().size());
    }

    @Test
    void client2CheckingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[1]);

        CheckingAccount account = (CheckingAccount) client.getAccount(CheckingAccount.class);

        assertNotNull(account,
                      client.getName() + "should have "
                          + CheckingAccount.class.getSimpleName() + " account");

        assertEquals(0, account.getBalance());
        assertEquals(1500, account.getOverdraft());
    }

    @Test
    void getNumberOfBankClients() {
        assertEquals(2, bankReport.getNumberOfBankClients());

//        BankApplication.workWithExistingClients(banking);
//        BankApplication.bankingServiceDemo(banking);

        assertEquals(3, bankReport.getNumberOfBankClients());
    }

    @Test
    void getAccountsNumber() {
        assertEquals(3, bankReport.getAccountsNumber());

//        BankApplication.workWithExistingClients(banking);
//        BankApplication.bankingServiceDemo(banking);

        assertEquals(5, bankReport.getAccountsNumber());
    }

    @Test
    void getBankCreditSum() {
        assertEquals(0, bankReport.getBankCreditSum());

//        BankApplication.workWithExistingClients(banking);
//        BankApplication.bankingServiceDemo(banking);

        assertEquals(-500, bankReport.getBankCreditSum());

    }

}
