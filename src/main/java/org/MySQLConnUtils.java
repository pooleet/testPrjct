package org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnUtils {


    // Connect to MySQL
    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "testbaza";
        String userName = "root";
        String password = "admin";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException,
            ClassNotFoundException {


        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false &serverTimezone=UTC";
        Connection conn = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");//Проверяем наличие JDBC драйвера для работы с БД
            conn = DriverManager.getConnection(connectionURL, userName,
                    password);//соединениесБД
          //  System.out.println("Соединение с СУБД выполнено.");

            // conn.close();       // отключение от БД
            //  System.out.println("Отключение от СУБД выполнено.");


        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // обработка ошибки  Class.forName
            System.out.println("JDBC драйвер для СУБД не найден!");
        } catch (SQLException e) {
            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println("Ошибка SQL !");
        }
        return conn;
    }
}