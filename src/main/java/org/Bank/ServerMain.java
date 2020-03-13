package org.Bank;

import org.Bank.controller.RemoteBankServer;
import org.Bank.controller.sql.SQLbuild;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ServerMain  {





    // уникальное имя удаленного объекта. По этому имени программа-клиент сможет найти наш сервер
    public static final String UNIQUE_BINDING_NAME = "server.bank";

    public static void main(String[] args) throws AlreadyBoundException, InterruptedException, RemoteException {
        new SQLbuild();

// Далее мы создаем наш объект-калькулятор:
       // final RemoteCalculationServer server = new RemoteCalculationServer();
        final RemoteBankServer server = new RemoteBankServer();
// Registry — реестр удаленных объектов.  к объектам из этого регистра возможен удаленный доступ из других программ
        final Registry registry = LocateRegistry.createRegistry(2732);
//Заглушка (stub) инкапсулирует внутри себя весь процесс удаленного вызова. самый важный элемент RMI.
        /*Принимает всю информацию об удаленном вызове какого-то метода.
Если у метода есть параметры, заглушка десериализует их.
Параметры, которые ты передаешь методам для удаленного вызова, должны быть сериализуемыми (ведь они будут передаваться по сети).
У нас такой проблемы нет — мы передаем просто числа. Но если ты будешь передавать объекты, не забудь об этом!
После этого она вызывает нужный метод.*/
        Remote stub = UnicastRemoteObject.exportObject(server, 0);
        // «регистрируем» нашу заглушку в реестре удаленных объектов под тем именем, которое придумали в самом начале.
        // Теперь клиент сможет ее найти!
        registry.bind(UNIQUE_BINDING_NAME, stub);
        // чтобы программа-сервер не вырубилась, пока мы будем запускать клиент, поэтому мы ее просто усыпили на долгое время.
        // Работать она все равно будет
        Thread.sleep(Integer.MAX_VALUE);


    }
}
