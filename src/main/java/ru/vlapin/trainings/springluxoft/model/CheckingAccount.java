package ru.vlapin.trainings.springluxoft.model;

import lombok.NoArgsConstructor;
import ru.vlapin.trainings.springluxoft.exceptions.OverDraftLimitExceededException;

@NoArgsConstructor
public class CheckingAccount extends AbstractAccount {

    double overdraft = 0;

    public CheckingAccount(double overdraft) {
        setOverdraft(overdraft);
    }

    public void setOverdraft(double amount) {
        if (overdraft >= 0)
            overdraft = amount;
    }

    public double getOverdraft() {
        return overdraft;
    }

    @Override
    public void withdraw(double amount) throws OverDraftLimitExceededException {
        if (getBalance() + overdraft < amount) {
            throw new OverDraftLimitExceededException(
                this.getClass().getSimpleName(), getBalance() + overdraft);
        }
        setBalance(getBalance() - amount);
    }
}
