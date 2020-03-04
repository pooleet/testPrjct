package org.Bank.controller.sql;

import org.MySQLConnUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLfunctionBank {
    private String sql;

    public SQLfunctionBank() {
    }

    // проверка введеного PIN
    public boolean TryPin(int id, String s) throws SQLException, ClassNotFoundException {
        Connection conn = MySQLConnUtils.getMySQLConnection();
        //   System.out.println("Соединение с СУБД выполнено main.ddd" + conn);
        Statement stmnt = conn.createStatement();
        boolean status = false;
        try {
            sql = "select count(*) count from bank where IDCard=" + id + " and PinHash='" + s + "';";

            ResultSet res = (ResultSet) stmnt.executeQuery(sql);
            int i = 0;
            while (res.next()) {
                if (res.getInt("count") == 1) {
                    status = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e + " проверка подлиности PIN  " + sql);
        }

        return status;
    }


    public double CountMoney(int idcart) throws SQLException, ClassNotFoundException {
        Connection conn = MySQLConnUtils.getMySQLConnection();

        Statement stmnt = conn.createStatement();

        double countMoney = 0;
        try {
            sql = "select max(a1.id), a1.acct, a2.IDCard from scoreMove a1\n" +
                    "    inner join bank a2 on a1.idBill=a2.IDBill\n" +
                    "where a2.IDCard = " + idcart + "";

            ResultSet res = (ResultSet) stmnt.executeQuery(sql);
            int i = 0;
            while (res.next()) {

                countMoney = res.getDouble("acct");
                System.out.println(" на счету " + countMoney+ "   "+sql);
            }
        } catch (Exception e) {
            System.out.println(e + " узнать счет клиена  " + sql);
        }

        return countMoney;
    }

    // снятие денег со счета
    public boolean SaveTrRem(int idCart, double money) throws SQLException, ClassNotFoundException {
        Connection conn = MySQLConnUtils.getMySQLConnection();
        Statement stmnt = conn.createStatement();
        // получаем номер счета
        int idBill = getBill(idCart);
        // изначальное количество денег
        double billstart = getCustomerAccount(idCart) + money;
        sql = "INSERT into scoreMove (date,idBill,cMoney,toFrom,acct) values (now()," + idBill + "," + money + "," + idBill + "," + billstart + ");";

        try {
            System.out.println("Снятье денег " + sql);
            stmnt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println(e + " Ошибка добавления транзакции снятия " + sql);
            return false;
        }
        return true;
    }

    // получаем id со счета
    public int getBill(int id) throws SQLException, ClassNotFoundException {
        Connection conn = MySQLConnUtils.getMySQLConnection();
        Statement stmnt = conn.createStatement();
        sql = "select idBill from bank where IDCard=" + id + "";

        int idB = 0;
        try {
            ResultSet res = (ResultSet) stmnt.executeQuery(sql);
            System.out.println("получили id счета  " + sql);
            while (res.next()) {

                idB = res.getInt("idBill");
            }

        } catch (Exception e) {
            System.out.println(e + " Ошибка получения номера счета " + sql);
        }
        conn.close();
        return idB;
    }

    // получаем состояние счета клиента
    public double getCustomerAccount(int id) throws SQLException, ClassNotFoundException {
        Connection conn = MySQLConnUtils.getMySQLConnection();
        Statement stmnt = conn.createStatement();
        sql = "select acct from scoreMove where idBill in (select idBill from bank where IDCard=" + id + " ) order by id desc limit 1";
        double schet = 0;
        try {
            ResultSet res = (ResultSet) stmnt.executeQuery(sql);
          //  System.out.println("Получили состояние счета " + sql);
            while (res.next()) {

                schet = res.getDouble("acct");
            }

        } catch (Exception e) {
            System.out.println(e + " Ошибка получения количества денег  на счету " + sql);
        }
        conn.close();
        return schet;
    }


}
