package se.ifmo.ru.client.util.transceiving;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import se.ifmo.ru.client.talkers.Talker;
import se.ifmo.ru.common.dto.Response;
import se.ifmo.ru.common.util.Convertor;

/**
 * Класс для получения ответов от сервра
 */
public class Receiver {
    private final DatagramChannel datagramChannel;
    private final ByteBuffer buffer = ByteBuffer.allocate(65507);
    private final Talker talker;

    public Receiver(DatagramChannel datagramChannel, Talker talker) {
        this.datagramChannel = datagramChannel;
        this.talker = talker;
    }

    /**
     * Получить ответ сервера и вывести пользователю
     *
     * <p>
     * Если ответ от сервера запаздывает, но подключение есть, существует timeout в 2 секунды ожидания ответа.
     * Если подключение отсутствует, то будет выкинута ошибка {@link PortUnreachableException}.
     * </p>
     */
    public Response receive() {
        try {
            long timeout = 2000;
            SocketAddress address = null;
            long startTime = System.currentTimeMillis();
            while (address == null && (System.currentTimeMillis() - startTime) < timeout) {
                address = datagramChannel.receive(buffer);
            }
            if ((System.currentTimeMillis() - startTime) >= timeout) {
                talker.println("[ОШИБКА]: Превышено время ожидания ответа сервера.");
            } else {
                Response response = null;
                try {
                    buffer.flip(); // changed
                    response = (Response) Convertor.convertBytesToObject(buffer.array());
                } catch (IOException | ClassNotFoundException e) {
                    talker.println("[ОШИБКА]: Ошибка сериализации ответа.");
                }
                if (response == null) {
                    talker.println("[ОШИБКА]: Пустой ответ сервера.");
                } else {
                    return response;
                }
            }
        } catch (PortUnreachableException e) {
            talker.println("[ОШИБКА]: Сервер недоступен, попробуйте снова.");
        } catch (IOException e) {
            talker.println("[ОШИБКА]: Проблема соединения с сервером..");
        } finally {
            buffer.clear(); // changed
        }
        return null;
    }
}
