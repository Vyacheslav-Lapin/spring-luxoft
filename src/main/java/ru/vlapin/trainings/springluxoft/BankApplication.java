package ru.vlapin.trainings.springluxoft;

import java.util.List;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.annotation.Aspect;
import ru.vlapin.trainings.springluxoft.exceptions.ActiveAccountNotSet;
import ru.vlapin.trainings.springluxoft.model.AbstractAccount;
import ru.vlapin.trainings.springluxoft.model.CheckingAccount;
import ru.vlapin.trainings.springluxoft.model.Client;
import ru.vlapin.trainings.springluxoft.model.SavingAccount;
import ru.vlapin.trainings.springluxoft.service.BankReportService;
import ru.vlapin.trainings.springluxoft.service.BankReportServiceImpl;
import ru.vlapin.trainings.springluxoft.service.Banking;
import ru.vlapin.trainings.springluxoft.service.storage.ClientRepository;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.annotation.Order;

@Slf4j
@EnableFeignClients
@SpringBootApplication
@RequiredArgsConstructor
//@RequiredArgsConstructor
@ConfigurationPropertiesScan
//@EnableHypermediaSupport(type = HAL)
@ComponentScan(includeFilters = @Filter(Aspect.class))
public class BankApplication {

  public static void main(String[] args) {

    @Cleanup val context =
        SpringApplication.run(BankApplication.class, args);

    bankingServiceDemo(banking);

//        bankReportsDemo(repository);
  }

  public static void bankReportsDemo(ClientRepository repository) {

    BankReportService reportService = new BankReportServiceImpl();
    reportService.setRepository(repository);

    System.out.println("Number of clients: " + reportService.getNumberOfBankClients());

    System.out.println("Number of accounts: " + reportService.getAccountsNumber());

    System.out.println("Bank Credit Sum: " + reportService.getBankCreditSum());
  }

  @Bean
  ApplicationRunner bankingServiceDemo(List<Client> clients, Banking banking) {
    return __ -> {
      var anna = clients.get(2);
      anna = banking.addClient(anna);

      val saving = banking.createAccount(anna, SavingAccount.class);
      saving.deposit(1_000);

      banking.updateAccount(anna, saving);

      AbstractAccount checking = banking.createAccount(anna, CheckingAccount.class);
      checking.deposit(3_000);

      banking.updateAccount(anna, checking);
      banking.getAllAccounts(anna).forEach(annaAccount -> log.info("annaAccount = {}", annaAccount));
    };
  }

  @Bean
  @Order(1)
  ApplicationRunner workWithJohnyClient(List<Client> clients) {
    return __ -> {
      Client jonny = clients.get(0);

      try {
        jonny.deposit(5_000);
      } catch (ActiveAccountNotSet e) {
        log.info("exception = ", e);

        jonny.setDefaultActiveAccountIfNotSet();
        jonny.deposit(5_000);
      }

      log.info("jonny = {}", jonny);
    };
  }

  @Bean
  @Order(2)
  ApplicationRunner workingWithAdamClient(List<Client> clients) {
    return __ -> {
      Client adam = clients.get(1);
      adam.setDefaultActiveAccountIfNotSet();
      adam.withdraw(1_500);
      double balance = adam.getBalance();
      log.info("{}, current balance: {}", adam.getName(), balance);
    };
  }

  @Bean
  @Order(3)
  ApplicationRunner workWithExistingClients(List<Client> clients, Banking banking) {
    return __ -> {
      banking.transferMoney(clients.get(0), clients.get(1), 1_000);
      banking.getClients().forEach(client -> log.info("client: {}", client));
    };
  }
}
