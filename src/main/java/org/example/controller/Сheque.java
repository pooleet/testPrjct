package org.example.controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

// Формирует чек, или сохраняет его на жесткий диск
public class Сheque {
    public Сheque() {
    }

 /*   public static void main(String[] args) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("text only", "txt");
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        File f = new File("C://Users/pooleet/Desktop/чек.txt");
        fc.setSelectedFile(f);
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                fw.write("Номер таранзакции:\n");
                fw.write("Дата транзакции:\n");
                fw.write("Номер счета:\n");
                fw.write("Номер перевода:\n");

                fw.write("Пополнение на:\n");
                fw.write("Снятие:\n");
                fw.write("Остаток:\n");

            } catch (IOException e) {
                System.out.println("Закончилась бумага");
            }
        }
    }*/

    public String СhequeOtvet(int idch, int idO, ArrayList<String> Arr) {

        String otvet = toString(Arr, idO);
        // чек на экран

        if (idch == 1) {


        }

        // чек на бумажном носителе
        if (idch == 2) {

            FileNameExtensionFilter filter = new FileNameExtensionFilter("text only", "txt");
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(filter);
            File f = new File("C://Users/pooleet/Desktop/чек.txt");
            fc.setSelectedFile(f);
           // if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                    fw.write(otvet);

                   // System.out.println("Заберите чек");
                } catch (IOException e) {
                    System.out.println("Закончилась бумага");

            }
        }

        return otvet;
    }


    private String toString(ArrayList<String> Arr, int idO) {
        // System.out.println(Arr.size());
        String otvet = "";

        if (idO == 1) {
            otvet = "Номер таранзакции: " + Arr.get(0) + "\n";
            otvet = otvet + "Номер счета: " + Arr.get(4) + "\n";
            otvet = otvet+"Дата транзакции:"+Arr.get(1)+"\n";
            otvet = otvet + "Операция: Снятие наличных\n";
            otvet = otvet + "Списано:"+Arr.get(3)+"\n";
            otvet = otvet + "Остаток на счете: " + Arr.get(2) + "\n";

        }
        if (idO == 2) {   otvet = "Номер таранзакции: " + Arr.get(0) + "\n";
            otvet = otvet + "Номер счета: " + Arr.get(4) + "\n";
            otvet = otvet+"Дата транзакции:"+Arr.get(1)+"\n";
            otvet = otvet + "Операция: Пополнение счета\n";
            otvet = otvet + "Зачислено:"+Arr.get(3)+"\n";
            otvet = otvet + "Остаток на счете: " + Arr.get(2) + "\n";
        }
        if (idO == 3) {otvet = "Номер таранзакции: " + Arr.get(0) + "\n";
            otvet = otvet + "Номер счета: " + Arr.get(4) + "\n";
            otvet = otvet+"Дата транзакции:"+Arr.get(1)+"\n";
            otvet = otvet + "Операция: Оплата счета: "+Arr.get(5)+"\n";
            otvet = otvet + "Списано:"+Arr.get(3)+"\n";
            otvet = otvet + "Остаток на счете: " + Arr.get(2) + "\n";}

        if (idO == 4) {
            otvet =  "Номер счета: " + Arr.get(4) + "\n";
            otvet = otvet + "Операция: Проверка счета\n";
            otvet = otvet + "Остаток на счете: " + Arr.get(2) + "\n";
            otvet = otvet + "Сформирован: " + LocalDateTime.now() + "\n";
        }
      //  System.out.println(otvet);

        //  }

        return otvet;


    }
}