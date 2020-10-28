package part_three.task_three;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.*;

import part_two.task_four.*;

public class StartFrameExtends extends StartFrame implements Serializable {
	protected JButton buttonServer = new JButton("Connect");
	protected boolean connected = false;
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	volatile protected PointGenerator serverPointGenerator;
	Handler handler;

	StartFrameExtends() {
		super();
		button.setBounds(50, 230, 100, 35);
		buttonServer.setBounds(200, 230, 100, 35);
		pane.add(buttonServer);
		addServerButtonListener();
	}

	private void addServerButtonListener() {

		MouseAdapter genereteServer = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				handler.sendClickToServer("click");
			}
		};
		MouseAdapter connectedServer = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("connected to server");
				JButton target = (JButton) e.getComponent();
				target.setEnabled(false);
				try {
					connectedToServer();
					buttonServer.removeMouseListener(this);
					buttonServer.addMouseListener(genereteServer);
					target.setText("Click me!");
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					System.out.println("connected to server is fail");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} finally {
					target.setEnabled(true);
				}
			}
		};
		buttonServer.addMouseListener(connectedServer);
	}

	private void connectedToServer() throws UnknownHostException, IOException, ClassNotFoundException {
		socket = new Socket(ServerConst.HOST, ServerConst.PORT);
		out = socket.getOutputStream();
		in = socket.getInputStream();
		handler = new Handler(socket);
		executor.execute(handler);
	}

	private class Handler implements Runnable, Serializable {

		private ObjectInputStream objIn;
		private ObjectOutputStream objOut;

		public Handler(Socket socket) throws IOException, ClassNotFoundException {

			objOut = new ObjectOutputStream(out);
			objIn = new ObjectInputStream(in);
			getGeneratorFromServer();

		}

		@Override
		public void run() {
			while (true) {
				try {
					getGeneratorFromServer();
					Thread.sleep(150);
				} catch (IOException | ClassNotFoundException | InterruptedException e) {
					e.printStackTrace();
					break;
				}
			}
			try {
				objIn.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("end handler run");
		}

		private void getGeneratorFromServer() throws ClassNotFoundException, IOException {
			PointGenerator reader = (PointGenerator) objIn.readObject();
			if (reader != null) {
				if (serverPointGenerator == null) {
					serverPointGenerator = reader;
					serverPointGenerator.setPanel(StartFrameExtends.this);
					executor.execute(serverPointGenerator);
				} else {
					if(!reader.getPoints().equals(serverPointGenerator.getPoints())) {
						serverPointGenerator.setPoints(reader.getPoints());
					}
				}
			}
		}

		public void sendClickToServer(String click) {
			PrintWriter prOut = new PrintWriter(out);
			prOut.println(click);
			prOut.flush();
		}
	}
}
