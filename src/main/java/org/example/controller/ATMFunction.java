package org.example.controller;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ATMFunction {
    // проверка даты карты
    boolean activeDate (int id) throws SQLException, ClassNotFoundException;

    // проверка введеного PIN
    boolean pinCorrect(int id, String hash) throws RemoteException;

    // проверка количества денег на счету (Данные из программы Банк)
     double getCountMoney(int idcart) throws RemoteException, Exception;

    //берем из банкомата деньги
     boolean getMoney(int countMoney) throws IOException;

    // обновить свежения о наличии купюр
   void refreshTankData() throws IOException;

    //подключючаемся к банку и передаем значения,
    // получаем положительгый или отрицательный результат добавления
    boolean saveDataClient(int id, double v, int id1, int i) throws RemoteException, NotBoundException, SQLException, ClassNotFoundException;

    // оплатим счет
    boolean getPaypayTransaction(int id, double money, int id1) throws RemoteException, NotBoundException, SQLException, ClassNotFoundException;
// последняя операция из банка
     ArrayList<String> getСhequeBank(int id) throws RemoteException, NotBoundException, SQLException, Exception;
}
