package Store;

import static Store.Function.*;

public class Store {
    public static void main(String[] args) {
        clear();
        boolean open = true;
        int response;
        ConnectToDB.importFoodDB();
        ConnectToDB.importCustomerDB();
        while (open) {
            System.out.println("""
                    Welcome to our Store!
                    \t\t1. Edit Menu
                    \t\t2. Customer Options
                    \t\t3. Additional Orders
                    \t\t4. Customer payment
                    \t\t5. Total Revenue
                    \t\t6. Quit""");
            response = (int) validateNumber();
            switch (response) {
                case 1:
                    Menu.editMenu();
                    clear();
                    break;
                case 2:
                    CustomerStatusList.customerListOptions();
                    break;
                case 3:
                    Order.additionalOrder();
                    break;
                case 4:
                    Transaction.customerPay();
                    break;
                case 5:
                    clearAndPrintCode("Total money for today: " + Cashier.getTotalMoney());
                    break;
                case 6:
                    clearAndPrintCode("Good Job! Let's rest for the day");
                    open = false;
                    break;
            }
        }
    }
}