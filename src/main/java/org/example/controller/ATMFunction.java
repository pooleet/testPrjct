package org.example.controller;

import java.rmi.RemoteException;
import java.sql.SQLException;

public interface ATMFunction {
    // проверка даты карты
    boolean activeDate (int id) throws SQLException, ClassNotFoundException;

    // проверка введеного PIN
    boolean pinCorrect(int id, String hash) throws RemoteException;


}
