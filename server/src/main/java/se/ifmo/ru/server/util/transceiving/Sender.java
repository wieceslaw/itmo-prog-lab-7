package se.ifmo.ru.server.util.transceiving;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.ifmo.ru.common.dto.Response;
import se.ifmo.ru.common.util.Convertor;

/**
 * Класс для отправки ответов сервера
 */
public class Sender {
    private final DatagramSocket datagramSocket;
    private final Logger logger = LogManager.getLogger();
    private final ExecutorService threadPool;

    public Sender(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
        this.threadPool = Executors.newCachedThreadPool();
    }

    /**
     * Отправить {@link Response ответ} сервера клиенту
     *
     * @param socketAddress адрес получателя
     * @param response      ответ сервера
     */
    public void send(SocketAddress socketAddress, Response response) {
        threadPool.execute(() -> {
            try {
                byte[] data = Convertor.convertObjectToBytes(response);
                datagramSocket.send(new DatagramPacket(data, data.length, socketAddress));
            } catch (IOException e) {
                logger.error("Ошибка отправки запроса.", e);
            }
        });
    }
}
