package org.Bank.controller;

import org.Bank.controller.sql.SQLfunctionBank;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoteBankServer implements UserData, Serializable {
    // проверяем ПИН
    @Override
    public boolean userActive(int idCart, String hash) throws RemoteException, SQLException, ClassNotFoundException {
        SQLfunctionBank sql = new SQLfunctionBank();
        System.out.println("RemoteBankServer" + idCart + "     " + hash);
        return sql.TryPin(idCart, hash);
    }

    // количество денег по номеру карты
    @Override
    public double billUser(int idCart, boolean UserActive) throws RemoteException, SQLException, ClassNotFoundException {
        SQLfunctionBank sql = new SQLfunctionBank();
        // System.out.println("RemoteBankServer" + idCart+"     "+hash );
        return sql.getCustomerAccount(sql.getBill(idCart));
        //return sql.CountMoney(idCart);


    }

    //добавляем транзакцию снятия денег
    @Override
    public boolean SaveTransactionRemover(int id, double money) throws SQLException, ClassNotFoundException {

        SQLfunctionBank sql = new SQLfunctionBank();
        boolean bool = sql.SaveTrRem(id, money);
        return bool;
    }

    @Override
    public boolean PayTransactionRemover(int id, double money, int idBill) throws RemoteException, SQLException, ClassNotFoundException {
        SQLfunctionBank sql = new SQLfunctionBank();
        boolean bool = sql.payTransaction(id, money, idBill);
        return bool;
    }

    @Override
    public String СhequeTransaction(int id) {
        SQLfunctionBank sql = new SQLfunctionBank();
        System.out.println("зашли в метод");
       String arrL = sql.chequeTransaction(id);


        return arrL;
    }


}
