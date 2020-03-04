package org.example;

import org.example.controller.Client;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Hello world!
 */


public class App {

    // private  Client client = new Client();
    private static int id = 0;
    private static String pin = "";
    //private static int count=0;
    private static int j = 0;
    private static int otvet = 0;
    private static Client client = new Client();
    private static TimerTask task = new TimerTask() {
        public void run() {
            if (otvet == 0) {
                System.out.println("Вы не забрали деньги.Деньги возвращаются в банкомат exit...");
                try {
                    client.refreshData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        }
    };

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InterruptedException, NotBoundException {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Здравсвуйте!");
        System.out.println("             Зарегистрированые карты 1-1234, 2-истек срок годности, 3-1234 (неверный PIN)");


        ConttrollerAct(1);

    }

    public static void ConttrollerAct(int i) throws SQLException, ClassNotFoundException, IOException, InterruptedException, NotBoundException {
        Scanner scanner = new Scanner(System.in);
        //scanner.findInLine(".{6}");


        if (i == 1) {
            //проверка карты
            System.out.print("Вставьте карту\n:");
            id = scanner.nextInt();
            client.insertcard(id);
        }

        if (i == 2) {
            //count++;
            System.out.print("Введите пинкод\n:");
            pin = scanner.nextLine();
            i = 3;
        }

        if (i == 3) {
            i = -1;

            System.out.println("Выберите операцию");
            System.out.println("1 Снать деньги\n2 Пополнить баланс карты\n3 Оплатить счет\n4 Проверить баланс счета ");
            j = scanner.nextInt();
        }

        if (j == 1) // снять деньги
        {
            j = -1;
            System.out.print("Сколько будем снимать?\n:");
            double getMoney = scanner.nextInt();
            if (getMoney % 100 != 0&& getMoney>0) {
                System.out.println("Сумма не кратна 100, повторите попытку");
                j = 1;

            } else {
                System.out.print("Чек на экран(1) или распечатать(2)\n:");
                int check = scanner.nextInt();
                // проверяем PIN
                boolean status = client.setPIN(id, pin);
                if (status) {
                    // запрос в банк о наличии суммы
                    if (client.getCountMoneyBank(id, getMoney)) {
                        System.out.println(" (ЗАПРОС ИЗ БАНКА)денег хватит можно снимать");

                        // есть ли столько денег в банкомате
                        if (client.getMoneyTank((int) getMoney)) {
                            int y = 0;
                            Timer timer = new Timer();
                            System.out.print("1 забрать деньги\nчерез 10 секунд завершится сеанс\n: ");
                            timer.schedule(task, 10 * 1000);

                            otvet = scanner.nextInt();
                            timer.cancel();
                            if (otvet == 1) {
                                // т.к. сняли  умножаем на -1
                                // (Клиент, сколько снимаем, куда снимаем, №счета)
                                client.saveDataClient(id, (getMoney * -1), id, 0);
                                client.saveData();
                                i = 0;
                                }
                        }

                    } else {
                        System.out.println("Сообщение с экрана: Недостаточно средств на счету");

                    }
                } else {
                    // отказ при неверном pin
                    // выводим чек с надписью о неправильном пин
                    // отдаем карту
                }
            }

        }

        if (j == 2) {
            j = -1;
            System.out.print("Сколько будем класть?\n:");
            double setMoney = scanner.nextInt();
            if (setMoney % 100 != 0 && setMoney>0 ) {
                System.out.println("Сумма не кратна 100, повторите попытку");
                j = 2;}
                else {
                    System.out.print("Чек на экран(1) или распечатать(2)\n:");
                    int check = scanner.nextInt();
                    // проверяем PIN
                    boolean status = client.setPIN(id, pin);
                    if (status) {
                        client.saveDataClient(id, (setMoney ), id, 0);
                        System.out.print("Деньги в банке:");
                    }



            }

        }




            if (i == 0) {
            System.out.println("Заберите карту");
            System.out.println("Выходим");
        }

    }


}



