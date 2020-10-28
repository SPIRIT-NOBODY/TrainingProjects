package part_three.task_one;

import java.io.*;
import java.net.*;

public class Server {	
	
	public static void main(String[] args) throws InterruptedException {
		// стартуем сервер на порту 3345
		Server server = new Server();
		try (ServerSocket serverSocket = new ServerSocket(3345)) {
			
			System.out.println("Server running");
			Socket client = serverSocket.accept();
			System.out.println("client connect");			
			DataInputStream clientInput = new DataInputStream(client.getInputStream());
			System.out.println("Input connect");
			DataOutputStream clientOutput = new DataOutputStream(client.getOutputStream());
			System.out.println("Output connect");			
			FileUploader loader = new FileUploader(clientOutput);			
					
			if (!client.isClosed()) {
				System.out.println("server wait..");				
				if(clientInput.readUTF().equals("download")) {
					System.out.println("upload file progress");
					loader.uploadFile();
					System.out.println("file uploaded");
					clientOutput.write(-1);
					clientOutput.flush();
				}
			}
			System.out.println("------------------------");
			clientInput.close();
			clientOutput.close();
			client.close();
			serverSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
}
