package part_two.task_four;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class OnePoint extends JPanel {
	private int corX = 0;
	private int corY = 0;
	private int pointNumber = 0;
	private boolean clear = true;
	private static final int POINT_OFFSET = 20;
	private int pointSize = 2;
	private JLabel label;
	private JPanel point;

	public OnePoint() {
		super();
		corX = (int) (Math.random() * 200) + POINT_OFFSET;
		corY = (int) (Math.random() * 100) + POINT_OFFSET;
		setBounds(corX, corY, 20, 10);
		setBackground(new Color(0, 0, 0, 1));
		point = new JPanel();
		point.setBounds(0, 0, pointSize, pointSize);
		setPointColor(Color.RED);
		add(point);
		setLayout(null);
		setVisible(true);
	}

	public int getCorX() {
		return corX;
	}

	public int getCorY() {
		return corY;
	}	

	public void setPointColor(Color color) {
		point.setBackground(color);
	}

	public int getCount() {
		return pointNumber;
	}

	public void setCount(int count) {
		this.pointNumber = count;
	}

	public boolean getClear() {
		return clear;
	}

	public void setClear(boolean clear) {
		this.clear = clear;
		if (!clear) {
			label = new JLabel();
			// label.setText(""+getCount());
			label.setBounds(2, 0, 10, 10);
			Font font = new Font(label.getFont().getName(), label.getFont().getStyle(), 8);
			label.setFont(font);
			add(label);
		}

	}

	public void setNumber() {
		label.setText("" + pointNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		OnePoint other = (OnePoint) obj;
		boolean res = this.corX == other.getCorX() && this.corY == this.getCorY();
		return res;
	}
}
