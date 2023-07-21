import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RentCarService db = new RentCarService();
        Connection connection = db.connection_psql("asd", "postgres", "123");
        db.setcars(connection);
        Scanner sc = new Scanner(System.in);
        System.out.println("Write your name first, then start date, then end date, then car number that you want to book:");
        String name = sc.nextLine();
        String start_date = sc.nextLine();
        String end_date = sc.nextLine();
        String a = sc.nextLine();
        try{
            Car car = db.findCarByNumber(connection, a);
            db.bookCar(connection, car, name, start_date, end_date);
            db.printOrder(car, name, start_date, end_date);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
