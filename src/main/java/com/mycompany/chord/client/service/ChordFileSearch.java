/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.service;

import com.mycompany.chord.client.model.Address;
import com.mycompany.chord.client.model.Finger;
import com.mycompany.chord.client.model.SHA1Hash;
import java.util.logging.Logger;
import com.mycompany.chord.client.state.ChordState;
import com.mycompany.chord.client.util.SHA1Hasher;

public class ChordFileSearch {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private static ChordFileSearch me;

    public static ChordFileSearch getInstance() {
        if (me == null) {
            me = new ChordFileSearch();
        }
        return me;
    }

    private ChordFileSearch() {
    }

    public Address searchFile(String fullFileName) {
        SHA1Hash hash = SHA1Hasher.getInstance().hash(fullFileName);
        long fileKey = hash.getLongValue();
        if (fileKey >= ChordOperation.RING_SIZE) {
            fileKey -= ChordOperation.RING_SIZE;
        }
        return findKeyUsingFinger(fileKey);
    }

    private Address findKeyUsingFinger(long key) {
        try {
            Finger successor = ChordState.getNode().findSuccessor(key, 0);
            System.out.println("Key " + key + " should be there in " + successor.getNode());
            System.out.println("Hop Count is " + successor.getHopCount());
            return successor.getAddress();
        } catch (Exception e) {
            System.err.println("Error while finding successor");
            e.printStackTrace();
        }
        return null;
    }
    /*
    private List<Finger> decodeServerResponse(String serverResponse){
         String[] queryContents = serverResponse.split(" ");
         String command = queryContents[1];
         
         List<Finger> fingetList = new ArrayList<>();
         
         if(command.equals(ChordOperation.VALUE_FOUND)){
             int noOfNodes = Integer.valueOf(queryContents[2]);
             
             for(int i=0; i<noOfNodes; i++){
                  Finger fileOwner = new Finger(queryContents[3+(2*i)], Integer.valueOf(queryContents[4+(2*i)]));
                  fingetList.add(fileOwner);
             }
         }
         return fingetList;
    }
     */
}
