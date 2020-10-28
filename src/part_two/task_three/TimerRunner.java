package part_two.task_three;

public class TimerRunner implements Runnable {
	private Object monitor = new Object();
	private StartFrame frame = null;

	TimerRunner(StartFrame frame) {
		this.frame = frame;
		frame.setMonitor(monitor);
	}

	public void run() {
		frame.setThread(Thread.currentThread());
		try {
			showTime();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void showTime() throws InterruptedException {

		synchronized (monitor) {
			while (true) {
				frame.setTimeText();
				Thread.sleep(200);
				if (!frame.getStart()) {
					monitor.wait();
				}
			}
		}
	}
}