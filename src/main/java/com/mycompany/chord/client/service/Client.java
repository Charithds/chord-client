/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 *
 * @author Charith.S
 */

public class Client {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	public ObjectMapper objectMapper;

	public Client() {
		objectMapper = new ObjectMapper();
	}

	public <T> T getDetails(String ip, int port, String command, Class<T> tClass) throws IOException {
		startConnection(ip, port);
		String response = sendMessage(command);
		stopConnection();
		return objectMapper.readValue(response, tClass);
	}

	public void updateFingerTable(String ip, int port, String command, long n, int i, String sourceIp, int sourcePort)
			throws IOException {
		startConnection(ip, port);
		String response = sendMessage(command+ " " + n + " " + i + " " + sourceIp  + " " + sourcePort);
		stopConnection();
		objectMapper.readValue(response, Object.class);
	}

	public void startConnection(String ip, int port) throws IOException {
		clientSocket = new Socket(ip, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public String sendMessage(String msg) throws IOException {
		out.println(msg);
		String resp = in.readLine();
		return resp;
	}

	public void stopConnection() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
	}

	public void updatePredecessor(String ip, int port, String command, long id, String sourceIp, int sourcePort) throws IOException {
		startConnection(ip, port);
		String response = sendMessage(command+ " " + id + " " + sourceIp  + " " + sourcePort);
		stopConnection();
		objectMapper.readValue(response, Object.class);
	}
}
