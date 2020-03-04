package org.example.controller.atm;

import org.MySQLConnUtils;
import org.example.model.Company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlATM {

    public SqlATM() {

    }

    public boolean dateActive(int idCart) throws SQLException, ClassNotFoundException {

        boolean active = false;

        Connection conn = MySQLConnUtils.getMySQLConnection();
        Statement stmnt = conn.createStatement();

        try {
            ResultSet res = stmnt.executeQuery("select id, case when DateEnd>=Now() then 'true' else 'false' end status from card where id=" + idCart + "");
            while (res.next()) {
                active = res.getBoolean("status");
            }
        } catch (Exception e) {
            System.out.println(e + "  Проверка даты окончания работы карты");
        }
        return active;
    }

    // получаем имя компаний для оплаты счета
    public ArrayList<Company> getNameCompany(String name) throws SQLException, ClassNotFoundException {
        Connection conn = MySQLConnUtils.getMySQLConnection();
        Statement stmnt = conn.createStatement();
        ArrayList<Company> cList = new ArrayList<Company>();
        String sql = "select id, idbill, nameu from bill where id not in (select distinct idbill from card)\n" +
                "and (idbill like '%" + name + "%' or nameu like '%" + name + "%') ";
        try {
            ResultSet res = stmnt.executeQuery(sql);
            while (res.next()) {
                cList.add(new Company(res.getInt("id"), res.getString("idbill"), res.getString("nameu")));
            }
        } catch (Exception e) {
            System.out.println(e + "Ошибка:  Поиск компании по счету/имени  " + sql);
        }
        return cList;
    }
}
