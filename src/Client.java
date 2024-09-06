import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.LocalDate;
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

        // sends out the packet
        DatagramSocket socket = new DatagramSocket(); // allocates a bluffer to the port and creates the socket
        DatagramPacket request = new DatagramPacket(
                message.getBytes(), // converts the String to the byte array
                message.getBytes().length, // gets the length of the byte array, which must be provided
                serverIP,
                serverPort
        );
        socket.send(request); // places the packet into the internal buffer of the socket

        // receives a reply from sending out the packet -- is a UDP packet
        DatagramPacket reply = new DatagramPacket( // this packet is BACKED by the byte array
                new byte[1024], // 1024 is the maximum number of bytes that is able to be held
                1024 // used as the length
        );
        socket.receive(reply); // the socket will put the bytes that it received in the reply packet
        socket.close(); // allows us to recycle the memory that was allocated to the socket

        byte[] serverMessage = Arrays.copyOf( // creates a copy of the byte array given to the reply packet
                reply.getData(),
                reply.getLength() // specifies the actual number of bytes received by the server
        );

        int value = ByteBuffer.wrap(serverMessage).getInt();
        long unsignedValue = Integer.toUnsignedLong(value);
        //LocalDate date = LocalDate.ofEpochDay(unsignedValue);

        //System.out.println(date);
        //System.out.println(new String(date));
    }
}
