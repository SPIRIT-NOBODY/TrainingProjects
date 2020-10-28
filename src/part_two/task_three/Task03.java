package part_two.task_three;

/**
 * Поток выводит на экран текущее время в виде текста. Кнопка должна
 * приостанавливать/запускать поток.
 */
public class Task03 {

	public static void main(String[] args) {
		StartFrame startFrame = new StartFrame();		
		Thread thread = new Thread(new TimerRunner(startFrame));		
		thread.start();		
	}

}
