/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.model;

import com.mycompany.chord.client.service.ChordState;

/**
 *
 * @author Charith.S
 */
public class Node {
    private long id;
    private Node successor;
    
    private Node findSuccessor(long id) {
        Node node = findPredecessor(id);
        return node.getSuccessor();
    }
    
    private Node findPredecessor(long id) {
        Node node = this;
        while (!(id > node.getId() && id <= node.getSuccessor().getId())) {
            node = node.closestPrecedingFinger(id);
        }
        return node;
    }
    
    public Node closestPrecedingFinger(long id) {
        int m = ChordState.getFingerTable().size();
        for (int i = m-1; i <= 0; i--) {
            Node node = ChordState.getFingerTable().get(i);
            if(node.getId() > this.id && node.getId() < id) {
                return node;
            }
        }
        return this;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the successor
     */
    public Node getSuccessor() {
        return successor;
    }

    /**
     * @param successor the successor to set
     */
    public void setSuccessor(Node successor) {
        this.successor = successor;
    }
    
}
