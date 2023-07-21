import java.sql.*;
import java.text.DecimalFormat;

public class RentCarService {

    public Connection connection_psql(String dbname, String user, String pass)
    {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+ dbname,user,pass);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }


    // Other methods
    public void setcars(Connection connection){
        Statement statement;
        ResultSet res = null;
        try{
            String query=String.format("select * from %s", "cars");
            statement = connection.createStatement();
            res = statement.executeQuery(query);
            while(res.next()){
                Car car = new Car(res.getString("number"), res.getString("model"), res.getInt("years"), res.getString("gearbox"), res.getDouble("volume"));
                System.out.println("numbers: " + car.getNumber());
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    public Car findCarByNumber(Connection connection,String number) throws SQLException {
        String query = "SELECT * FROM cars WHERE number = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, number);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            String model = result.getString("model");
            int year = result.getInt("years");
            String gearbox = result.getString("gearbox");
            Float volume = result.getFloat("volume");
            return new Car(number, model, year, gearbox, volume);
        } else {
            return null;
        }
    }

    public void bookCar(Connection connection,Car car, String customerName, String startDate, String endDate) throws SQLException {
        String query = "INSERT INTO bookings (car_number, customer_name, start_date, end_date) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, car.getNumber());
        statement.setString(2, customerName);
        statement.setString(3, startDate);
        statement.setString(4, endDate);
        int rowsUpdated = statement.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Car successfully booked");
        } else {
            System.out.println("Unable to book car");
        }
    }

    public void printOrder(Car car, String customerName, String startDate, String endDate) {
        System.out.println("Order Details:");
        System.out.println("Car Number: " + car.getNumber());
        System.out.println("Model: " + car.getModel());
        System.out.println("Year: " + car.getYear());
        System.out.println("Gearbox: " + car.getGearbox());
        System.out.println("Volume:" + new DecimalFormat("##.##").format(car.getVolume()));
        System.out.println("Customer Name: " + customerName);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
    }

    public void sendMessage(String message) {
        System.out.println("Message sent: " + message);
    }

}
class Car {
    private String number;
    private String model;
    private int year;
    private String gearbox;
    private double volume;

    public Car(String number, String model, int year, String gearbox, double volume) {
        this.number = number;
        this.model = model;
        this.year = year;
        this.gearbox = gearbox;
        this.volume = volume;
    }

    public String getNumber() {
        return number;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getGearbox() {
        return gearbox;
    }

    public double getVolume() {
        return volume;
    }
}
