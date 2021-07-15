/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.service;

import com.mycompany.chord.client.others.Node;
import java.util.List;

/**
 *
 * @author Charith.S
 */
public class ChordState {
//    private static ChordState me;
//    private ChordState() { }
//    public static ChordState getInstance() {
//        if (me == null) {
//            me = new ChordState();
//        }
//        return me;
//    }
    
    private static Node node;
    private static boolean joined;
    private static List<String> fileList;
    private static List<Long> keyList;
    private static String myIP;
    private static int port;

    /**
     * @return the node
     */
    public static Node getNode() {
        return node;
    }

    /**
     * @param aNode the node to set
     */
    public static void setNode(Node aNode) {
        node = aNode;
    }

    /**
     * @return the joined
     */
    public static boolean isJoined() {
        return joined;
    }

    /**
     * @param aJoined the joined to set
     */
    public static void setJoined(boolean aJoined) {
        joined = aJoined;
    }

    /**
     * @return the fileList
     */
    public static List<String> getFileList() {
        return fileList;
    }

    /**
     * @param fileList the fileList to set
     */
    public static void setFileList(List<String> afileList) {
        fileList = afileList;
    }

    /**
     * @return the keyList
     */
    public static List<Long> getKeyList() {
        return keyList;
    }

    /**
     * @param keyList the keyList to set
     */
    public static void setKeyList(List<Long> aKeyList) {
        keyList = aKeyList;
    }

    /**
     * @return the myIP
     */
    public static String getMyIP() {
        return myIP;
    }

    /**
     * @param aMyIP the myIP to set
     */
    public static void setMyIP(String aMyIP) {
        myIP = aMyIP;
    }

    /**
     * @return the port
     */
    public static int getPort() {
        return port;
    }

    /**
     * @param aPort the port to set
     */
    public static void setPort(int aPort) {
        port = aPort;
    }
}
