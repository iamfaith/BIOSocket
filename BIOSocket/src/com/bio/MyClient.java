package com.bio;

import com.util.InputOutStreamUitl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by faith on 16/7/11.
 */
public class MyClient {

    private Socket client;
    private OutputStream outputStream;
    private InputStream inputStream;

    class ReadThread implements Runnable {

        private InputStream inputStream;

        public ReadThread(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String line = InputOutStreamUitl.readLine(this.inputStream);
                    if (line != null)
                        System.out.println(line);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }

            }
        }
    }

    public void starClient(String ip, int port) {
        try {
            client = new Socket(ip, port);
            this.inputStream = client.getInputStream();
            this.outputStream = client.getOutputStream();
            Thread readThread = new Thread(new ReadThread(this.inputStream));
            readThread.setDaemon(true);
            readThread.start();
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                if (line.equals("q")) {
                    break;
                }
                System.out.println("send[" + line + "]");
                InputOutStreamUitl.writeSocketLine(this.outputStream, line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (client != null) {
                    client.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }


        }


    }

    public static void main(String[] args) {
        MyClient client = new MyClient();
        client.starClient("127.0.0.1", 5000);
    }
}
