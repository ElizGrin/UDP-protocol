package sample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField number1;

    @FXML
    private TextField number2;

    @FXML
    private TextField number3;

    @FXML
    private Button sendButton;

    @FXML
    private Button receiveButton;

    @FXML
    private Label showRes;

    @FXML
    void initialize() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress loc = InetAddress.getByName("localhost");
        byte[] buf = new byte[100];
        sendButton.setOnAction(actionEvent -> {
            String s =  number1.getText() + "|" + number2.getText() + "|" + number3.getText();
            DatagramPacket pack = new DatagramPacket(s.getBytes(), s.length(), loc, 12345);
            try {
                socket.send(pack);
            } catch (IOException e) {
            }
        });
        receiveButton.setOnAction(actionEvent -> {
            DatagramPacket packet = new DatagramPacket(buf,100, socket.getLocalAddress(), socket.getLocalPort());
            try {
                socket.receive(packet);
            } catch (IOException e) {
            }
            showRes.setText("The result is " + new String(packet.getData()).trim());
            socket.close();
        });
    }
}

