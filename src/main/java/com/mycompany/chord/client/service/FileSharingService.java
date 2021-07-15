/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.service;

import com.mycompany.chord.client.constant.HostConfiguration;
import com.mycompany.chord.client.model.Finger;
import com.mycompany.chord.client.others.NodeStabilizer;
import com.mycompany.chord.client.others.SHA1Hasher;
import com.mycompany.chord.client.ui.ChordMainFrame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Charith.S
 */
public class FileSharingService {
    private static FileSharingService me;
    private FileSharingService() { }
    public static FileSharingService getInstance() {
        if (me == null) {
            me = new FileSharingService();
        }
        return me;
    }
    
    private ChordFileSearch chordFileSearch;
    
    public void initializeFilesShared() {
        DefaultListModel listModel = new DefaultListModel();
        List<String> fileList = new ArrayList<>();

        ArrayList<String> zNames = new ArrayList<>();
        try {
            File myObj = new File(new File("").getAbsolutePath()+"\\resources\\FileNames.txt");

            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                zNames.add(data);
        }
        myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        Random rand = new Random();
        Collections.shuffle(zNames);
        int size = rand.nextInt(5)+ 1;
        List<Long> keyList = new ArrayList<>(size);
        for(int i = 0; i < size ; i++)
        {
            listModel.addElement(zNames.get(i));
            fileList.add(zNames.get(i));
            keyList.add(new SHA1Hasher(zNames.get(i)).getLong()) ;
        }
        
        ChordState.setFileList(fileList);
        ChordState.setKeyList(keyList);
    }
    
    public void publishToIndexServer() {
        Map<String, List<Finger>> keys = new HashMap<>();
        //Message String creation
        String message = "ADD:";
        message = message + String.join(":", ChordState.getFileList());

        for(int i = 0; i < ChordState.getFileList().size(); i++) {
            List<Finger> fingerList = new ArrayList<>();
            fingerList.add(new Finger(ChordState.getNode().getAddress(), ChordState.getNode().getPort()));
            keys.put(ChordState.getKeyList().get(i) + "", fingerList);
        }

        ChordState.getNode().setKeys(keys);

        chordFileSearch = new ChordFileSearch(ChordState.getNode());

        //publish to index server
        InetAddress IPAddress1;
        try {
            DatagramSocket socket= new DatagramSocket();
            byte[] toSend1  = message.getBytes();
            IPAddress1 = InetAddress.getByName("127.0.0.1");
            DatagramPacket packet =new DatagramPacket(toSend1, toSend1.length, IPAddress1, 4444);
            socket.send(packet);
        } catch (UnknownHostException ex) {
            Logger.getLogger(NodeStabilizer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChordMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
