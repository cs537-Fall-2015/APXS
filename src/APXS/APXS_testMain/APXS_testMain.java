package APXS.APXS_testMain;

import java.io.IOException;

import generic.RoverThreadHandler;
import APXS.module.APXSClient;
import APXS.module.APXSServer;

public class APXS_testMain {
	public static void main(String[] args) {
		int port_one = 9017;

		try {
			// create a thread for module one
			APXSServer serverOne = new APXSServer(port_one);
			Thread server_1 = RoverThreadHandler.getRoverThreadHandler()
					.getNewThread(serverOne);

			// server begins listening
			server_1.start();

			// client one server sending messages to server
			APXSClient clientOne = new APXSClient(port_one, null); // notice
			// port_two
			Thread client_1 = RoverThreadHandler.getRoverThreadHandler()
					.getNewThread(clientOne);

			// start the thread which communicates through sockets
			client_1.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}