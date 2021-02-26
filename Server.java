import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[]args) throws IOException, InterruptedException{

    DatagramSocket socket;
    byte[] buffer = new byte[100];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

    socket = new DatagramSocket(12345);
        socket.receive(packet);
        int port = packet.getPort();
        InetAddress address = packet.getAddress();
        String s = new String(packet.getData()).trim();
        System.out.println("Numbers: "+ s);
        String[] strings = s.split("\\|");
        final int a = Integer.parseInt(strings[0]);
        final int b = Integer.parseInt(strings[1]);
        final int c = Integer.parseInt(strings[2]);
        final double[] sum1 = {0};
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = a; i < b; i++) {
                    sum1[0] += i * (i - 1);
                }
            }
        });
        final double[] sum2 = {0};
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = b; j < c; j++) {
                    sum2[0] += 1.0 / (j + 1.0);
                }
            }
        });
        thread2.start();
        thread1.start();
        thread1.join();
        thread2.join();

        String str = String.valueOf(sum1[0] - sum2[0]);
        byte[] sendBuf = str.getBytes();
        packet = new DatagramPacket(sendBuf, sendBuf.length, address, port);
        socket.send(packet);
        socket.close();
    }
}
