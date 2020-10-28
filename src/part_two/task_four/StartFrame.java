package part_two.task_four;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;

public class StartFrame {
	protected static int maxMillisec = 2501;
	protected static int minMillisec = 500;
	protected JFrame startFrame = new JFrame();
	protected JButton button = new JButton("Click me");
	protected Dimension size = new Dimension(360, 320);
	protected JPanel panel = new JPanel();
	protected Container pane;
	protected ExecutorService executor;

	public StartFrame() {
		executor = Executors.newCachedThreadPool();
		startFrame.setSize(size);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = startFrame.getContentPane();
		pane.setLayout(null);
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		panel.setBounds(50, 50, 250, 150);
		pane.add(panel);
		button.setBounds(120, 230, 100, 35);
		pane.add(button);
		PointGenerator pointGenerator = new PointGenerator(Color.RED);
		pointGenerator.setPanel(this);
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				pointGenerator.addPointToArray(false);
			}
		});
		startFrame.setLocationRelativeTo(null);
		startFrame.setMinimumSize(size);
		startFrame.pack();
		startFrame.setResizable(false);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				startFrame.setVisible(true);
			}
		});

		executor.execute(pointGenerator);
		
	}

	public Container getPanel() {
		return panel;
	}
}
