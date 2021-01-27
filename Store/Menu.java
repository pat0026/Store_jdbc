package Store;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Scanner;
import static Store.Function.*;

public class Menu {
    private static final ArrayList<String> listOfMenu = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    private Menu() {
    }

    public static ArrayList<String> getListOfMenu() {
        return listOfMenu;
    }

    public static void addFood(){
        clear();
        System.out.print("Food: ");
        String food = sc.nextLine();
        System.out.print("Price: ");
        Double price = validateNumber();
        try{
            ConnectToDB.registerFood(food,price);
            listOfMenu.add(food);
        }catch (SQLIntegrityConstraintViolationException ue){
            System.out.println("Already registered in database. Modification invalid");
        }
        ConnectToDB.showMenu();
    }

    public static void removeFood(){
        ConnectToDB.showMenu();
        System.out.print("Food to remove: ");
        int food = (int)validateNumber() - 1;
        if(listOfMenu.contains(listOfMenu.get(food))){
            ConnectToDB.removeFood(listOfMenu.get(food));
            listOfMenu.remove(food);
            System.out.println("Remove Successful");
        }else System.out.println("Food not found");
    }

    public static void editMenu(){
        clear();
        System.out.println("""
                EDIT MENU:
                \t\t1. ShowMenu
                \t\t2. Add food
                \t\t3. Delete food
                \t\t4. Go back
                """);
        int ans = (int) validateNumber();
        switch (ans){
            case 1:
                ConnectToDB.showMenu();
                pressEnter();
                clear();
                break;
            case 2:
                addFood();
                pressEnter();
                clear();
                break;
            case 3:
                removeFood();
                pressEnter();
                clear();
                break;
            case 4:
                break;
            default:
                System.out.println("Number Incorrect!");
                editMenu();
        }
    }
}

