package Store;

import java.util.Scanner;
import static Store.Function.*;

public class Order {
    public static void whatIsYourOrder(String customer) {
        int customersOrder;
        do {
            ConnectToDB.showMenu();
            System.out.println("Customer: " + customer);
            System.out.print("What is customers order: ");
            customersOrder = (int) validateNumber();
            if (Menu.getListOfMenu().size() < customersOrder || customersOrder <= 0) {
                System.out.println("Error! There is no food with that number");
                pressEnter();
            } else {
                break;
            }
        } while (true);
        double curBal = ConnectToDB.getBalance(customer) + ConnectToDB.getPrice(Menu.getListOfMenu().get(customersOrder-1));
        clear();
        ConnectToDB.setBalance(curBal,customer);
        System.out.println("Customer: " + customer +
                "\nTo be paid: " + ConnectToDB.getBalance(customer) );
    }

    public static void additionalOrder() {
        Scanner sc = new Scanner(System.in);
        String ans ="";
        if (!CustomerStatusList.getListOfCustomer().isEmpty()) {
            ConnectToDB.showCustomerList();
            boolean go = true;
            while(go) {
                System.out.print("Please enter customer's name(q to quit): ");
                ans = sc.nextLine();
                for (String x : CustomerStatusList.getListOfCustomer()) {
                    if (x.equals(ans)) {
                        whatIsYourOrder(x);
                        go = false;
                        break;
                    } else if (ans.equalsIgnoreCase("q")) {
                        go = false;
                        break;
                    }
                }
                if(go) System.out.println("Invalid input");
            }
            while (true) {
                System.out.println("Additional Order? (y/n)");
                String ans1 = sc.nextLine();
                if (ans1.toLowerCase().equals("y")) {
                    Order.whatIsYourOrder(ans);
                } else if (ans1.toLowerCase().equals("n")) {
                    clearAndPrintCode("Customer: " + ans +
                            "\nTo be paid: " + ConnectToDB.getBalance(ans));
                    break;
                } else {
                    clearAndPrintCode("Invalid input. Please try again");
                }
            }
        } else clearAndPrintCode("There are no Customers");
    }
}

