package part_three.task_two;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Написать чат «сервер — много клиентов». Можно в консольном варианте.
 */
public class ChatServerSocket {
	ServerSocket server = null;
	Socket clientSocket = null;
	ExecutorService executor;
	private static volatile int userCount = 0;
	
	List<Handler> clients = Collections.synchronizedList( new ArrayList<>());

	public static void main(String[] args) {
		ChatServerSocket chatStart = new ChatServerSocket();
	}

	public ChatServerSocket() {
		executor = Executors.newCachedThreadPool();
		try {
			server = new ServerSocket(ServerConst.PORT);
			System.out.println("Server started.");
			while (true) {
				clientSocket = server.accept();
				Handler client = new Handler(clientSocket);
				clients.add(client);
				executor.execute(client);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void sendMessageToAll(String msg) {
		for (Handler hand : clients) {
			hand.sendMessage(msg);
		}
	}
	
	private void sendMessageToAll(String msg, Handler ignore) {
		for (Handler hand : clients) {
			if(!hand.equals(ignore)) {
				hand.sendMessage(msg);
			}
		}
	}

	private class Handler implements Runnable {		
		private Socket socket;
		private PrintWriter out;
		private Scanner in;
		private String clientName;
		public Handler(Socket socket) {
			userCount++;
			this.socket = socket;
			try {
				out = new PrintWriter(socket.getOutputStream());				
				in = new Scanner(socket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				getClientName();
				sendMessage(ServerConst.HELLO_SERVER_MESSAGE.replaceAll("##USER_NAME##", clientName));
				sendMessageToAll(clientName + " connected", this);
				while (true) {
					if (in.hasNext()) {
						String clientMessage = in.nextLine();
						if (clientMessage.equalsIgnoreCase(ServerConst.EXIT_MESSAGE)) {
							sendMessage(clientMessage);
							break;
						}
						sendMessageToAll(clientName + ": "+clientMessage,this);
					}
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				userCount--;
				clients.remove(this);
				sendMessageToAll(clientName + " leave chat");
			}

		}

		public void sendMessage(String msg) {
			out.println(msg);
			out.flush();
		}
		
		private void getClientName() {
			while(in.hasNext()) {
				String input = in.nextLine();
				if(!input.trim().equals("")) {
					clientName = input;
					break;
				}
			}
		}

	}

}
