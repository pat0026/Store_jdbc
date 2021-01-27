package Store;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Scanner;

import static Store.Function.*;
import static Store.Function.clearAndPrintCode;

public class CustomerStatusList {
    private static ArrayList<String> listOfCustomer = new ArrayList<>();

    private CustomerStatusList() {
    }

    private static final Scanner sc = new Scanner(System.in);
    public static void addCustomer(){
        if(!Menu.getListOfMenu().isEmpty()) {
            clear();
            System.out.print("Customers name: ");
            String name = sc.nextLine();
            try{
                ConnectToDB.registerCustomer(name);
                listOfCustomer.add(name);
            }catch (SQLIntegrityConstraintViolationException e){
                System.out.println("Already registered in database. Customer Invalid");
            }
            Order.whatIsYourOrder(name);
            while (true) {
                System.out.println("Additional Order? (y/n)");
                String ans = sc.nextLine();
                if (ans.toLowerCase().equals("y")) {
                    Order.whatIsYourOrder(name);
                } else if (ans.toLowerCase().equals("n")) {
                    clearAndPrintCode("Customer: " + name +
                            "\nTo be paid: " + ConnectToDB.getBalance(name));
                    break;
                } else {
                    clearAndPrintCode("Invalid input. Please try again");
                }
            }
        }else clearAndPrintCode("There is no Menu. Please make Menu first");
    }

    public static void removeCustomer(){
        if(!listOfCustomer.isEmpty()){
            ConnectToDB.showCustomerList();
            System.out.print("Which customer to remove: ");
            int rmCustomer = (int) validateNumber() - 1;
            if (!(listOfCustomer.size() - 1 < rmCustomer || rmCustomer < 0)){
                System.out.println("Removing customer: " + listOfCustomer.get(rmCustomer));
                System.out.print("Balance is still " + ConnectToDB.getBalance(listOfCustomer.get(rmCustomer)) +
                        "\nWould you like to proceed? (y/n): ");
                String answer = sc.nextLine();
                if (answer.equalsIgnoreCase("y")) {
                    ConnectToDB.removeCustomer(listOfCustomer.get(rmCustomer));
                    listOfCustomer.remove(rmCustomer);
                    clearAndPrintCode("Remove Succesful");
                } else if (!answer.equalsIgnoreCase("n")) {
                    clearAndPrintCode("Invalid Input");
                }
            } else clearAndPrintCode("No customer with that number");
        }else clearAndPrintCode("No customers yet.");
    }



    public static void removeCustomer(String customer){
        ConnectToDB.removeCustomer(customer);
        listOfCustomer.remove(customer);
        System.out.println("Removed Successfully");
    }


    public static ArrayList<String> getListOfCustomer() {
        return listOfCustomer;
    }

    public static void setListOfCustomer(ArrayList<String> listOfCustomer) {
        CustomerStatusList.listOfCustomer = listOfCustomer;
    }

    public static void customerListOptions(){
        clear();
        System.out.println("""
                CUSTOMER Options:
                \t\t1. Show Customer List
                \t\t2. Add Customer
                \t\t3. Remove Customer
                \t\t4. Go back
                """);
        int response = (int) validateNumber();
        switch (response) {
            case 1:
                ConnectToDB.showCustomerList();
                pressEnter();
                clear();
                break;
            case 2:
                addCustomer();
                break;
            case 3:
                removeCustomer();
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid Input");
                customerListOptions();
        }
    }


}
