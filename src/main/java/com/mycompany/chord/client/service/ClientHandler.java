/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.chord.client.constant.ServerCommands;
import com.mycompany.chord.client.model.Node;
import com.mycompany.chord.client.state.ChordState;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
public class ClientHandler extends Thread {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private ObjectMapper objectMapper;

	public ClientHandler(Socket socket) {
		this.clientSocket = socket;
		this.objectMapper = new ObjectMapper();
	}

	public void run() {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));

			String inputLine = in.readLine();
			System.out.println("Command received:" + inputLine);
			String command = "";
			if (inputLine != null) {
				command = inputLine.split(" ")[0];
				// inputLine = " ";
			}
			ServerCommands serverCommand = ServerCommands.valueOf(command);
			switch (serverCommand) {
				case DETAILS:
                                        Node node = ChordState.getNode();
					if (node != null) {
                                            node.setHopCount(Integer.parseInt(inputLine.split(" ")[1])+1);
						String message = objectMapper.writer().writeValueAsString(node);
						out.println(message);
                                                node.setHopCount(0);
					} else {
						String message = objectMapper.writer().writeValueAsString(new HashMap<>());
						out.println(message);
					}
					break;
				case UPDATE_FINGER_TABLE:
					String[] args = inputLine.split(" ");
					node = ChordState.getNode();
					node.updateFingerTable(Long.parseLong(args[1]), Integer.parseInt(args[2]), args[3], Integer.parseInt(args[4]));
					String message = objectMapper.writer().writeValueAsString(new HashMap<>());
					out.println(message);;
					break;
                                case UPDATE_FINGER_TABLE_WHEN_UNREG:
					args = inputLine.split(" ");
					node = ChordState.getNode();
					node.updateFingerTableAfterRemoval(Long.parseLong(args[1]), Integer.parseInt(args[2]), args[3], Integer.parseInt(args[4]), args[5], Integer.parseInt(args[6]));
					message = objectMapper.writer().writeValueAsString(new HashMap<>());
					out.println(message);;
					break;
				case UPDATE_PREDECESSOR:
					args = inputLine.split(" ");
					node = ChordState.getNode();
					node.updatePredecessor(Long.parseLong(args[1]), args[2], Integer.parseInt(args[3]));
					message = objectMapper.writer().writeValueAsString(new HashMap<>());
					out.println(message);;
					break;
                                case ADD_FILE_TO_NODE:
					args = inputLine.split(" ");
                                        FileSharingService.getInstance().addFileToNode(args[1].replaceAll("_", " "), Long.parseLong(args[2]));
					message = objectMapper.writer().writeValueAsString(new HashMap<>());
					out.println(message);;
					break;
                                case UPDATE_NODE_FILES:
                                        FileSharingService.getInstance().publishToCorrectNodes(ChordState.getFileList(),
                                                ChordState.getKeyList());
					message = objectMapper.writer().writeValueAsString(new HashMap<>());
					out.println(message);;
					break;
				default:
					message = objectMapper.writer().writeValueAsString(new HashMap<>());
					out.println(message);;
			}

			in.close();
			out.close();
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

