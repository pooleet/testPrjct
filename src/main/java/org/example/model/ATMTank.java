package org.example.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.ArrayList;

//банкомат ATM
@JsonAutoDetect
public class ATMTank  extends listAMT{
    public ATMTank() throws IOException {
        }

    public ATMTank(int bill, int countBill) {
        this.bill = bill;
        this.countBill = countBill;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public int getCountBill() {
        return countBill;
    }

    public void setCountBill(int countBill) {
        this.countBill = countBill;
    }



    private int bill;
    private int countBill;


}
// список купюр
@JsonAutoDetect
class listAMT {

    @JsonDeserialize(as = ArrayList.class)
    private static ArrayList<ATMTank> tank;
    public listAMT() {
    }

    public static ArrayList<ATMTank> getTank() {
        return tank;
    }

    public void setTank(ArrayList<ATMTank> tank) {
        this.tank = tank;
    }
}
