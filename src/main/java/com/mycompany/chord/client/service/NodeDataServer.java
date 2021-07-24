/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chord.client.service;


import java.io.IOException;
import java.net.ServerSocket;

public class NodeDataServer {
	private ServerSocket serverSocket;
        private Thread t;
        private Thread downloadListener;

	public void start(int port) throws IOException {
		Runnable runnable = () -> {
			try {
				serverSocket = new ServerSocket(port);
				while (true) {
					new ClientHandler(serverSocket.accept()).start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		t = new Thread(runnable);
		t.start();
                
		downloadListener = new Thread(new DownloadListener());
		downloadListener.start();
	}
        
        public void stop() {
            t.interrupt();
            downloadListener.interrupt();
        }
//		serverSocket = new ServerSocket(port);
//		clientSocket = serverSocket.accept();
//		out = new PrintWriter(clientSocket.getOutputStream(), true);
//		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//		Node node = new Node(55);
//		String bytes = objectMapper.writer().writeValueAsString(node);
//		String greeting = in.readLine();
//		out.println(bytes);
//		if ("hello server".equals(greeting)) {
//
//		}
//		else {
//			out.println("unrecognised greeting");
//		}
//	}



//	public static void main(String[] args) {
//		NodeDataServer server = new NodeDataServer();
//		try {
//			server.start(6666);
//
//			Client client = new Client();
//			client.startConnection("127.0.0.1", 6666);
//			String response = client.sendMessage("DETAILS");
//			System.out.println(response);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
