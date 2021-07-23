/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.service;

import com.mycompany.chord.client.constant.HostConfiguration;
import com.mycompany.chord.client.constant.MessageType;
import com.mycompany.chord.client.model.Address;
import com.mycompany.chord.client.others.Message;
import com.mycompany.chord.client.others.Sender;
import com.mycompany.chord.client.state.ChordState;
import com.mycompany.chord.client.state.UserConfig;
import java.util.logging.Logger;

/**
 *
 * @author Charith.S
 */
public class NetworkRegisterService {
    private static final Logger logger = Logger.getLogger(NetworkRegisterService.class.getName());
    
    private static NetworkRegisterService me;
    private NetworkRegisterService() { }
    public static NetworkRegisterService getInstance() {
        if (me == null) {
            me = new NetworkRegisterService();
        }
        return me;
    }
    
    public void registerOnNetwork(String username, String ipAddress, String port) throws Exception {
        // resMsg = "Failed";
        String message = (new Message(MessageType.REG, ipAddress, Integer.valueOf(port), username)).getMessage();
        String response = Sender.getInstance().sendUDPMessage(message, HostConfiguration.BOOTSTRAP_IP, Integer.valueOf(HostConfiguration.BOOTSTRAP_PORT));

        if ("".equals(response)) {
            throw new Exception("Register on network failed: connection error!");
        }
        logger.info("Response:" + response);

        updateState(response, username, ipAddress, port);
    }

    private void updateState(String response, String username, String myIP, String myPort) throws Exception {
        String[] splitted = response.split(" ");

        int noOfNodes = Integer.parseInt(splitted[2]);
        HostConfiguration.myNodeNumber = noOfNodes;
        HostConfiguration.noOfNodes = HostConfiguration.myNodeNumber + 1;

        String[] peerIps;
        int[] peerPorts;
        switch (noOfNodes) {
            case 0:
                ChordState.setJoined(true);
                initializeNodes(username, myIP, myPort, null, null);
                break;
            case 1:
                peerIps = new String[1];
                peerPorts = new int[1];
                peerIps[0] = splitted[3];
                peerPorts[0] = Integer.parseInt(splitted[4]);
                initializeNodes(username, myIP, myPort, peerIps, peerPorts);
                ChordState.setJoined(true);
                break;
            case 2:
                peerIps = new String[2];
                peerPorts = new int[2];
                
                peerIps[0] = splitted[3];
                peerPorts[0] = Integer.parseInt(splitted[4]);
                
                peerIps[1] = splitted[5];
                peerPorts[1] = Integer.parseInt(splitted[6]);
                
                initializeNodes(username, myIP, myPort, peerIps, peerPorts);
                ChordState.setJoined(true);
                break;
            case 9996:
                ChordState.setJoined(false);
                throw new Exception("Register on network failed: failed, there is some error in the command!");
            case 9997:
                ChordState.setJoined(false);
                throw new Exception("Register on network failed: failed, already registered to you, unregister first!");
            case 9998:
                ChordState.setJoined(false);
                throw new Exception("Register on network failed: failed, registered to another user, try a different IP and port!");
            case 9999:
                ChordState.setJoined(false);
                throw new Exception("Register on network failed: failed, can’t register. BS full!");
            default:
                ChordState.setJoined(false);
                throw new Exception("Register on network failed: unknown error!");
        }
        
    }
    
    private void initializeNodes(String username, String myIP, String myPort, String[] peerIps, int[] peerPorts) {
        UserConfig.setIp(myIP);
        UserConfig.setPort(Integer.parseInt(myPort));
        UserConfig.setUsername(username);
        if (peerIps == null) {
            NetworkJoinService.getInstance().join(null);
        } else {
            Address address = new Address(null, peerIps[0], peerPorts[0]);
            NetworkJoinService.getInstance().join(address);
        }
    }
    
    public void unreg(String username, String ipAddress, String port) {
        // TODO: do something for running threads
        String message = (new Message(MessageType.UNREG, ipAddress, Integer.valueOf(port), username)).getMessage();
        System.out.println(message);
        Sender.getInstance().sendUDPMessage(message, HostConfiguration.BOOTSTRAP_IP, HostConfiguration.BOOTSTRAP_PORT);
        ChordState.setJoined(false);
    }
    
}
