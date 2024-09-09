import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Please specify <serverIP> and <serverPort>");
            return;
        }
        InetAddress serverIP = InetAddress.getByName(args[0]);
        int serverPort = Integer.parseInt(args[1]);

        String message = "";

        DatagramSocket socket = new DatagramSocket();
        DatagramPacket request = new DatagramPacket(
                message.getBytes(),
                message.getBytes().length,
                serverIP,
                serverPort
        );
        socket.send(request);

        DatagramPacket reply = new DatagramPacket(
                new byte[1024],
                1024
        );
        socket.receive(reply);
        socket.close();

        byte[] serverMessage = Arrays.copyOf(
                reply.getData(),
                reply.getLength()
        );

        int value = ByteBuffer.wrap(serverMessage).getInt();
        long unsignedValue = Integer.toUnsignedLong(value);
        ZonedDateTime date = ZonedDateTime.ofInstant(Instant.ofEpochSecond(unsignedValue), ZoneId.systemDefault()).minusYears(70);
        System.out.println(unsignedValue);
        System.out.println(date);
    }
}
