package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import dao.BaseDao;
import utils.ErrorLog;

public class Server {

	private ServerSocket server;

	public Server() throws IOException {
		this.server = new ServerSocket(8000);
	}

//	监听和处理用户请求
	public void listening() throws IOException {
		BaseDao.init();
		while (true) {
			final Socket socket = server.accept();
			new Thread() {
				public void run() {
					try {
						Handle handle = new Handle(new BufferedReader(new InputStreamReader(socket.getInputStream())),
								new PrintStream(socket.getOutputStream()));
						handle.handle();
						handle = null;
						socket.close();
					} catch (Exception e) {
						ErrorLog.log(e);
					}
				}
			}.start();
		}
	}

	public static void main(String[] args) throws IOException {
		new Server().listening();
	}

}
