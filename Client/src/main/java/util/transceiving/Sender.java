package util.transceiving;

import dto.Request;
import talkers.Talker;
import util.Convertor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

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
