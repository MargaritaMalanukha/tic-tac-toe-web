package com.socket.app.networkClient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client extends Thread{
    private static Socket socket = null;
    private static Scanner input = null;
    private static PrintWriter output = null;
    private static final int SERVICE_PORT = 1997;

    @Override
    public synchronized void start() {
        if(establishConnection() && setupCommunicationStreams()) playGame();
        closeConnection();
    }

    // clientStages
    private static boolean establishConnection(){
        try {
            System.out.print("Establishing server connection --> ");
            InetAddress serverAddress = InetAddress.getByName("192.168.0.101");
            socket = new Socket(serverAddress, SERVICE_PORT);
            System.out.println("SUCCESS");
            return true;
        }catch (Exception e){
            System.out.println("FAIL");
            return false;
        }
    }
    private static boolean setupCommunicationStreams(){
        try {
            System.out.println("Preparing communication streams --> ");
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(),true);
            System.out.println("SUCCESS");
            return true;
        } catch (IOException ioEx) {
            System.out.println("FAIL");
            return false;
        }
    }
    private static void playGame(){
        String move,response;

        do{
            response = input.nextLine();
            System.out.println(response);
            switch (response){
                case Protocol.SIGNAL_MOVE: {
                    break;
                }
                case Protocol.SIGNAL_WAIT: {
                    break;
                }
                default:{
                    break;
                }
            }
        }while (!(response.equals(Protocol.SIGNAL_END)));
    }
    private static void closeConnection(){
        try {
            System.out.println("Closing connection --> ");
            socket.close();
            System.out.println("SUCCESS");
        }catch (IOException ioEx){
            System.out.println("FAIL");
        }
    }

    // methods
    public void MakeMove(){
        output.println("MAKING MOVE");
    }
}