package org.example.controller;


import org.example.App;
import org.example.model.Company;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

// взаимодействие клинета с банкоматом
public class Client {

    public Client() {
    }

    //срок действия карты
    public void insertcard(int id) throws Exception {
        // System.out.println("Вставьте карту");
        if (!new ATM().activeDate(id)) {
            System.out.println("Срок действия карты №" + id + " истек");
            App.ConttrollerAct(0);
        } else App.ConttrollerAct(2);

    }

    // пин верен?
    public boolean setPIN(int id, String pin) throws RemoteException, SQLException, ClassNotFoundException, InterruptedException {
        //System.out.println("Введите пинкод");

        if (pin.matches("[-+]?\\d+") && pin.length() == 4)
            if (new ATM().pinCorrect(id, pin))
                // App.ConttrollerAct(3);//выберите oперацию
                return true;
            else return false;//App.ConttrollerAct(2); //ответ банкомата о неверном pin

        else {
            System.out.println(">>>Неверный формат PIN: 4 цифры<<<");
            //App.ConttrollerAct(2); // выход
            return false;
        }
    }

    // проверяем хватает ли на счету денег для снятия (обращаемся к серверу из ATM)
    public boolean getCountMoneyBank(int idCart, double money) throws Exception {
        double bool = new ATM().getCountMoney(idCart);
        // System.out.println("Денег на счету"+ bool);
        return money <= bool;
    }

    // непосредственное снятие денег (если успешно предлагаем снять деньги)
    public boolean getMoneyTank(int countMoney) throws IOException {
        final boolean moneySt = new ATM().getMoney(countMoney);
        //ATMTank.getTank().indexOf("5000");
        return moneySt;
    }

    // записываем новые данные в банк ()
    public void saveData() throws IOException {
        //записываем данные  в таблицу
        new ATM().saveData();
    }

    //возвращаем деньги в купюроприемник
    public void refreshData() throws IOException {
        new ATM().refreshTankData();
    }

    // обновлям данные о счете клиента
    public boolean saveDataClient(int id, double v, int id1, int i) throws RemoteException, SQLException, ClassNotFoundException, NotBoundException {
        boolean statusSave;
        statusSave = new ATM().saveDataClient(id, v, id1, i);
        return statusSave;

    }

    public ArrayList<Company> setSearch(String name) throws SQLException, ClassNotFoundException {

        ArrayList<Company> listC = new ATM().getCompanyList(name);
        return listC;
    }

    // отправляем команду банкомату об оплате счета
    public String setPayDate(int id, double money, int id1) throws RemoteException, SQLException, ClassNotFoundException, NotBoundException {
        boolean otvet = new ATM().getPaypayTransaction(id, money, id1);
        if (otvet)
            return "Счет оплачен";
        else
            return "Оплата не пропрошла";
    }


    // проверяем хватает ли на счету денег для снятия (обращаемся к серверу из ATM)
    public String getCountMoneyBankString(int idCart) throws Exception {
        double bool = new ATM().getCountMoney(idCart);
        // System.out.println("Денег на счету"+ bool);

        String str = "На вашем счету : " + bool;
        return str;
    }

    // спросить банк о последней операции
    public ArrayList<String> getСhequeArr(int id) throws Exception {
        ArrayList<String> arr = new ATM().getСhequeBank(id);
        return arr;
    }
}
