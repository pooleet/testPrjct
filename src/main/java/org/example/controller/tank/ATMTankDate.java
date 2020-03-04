package org.example.controller.tank;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ATMTank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;

public class ATMTankDate {

    private static String path = "C:\\Users\\pooleet\\IdeaProjects\\testPrjct\\src\\main\\java\\org\\example\\billATM.json";
    private File f = new File(path);
    static ObjectMapper mapper = new ObjectMapper();

    public ATMTankDate() throws IOException {

        mapper.readValue(f, ATMTank.class);
        //  System.out.println( ATMTank.getTank().get(2).getBill());
    }


    //Записываем в файл массив из количества купюр/ отдаем пересчитаный массив ATMTank.getTank()
    public static void ATMTankUpdate(ArrayList<ATMTank> list) throws IOException {
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, list);
        String str = writer.toString().replace("[{", "{\"tank\":[{") + "}";
       // System.out.println(str);
        FileWriter writerF = new FileWriter(path, false);
        writerF.write(str);
        writerF.flush();
        writerF.close();
    }

    public static ArrayList<ATMTank> getCountBillTank(int countBill) throws IOException {
        ArrayList<ATMTank> listFinish = new ArrayList<>();
        LinkedList<arraybank> list = new LinkedList();
        int sum = 0;
        //сколько хотим снять
        //  System.out.println("сколько хотим снять  " + countBill);
        //int countbill = countBill;
        // номинал купюр
        int[] mas = {5000, 1000, 500, 100};
        // процент номинала от количества
        int[] proc = {95, 95, 99, 100};
        // количество вариантов соотношений купюр
        int progon = 0;

        for (int i = 0; i < 4; i++) {
// начальное значение
            int count = 0;
            if (list.size() == 0) {
                i = 0;
                count = countBill;
            } else {
                count = list.get(i - 1).count - list.get(i - 1).sumKupura;
            }
            int kupura = mas[i];
            int procent = (int) Math.floor(count / 100 * proc[i]);
            int countku = countTankcupure(i, (int) Math.ceil(procent / kupura));
            int sumKupura = kupura * countku;
            sum = sum + sumKupura;
            list.add(new arraybank(count, procent, kupura, countku, sumKupura));
            // System.out.println(list.get(i).count + "    |   " + list.get(i).procent + " |   " + list.get(i).kupura + "  |   " + list.get(i).countku + " |   " + list.get(i).sumKupura);

            if (sum < countBill && progon == 0 && i == 3) {
                // System.out.println("Недостаточно средств в банкомате, повторим попытку");
                progon++;
                i = -1;
                list.clear();
                // варианты перебора отношения выдачи количества купюр
                proc = new int[]{100, 100, 100, 100};
                sum = 0;
            }
            if (sum < countBill && progon == 1 && i == 3) {
                System.out.println(">>>В банкомате нет нужной суммы<<<  ");
            }

            if (sum == countBill) {

                for (arraybank arrb : list) {
                    //  System.out.println(sum +"  "+arrb.kupura+" "+arrb.countku);
                    listFinish.add(new ATMTank(arrb.kupura, arrb.countku));

                }
                return listFinish;
            }
        }

        return listFinish;

    }

    public static int countTankcupure(int index, int countBill) throws IOException {
        //создаем таблицу с купюрами в банкомате
        new ATMTankDate();
        // если купюр достаточно возвращаем расчитаное количество купюр
        if (ATMTank.getTank().get(index).getCountBill() >= countBill)
            return countBill;
            // если купюр недостаточно,отправлем сколько если ли 0 есливообщенет
        else return ATMTank.getTank().get(index).getCountBill();
    }


    private static class arraybank {

        public int count;
        public int procent;
        public int kupura;
        public int countku;
        public int sumKupura;


        public arraybank(int count, int procent, int kupura, int countku, int sumKupura) {
            this.count = count;
            this.procent = procent;
            this.kupura = kupura;
            this.countku = countku;
            this.sumKupura = sumKupura;
        }
    }


}
