///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.chord.client.others;
//
//import com.mycompany.chord.client.constant.HostConfiguration;
//import com.mycompany.chord.client.service.ChordState;
//import java.io.IOException;
//import java.net.DatagramSocket;
//
//public class NodeListener implements Runnable {
//    private Node chordNode;
//
//    public NodeListener(Node chordNode) {
//        this.chordNode = chordNode;
//    }
//
//    public void run() {
//        try {
//            // Listen for connections on port
////            ServerSocket serverSocket = new ServerSocket(this.chordNode.getPort());
//            DatagramSocket serverSocket = new DatagramSocket(ChordState.getPort());
//            // Continuously loop for connections
//            
//            new Thread(new ChordThread(this.chordNode, serverSocket)).start();
//            
//        } catch (IOException e) {
//            System.err.println("error when listening for connections");
//            e.printStackTrace();
//        }
//    }
//}
