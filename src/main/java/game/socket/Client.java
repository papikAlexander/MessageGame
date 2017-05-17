package game.socket;

import game.model.Player;

import java.io.*;
import java.net.Socket;

/**
 * Created by alex on 16.05.17.
 */
public class Client {
    public static void main(String[] ar) {
        try {
            String code = "404";
            Player player = new Player(false);
            // создание сокета используя IP-адрес и порт сервера.
            Socket socket = new Socket(player.getIp(), player.getPort());
            System.out.println("Connect to the player.");

            // стримы для получения и оправки сообщений
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            // Создаем поток для чтения с клавиатуры.
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            while (!player.isReady()){
                System.out.println("If you are ready to play, send a message \"ready\"");
                if (keyboard.readLine().equals("ready")){
                    player.setReady(true);
                }
            }

            String line = "";
            int count = 0;
            while (count < 10) {

                System.out.println("Write message");
                line = "Player2 : " + keyboard.readLine() + "\n" + line;

                // отсылаем введенную строку текста серверу.
                out.writeUTF(line);

                System.out.println("Sending this line to the player...");

                // заставляем поток закончить передачу данных.
                out.flush();

                System.out.println("Waiting for the next message...");
                System.out.println();

                // ждем пока сервер отошлет строку текста.
                line = in.readUTF();

                if (line.equals("404")) {
                    System.out.println("You lose!");
                    System.exit(0);
                }
                System.out.println(line);
                count++;
            }

            System.out.println("You win!");
            out.writeUTF(code);
            // закрываем сокет
            socket.close();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
