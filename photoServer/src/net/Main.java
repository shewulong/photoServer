package net;

import java.io.IOException;

import utils.ErrorLog;

public class Main {

	public static void main(String[] args) {
		try {
			new Server().listening();
		} catch (IOException e) {
			ErrorLog.log(e);
		}
	}

}
