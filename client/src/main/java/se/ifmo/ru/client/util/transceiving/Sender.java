package se.ifmo.ru.client.util.transceiving;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import se.ifmo.ru.client.talkers.Talker;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.common.util.Convertor;

/**
 * Класс для отправки запросов на сервер
 */
public class Sender {
    private final DatagramChannel datagramChannel;
    private final InetSocketAddress inetSocketAddress;
    private final Talker talker;

    public Sender(DatagramChannel datagramChannel, Talker talker, InetSocketAddress inetSocketAddress) {
        this.datagramChannel = datagramChannel;
        this.inetSocketAddress = inetSocketAddress;
        this.talker = talker;
    }

    /**
     * Отправить запрос на сервер
     *
     * @param request {@link Request запрос} к серверу
     */
    public boolean send(Request request) {
        try {
            if (datagramChannel.send(ByteBuffer.wrap(Convertor.convertObjectToBytes(request)), inetSocketAddress) > 0) {
                return true;
            }
        } catch (IOException e) {
            talker.println("Ошибка при отправке запроса.");
        }
        return false;
    }
}
