package part_three.task_three;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.*;
import part_two.task_four.*;

public class ServerPointGenerator {
	private ServerSocket server;
	private Socket socket;
	private ExecutorService executor;
	private StartFrameExtends startFrame;
	private PointGenerator pointGenerator;
	private ObjectOutputStream objOut;
	private DataInputStream objIn;
	private InputStream in;
	private OutputStream out;
	
	public static void main(String[] args) {
		ServerPointGenerator server = new ServerPointGenerator();
	}

	public ServerPointGenerator() {
		try {
			executor = Executors.newCachedThreadPool();
			server = new ServerSocket(ServerConst.PORT);
			System.out.println("server start");
			while (true) {
				socket = server.accept();
				out = socket.getOutputStream();
				in = socket.getInputStream();
				objOut = new ObjectOutputStream(out);
				objIn = new DataInputStream(in);
				System.out.println("connected");
				pointGenerator = new PointGenerator(Color.GREEN);				
				Runnable taskSender = () -> {
					try {
						while (true) {
							sendGenerator();
							Thread.sleep(150);
						}
					} catch (IOException | InterruptedException e) {
						System.out.println(e);
					}
					System.out.println("task end");
										
				};
				executor.execute(taskSender);
				executor.execute(() -> {
					Scanner scIn = new Scanner(in);
					while (true) {
						if (scIn.hasNext()) {
							String click = scIn.nextLine();
							if (click.equals("click")) {
								pointGenerator.addPointToArray(false);
							}

						}
					}
										

				});
			}

		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	private void sendGenerator() throws IOException {	
		synchronized(pointGenerator.getPoints()) {
			objOut.writeObject(pointGenerator);
			objOut.flush();
			objOut.reset();
		}		
	}
	
	

}
