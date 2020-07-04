package net;

import java.awt.EventQueue;
import java.io.IOException;

import utils.ErrorLog;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						new Server().listening();
					} catch (IOException e) {
						ErrorLog.log(e);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
