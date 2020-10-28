package part_one.task_three;

import java.awt.*;

import javax.swing.*;

public class VerticalLayout implements LayoutManager {
	private Dimension size = new Dimension();

	VerticalLayout(int width, int height) {
		size.width = width;
		size.height = height;
	}

	VerticalLayout() {
		this(100, 20);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return size;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return size;
	}

	@Override
	public void layoutContainer(Container parent) {
		Component[] list = parent.getComponents();
		int currentX = 5;
		int currentY = 5;
		int labelPrefWitdh = getMaxLabelWitdh(list);
		for (int i = 0; i < list.length; i++) {
			Dimension pref = list[i].getPreferredSize();
			if (list[i] instanceof JLabel) {
				list[i].setBounds(currentX, currentY, pref.width, pref.height);
				currentX += (labelPrefWitdh + 5);

			} else {
				list[i].setBounds(currentX, currentY, pref.width, pref.height);
				currentX = 5;
				currentY += 5;
				currentY += pref.height;
			}

		}

	}

	protected int getMaxLabelWitdh(Component[] list) {
		int maxWidth = 0;
		for (int i = 0; i < list.length; i++) {
			if (list[i] instanceof JLabel && i != list.length - 1) {
				Dimension pref = list[i].getPreferredSize();
				if (pref.width > maxWidth) {
					maxWidth = pref.width;
				}
			}
		}
		return maxWidth;
	}

}
