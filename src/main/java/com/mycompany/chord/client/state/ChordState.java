/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.state;

import com.mycompany.chord.client.model.Node;
import com.mycompany.chord.client.service.NodeDataServer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Charith.S
 */
public class ChordState {
    private static Node node;
    private static NodeDataServer nodeDataServer;
    private static String downloadPath;
    private static List<String> fileList = new ArrayList<>();
    private static List<Long> keyList = new ArrayList<>();
    private static boolean joined = false;

    public static Node getNode() {
        return node;
    }

    public static void setNode(Node node) {
        ChordState.node = node;
    }

    public static NodeDataServer getNodeDataServer() {
        return nodeDataServer;
    }

    public static void setNodeDataServer(NodeDataServer nodeDataServer) {
        ChordState.nodeDataServer = nodeDataServer;
    }

    /**
     * @return the downloadPath
     */
    public static String getDownloadPath() {
        return downloadPath;
    }

    /**
     * @param aDownloadPath the downloadPath to set
     */
    public static void setDownloadPath(String aDownloadPath) {
        downloadPath = aDownloadPath;
    }

    /**
     * @return the fileList
     */
    public static List<String> getFileList() {
        return fileList;
    }

    /**
     * @param aFileList the fileList to set
     */
    public static void setFileList(List<String> aFileList) {
        fileList = aFileList;
    }

    /**
     * @return the keyList
     */
    public static List<Long> getKeyList() {
        return keyList;
    }

    /**
     * @param aKeyList the keyList to set
     */
    public static void setKeyList(List<Long> aKeyList) {
        keyList = aKeyList;
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
}
