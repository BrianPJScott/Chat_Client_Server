package ie.atu.sw;

public class Runner {

	static final int PORT = 4321;
	
	public static void main(String[] args) {

		ChatServer myServer = new ChatServer(PORT);
		myServer.Run();

	}

}
