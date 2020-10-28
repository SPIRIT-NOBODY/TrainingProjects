package part_three.task_two;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Client {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Scanner scanner;
	private ExecutorService executor;
	private String clientMessage;
	private String clientName;

	public static void main(String[] args) {
		Client client = new Client();
		System.out.println("exit");
		System.exit(1);
	}

	public Client() {
		executor = Executors.newFixedThreadPool(1);
		scanner = new Scanner(System.in);
		System.out.println("Enter your nickname>");
		while (scanner.hasNext()) {
			String entered = scanner.nextLine();
			if (!entered.trim().equals("")) {
				clientName = entered.trim();
				break;
			}
		}
		try {
			socket = new Socket(ServerConst.HOST, ServerConst.PORT);
			String consoleEncoding = System.getProperty("sun.stdout.encoding");
			if (consoleEncoding == null) {
				consoleEncoding = "UTF-8";
			}
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), consoleEncoding));
			out = new PrintWriter(socket.getOutputStream());
			sendToServer(clientName);
			Handler handler = new Handler();
			executor.execute(handler);
			clientInput();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendToServer(String msg) {
		out.println(msg);
		out.flush();
	}

	private void clientInput() {
		while (true) {
			if (scanner.hasNext()) {
				clientMessage = scanner.nextLine();
				sendToServer(clientMessage);
				if (clientMessage.equals(ServerConst.EXIT_MESSAGE)) {
					break;
				}
			}
		}
	}

	private class Handler implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					String serverMsg = null;
					if ((serverMsg = in.readLine()) != null) {
						if (serverMsg.equals(ServerConst.EXIT_MESSAGE)) {
							break;
						}
						System.out.println(serverMsg);
					}
					Thread.sleep(100);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
