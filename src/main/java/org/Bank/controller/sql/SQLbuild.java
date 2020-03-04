package org.Bank.controller.sql;

import com.mysql.cj.protocol.Resultset;
import org.Md5Encryp;
import org.MySQLConnUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLbuild {
    public ArrayList<String> table = new ArrayList();


    public SQLbuild() {
        table.add("Bill");
        table.add("Card");
        table.add("Bank");
        table.add("scoreMove");
        for (String s : table) {
            try {
                System.out.println(s);
                ConnectBase(s);
            } catch (Exception e) {
                System.out.println("Неудалось проверить наличе таблиц вбазе");
            }
        }
        for (String s : table) {
            try {
                System.out.println(s);
                SetDateBase(s);
            } catch (Exception e) {
                System.out.println("Неудалось заполнить таблцу " + s);
            }
        }


    }

    private void SetDateBase(String s) throws SQLException, ClassNotFoundException {
        Connection conn = MySQLConnUtils.getMySQLConnection();
        //   System.out.println("Соединение с СУБД выполнено main.ddd" + conn);
        Statement stmnt = conn.createStatement();
/*
// имя базы testBaza
stmnt.executeUpdate("drop table bank");
stmnt.executeUpdate("drop table card");
stmnt.executeUpdate("drop table scoreMove");
stmnt.executeUpdate("drop table bill");*/

        try {
            ResultSet res = (ResultSet) stmnt.executeQuery("select count(*) countZ from " + s + "");
            int i = 0;
            while (res.next()) {
                System.out.println("Сколько записей в таблице  " + s + "    " + res.getInt("countZ"));
                i = res.getInt("countZ");
            }


            if (i == 0) {
                if (s.equals("Card")) {
                    stmnt.executeUpdate("INSERT into Card (DateEnd,pin,idBill) values (DATE_ADD(now(), INTERVAL 15 DAY),'1234' ,1)");
                    stmnt.executeUpdate("INSERT into Card (DateEnd,pin,idBill) values (DATE_ADD(now(), INTERVAL -15 DAY),'1234',2)");
                    stmnt.executeUpdate("INSERT into Card (DateEnd,pin,idBill) values (DATE_ADD(now(), INTERVAL 15 DAY),'4321',3)");
                    stmnt.executeUpdate("INSERT into Card (DateEnd,pin,idBill) values (DATE_ADD(now(), INTERVAL 15 DAY),'3254',7)");

                }

                if (s.equals("Bill")) {
                    stmnt.executeUpdate("INSERT into Bill (idbill,NameU) values ('74799104151422253000','Иванов Иван')");
                    stmnt.executeUpdate("INSERT into Bill (idbill,NameU) values ('11375552024637828000','Петров Петр')");
                    stmnt.executeUpdate("INSERT into Bill (idbill,NameU) values ('23245400511497489000','Ильин Илья')");
                    stmnt.executeUpdate("INSERT into Bill (idbill,NameU) values ('71100542424788331000','ООО Рога')");
                    stmnt.executeUpdate("INSERT into Bill (idbill,NameU) values ('60200102326110061200','ООО Копыта')");
                    stmnt.executeUpdate("INSERT into Bill (idbill,NameU) values ('40030920073004497368','ООО Исток')");
                    stmnt.executeUpdate("INSERT into Bill (idbill,NameU) values ('40030945073004497319','Сидр Сидоров')");
                }
                if (s.equals("Bank")) {
                    // System.out.println("INSERT into Bank (IDBill,IDCard,PinHash) values ('3','4', "+"'"+new Md5Encryp("1234").getText()+"'");
                    stmnt.executeUpdate("INSERT into Bank (IDBill,IDCard,PinHash) values ('1','1', " + "'" + new Md5Encryp("1234").getText() + "')");
                    stmnt.executeUpdate("INSERT into Bank (IDBill,IDCard,PinHash) values ('2','2'," + "'" + new Md5Encryp("1234").getText() + "')");
                    stmnt.executeUpdate("INSERT into Bank (IDBill,IDCard,PinHash) values ('3','3','b59c67bf196a4758191e42f76670ceba')");
                    stmnt.executeUpdate("INSERT into Bank (IDBill,IDCard,PinHash) values ('6','0','0')");
                    stmnt.executeUpdate("INSERT into Bank (IDBill,IDCard,PinHash) values ('7','0','0')");
                    stmnt.executeUpdate("INSERT into Bank (IDBill,IDCard,PinHash) values ('8','0','0')");
                    stmnt.executeUpdate("INSERT into Bank (IDBill,IDCard,PinHash) values ('7','4'," + "'" + new Md5Encryp("3254").getText() + "')");

                }


            }

            if (s.equals("scoreMove")) {
// выбираем те номера счетов которых нет в таблице операции по счету
                try {
                    Statement stmnt2 = conn.createStatement();
                    ResultSet res2 = (ResultSet) stmnt.executeQuery("select id from bill where id not in (select distinct idBill from scoreMove);");
                    while (res2.next()) {

                        int id = res2.getInt("id");
                        System.out.println(id + "   не открыт счет");
 int money =800 + (int) (Math.random() * 11000);
                        String sql2 = "INSERT into scoreMove (date,idBill,cMoney,toFrom,acct) values (now()," + id + ","+money+"," + id + ","+money+");";
                        System.out.println(sql2);
                        stmnt2.executeUpdate(sql2);
                    }

                } catch (Exception e) {
                    System.out.println(e + " Ошибка:  Создать счет");
                }


            }


        } catch (SQLException exc) {
            System.out.println(exc + "В таблицу небыли добавлены записи");
        }

        conn.close();
    }

    // поключаемся к базе и роверяем наличие таблиц? если таблицы нет создаем
    private void ConnectBase(String tablestr) throws SQLException, ClassNotFoundException {


        Connection conn = MySQLConnUtils.getMySQLConnection();
        //  System.out.println("Соединение с СУБД выполнено main.ddd" + conn);
        Statement stmnt = conn.createStatement();

        try {
            Resultset res = (Resultset) stmnt.executeQuery("select * from " + tablestr + "");
            // res = stmnt.executeQuery(res);
            System.out.println("Таблица " + tablestr + " уже создана");
        } catch (SQLException exc) {
            System.out.println("Таблица несоздана");
            String sql = "";
            if (tablestr.equals("Card")) {
                sql = "CREATE TABLE Card (    Id INT PRIMARY KEY AUTO_INCREMENT,    DateEnd date NOT NULL,    pin char(4) NOT NULL,  idBill int);";
            }

            if (tablestr.equals("Bill")) {
                sql = "CREATE TABLE Bill(    Id     INT PRIMARY KEY AUTO_INCREMENT,    IDBill char(20),    NameU  varchar(250));";
            }

            if (tablestr.equals("Bank")) {
                sql = "CREATE TABLE Bank (   Id      INT PRIMARY KEY AUTO_INCREMENT,    IDBill  int NOT NULL,    IDCard  int,    PinHash varchar(33));";
            }

            if (tablestr.equals("scoreMove")) {
                sql = "CREATE TABLE scoreMove (    Id INT PRIMARY KEY AUTO_INCREMENT,    Date datetime NOT NULL, idBill int NOT NULL, cMoney double, toFrom int, acct double);";
            }
            stmnt.executeUpdate(sql);
            System.out.println("Создали таблицу " + tablestr);

        }
        conn.close();
        System.out.println("Закрыли соединение соддания таблицы!");
        // SetDateBase(tablestr);

    }

}
