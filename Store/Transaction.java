package Store;


import java.util.Scanner;
import static Store.Function.*;

public class Transaction {
    public static void pay(String cstmr) {
        Scanner sc = new Scanner(System.in);
        double payment;
        double change;
        System.out.println("Balance: " + ConnectToDB.getBalance(cstmr));
        System.out.print("Input Payment: ");
        while(!sc.hasNextDouble()) {
            System.out.println("Error! Must be a number");
            System.out.print("Input Payment: ");
            sc.next();
            }
        payment = sc.nextDouble();
        clear();
        Cashier.setTotalMoney(Cashier.getTotalMoney()+ payment);
        double totalBalance = ConnectToDB.getBalance(cstmr) - payment;
        if(totalBalance>0){
            ConnectToDB.setBalance(totalBalance,cstmr);
            System.out.println("Remaining Balance is Php" + totalBalance);
        }else if(totalBalance<0){
            Cashier.setTotalMoney(Cashier.getTotalMoney() + totalBalance);
            change = Math.abs(totalBalance);
            CustomerStatusList.removeCustomer(cstmr);
            System.out.printf("Change should be Php%.2f\n", change);
        }else {
            CustomerStatusList.removeCustomer(cstmr);
            ConnectToDB.removeCustomer(cstmr);
            System.out.println("Paid exact");
        }
    }

    public static void customerPay() {
        Scanner sc = new Scanner(System.in);
        if (!CustomerStatusList.getListOfCustomer().isEmpty()) {
            ConnectToDB.showCustomerList();
            System.out.print("Select customer that is paying: ");
            int payingCustomer = (int) validateNumber() - 1;
            if (!(CustomerStatusList.getListOfCustomer().size() - 1 < payingCustomer || payingCustomer < 0)) {
                System.out.println("Paying customer is: " + CustomerStatusList.getListOfCustomer().get(payingCustomer));
                System.out.print("Would you like to proceed? (y/n): ");
                String answer = sc.nextLine();
                if (answer.equalsIgnoreCase("y")) {
                    clear();
                    pay(CustomerStatusList.getListOfCustomer().get(payingCustomer));
                    pressEnter();
                    clear();
                } else if (answer.equalsIgnoreCase("n")) {
                    System.out.print("Go back to Menu?(y/n): ");
                    answer = sc.nextLine();
                    if (answer.equalsIgnoreCase("y")) {
                        clear();
                    } else if (answer.equalsIgnoreCase("n")) {
                        customerPay();
                    } else clearAndPrintCode("Invalid Input");
                } else clearAndPrintCode("No customer with that number");
            } else clearAndPrintCode("Invalid Input");
        }else clearAndPrintCode("There is no customer yet");
    }
}

