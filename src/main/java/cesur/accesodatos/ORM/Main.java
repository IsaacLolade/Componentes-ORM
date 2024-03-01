package cesur.accesodatos.ORM;

public class Main {
    public static void main(String[] args) {
        ORMDAO test = new ORMDAO();
        if (test.connectDB()) test.executeMenu();
    }
}
