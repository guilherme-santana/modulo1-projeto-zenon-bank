package br.com.zenon.fraud;

public class BankService {

    public void validateAge(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("Menor de idade!");
        }
    }

    public void withdraw(double balance, double amount) {
        if (amount > balance) {
            throw new NegativeBalanceException("Saldo insuficiente");
        }
    }


}
