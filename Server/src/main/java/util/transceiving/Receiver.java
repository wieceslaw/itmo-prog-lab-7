package util.transceiving;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Класс для получения запросов от клиентов
 */
public class Receiver {
    private final byte[] buffer = new byte[65507];
    private final DatagramSocket datagramSocket;
    private final DatagramPacket datagramPacket;
    private static final Logger logger = LogManager.getLogger();

    public Receiver(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
        this.datagramPacket = new DatagramPacket(buffer, buffer.length);
    }

    /**
     * Принять запрос клиента
     *
     * @return {@link DatagramPacket пакет} от клиента
     */
    public DatagramPacket receive() {
        try {
            datagramSocket.receive(datagramPacket);
            return datagramPacket;
        } catch (IOException e) {
            logger.error("Ошибка получения запроса.", e);
        }
        return null;
    }
}
