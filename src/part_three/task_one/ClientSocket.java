package part_three.task_one;

import java.io.*;
import java.net.*;

public class ClientSocket {

	public static void main(String[] args) throws InterruptedException {
		try (
			Socket socket = new Socket("localhost", 3345);
			DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
			DataInputStream ois = new DataInputStream(socket.getInputStream());) {
			System.out.println("Client connected to socket.");
			
			oos.writeUTF("download");
			oos.flush();
			FileDownloader downolader = new FileDownloader(ois);
			
			while (!socket.isInputShutdown()) {				
				if(downolader.isDownload()) {					
					break;
				}else {
					downolader.downloadFile();
				}
			}
			
			System.out.println("download complete");			
			System.out.println("------------------------");
			oos.close();
			ois.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
