package ie.atu.sw;

public class Runner {

	public static void main(String[] args) {
	
		String host = args.length > 0 ? args[0] : "localhost";
		
		if (host.compareTo("localhost") == 0){
			System.out.println("No host specified - using default 'localhost'");
		}
		
		ChatClient myClient = new ChatClient(host, 4321);
		myClient.Run();
		
	}
		
}
