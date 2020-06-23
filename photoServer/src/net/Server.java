package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import utils.ServerHandle;

public class Server {

	private ServerSocket server;

	public Server() throws IOException {
		this.server = new ServerSocket(8000);
	}

//	监听和处理用户请求
	public void listening() throws IOException {
		while (true) {
			final Socket socket = server.accept();
			new Thread() {
				public void run() {
						try {
							new ServerHandle(
									new DataInputStream(socket.getInputStream()),
									new DataOutputStream(socket.getOutputStream())
							).handle();
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}.start();
		}
	}

	public static void main(String[] args) throws IOException {
		new Server().listening();
	}

}
