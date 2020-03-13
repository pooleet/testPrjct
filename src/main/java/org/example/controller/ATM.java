package org.example.controller;

import org.Bank.controller.UserData;
import org.Md5Encryp;
import org.example.controller.atm.SqlATM;
import org.example.controller.tank.ATMTankDate;
import org.example.model.ATMTank;
import org.example.model.Company;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

//Банкомат
public class ATM implements ATMFunction, Serializable {
    public static final String UNIQUE_BINDING_NAME = "server.bank";


    private SqlATM sql;

    public ATM() {
        sql = new SqlATM();
    }


    //поиск компаний для оплаты счета
    public ArrayList<Company> getCompanyList(String name) throws SQLException, ClassNotFoundException {
        return sql.getNameCompany(name);
    }


    // проверка на срок годности карты (Данные из БД)
    public boolean activeDate(int id) throws SQLException, ClassNotFoundException {
        return sql.dateActive(id);
    }


    // проверка на корректность (Данные из программы Банк)
    public boolean pinCorrect(int id, String hash) throws RemoteException {
        //получаем доступ к регистру удаленных объектов.
// передать туда номер порта, на котором создавали наш регистр в программе ServerMain

        final Registry registry = LocateRegistry.getRegistry(2732);
// получить из регистра нужный объект
        UserData userdata;
        boolean multiplyResult = false;
        try {
            userdata = (UserData) registry.lookup(UNIQUE_BINDING_NAME);

            //проверяем правильность pin
            multiplyResult = userdata.userActive(id, new Md5Encryp(hash).getText());

            if (!multiplyResult) {
                System.out.println(">>>PIN код введен неверно<<<");
            }
            // System.out.println("класс сработал ");
        } catch (Exception e) {
            System.out.println("Сервис банка недоступен ");
            return false;
        }
        return multiplyResult;
    }

    // проверка количества денег на счету (Данные из программы Банк)
    public double getCountMoney(int idcart) throws Exception {
        final Registry registry = LocateRegistry.getRegistry(2732);
        double bancCountMoney;
        UserData userdata;
        userdata = (UserData) registry.lookup(UNIQUE_BINDING_NAME);
        bancCountMoney = userdata.billUser(idcart, true);
        return bancCountMoney;
    }

    //берем из банкомата деньги
    public boolean getMoney(int countMoney) throws IOException {

        ArrayList<ATMTank> listFinish = ATMTankDate.getCountBillTank(countMoney);
        boolean status = listFinish.size() == 4;
        // если массив заполнен и можно снять деньги
        // захватываем деньги на выдачу
        if (status) {
            ArrayList<ATMTank> list = ATMTank.getTank();
            for (int i = 0; i < 4; i++) {
                // количество купюр в банке
                int resB = list.get(i).getCountBill();
                // количество купюр для снятия
                int resF = listFinish.get(i).getCountBill();
                list.set(i, new ATMTank(list.get(i).getBill(), resB - resF));

            }
        }

        return status;
    }


    // обновить свежения о наличии купюр
    public void refreshTankData() throws IOException {
        new ATMTankDate();
    }


    // вначале обновляем таблицу банкомата
    public void saveData() throws IOException {
        ATMTankDate.ATMTankUpdate(ATMTank.getTank());
        refreshTankData();

    }

    //подключючаемся к банку и передаем значения,
    // получаем положительгый или отрицательный результат добавления
    public boolean saveDataClient(int id, double v, int id1, int i) throws RemoteException, NotBoundException, SQLException, ClassNotFoundException {
        final Registry registry = LocateRegistry.getRegistry(2732);
        UserData userdata;
        userdata = (UserData) registry.lookup(UNIQUE_BINDING_NAME);
        boolean tranzRemover;
        // записываем снятие денег со счета
        tranzRemover = userdata.SaveTransactionRemover(id, v);
        return tranzRemover;
    }

    // оплатим счет
    public boolean getPaypayTransaction(int id, double money, int id1) throws RemoteException, NotBoundException, SQLException, ClassNotFoundException {
        final Registry registry = LocateRegistry.getRegistry(2732);
        UserData userdata;
        userdata = (UserData) registry.lookup(UNIQUE_BINDING_NAME);
        boolean tranzPay;
        // записываем снятие денег со счета
        tranzPay = userdata.PayTransactionRemover(id, money, id1);
        return tranzPay;

    }

    // зпрос последней операции у банка
    public ArrayList<String> getСhequeBank(int id) throws RemoteException, NotBoundException, SQLException, ClassNotFoundException {

        UserData userdata;
        ArrayList<String> tranzСheque = new ArrayList<>();

        try {
            final Registry registry = LocateRegistry.getRegistry(2732);
            userdata = (UserData) registry.lookup(UNIQUE_BINDING_NAME);
            //String otvet= userdata.СhequeTransaction(id);

           // System.out.println(otvet);
           tranzСheque =   new ArrayList<>(Arrays.asList(userdata.СhequeTransaction(id).split(";")));

        } catch (Exception e) {
            System.out.println("Сервис банка недоступен, последняя опреацияпо чеку "+e);

        }

        return tranzСheque;

    }
}
