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

	public static Node getFingerDetails(Finger finger) {
		return getFingerDetails(finger.getAddress());
	}

	public static Node getFingerDetails(Address address) {
		try {
			return client.getDetails(address.getIp(), address.getPort(), "DETAILS", Node.class);
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

	public static void updatePredecessor(Address address, Node node) {
		try {
			client.updatePredecessor(address.getIp(), address.getPort(), ServerCommands.UPDATE_PREDECESSOR.name(),
					node.getId(), node.getAddress().getIp(), node.getAddress().getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
