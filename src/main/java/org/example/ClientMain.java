package org.example;


import org.example.controller.tank.ATMTankDate;
import org.example.model.ATMTank;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;


public class ClientMain {
    //клиент должен быть в курсе уникального имени объекта, методы которого он будет вызывать удаленно.
    // public static final String UNIQUE_BINDING_NAME = "server.bank";

    public static void main(String[] args) throws IOException, NotBoundException {
        new ATMTankDate();
        ArrayList<ATMTank> list = ATMTank.getTank();

        for (int i = 0; i < 4; i++) {
            System.out.println(list.get(i).getCountBill());
            int res = list.get(i).getCountBill();
            list.set(i, new ATMTank(list.get(i).getBill(), res - 10));
            System.out.println(list.get(i).getBill() + "    " + list.get(i).getCountBill());
        }


    }
}
