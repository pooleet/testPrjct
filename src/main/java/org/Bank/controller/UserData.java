package org.Bank.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserData extends Remote {
    // пользователь активный?
    boolean userActive (int idCart, String pin) throws RemoteException, SQLException, ClassNotFoundException;

    // сколько на счету осталось денег
    double billUser (int idCart,boolean UserActive) throws RemoteException, SQLException, ClassNotFoundException;

    // снять деньги
    boolean SaveTransactionRemover (int id, double money) throws RemoteException, SQLException, ClassNotFoundException;

    // перевести деньги между счетами
    boolean PayTransactionRemover (int id, double money, int idBill) throws RemoteException, SQLException, ClassNotFoundException;

    //последняя операция
    String СhequeTransaction(int id)throws RemoteException, SQLException, ClassNotFoundException;


}
