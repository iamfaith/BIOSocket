package com.bio;

import com.util.InputOutStreamUitl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by faith on 16/7/11.
 */
public class Myserver {
    private WeakHashMap<Socket, String> map = new WeakHashMap<Socket, String>();


    public static void main(String[] args) {
        Myserver myserver = new Myserver();
        myserver.startServer(5000);
    }

    class AcceptThread implements Runnable {
        private Socket socket;
        private InputStream inputStream = null;
        private OutputStream outputStream = null;
        private String key;

        public AcceptThread(Socket socket) {
            this.socket = socket;
            this.key = socket.getInetAddress().getHostName() + ":" + socket.getPort();
            map.put(socket, this.key);

        }

        @Override
        public void run() {
            try {

                this.inputStream = socket.getInputStream();
                this.outputStream = socket.getOutputStream();

                System.out.println(this.key + " is on line");
                while (true) {

                    String line = InputOutStreamUitl.readLine(this.inputStream);
                    if (line == null) {
                        break;
                    }

                    System.out.println(this.key + " says: " + line);
                    for (Map.Entry<Socket, String> entry : map.entrySet()) {

                        try {
                            Socket tempSocket = entry.getKey();
                            if (tempSocket != null && !this.key.equals(entry.getValue())) {
                                InputOutStreamUitl.writeSocketLine(tempSocket.getOutputStream(), this.key + " says: " + line);
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }



            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    System.out.println(this.key + " is off line");

                    map.remove(this.socket);

                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (outputStream != null) {
                        outputStream.close();
                    }

                    if (socket != null) {
                        socket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }

    public void startServer(int port) {
        ServerSocket serverSocket = null;
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();
                AcceptThread acceptThread = new AcceptThread(socket);
                executorService.execute(acceptThread);

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }


}
