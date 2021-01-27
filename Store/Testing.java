package Store;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args) throws SQLException {

        ConnectToDB.importFoodDB();
        ConnectToDB.importCustomerDB();
//        CustomerStatusList.addCustomer();
//        ConnectToDB.setBalance(30, "pat");
//        System.out.println(Menu.getListOfMenu().get(0));
        CustomerStatusList.removeCustomer("test2");
        for(String x : CustomerStatusList.getListOfCustomer()){
            System.out.println(x);
        }
    }
}
