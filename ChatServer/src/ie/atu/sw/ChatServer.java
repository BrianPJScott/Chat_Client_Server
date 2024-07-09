package ie.atu.sw;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatServer {

	ServerSocket myServer;
	Socket myClient;
	boolean finished = false;
	String host = "localhost";
	int port = 4321;

	BufferedReader input;
	PrintWriter output;
	Scanner myScanner;

	public ChatServer(int newPort) {

		this.port = newPort;

	}

	public void Run() {

		System.out.println("Creating server on " + host + " : " + port);
		System.out.println("Waiting for client to connect...");

		try {

			myServer = new ServerSocket(port);
			myClient = myServer.accept();
			System.out.println("Client connected! - Port: " + myClient.getPort());
			output = new PrintWriter(myClient.getOutputStream());
			input = new BufferedReader(new InputStreamReader(myClient.getInputStream()));
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
							System.out.println("\rClient: " + myMessage + "\n>> ");
							myMessage = input.readLine();
							if (myMessage != null && myMessage.compareTo("\\q") == 0) {
								finished = true;
								System.out.println("Quitting... Press enter to close.");
							}
						}

						System.out.println("Disconnected");
						output.close();
						myClient.close();
						myServer.close();
						
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