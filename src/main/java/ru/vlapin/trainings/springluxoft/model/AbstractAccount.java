package ru.vlapin.trainings.springluxoft.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static lombok.AccessLevel.*;

@Data
@NoArgsConstructor
public abstract class AbstractAccount {

    @Setter long id;

    @Setter(PROTECTED) double balance;

    public void deposit(double amount) {
        if (amount >= 0)
            balance += amount;
    }

    public abstract void withdraw(double amount);

}
