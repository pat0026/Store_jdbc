package Store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Function {
    private static final Scanner sc = new Scanner(System.in);
    public static void clear() {
        try{
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }catch (Exception e){
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
    }

    public static void pressEnterContinue() {
        try {
            System.out.print("Press Enter to Continue:");
            BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
            String line = buf.readLine();
        }catch (IOException ioe){
            System.out.println("Something went wrong!");
            ioe.printStackTrace();
        }
    }

    public static void pressEnter() {
//        Scanner sc = new Scanner(System.in);
        System.out.println("Press Enter to Continue");
        sc.nextLine();
    }
    public static void clearAndPrintCode(String x) {
            clear();
            System.out.println(x);
            pressEnter();
            clear();
    }

    public static double validateNumber(){
//        Scanner sc = new Scanner(System.in);
        while(!(sc.hasNextInt()||sc.hasNextDouble())){
            System.out.print("Error! Numbers only: ");
            sc.next();
        }
        double x = sc.nextDouble();
        sc.nextLine();
        return x;
    }
}
