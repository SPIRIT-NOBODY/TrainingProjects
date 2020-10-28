package part_one.task_four;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

class StartFrame {

	private final int grindCount = 5;
	private MouseListener listener = new MouseEventListener();
	JFrame startFrame = new JFrame();
	
	public StartFrame() {
		
		startFrame.setLayout(new GridLayout(grindCount,grindCount));
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for (int i = 1; i <= grindCount * grindCount; i++) {
			JButton button = new JButton(""+i);			
			button.addMouseListener(listener);
			startFrame.add(button);
		}
		startFrame.pack();
		startFrame.setMinimumSize(startFrame.getPreferredSize());
		startFrame.setSize(grindCount *100 , grindCount*100);
		startFrame.setLocationRelativeTo(null);
		startFrame.setVisible(true);
	}
	
	class MouseEventListener extends  MouseAdapter {
		private String defaultText;
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			JButton target = (JButton) e.getSource();			
			target.setText(defaultText);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {	
			
			JButton target = (JButton) e.getSource();
			defaultText = target.getText();			
			target.setText("Clicked!");
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			
			JButton target = (JButton) e.getSource();
			target.setBackground(null);
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			
			JButton target = (JButton) e.getSource();
			target.setBackground(Color.GREEN);
		}
	}
}
