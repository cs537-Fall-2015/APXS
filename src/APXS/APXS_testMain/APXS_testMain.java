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
			Thread apxs_server = RoverThreadHandler.getRoverThreadHandler()
					.getNewThread(serverOne);

			// server begins listening
			apxs_server.start();

			// client one server sending messages to server
			APXSClient clientOne = new APXSClient(port_one, null); // notice
			// port_two
			Thread apxs_client = RoverThreadHandler.getRoverThreadHandler()
					.getNewThread(clientOne);

			// start the thread which communicates through sockets
			apxs_client.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
