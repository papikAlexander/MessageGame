package game.socket;

import game.model.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by alex on 16.05.17.
 */
public class Server{
    public static void main(String[] args) {
        String code = "404";
        try {
            Player player = new Player(false);

            ServerSocket serverSocket = new ServerSocket(player.getPort());
            System.out.println("Waiting for a player...");

            //ожидание подключения
            Socket socket = serverSocket.accept();
            System.out.println("Player connect");
            System.out.println();

            // стримы для получения и отправки сообщений
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            //Поток для чтения с клавиатуры
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            while (!player.isReady()){
                System.out.println("If you are ready to play, send a message \"ready\"");
                if (keyboard.readLine().equals("ready")){
                    player.setReady(true);
                }
                System.out.println("Wait a message");
                System.out.println();
            }

            String line = "";
            int count = 0;
            while(count < 10) {

                if (line.equals("404")) {
                    System.out.println("You lose!");
                    System.exit(0);
                }
                // принимаем сообщение
                line = in.readUTF();
                System.out.println(line);

                System.out.println("Write message");
                line = "Player1 : " + keyboard.readLine() + "\n" + line;
                // отсылаем клиенту текст.
                out.writeUTF(line);
                System.out.println("Sending this line to the player...");
                // заставляем поток закончить передачу данных.
                out.flush();
                System.out.println();

                System.out.println("Waiting for the next message...");
                count++;
            }

            System.out.println("You win!");
            out.writeUTF(code);
            // закрываем сокет
            serverSocket.close();

        } catch(Exception x) { x.printStackTrace(); }
    }

}
