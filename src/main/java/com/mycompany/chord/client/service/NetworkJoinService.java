/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.service;

import com.mycompany.chord.client.model.Address;
import com.mycompany.chord.client.model.Finger;
import com.mycompany.chord.client.model.Node;
import com.mycompany.chord.client.state.ChordConfig;
import com.mycompany.chord.client.state.ChordState;
import com.mycompany.chord.client.state.UserConfig;

/**
 *
 * @author Charith.S
 */
public class NetworkJoinService {
	private static NetworkJoinService single_instance = null;
	public static NetworkJoinService getInstance() {
		if (single_instance == null)
			single_instance = new NetworkJoinService();

		return single_instance;
	}

	public void join(Address address) {
            try {
                    NodeDataServer nodeDataServer = new NodeDataServer();
                    nodeDataServer.start(UserConfig.getPort());
                    ChordState.setNodeDataServer(nodeDataServer);
            } catch (Exception e) {
                    e.printStackTrace();
            }
		Address myAddress = new Address(UserConfig.getUsername(), UserConfig.getIp(), UserConfig.getPort());
		Node node = new Node(myAddress);
		ChordState.setNode(node);
		if (address == null) {
			                 Finger finger = node.convertToFinger();
			for (int i = 0; i < ChordConfig.FINGER_TABLE_SIZE; i++) {
				node.getFingers().add(finger);
			}
			node.setPredecessor(finger);
		} else {
			initFingerTable(address);
			updateOthers();
		}
	}

	private void updateOthers() {
		Node myNode = ChordState.getNode();
		for (int i = 0; i < ChordConfig.FINGER_TABLE_SIZE; i++) {
			long pred = (long) (myNode.getId() - Math.pow(2, i));
			Node predecessor = myNode.findPredecessor(pred);
			FingerDetailService.updateFingerTable(predecessor.getAddress(), myNode.getId(), i, myNode);
		}
	}

	public void initFingerTable(Address address) {
		          Node myNode = ChordState.getNode();
		Node node1 = FingerDetailService.getFingerDetails(address);
		Finger successor = node1.findSuccessor(myNode.getFingerStarts().get(0));
		Node successorNodeDetails = FingerDetailService.getFingerDetails(successor);

		myNode.getFingers().add(successor);
		myNode.setPredecessor(successorNodeDetails.getSuccessor());
		// successor.setPredecessor(myNode.getId());

		for (int i = 0; i < ChordConfig.FINGER_TABLE_SIZE - 1; i++) {
			Long start = myNode.getFingerStarts().get(i + 1);
			if (start >= myNode.getId()
					&& start < myNode.getFingers().get(i).getNode()) {
				myNode.getFingers().add(myNode.getFingers().get(i));
			} else {
				myNode.getFingers().add(node1.findSuccessor(myNode.getFingerStarts().get(i+1)));
			}
		}
	}
}
