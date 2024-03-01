package cesur.accesodatos.ORM;

public interface ConnectionInterface {
    public boolean connectDB();
    public void closeConnection();
}