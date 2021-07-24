/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.chord.client.service.FingerDetailService;
import com.mycompany.chord.client.state.ChordConfig;
import com.mycompany.chord.client.util.RangeUtil;
import com.mycompany.chord.client.util.SHA1Hasher;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Charith.S
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Node {

    private long id;
    private Address address;

    private List<Finger> fingers = new ArrayList<Finger>(ChordConfig.FINGER_TABLE_SIZE);
    private List<Long> fingerStarts = new ArrayList<Long>(ChordConfig.FINGER_TABLE_SIZE);
    private List<Long> fingerIntervals = new ArrayList<Long>(ChordConfig.FINGER_TABLE_SIZE);
//	private Finger successor;
    private Finger predecessor;

    public Node() {
        this.id = -1;
    }

    public Node(Address address) {
        SHA1Hasher hasher = SHA1Hasher.getInstance();
        SHA1Hash hash = hasher.hash(address);
        this.id = hash.getLongValue();
        this.address = address;
        for (int i = 0; i < ChordConfig.FINGER_TABLE_SIZE; i++) {
            long start = (long) ((id + Math.pow(2, i)) % ChordConfig.HASH_BITS);
            fingerStarts.add(start);
        }
        for (int i = 0; i < ChordConfig.FINGER_TABLE_SIZE - 1; i++) {
            fingerIntervals.add(fingerStarts.get(i + 1) - fingerStarts.get(i));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Finger> getFingers() {
        return fingers;
    }

    public void setFingers(List<Finger> fingers) {
        this.fingers = fingers;
    }

    public List<Long> getFingerStarts() {
        return fingerStarts;
    }

    public void setFingerStarts(List<Long> fingerStarts) {
        this.fingerStarts = fingerStarts;
    }

    public List<Long> getFingerIntervals() {
        return fingerIntervals;
    }

    public void setFingerIntervals(List<Long> fingerIntervals) {
        this.fingerIntervals = fingerIntervals;
    }

    public Finger getSuccessor() {
        return fingers.get(0);
    }

//	public void setSuccessor(Finger successor) {
//		this.successor = successor;
//	}
    public Finger getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Finger predecessor) {
        this.predecessor = predecessor;
    }

    public Finger findSuccessor(long id) {
        Node node = findPredecessor(id);
        return node.getSuccessor();
    }

    public Node findPredecessor(long id) {
        Node node = this;
        while (RangeUtil.predecessorContition(id, node)) {
            node = node.closestPrecedingFinger(id);
        }
        return node;
    }

    public Node closestPrecedingFinger(long id) {
        int m = ChordConfig.FINGER_TABLE_SIZE;
        for (int i = m - 1; i >= 0; i--) {
            Finger node = fingers.get(i);
            if (RangeUtil.closestPrecedingContition(node.getNode(), this.id, id)) {
                return FingerDetailService.getFingerDetails(node);
            }
        }
        return this;
    }

    public void updateFingerTable(long n, int i, String ip, int port) {
        if (RangeUtil.intiFingerTableCondition(n, id, fingers.get(i).getNode())) {
            Address address = new Address("", ip, port);
            Node fingerDetails = FingerDetailService.getFingerDetails(address);
            fingers.set(i, fingerDetails.convertToFinger());
            if (!predecessor.getAddress().getIp().equals(ip) || predecessor.getAddress().getPort() != port) {
                FingerDetailService.updateFingerTable(predecessor.getAddress(), n, i, fingerDetails);
            }
        }
    }

    public Finger convertToFinger() {
        Finger finger = new Finger();
        finger.setAddress(this.address);
        finger.setNode(this.id);
        return finger;
    }

    public void updatePredecessor(long id, String ip, int port) {
        Address address = new Address(null, ip, port);
        Finger finger = new Finger();
        finger.setAddress(address);
        finger.setNode(id);
        this.predecessor = finger;
    }
}
