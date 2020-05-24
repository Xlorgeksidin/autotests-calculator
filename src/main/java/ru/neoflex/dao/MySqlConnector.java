package ru.neoflex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySqlConnector {

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testimony?useUnicode=true&serverTimezone=UTC", "root", "148192");
            System.out.println("Успешное поключение к базе данных");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка подключения " + e);
        }
        return connection;
    }

    public static ResultSet selectAllFromBilling(String currentmonth) throws SQLException {
        Statement st = getConnection().createStatement();
        return st.executeQuery("SELECT * FROM billing_period WHERE currentmonth = \"" + currentmonth + "\"");

    }
    public static ResultSet selectAllFromPriceGuide() throws SQLException {
        Statement st = getConnection().createStatement();
        return st.executeQuery("select * from price_guide");

    }
    public static ResultSet selectAllFromTestimonyHistory(String currentmonth) throws SQLException {
        Statement st = getConnection().createStatement();
        return st.executeQuery("select * from testimony_history WHERE currentmonth = \"" + currentmonth + "\"");

    }
}
