package br.com.zenon.fraud;

public class ExceptionMain {

    static void main() {
        BankService bankService = new BankService();

        try {
            bankService.validateAge(17);
        } catch (InvalidAgeException e) {
            throw new RuntimeException(e);
        }

        bankService.withdraw(1800, 1800);

    }

}
