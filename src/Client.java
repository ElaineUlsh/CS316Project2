import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Please specify <serverIP> and <serverPort>");
            return;
        }
        InetAddress serverIP = InetAddress.getByName(args[0]);
        int serverPort = Integer.parseInt(args[1]);

        Scanner keyboard = new Scanner(System.in);
        String message = keyboard.nextLine();

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

        ZonedDateTime date = ZonedDateTime.ofInstant(Instant.ofEpochMilli(unsignedValue), ZoneId.systemDefault());
        System.out.println(unsignedValue);
        System.out.println(date);
    }
}
