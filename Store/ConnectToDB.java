package Store;

import java.sql.*;
import static Store.Function.*;

public class ConnectToDB {
    private static Connection con = null;
    private static PreparedStatement prpStmt = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static Connection getCon(){
       try {
           con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/store","root", "");
       }catch (SQLException se){
           se.printStackTrace();
           System.out.println("Can't connect to db");
       }
       return con;
   }

   public static <V extends Number> void registerFood(String food, V price) throws SQLIntegrityConstraintViolationException {
        con = getCon();
       try {
           prpStmt = con.prepareStatement("INSERT INTO food (name, price) VALUES (?, ?);");
           prpStmt.setString(1, food);
           prpStmt.setDouble(2, price.doubleValue());
           prpStmt.executeUpdate();

       }catch (SQLIntegrityConstraintViolationException ue){
           throw new SQLIntegrityConstraintViolationException();
       } catch (SQLException se){
           System.out.println("Already registered in database. Please clear previous data.");
       }finally {
           closeAll(prpStmt,con);
       }
   }



   public static void removeFood(String food){
        con = getCon();
       try {
           prpStmt = con.prepareStatement("DELETE FROM food WHERE name = ?;");
           prpStmt.setString(1, food);
           prpStmt.executeUpdate();

       }catch (SQLException se){
           se.printStackTrace();
           System.out.println("Can't connect to db");
       }finally {
            closeAll(prpStmt,con);
       }
   }

    public static void importFoodDB(){
        try{
            Connection con = ConnectToDB.getCon();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select name FROM food ORDER BY idfood;");

            while(rs.next()){
                Menu.getListOfMenu().add(rs.getString("name"));
            }
        }catch (SQLException se){
            se.printStackTrace();
            System.out.println("Import unsuccessful");
        }finally {
            closeAll(rs,stmt,con);
        }
    }

   public static ResultSet getMenu(){
        con = getCon();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT name, price FROM food");
        }catch (SQLException se){
            se.printStackTrace();
            System.out.println("Can't connect to db");
        }
        return rs;
   }

   public static double getPrice(String food){
       ResultSet rs = getMenu();
       double price = 0;
       String x = "";
        try {
            while (!x.equals(food)&&rs.next()){
                x = rs.getString("name");
                price = rs.getDouble("price");
            }
        }catch (SQLException se){
            se.printStackTrace();
            System.out.println("Can't connect to db");
        }finally {
            closeAll(rs,stmt,con);
        }
        if (!x.equals(food)){
            return 0;
        }
        return price;
   }

   public static void showMenu(){
        clear();
        rs = ConnectToDB.getMenu();
        int count = 1;
       try {
           while (rs.next()){
               String name = rs.getString("name");
               Double price = rs.getDouble("price");
               System.out.printf("%d. Food: %-20sPrice: %-10.2f\n", count,name,price );
               count++;
           }
       }catch (SQLException se){
           se.printStackTrace();
           System.out.println("Can't connect to db");
       }finally {
           closeAll(rs,stmt,con);
       }

   }

    public static void registerCustomer(String name) throws SQLIntegrityConstraintViolationException {
        con = getCon();
        try {
            prpStmt = con.prepareStatement("INSERT INTO customer (name, balance) VALUES (?, ?);");
            prpStmt.setString(1, name);
            prpStmt.setDouble(2, 0);
            prpStmt.executeUpdate();

        }catch (SQLIntegrityConstraintViolationException ue){
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException se){
            System.out.println("Can't connect to db");
        }finally {
            closeAll(prpStmt,con);
        }
    }

    public static void removeCustomer(String name){
        con = getCon();
        try {
            prpStmt = con.prepareStatement("DELETE FROM customer WHERE name = ?;");
            prpStmt.setString(1, name);
            prpStmt.executeUpdate();

        }catch (SQLException se){
            System.out.println("Can't connect to db");
        }finally {
            closeAll(prpStmt,con);
        }
    }

    public static void importCustomerDB(){
        try{
            Connection con = ConnectToDB.getCon();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select name FROM customer ORDER BY idcustomer;");

            while(rs.next()){
                CustomerStatusList.getListOfCustomer().add(rs.getString("name"));
            }
        }catch (SQLException se){
            System.out.println("Import unsuccessful");
        }finally {
            closeAll(rs,stmt,con);
        }
    }

    public static void setBalance(double balance, String name){
        con = getCon();
        try {
            prpStmt = con.prepareStatement("UPDATE customer SET balance = ? WHERE name = ?;");
            prpStmt.setDouble(1, balance);
            prpStmt.setString(2, name);
            prpStmt.executeUpdate();
            System.out.println("Update succesful");
        }catch (SQLException se){
            System.out.println("Can't connect to db");
        }finally {
            closeAll(prpStmt,con);
        }
    }

    public static double getBalance(String name){
        con = getCon();
        double balance = 0.0;
        try {
            prpStmt = con.prepareStatement("SELECT balance FROM customer WHERE name = ?;");
            prpStmt.setString(1, name);
            rs = prpStmt.executeQuery();
            rs.next();
            balance = rs.getDouble("balance");
        }catch (SQLException se){
            System.out.println("Can't connect to db");
        }finally {
            closeAll(prpStmt,con);
        }
        return balance;
    }

    public static void showCustomerList(){
        clear();
        con = getCon();
        int count = 1;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT name, balance FROM customer");
            while (rs.next()){
                String name = rs.getString("name");
                Double balance = rs.getDouble("balance");
                System.out.printf("%d. Customer: %-10sBalance: %7.2f\n", count, name, balance);
                count++;
            }
        }catch (SQLException se){
            System.out.println("Can't connect to db");
        }finally {
            closeAll(rs,stmt,con);
        }
    }

   public static void closeAll(ResultSet rs, PreparedStatement prpStmt, Connection con){
       try{
           if (rs != null) {
               rs.close();
           }

           if (prpStmt != null) {
               prpStmt.close();
           }

           if (con != null) {
               con.close();
           }
       }catch (SQLException sqle){
           sqle.printStackTrace();
       }
   }

    public static void closeAll(PreparedStatement prpStmt, Connection con){
        try{
            if (prpStmt != null) {
                prpStmt.close();
            }

            if (con != null) {
                con.close();
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public static void closeAll(ResultSet rs, Statement stmt, Connection con){
        try{
            if (rs != null) {
                rs.close();
            }

            if (stmt != null) {
                stmt.close();
            }

            if (con != null) {
                con.close();
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public static void closeAll(Statement stmt, Connection con){
        try{
            if (stmt != null) {
                stmt.close();
            }

            if (con != null) {
                con.close();
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
}
