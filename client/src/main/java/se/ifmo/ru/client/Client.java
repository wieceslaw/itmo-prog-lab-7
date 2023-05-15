package se.ifmo.ru.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import se.ifmo.ru.client.talkers.ScannerTalker;
import se.ifmo.ru.client.talkers.Talker;
import se.ifmo.ru.client.util.CommandSelector;
import se.ifmo.ru.client.util.Session;
import se.ifmo.ru.client.util.transceiving.Receiver;
import se.ifmo.ru.client.util.transceiving.Sender;

public class Client {
    private final InetSocketAddress inetSocketAddress;
    private final DatagramChannel datagramChannel;
    private final Talker talker;
    private final CommandSelector commandSelector;
    private final Session session;
    private final Sender sender;
    private final Receiver receiver;


    public Client(String hostName, int port) throws IOException {
        inetSocketAddress = new InetSocketAddress(hostName, port);
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.connect(inetSocketAddress);
        talker = new ScannerTalker();
        sender = new Sender(datagramChannel, talker, inetSocketAddress);
        receiver = new Receiver(datagramChannel, talker);
        commandSelector = new CommandSelector(
                sender,
                receiver,
                talker);
        session = Session.getInstance();
    }

    public void run() {
        String str;
        while (!session.isOpened()) {
            session.openSession(talker, sender, receiver);
        }
        while (true) {
            str = talker.readCommand();
            if (str != null) commandSelector.execute(str);
            else talker.println("");
        }
    }

    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 2001;
        try {
            Client client = new Client(hostName, port);
            client.run();
        } catch (IOException e) {
            System.out.println("Ошибка запуска.");
            System.exit(1);
        }

    }
}
