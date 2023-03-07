import controller.MainController;
import db.Database;

public class Main {
    public static void main(String[] args) {
        Database.createTable();
        Database.createCardTable();
        Database.createTerminalTable();
        Database.createTransactionTable();
        MainController mainController=new MainController();
        mainController.start();
    }
}
