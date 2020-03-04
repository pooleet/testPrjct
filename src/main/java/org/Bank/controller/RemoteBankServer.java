package org.Bank.controller;

import org.Bank.controller.sql.SQLfunctionBank;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class RemoteBankServer implements UserData {
    @Override
    public boolean userActive(int idCart, String hash) throws RemoteException, SQLException, ClassNotFoundException {
        SQLfunctionBank sql = new SQLfunctionBank();
        System.out.println("RemoteBankServer" + idCart + "     " + hash);
        return sql.TryPin(idCart, hash);
    }

    @Override
    public double billUser(int idCart, boolean UserActive) throws RemoteException, SQLException, ClassNotFoundException {
        SQLfunctionBank sql = new SQLfunctionBank();
        // System.out.println("RemoteBankServer" + idCart+"     "+hash );

        return sql.CountMoney(idCart);


    }

    //добавляем транзакцию снятия денег
    @Override
    public boolean SaveTransactionRemover(int id, double money) throws SQLException, ClassNotFoundException {

      SQLfunctionBank sql = new SQLfunctionBank();
          boolean bool = sql.SaveTrRem(id,money);
        return bool;
    }


}
