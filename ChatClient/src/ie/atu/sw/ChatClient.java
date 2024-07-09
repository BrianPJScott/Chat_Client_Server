package ie.atu.sw;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

	boolean finished = false;
	String host = "localhost";
	int port = 4321;

	ServerSocket myServer;
	Socket mySocket;
	BufferedReader input;
	PrintWriter output;
	Scanner myScanner;

	public ChatClient(String newHost, int newPort) {

		this.host = newHost;
		this.port = newPort;

	}

	public void Run() {

		System.out.println("Connecting to server on " + host + " : " + port);

		try {

			mySocket = new Socket(host, port);
			System.out.println("Connected to server!");
			output = new PrintWriter(mySocket.getOutputStream());
			input = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			myScanner = new Scanner(System.in);

			Thread myWriter = new Thread(new Runnable() {

				String myMessage;

				@Override
				public void run() {
					while (!finished) {
						System.out.print(">> ");
						myMessage = myScanner.nextLine();
						output.println(myMessage);
						output.flush();
						if (myMessage.compareTo("\\q") == 0) {
							finished = true;
							System.out.println("Quitting... Press enter to close.");
						}
					}
				}

			});

			Thread myReader = new Thread(new Runnable() {

				String myMessage;

				@Override
				public void run() {
					try {
						myMessage = input.readLine();

						while (myMessage != null && !finished) {
							System.out.println("Server: " + myMessage);
							myMessage = input.readLine();
							if (myMessage != null && myMessage.compareTo("\\q") == 0) {
								finished = true;
								System.out.println("Quitting... Press enter to close.");
							}
						}
					} catch (IOException ioe) {
						finished = true;
					}

				}
			});

			myWriter.start();
			myReader.start();

		} catch (IOException ioe) {
			finished = true;
		}
	}
}