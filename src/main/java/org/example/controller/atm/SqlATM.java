package org.example.controller.atm;

import org.MySQLConnUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlATM {

    public SqlATM() {

    }

    public boolean dateActive(int idCart) throws SQLException, ClassNotFoundException {

        boolean active = false;

        Connection conn = MySQLConnUtils.getMySQLConnection();
        Statement stmnt = conn.createStatement();

        try {
            ResultSet res = (ResultSet) stmnt.executeQuery("select id, case when DateEnd>=Now() then 'true' else 'false' end status from card where id=" + idCart + "");
            while (res.next()) {
                active = res.getBoolean("status");
            }
        } catch (Exception e) {
            System.out.println(e + "  Проверка даты окончания работы карты");
        }
        return active;
    }

}
