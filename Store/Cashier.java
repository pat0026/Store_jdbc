package Store;

public class Cashier {
    private static double totalMoney;

    private Cashier(){
    }

    public static double getTotalMoney() {
        return totalMoney;
    }

    public static void setTotalMoney(double totalMoney){
        Cashier.totalMoney = totalMoney;
    }
}
