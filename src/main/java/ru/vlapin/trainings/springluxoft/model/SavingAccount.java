package ru.vlapin.trainings.springluxoft.model;

import lombok.NoArgsConstructor;
import ru.vlapin.trainings.springluxoft.exceptions.NotEnoughFundsException;

@NoArgsConstructor
public class SavingAccount extends AbstractAccount {

    public SavingAccount(double initialBalance) {
        if (initialBalance >= 0)
            setBalance(initialBalance);
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() < amount)
            throw new NotEnoughFundsException(amount);
        setBalance(getBalance() - amount);
    }
}
