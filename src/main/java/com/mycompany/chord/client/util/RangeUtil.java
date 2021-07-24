/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.util;

import com.mycompany.chord.client.model.Node;
import com.mycompany.chord.client.state.ChordConfig;

/**
 *
 * @author Charith.S
 */
public class RangeUtil {

    public static boolean predecessorContition(long id, Node node) {
        if (node.getId() == node.getSuccessor().getNode()) {
            return false;
        }
        if (node.getSuccessor().getNode() < node.getId()) {
            if ((id > node.getId()) && id <= ChordConfig.HASH_BITS - 1) {
                return false;
            }
            if (id <= node.getSuccessor().getNode()) {
                return false;
            }
        } else {
            return !(id > node.getId() && id <= node.getSuccessor().getNode());
        }
        return true;
    }

    public static boolean closestPrecedingContition(long x, long n, long id) {
        if (x == n && n == id) {
            return false;
        }
        if (id < n) {
            if (x > n && x <= ChordConfig.HASH_BITS) {
                return true;
            }
            if (x <= id) {
                return true;
            }
        } else {
            if (x > n && x < id) {
                return true;
            }
        }
        return false;
    }

    public static boolean intiFingerTableCondition(long start, long myId, long node) {
        // start >= myNode.getId() && start < myNode.getFingers().get(i).getNode()
        if (myId == node) {
            return true;
        }
        if (node >= myId) {
            return start >= myId && start < node;
        } else {
            if (start >= myId && start <= ChordConfig.HASH_BITS) {
                return true;
            }
            if (start < node) {
                return true;
            }
        }
        return false;
    }
}
