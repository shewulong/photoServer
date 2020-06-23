package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import utils.Handle;

public class Server {

	private ServerSocket server;

	public Server() throws IOException {
		this.server = new ServerSocket(8000);
	}

//	监听用户请求
	public void listening() throws IOException {
		while (true) {
			final Socket socket = server.accept();
			new Thread() {
				public void run() {
					try {
						Handle handle = new Handle(
								new ObjectInputStream(socket.getInputStream()),
								new ObjectOutputStream(socket.getOutputStream()));
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
