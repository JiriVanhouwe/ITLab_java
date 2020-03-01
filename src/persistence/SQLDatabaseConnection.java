package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLDatabaseConnection {
    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) {
    	final String IP_SERVER = "51.144.159.116";
    	final String DB_NAME ="testjava";
    	final String DB_USERNAME = "sebastiaan.labijn";
    	final String DB_PWD = "2.f17302537U";
    	
        String connectionUrl =
                "jdbc:sqlserver://"+IP_SERVER+ ":1433;"
                        + "database="+ DB_NAME +";"
                        + "user="+ DB_USERNAME+";"
                        + "password="+DB_PWD+";"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        
        System.out.println(connectionUrl);
        
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * from dbo.Gebruiker";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}