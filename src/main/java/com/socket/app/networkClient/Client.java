package com.socket.app.networkClient;

import com.socket.app.GameController;
import com.socket.models.pojo.Move;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client extends Thread{
    // Network data
    private static Socket socket = null;
    private static Scanner input = null;
    private static PrintWriter output = null;
    private static final int SERVICE_PORT = 1997;

    // Game data
    private static boolean canMove = true;

    @Override
    public void run() {
        if(establishConnection() && setupCommunicationStreams()) dispatchGame();
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
    private static void dispatchGame(){
        String response;

        do{
            response = input.nextLine();
            System.out.println(response);
            if (response.equals(Protocol.SIGNAL_OPPONENT_LOOSE)){
                GameController.tiles[0][0].drawLooseScreen();
            }
            if (response.startsWith(Protocol.SIGNAL_MOVE)) {
                if(response.lastIndexOf('/') != -1) {
                    String serializedMove = response.substring(response.lastIndexOf('/') + 1);
                    Move move = Move.deserializeFromString(serializedMove);
                    GameController.tiles[move.getX()][move.getY()].drawOpponentMove(move);
                }
                canMove = true;
            } else if (Protocol.SIGNAL_WAIT.equals(response)) {
                canMove = false;
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
    public void MakeMove(String serializedMove){
        if(canMove) output.println(Protocol.SIGNAL_CLIENT_MOVE + "/" + serializedMove);
    }

    public void sendLooseMessage() {
        output.println(Protocol.SIGNAL_OPPONENT_LOOSE);
    }
}