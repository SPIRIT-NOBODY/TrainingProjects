package part_two.task_one;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

public class StartFrame {

	JFrame startFrame;
	int frameSize = 800;

	double rotateRadius;
	int sizeSquare = 75;
	double centerX;
	double centerY;
	private double startFigureX;
	private double startFigureY;
	private float angle = 0F;

	FigurePanel figure;
	
	volatile private Color colorFirst =  Color.RED;
	volatile private Color colorSecond = Color.green;

	StartFrame() {
		init();
	}

	protected void init() {
		startFrame = new JFrame();
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setSize(frameSize, frameSize);
		startFrame.setResizable(false);
		startFrame.setLocationRelativeTo(null);

		figure = new FigurePanel(frameSize);

		startFrame.getContentPane().add(figure);
		startFrame.setVisible(true);
		
	}

	public void animation() {		
		startFigureX = centerX - rotateRadius * Math.cos(angle);
		startFigureY = centerY - rotateRadius * Math.sin(angle);
		figure.repaint();
		
		angle += 0.25;		
	}

	class FigurePanel extends JPanel {

		FigurePanel(int frameSize) {

			int tmpFrame = frameSize - 20;

			centerX = (double) (tmpFrame) / 2.0;
			centerY = (double) (tmpFrame) / 2.0;

			rotateRadius = centerX - sizeSquare * 2;

			startFigureX = centerX - rotateRadius;
			startFigureY = centerY;

		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			drawFigure(g2d);

		}

		protected void drawFigure(Graphics2D g2d ) {

			Shape ellipse = new Ellipse2D.Double(startFigureX, startFigureY, sizeSquare, sizeSquare);
			Shape rectangle = new Rectangle2D.Double(startFigureX, startFigureY, sizeSquare, sizeSquare);

			g2d.setColor(colorFirst);			
			g2d.fill(rectangle);
			g2d.setColor(colorSecond);
			g2d.fill(ellipse);

		}

	}

	public void setColorWrap() {
//		figure.setBackground(
//				new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
		
		this.colorFirst = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
		this.colorSecond = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
		
		
	}

}
