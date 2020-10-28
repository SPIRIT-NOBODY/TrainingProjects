package part_two.task_three;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

public class StartFrame {
	private Dimension size = new Dimension(400, 65);
	private JFrame startFrame = null;
	private JTextField time = null;
	private JButton button = null;
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	volatile private boolean start = true;
	private Thread thread = null;

	private static Object monitor = null;

	StartFrame() {

		startFrame = new JFrame("Timer");
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = startFrame.getContentPane();
		startFrame.setMinimumSize(new Dimension(500, 150));

		button = new JButton("Click me!");
		button.setSize(size);
		button.addMouseListener(new ClickMouseListener());

		time = new JTextField(dtf.format(LocalDateTime.now()));
		time.setDisabledTextColor(Color.BLACK);
		time.setEnabled(false);
		time.setMaximumSize(size);
		startFrame.setLayout(new FlowLayout());
		contentPane.add(button);
		contentPane.add(time);
		startFrame.pack();
		startFrame.setLocationRelativeTo(null);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				startFrame.setVisible(true);

			}
		});

	}

	class ClickMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			setStart();
			synchronized (monitor) {
				if (thread.getState() == Thread.State.WAITING) {
					monitor.notifyAll();					
				}
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

	synchronized public void setTimeText() {
		time.setText(dtf.format(LocalDateTime.now()));
	}

	public boolean getStart() {
		return start;
	}

	synchronized private void setStart() {
		start = !start;
	}

	public void setMonitor(Object monitor) {
		this.monitor = monitor;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

}
