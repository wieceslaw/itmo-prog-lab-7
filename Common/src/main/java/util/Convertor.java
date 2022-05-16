package util;

import java.io.*;

/**
 * Статический класс конвертирующий массивы байт в объекты и обратно
 */
public class Convertor {
    /**
     * Конвертировать байты в объект
     *
     * @param bytes массив байт
     * @return объект {@link Object}
     */
    static public Object convertBytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);
        return objectInput.readObject();
    }

    /**
     * Конвертировать объект в байты
     *
     * @param serializable сериализуемый объект, который необходимо конвертировать в байты
     * @return массив байт {@code} byte[]
     */
    static public byte[] convertObjectToBytes(Serializable serializable) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(serializable);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }
}
