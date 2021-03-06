///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.chord.client.others;
//
//import com.mycompany.chord.client.model.Finger;
//import com.mycompany.chord.client.service.ChordOperation;
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import static com.mycompany.chord.client.others.Sender.data;
//
//public class ChordThread implements Runnable {
//    private Node chordNode;
//    private DatagramSocket socket = null;
//
//    public ChordThread(Node chordNode, DatagramSocket socket) {
//        this.chordNode = chordNode;
//        this.socket = socket;
//    }
//
//    /**
//     * Method that will read/send messages. It should attempt to read PING/STORE/FIND_NODE/FIND_VALUE messages
//     */
//    public void run() {
//        System.out.println("Client connection established on port " + this.socket.getLocalPort());
//        while(true)
//        {
//            // Create readers and writers from socket
//            byte[] receive = new byte[65535];
//            DatagramPacket DpReceive = new DatagramPacket(receive, receive.length);
//            try {
//                socket.receive(DpReceive);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                Logger.getLogger(ChordThread.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            // Read input from client
//            String query;
//            if((query = data(receive).toString().trim()) != null) {
//                // Split the query on the : token in order to get the command and the content portions
//                String[] queryContents = query.split(" ", 3);
//                String command = queryContents[1];
//                String content = queryContents[2];
//                
//                System.out.println("Received: " + command + " " + content);
//                
//                switch (command) {
//                    case ChordOperation.FIND_VALUE: {
//                        String response = this.findValue(content);
//                        System.out.println("Sent: " + response);
//                        
//                        // Send response back to client
//                        String message = response;
//                        
//                        byte[] toSend  = message.getBytes();
//                        DatagramPacket packet =new DatagramPacket(toSend, toSend.length, DpReceive.getAddress(), DpReceive.getPort());
//                        try {
//                            socket.send(packet);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                            Logger.getLogger(ChordThread.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        
//                        
//                        break;
//                    }
//                    case ChordOperation.FIND_NODE: {
//                        String response = this.findNode(content);
//                        System.out.println("Sent: " + response);
//                        
//                        // Send response back to client
//                        String message = response;
//                        
//                        byte[] toSend  = message.getBytes();
//                        DatagramPacket packet =new DatagramPacket(toSend, toSend.length, DpReceive.getAddress(), DpReceive.getPort());
//                        try {
//                            socket.send(packet);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                            Logger.getLogger(ChordThread.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        
//                        
//                        break;
//                    }
//                    case ChordOperation.STORE: {
//                        this.chordNode.acquire();
//                        List<Finger> list = chordNode.getKey(content.split(" ")[0]);
//                        if(list == null)
//                        {
//                            list = new ArrayList<>();
//                        }
//                        for(int i = 0; i < Integer.valueOf(content.split(" ")[1]); i++)
//                        {
//                            list.add(new Finger(content.split(" ")[2*i+2], Integer.valueOf(content.split(" ")[2*i+3])));
//                        }
//                        this.chordNode.addKeys(content.split(" ")[0], list);
//                        
//                        // Release lock
//                        this.chordNode.release();
//                        // Send response back to client
//                        String message = ChordOperation.STOREOK + " " + "0";
//                        message = Message.customFormat("0000", message.length()) + " " + message;
//                        
//                        byte[] toSend  = message.getBytes();
//                        DatagramPacket packet =new DatagramPacket(toSend, toSend.length, DpReceive.getAddress(), DpReceive.getPort());
//                        try {
//                            socket.send(packet);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                            Logger.getLogger(ChordThread.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        
//                        
//                        break;
//                    }
//                    case ChordOperation.NODE_FOUND: {
//                        String response = this.findNode(content);
//                        System.out.println("Sent: " + response);
//                        
//                        // Send response back to client
//                        String message = response;
//                        
//                        byte[] toSend  = message.getBytes();
//                        DatagramPacket packet =new DatagramPacket(toSend, toSend.length, DpReceive.getAddress(), DpReceive.getPort());
//                        try {
//                            socket.send(packet);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                            Logger.getLogger(ChordThread.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        
//                        break;
//                    }
//                    case ChordOperation.JOIN: {
//                        // Parse address and port from message
//                        String[] contentFragments = content.split(" ");
//                        String address = contentFragments[0];
//                        int port = Integer.valueOf(contentFragments[1]);
//                        
//                        // Acquire lock
//                        this.chordNode.acquire();
//                        
//                        // Move fist predecessor to second
//                        this.chordNode.setSecondPredecessor(this.chordNode.getFirstPredecessor());
//                        
//                        // Set first predecessor to new finger received in message
//                        this.chordNode.setFirstPredecessor(new Finger(address, port));
//                        
//                        // Release lock
//                        this.chordNode.release();
//                        
//                        break;
//                    }
//                    case ChordOperation.REQUEST_PREDECESSOR: {
//                        // Return the first predecessor address:port
//                        String response = this.chordNode.getFirstPredecessor().getAddress() + " " + this.chordNode.getFirstPredecessor().getPort();
//                        System.out.println("Sent: " + response);
//                        
//                        // Send response back to client
//                        String message = response;
//                        
//                        byte[] toSend  = message.getBytes();
//                        DatagramPacket packet =new DatagramPacket(toSend, toSend.length, DpReceive.getAddress(), DpReceive.getPort());
//                        try {
//                            socket.send(packet);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                            Logger.getLogger(ChordThread.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        
//                        
//                        break;
//                    }
//                    case ChordOperation.PING_QUERY: {
//                        // Reply to the ping
//                        String response = ChordOperation.PING_RESPONSE;
//                        System.out.println("Sent: " + response);
//                        
//                        // Send response back to client
//                        String message = response;
//                        
//                        byte[] toSend  = message.getBytes();
//                        DatagramPacket packet =new DatagramPacket(toSend, toSend.length, DpReceive.getAddress(), DpReceive.getPort());
//                        try {
//                            socket.send(packet);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                            Logger.getLogger(ChordThread.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        
//                        break;
//                    }
//                }
//            }
//            
//            // Close connections
//        }
//    }
//
//    private String findValue(String query) {
//        
//        long queryId = Long.valueOf(query);
//
//        String response = "Not found.";
//
//        // If the query is greater than our predecessor id and less than equal to our id then we have the value
//        if (this.doesQueryIdBelongToCurrentNode(queryId)) {
//            System.out.println("Arrived On Node Responsible for File Key : "+query);
//            response = findKeyFromCurrentNode(query, this.chordNode);
//            
//        } else if (this.doesQueryIdBelongToNextNode(queryId)) { 
//            response = findKeyUsingFinger(this.chordNode.getFirstSuccessor(), query);
//            
//        } else { // We don't have the query so we must search our fingers for it
//            long minimumDistance = ChordOperation.RING_SIZE;
//            Finger closestPredecessor = null;
//
//            this.chordNode.acquire();
//
//            // Look for a node identifier in the finger table that is less than the key id and closest in the ID space to the key id
//            for (Finger finger : this.chordNode.getFingers().values()) {
//                long distance;
//
//                // Find clockwise distance from finger to query
//                if (queryId >= finger.getId()) {
//                    distance = queryId - finger.getId();
//                } else {
//                    distance = queryId + ChordOperation.RING_SIZE - finger.getId();
//                }
//
//                // If the distance we have found is smaller than the current minimum, replace the current minimum
//                if (distance < minimumDistance) {
//                    minimumDistance = distance;
//                    closestPredecessor = finger;
//                }
//            }
//
//            System.out.println("queryid: " + queryId + " minimum distance: " + minimumDistance + " on " + closestPredecessor.getAddress() + ":" + closestPredecessor.getPort());
//
//            response = findKeyUsingFinger(closestPredecessor, query);
//
//            this.chordNode.release();
//        }
//
//        return response;
//    }
//
//    private String findNode(String query) {
//        long queryId = Long.valueOf(query);
//
//        // Wrap the queryid if it is as big as the ring
//        if (queryId >= ChordOperation.RING_SIZE) {
//            queryId -= ChordOperation.RING_SIZE;
//        }
//
//        String response = "Not found.";
//
//        // If the query is greater than our predecessor id and less than equal to our id then we have the value
//        if (this.doesQueryIdBelongToCurrentNode(queryId)) {
//            response = ChordOperation.NODE_FOUND + " " + this.chordNode.getAddress() + " " + this.chordNode.getPort();
//            response = Message.customFormat("0000", response.length()) + " " + response;
//        } else if(this.doesQueryIdBelongToNextNode(queryId)) {
//            response = ChordOperation.NODE_FOUND + " " + this.chordNode.getFirstSuccessor().getAddress() + " " + this.chordNode.getFirstSuccessor().getPort();
//            response = Message.customFormat("0000", response.length()) + " " + response;
//        } else { // We don't have the query so we must search our fingers for it
//            long minimumDistance = ChordOperation.RING_SIZE;
//            Finger closestPredecessor = null;
//
//            this.chordNode.acquire();
//
//            // Look for a node identifier in the finger table that is less than the key id and closest in the ID space to the key id
//            for (Finger finger : this.chordNode.getFingers().values()) {
//                long distance;
//
//                // Find clockwise distance from finger to query
//                if (queryId >= finger.getId()) {
//                    distance = queryId - finger.getId();
//                } else {
//                    distance = queryId + ChordOperation.RING_SIZE - finger.getId();
//                }
//
//                // If the distance we have found is smaller than the current minimum, replace the current minimum
//                if (distance < minimumDistance) {
//                    minimumDistance = distance;
//                    closestPredecessor = finger;
//                }
//            }
//
//            System.out.println("queryid: " + queryId + " minimum distance: " + minimumDistance + " on " + closestPredecessor.getAddress() + ":" + closestPredecessor.getPort());
//
//            try {
//                // Open socket to chord node
//
//                DatagramSocket socket = new DatagramSocket();
//                // Send query to chord
//                String message = ChordOperation.FIND_NODE + " " + queryId;
//                message = Message.customFormat("0000", message.length()) + " " + message;
//                byte[] toSend  = message.getBytes();
//                InetAddress IPAddress; 
//                try {
//                    IPAddress = InetAddress.getByName(closestPredecessor.getAddress());
//                    DatagramPacket packet =new DatagramPacket(toSend, toSend.length, IPAddress, closestPredecessor.getPort());
//                    try {
//                        socket.send(packet);
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                        Logger.getLogger(ChordThread.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                } catch (UnknownHostException ex) {
//                    ex.printStackTrace();
//                    Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                
//                System.out.println("Sent: " + message);
//
//                byte[] receive = new byte[65535]; 
//                DatagramPacket DpReceive = new DatagramPacket(receive, receive.length); 
//                try {
//                    socket.receive(DpReceive);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                    Logger.getLogger(ChordThread.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//                // Read response from chord
//                String serverResponse = data(receive).toString();
//                        
//                
//                System.out.println("Response from node " + closestPredecessor.getAddress() + ", port " + closestPredecessor.getPort() + ", position " + " (" + closestPredecessor.getId() + "):");
//
//                response = serverResponse;
//
//                // Close connections
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            this.chordNode.release();
//        }
//
//        return response;
//    }
//
//    private boolean doesQueryIdBelongToCurrentNode(long queryId) {
//        boolean response = false;
//
//        // If we are working in a nice clockwise direction without wrapping
//        if (this.chordNode.getId() > this.chordNode.getFirstPredecessor().getId()) {
//            // If the query id is between our predecessor and us, the query belongs to us
//            if ((queryId > this.chordNode.getFirstPredecessor().getId()) && (queryId <= this.chordNode.getId())) {
//                response = true;
//            }
//        } else { // If we are wrapping
//            if ((queryId > this.chordNode.getFirstPredecessor().getId()) || (queryId <= this.chordNode.getId())) {
//                response = true;
//            }
//        }
//
//        return response;
//    }
//
//    private boolean doesQueryIdBelongToNextNode(long queryId) {
//        boolean response = false;
//
//        // If we are working in a nice clockwise direction without wrapping
//        if (this.chordNode.getId() < this.chordNode.getFirstSuccessor().getId()) {
//            // If the query id is between our successor and us, the query belongs to our successor
//            if ((queryId > this.chordNode.getId()) && (queryId <= this.chordNode.getFirstSuccessor().getId())) {
//                response = true;
//            }
//        } else { // If we are wrapping
//            if ((queryId > this.chordNode.getId()) || (queryId <= this.chordNode.getFirstSuccessor().getId())) {
//                response = true;
//            }
//        }
//
//        return response;
//    }
//    
//    private String findKeyFromCurrentNode(String queryId, Node currentNode){
//        String response = "Not found.";
//        Map<String, List<Finger>> nodeKeys = currentNode.getKeys();
//            
//        List<Finger> fileOwnerNodes = nodeKeys.get(String.valueOf(queryId));
//        
//        if(fileOwnerNodes!=null && fileOwnerNodes.size()>0){
//            String nodeList = getOwnerNodeList(fileOwnerNodes);
//            System.out.println("File with Key : "+queryId +" found. File Owners : "+nodeList);
//            response = ChordOperation.VALUE_FOUND + " " + fileOwnerNodes.size() + " " + nodeList;
//            response = Message.customFormat("0000", response.length()) + " " + response;
//        }else{
//            System.out.println("File with Key : "+queryId +" Not found.");
//            response = "VALUE_NOT_FOUND 0";
//            response = Message.customFormat("0000", response.length()) + " " + response;
//        }
//        return response;
//    }
//    
//    private String getOwnerNodeList(List<Finger> fileOwnerNodes){
//        StringBuilder sb = new StringBuilder();
//        fileOwnerNodes.forEach(node->{
//            sb.append(node.getAddress());
//            sb.append(" ");
//            sb.append(node.getPort());
//            sb.append(" ");
//        });
//        return sb.toString().trim();
//    }
//    
//    private String findKeyUsingFinger(Finger searchFinger, String key){
//        String response = "Not found.";
//        try {
//            // Open socket to chord node
//            DatagramSocket socket = new DatagramSocket();
//
//            // Send query to chord
//            String message = ChordOperation.FIND_VALUE + " " + key;
//            message = Message.customFormat("0000", message.length()) + " " + message;
//
//            byte[] toSend  = message.getBytes();
//            InetAddress IPAddress; 
//                try {
//                    IPAddress = InetAddress.getByName(searchFinger.getAddress());
//                    DatagramPacket packet =new DatagramPacket(toSend, toSend.length, IPAddress, searchFinger.getPort());
//                    try {
//                        socket.send(packet);
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                        Logger.getLogger(ChordThread.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                } catch (UnknownHostException ex) {
//                    ex.printStackTrace();
//                    Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            System.out.println("Sent: " + message);
//
//            byte[] receive = new byte[65535]; 
//            DatagramPacket DpReceive = new DatagramPacket(receive, receive.length); 
//            try {
//                socket.receive(DpReceive);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                Logger.getLogger(ChordThread.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            // Read response from chord
//            String serverResponse = data(receive).toString();
//
//            System.out.println("Response from node " + searchFinger.getAddress() + ", port " + searchFinger.getPort() + ", position " + " (" + searchFinger.getId() + "):");
//
//            response = serverResponse;
//
//            // Close connections
//            socket.close();
//        } catch (IOException e) {
//            
//            e.printStackTrace();
//        }
//        return response;
//    }
//}
//
