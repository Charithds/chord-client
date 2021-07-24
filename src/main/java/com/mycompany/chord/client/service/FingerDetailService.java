/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.service;

import com.mycompany.chord.client.constant.ServerCommands;
import com.mycompany.chord.client.model.Address;
import com.mycompany.chord.client.model.Finger;
import com.mycompany.chord.client.model.Node;
import java.io.IOException;

public class FingerDetailService {

    private static Client client = new Client();

    public static Node getFingerDetails(Finger finger, int hopCount) {
        return getFingerDetails(finger.getAddress(), hopCount);
    }

    public static Node getFingerDetails(Address address, int hopCount) {
        try {
            return client.getDetails(address.getIp(), address.getPort(), "DETAILS " + hopCount);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateFingerTable(Address address, long n, int i, Node node) {
        try {
            client.updateFingerTable(address.getIp(), address.getPort(), ServerCommands.UPDATE_FINGER_TABLE.name(), n, i,
                    node.getAddress().getIp(), node.getAddress().getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateFingerTableAboutUnreg(Address address, long n, int i, 
            Node node, Address successorAddress) {
        try {
            client.updateFingerTable(address.getIp(), address.getPort(), 
                    ServerCommands.UPDATE_FINGER_TABLE_WHEN_UNREG.name(), n, i,
                    node.getAddress().getIp(), node.getAddress().getPort(),
                    successorAddress.getIp(), successorAddress.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updatePredecessor(Address address, Node node) {
        try {
            client.updatePredecessor(address.getIp(), address.getPort(), ServerCommands.UPDATE_PREDECESSOR.name(),
                    node.getId(), node.getAddress().getIp(), node.getAddress().getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
