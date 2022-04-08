package ru.vlapin.trainings.springluxoft;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.vlapin.trainings.springluxoft.model.AbstractAccount;
import ru.vlapin.trainings.springluxoft.model.CheckingAccount;
import ru.vlapin.trainings.springluxoft.model.Client;
import ru.vlapin.trainings.springluxoft.model.SavingAccount;
import ru.vlapin.trainings.springluxoft.service.Banking;
import ru.vlapin.trainings.springluxoft.service.storage.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@SpringJUnitConfig(locations = "classpath:application-context.xml")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BankApplicationTask1Tests {

    private static final String[] CLIENT_NAMES =
        {"Jonny Bravo", "Adam Budzinsky", "Anna Smith"};

    Banking banking;

    ClientRepository repository;

    @Test
    void repositoryBeanConfiguration() {
        assertNotNull(repository, "repository bean should be configured");
    }

    @Test
    void bankingBeanConfiguration() {
        assertNotNull(banking, "banking bean should be configured");
    }

    @Test
    void initializationClient1() {
        //        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[0]);
        assertThat(banking.getClient(CLIENT_NAMES[0])).isNotNull()
            .extracting(Client::getAccounts)
            .asList()
            .hasSize(2);
    }

    @Test
    void client1SavingAccount() {
        assertThat(banking.getClient(CLIENT_NAMES[0])).isNotNull()
            .extracting(client -> client.getAccount(SavingAccount.class))
            .extracting(AbstractAccount::getBalance)
            .isEqualTo(1_000);

//        assertNotNull(account,
//                      String.format("%sshould have %s account",
//                                    client.getName(),
//                                    SavingAccount.class.getSimpleName()));
//
    }

    @Test
    void client1CheckingAccount() {
        assertThat(banking.getClient(CLIENT_NAMES[0])).isNotNull()
            .extracting(client -> client.getAccount(CheckingAccount.class))
            .extracting(AbstractAccount::getBalance, CheckingAccount::getOverdraft)
            .contains(0, 1_000);

//        assertNotNull(account,
//                      client.getName() + "should have "
//                          + CheckingAccount.class.getSimpleName() + " account");
    }

    @Test
    void initializationClient2() {
        assertThat(banking.getClient(CLIENT_NAMES[1])).isNotNull()
            .extracting(Client::getAccounts)
            .asList()
            .hasSize(1);
    }

    @Test
    void client2CheckingAccount() {
//        assertNotNull(account,
//                      client.getName() + "should have "
//                          + CheckingAccount.class.getSimpleName() + " account");

        assertThat(banking.getClient(CLIENT_NAMES[1])).isNotNull()
            .extracting(client -> client.getAccount(CheckingAccount.class))
            .extracting(AbstractAccount::getBalance, CheckingAccount::getOverdraft)
            .contains(0, 1_500);
    }

    @Test
    void workWithExistingClientsTest() {

        BankApplication.workWithExistingClients(banking);

        Client jonny = banking.getClient(CLIENT_NAMES[0]);
        assertEquals(4000, jonny.getActiveAccount().getBalance());

        Client adam = banking.getClient(CLIENT_NAMES[1]);
        assertEquals(-500, adam.getActiveAccount().getBalance());
    }

    @Test
    public void bankingServiceDemoTest() {
        BankApplication.bankingServiceDemo(banking);

        Client anna = banking.getClient(CLIENT_NAMES[2]);
        assertNotNull(anna, "banking should have client with name: " + CLIENT_NAMES[2]);

        AbstractAccount saving = anna.getAccount(SavingAccount.class);

        assertNotNull(saving, CLIENT_NAMES[2] + " should have "
                                  + SavingAccount.class.getSimpleName() + " account.");
        assertEquals(1000, saving.getBalance());


        AbstractAccount checking = anna.getAccount(CheckingAccount.class);

        assertNotNull(checking, CLIENT_NAMES[2] + " should have "
                                    + CheckingAccount.class.getSimpleName() + " account.");
        assertEquals(3000, checking.getBalance());
    }

}
