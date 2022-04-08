package ru.vlapin.trainings.springluxoft;

import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.BankingImpl;
import com.luxoft.bankapp.service.storage.ClientRepository;
import com.luxoft.bankapp.service.storage.MapClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(locations = {"classpath:application-context.xml", "classpath:test-clients.xml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BankApplicationTask3Tests {
    private static final String[] CLIENT_NAMES =
            {"Jonny Bravo", "Adam Budzinski", "Anna Smith"};

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Banking banking;

    @Autowired
    private ClientRepository repository;

    @Autowired
    @Qualifier("savingAccount1")
    private SavingAccount savingAccount1;

    @Autowired
    @Qualifier("checkingAccount1")
    private CheckingAccount checkingAccount1;

    @Autowired
    @Qualifier("client1")
    private Client client1;

    @Autowired
    @Qualifier("checkingAccount2")
    private CheckingAccount checkingAccount2;

    @Autowired
    @Qualifier("client2")
    private Client client2;

    @Autowired
    private PropertySourcesPlaceholderConfigurer placeholderConfigurer;

    @BeforeEach
    public void init() {

        try {
            BankApplication.class.getMethod("initialize", ApplicationContext.class).invoke(null, applicationContext);
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }

        // TODO you can replace code above with this when will have the method
//        BankApplication.initialize(applicationContext);
    }

    @Test
    public void placeholderConfigurerBeanConfiguration() {
        assertNotNull(placeholderConfigurer, "placeholderConfigurer bean should be configured");

        PropertySource<?> localProperties = placeholderConfigurer.getAppliedPropertySources().get("localProperties");
        assertNotNull(localProperties, "You should configure PropertySourcesPlaceholderConfigurer bean");

        assertEquals("Jonny Bravo", localProperties.getProperty("client1"));
        assertEquals("Adam Budzinski", localProperties.getProperty("client2"));
    }

    @Test
    public void repositoryBeanConfiguration() {
        assertNotNull(repository, "repository bean should be configured");
        assertTrue((repository instanceof MapClientRepository),
                "repository should be instantiated with MapClientRepository class");
    }

    @Test
    public void bankingBeanConfiguration() {
        assertNotNull(banking, "banking bean should be configured");
        assertTrue((banking instanceof BankingImpl), "storage should be instantiated with BankingImpl class");
    }

    @Test
    public void savingAccount1BeanConfiguration() {
        assertNotNull(savingAccount1, "savingAccount1 bean should be configured");
        assertTrue((savingAccount1 instanceof SavingAccount), "savingAccount1 should be instantiated with SavingAccount class");
    }

    @Test
    public void checkingAccount1BeanConfiguration() {
        assertNotNull(checkingAccount1, "checkingAccount1 bean should be configured");
        assertTrue((checkingAccount1 instanceof CheckingAccount), "checkingAccount1 should be instantiated with CheckingAccount class");
    }

    @Test
    public void client1Configuration() {
        assertNotNull(client1, "client1 bean should be configured");
        assertTrue((client1 instanceof Client), "client1 should be instantiated with Client class");

        assertSame(savingAccount1, client1.getAccount(SavingAccount.class));
        assertSame(checkingAccount1, client1.getAccount(CheckingAccount.class));

        assertEquals(Client.Gender.MALE, client1.getGender());
        assertEquals("Moscow", client1.getCity());
    }

    @Test
    public void checkingAccount2BeanConfiguration() {
        assertNotNull(checkingAccount2, "checkingAccount2 bean should be configured");
        assertTrue((checkingAccount2 instanceof CheckingAccount), "checkingAccount2 should be instantiated with CheckingAccount class");
    }

    @Test
    public void client2Configuration() {
        assertNotNull(client2, "client2 bean should be configured");
        assertTrue((client2 instanceof Client), "client2 should be instantiated with Client class");

        assertSame(checkingAccount2, client2.getAccount(CheckingAccount.class));

        assertEquals(Client.Gender.MALE, client2.getGender());
        assertEquals("Kiev", client2.getCity());
    }

    @Test
    public void initializationClient1() {
        Client client = banking.getClient(CLIENT_NAMES[0]);
        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[0]);

        assertEquals(2, client.getAccounts().size());
    }

    @Test
    public void client1SavingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        AbstractAccount account = client.getAccount(SavingAccount.class);

        assertNotNull(account,
                client.getName() + "should have "
                        + SavingAccount.class.getSimpleName() + " account");

        assertEquals(1000, account.getBalance());
    }

    @Test
    public void client1CheckingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        CheckingAccount account = (CheckingAccount) client.getAccount(CheckingAccount.class);

        assertNotNull(account,
                client.getName() + "should have "
                        + CheckingAccount.class.getSimpleName() + " account");

        assertEquals(0, account.getBalance());
        assertEquals(1000, account.getOverdraft());
    }

    @Test
    public void initializationClient2() {
        Client client = banking.getClient(CLIENT_NAMES[1]);
        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[1]);

        assertEquals(1, client.getAccounts().size());
    }

    @Test
    public void client2CheckingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[1]);

        CheckingAccount account = (CheckingAccount) client.getAccount(CheckingAccount.class);

        assertNotNull(account,
                client.getName() + "should have "
                        + CheckingAccount.class.getSimpleName() + " account");

        assertEquals(0, account.getBalance());
        assertEquals(1500, account.getOverdraft());
    }

    @Test
    public void workWithExistingClientsTest() {
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
