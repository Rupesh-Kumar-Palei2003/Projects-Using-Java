package HotelReservationSystem.com;

import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {
    private final static String url = "jdbc:mysql://localhost:3306/hotel_db";
    private final static String name = "root";
    private final static String password = "Rupesh@123";
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // load all the driver of jdbc;
        try{
            Class.forName("com.sql.jdbc.Driver");
        } catch (ClassNotFoundException e){
            System.out.println( e.getMessage());
        }
        // establish the connection...
        try{
            Connection connection = DriverManager.getConnection(url, name, password);
            System.out.println("Welcome to 🏨🏨🏨 Classical Hotel ....");
            while(true) {
                System.out.println();
                System.out.println("Classical Hotel Management System.....");
                Scanner scanner = new Scanner(System.in);
                System.out.println(" 1. New Reservation ");
                System.out.println(" 2. Check Reservation ");
                System.out.println(" 3. Get Room Number ");
                System.out.println(" 4. Update Reservation ");
                System.out.println(" 5. Delete Reservation ");
                System.out.println(" 6. Exit ");
                System.out.println("Enter any option you required......");
                int choice = scanner.nextInt();
                switch (choice){
                    case 1 : reserveRoom(connection, scanner); break;

                    case 2 : viewReservation(connection); break;

                    case 3 : getRoomNumber(connection, scanner); break;

                    case 4 : updateReservation(connection, scanner); break;

                    case 5 : deleteReservation(connection, scanner); break;

                    case 6 : exit();
                    break;

                    default :
                        System.out.println(" enter a valid option.... Try Again.");
                }
            }

        }catch (SQLException | InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    private static void reserveRoom(Connection connection, Scanner scanner){
        System.out.println("Enter Guest Name");
        String guestName = scanner.next();
        scanner.nextLine();
        System.out.println("Enter Room Number");
        int roomNumber = scanner.nextInt();
        System.out.println("Enter Contact Number");
        String contactNumber = scanner.next();
        String sqlQuery = "INSERT INTO reservation(guest_name, room_number, contact_number) " +
                "VALUES ('" + guestName + "', " + roomNumber + ", '" + contactNumber + "');";
        try(Statement statement = connection.createStatement()){
            int affectedRow = statement.executeUpdate(sqlQuery);
            if(affectedRow > 0){
                System.out.println(guestName+" reserved room no - "+roomNumber+" successfully.");
            }else{
                System.out.println("Sorry "+guestName+" your reservation failed.");
            }
        }catch (SQLException e){
            System.out.println( e.getMessage());
        }
    }
    private static void viewReservation(Connection connection){
        String sqlQuery = "SELECT * FROM reservation";
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            System.out.println("Current Reservations:");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            while(resultSet.next()){
                int reservationId = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();
                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

        }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    private static void getRoomNumber(Connection connection, Scanner scanner){
        System.out.println("Enter reservation ID : ");
        int reservationID = scanner.nextInt();
        System.out.println("Enter guest name : ");
        String guestName = scanner.next();
        scanner.nextLine();
        String sqlQuery = "SELECT room_number FROM reservation " +
                "WHERE reservation_id = " + reservationID +
                " AND guest_name = '" + guestName + "'";

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if(resultSet.next()){
                int roomNumber = resultSet.getInt("room_number");
                System.out.println("Reservation room for reservation id - "+reservationID+
                        "\n and guest name "+guestName+" is in room number -"+roomNumber);
            }
            else{
                System.out.println("reservation is not found for reservation id - "+reservationID
                +"and guest name "+guestName);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private static void updateReservation(Connection connection, Scanner scanner){
        System.out.println("Enter the reservation ID");
        int reservationID = scanner.nextInt();
        scanner.nextLine();
        if(reservationExists(connection, reservationID)){
            System.out.println("there is no reservation exists for ID - "+reservationID);
        }
        System.out.println("Enter new guest name : ");
        String newGuestName = scanner.nextLine();
        System.out.println("Enter new contact number");
        String newContactNumber = scanner.nextLine();
        System.out.println("Enter new room number");
        int newRoomNumber = scanner.nextInt();
        String sqlQuery = "UPDATE reservation SET guest_name = '" + newGuestName + "', " +
                "room_number = " + newRoomNumber + ", " +
                "contact_number = '" + newContactNumber + "' " +
                "WHERE reservation_id = " + reservationID;


        try(Statement statement = connection.createStatement()){
            int rowAffected = statement.executeUpdate(sqlQuery);
            if(rowAffected > 0){
                System.out.println("Reservation Update Successfully...");
            }
            else{
                System.out.println("Reservation Failed....");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private static boolean reservationExists(Connection connection, int reservationID){
        String sqlQuery = "SELECT reservation_id FROM reservation WHERE reservation_id = "+reservationID;
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            return !resultSet.next();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return true;
        }
    }
    private static void deleteReservation(Connection connection, Scanner scanner){
        System.out.println("Enter reservation Id ");
        int reservationID = scanner.nextInt();
        if(reservationExists(connection, reservationID)){
            System.out.println("Reservation is not found in reservation id - "+reservationID);
            return;
        }
        String sqlQuery = "DELETE FROM reservation WHERE reservation_id = "+reservationID;
        try(Statement statement = connection.createStatement()){
            int rowAffected = statement.executeUpdate(sqlQuery);
            if(rowAffected > 0){
                System.out.println("Reservation Deleted Successfully....");
            }
            else{
                System.out.println("Reservation Deletion Failed...");
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void exit() throws InterruptedException {
        System.out.print("Existing System");
        int i = 5;
        while(i != 0){
            System.out.print("🤗");
            Thread.sleep(500);
            i--;
        }
        System.out.println();
        System.out.println("Thank You For Coming Classical Hotel..🤩🤩🤩 ");
        System.exit(0);
    }
}
