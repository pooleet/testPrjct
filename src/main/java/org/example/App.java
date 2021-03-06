package org.example;

import org.example.controller.Client;
import org.example.controller.Сheque;
import org.example.model.Company;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Здравсвуйте!");
        System.out.println("             Зарегистрированые карты 1-1234, 2-истек срок годности, 3-1234 (неверный PIN), 4-3254");


        ConttrollerAct(1);

    }

    public static void ConttrollerAct(int i) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //scanner.findInLine(".{6}");
        Сheque che = new Сheque();

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
            System.out.print("1 Снать деньги\n2 Пополнить баланс карты\n3 Оплатить счет\n4 Проверить баланс счета \n:");
            j = scanner.nextInt();
        }
// снять деньги
        if (j == 1) {
            j = -1;
            System.out.print("Сколько будем снимать?\n:");
            double getMoney = scanner.nextInt();
            if (getMoney % 100 != 0 && getMoney > 0) {
                System.out.println("Сумма не кратна 100, повторите попытку");
                j = 1;
            } else {
                System.out.print("1 Чек на экран\n2 распечатать чек\n:");
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
                                check(check, che.СhequeOtvet(check, 1, client.getСhequeArr(id)));
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
// положить деньги на счет
        if (j == 2) {
            j = -1;
            System.out.print("Сколько будем класть?\n:");
            double setMoney = scanner.nextInt();
            if (setMoney % 100 != 0 && setMoney > 0) {
                System.out.println("Сумма не кратна 100, повторите попытку");
                j = 2;
            } else {
                System.out.print("1 Чек на экран\n2 распечатать чек\n:");
                int check = scanner.nextInt();
                // проверяем PIN
                boolean status = client.setPIN(id, pin);
                if (status && client.saveDataClient(id, (setMoney), id, 0)) {

                    System.out.println("Транзакция прошла успешно");
                    check(check, che.СhequeOtvet(check, 2, client.getСhequeArr(id)));
                    i = 0;
                } else System.out.print("Транзакция прошла неудачно");
            }

        }
// оплатить счет
        if (j == 3) {
            j = -1;
            System.out.println("_ПОИСК_ Фирмы из базы\n" +
                    "4	71100542424788331000	ООО Рога\n" +
                    "5	60200102326110061200	ООО Копыта\n" +
                    "6	40030920073004497368	ООО Исток");
            System.out.print("Поиск фирмы (имя,счет) \n:");
            String name = "";
            name = scanner.next();
            ArrayList<Company> cList = client.setSearch(name);

            System.out.println(cList.size());
            System.out.println("Выберите счет для оплаты");
            for (int l = 0; l < cList.size(); l++) {
                System.out.println(l + 1 + " " + cList.get(l).getSchet() + " " + cList.get(l).getName());
            }
            System.out.print(":");
            int index = scanner.nextInt();
            int check = 0;
            if (index < 1 || index > cList.size()) {
                System.out.println("Диапозон ввода 1-" + cList.size());
                j = 3;

            } else if (client.setPIN(id, pin)) {
                System.out.print("1 Чек на экран\n2 распечатать чек\n:");
                check = scanner.nextInt();
                // выставить счет. вызывает рандомное значение
                double random_number2 = 1 + (Math.random() * 10000);
                random_number2 = Math.floor(random_number2 * 100) / 100.0;

                System.out.println("К оплате:" + random_number2);
                if (client.getCountMoneyBank(id, random_number2)) {
                    System.out.println(" (ЗАПРОС ИЗ БАНКА)денег хватит можно оплатить " + random_number2);

                    // отправляем данные о переводе номер карты,количество денег, номер счета куда переводим
                    System.out.println(client.setPayDate(id, random_number2, cList.get(index - 1).getId()));
                    check(check, che.СhequeOtvet(check, 3, client.getСhequeArr(id)));
                    // получим чек выходим
                    i = 0;

                } else System.out.println(" На вашем счету недостаточно средств");

                i = 0;
            }


        }
// сколько денег осталось а счете
        if (j == 4) {
            j = -1;
            System.out.print("1 Чек на экран\n2 распечатать чек\n:");
            int check = scanner.nextInt();
            if (client.setPIN(id, pin)) {
            check(check, che.СhequeOtvet(check, 4, client.getСhequeArr(id)));

            }
            i = 0;
        }
        if (i == 0) {
            System.out.println("Заберите карту");
            System.out.println("Выход");
        }

    }

    public static void check(int check, String str) {

        if (check == 1) {
            System.out.println(str);
        } else System.out.println("Заберите чек");

    }


}



