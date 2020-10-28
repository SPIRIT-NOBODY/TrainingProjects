package part_two.task_four;

import java.awt.*;
import java.io.Serializable;
import java.time.*;
import java.time.format.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class PointGenerator implements Runnable, Serializable {
	private static final long serialVersionUID = 112983719287114L;
	protected static int maxMillisec = 2501;
	protected static int minMillisec = 500;
	private Container panel;
	private Color pointColor;
	private int sleep;
	private int count = 1;
	private volatile boolean loop = true;
	volatile protected List<OnePoint> points;

	public PointGenerator(Color pointColor) {
		this.pointColor = pointColor;
		points = Collections.synchronizedList(new ArrayList<>());		
	}

	@Override
	public void run() {
		while (loop) {
			sleep = (int) (Math.random() * (maxMillisec - minMillisec)) + minMillisec;
			try {
				Thread.sleep(sleep);
				addPointToPanel();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Thread.currentThread().interrupt();
	}

	public int getSleep() {
		return sleep;
	}

	public void stopLoop() {
		loop = false;
	}

	public List<OnePoint> getPoints() {
		return points;
	}

	public void setPoints(List<OnePoint> points) {
		synchronized(this.points) {
			for (OnePoint p : points) {
				if (!p.getClear() && !this.points.contains(p)) {
					this.points.add(p);
				}
			}
		}
	}

	public void setPanel(StartFrame frame) {
		panel = frame.getPanel();
	}

	public void addPointToArray(boolean b) {
		synchronized (points) {
			OnePoint p = new OnePoint();
			p.setPointColor(pointColor);
			p.setClear(b);
			points.add(p);
		}		
	}

	public void addPointToPanel() throws InterruptedException {
		addPointToArray(true);		
		synchronized(points) {
			Iterator<OnePoint> it = points.listIterator();
			while(it.hasNext()) {
				OnePoint p = it.next();
				int index = points.indexOf(p);	
				if (!p.getClear() && p.getCount() == 0) {									
					p.setCount(count);
					p.setNumber();
					count++;
				}
				List<Component> comp = Arrays.asList(panel.getComponents());
				if(!comp.contains(p)) {
					panel.add(p);
				}
			}
			panel.repaint();
			Thread.sleep(250);
			removePointFromPanel();
		}
		
	}	

	private void removePointFromPanel() {
		for (OnePoint p : new ArrayList<>(points)) {
			if (p.getClear()) {
				panel.remove(p);
				points.remove(p);
			}
		}
		panel.repaint();
	}
	
}
