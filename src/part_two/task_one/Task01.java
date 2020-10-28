package part_two.task_one;

import java.awt.*;

/**
 * Реализовать приложение с двумя потоками. Первый гоняет по кругу некоторую
 * нарисованную Вами разноцветную картинку, второй периодично изменяет цвет фона
 * на случайный цвет.
 * 
 */
public class Task01 {

	public static void main(String[] args) {
		StartFrame start = new StartFrame();
		Runnable thr1interface = ()->{
			while (true) {
				start.animation();					
				try {
					Thread.sleep(50);						
				} catch (InterruptedException e) {					
				}
			}
		};
		Runnable thr2interface = ()->{
			while(true) {
				start.setColorWrap();					
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO: handle exception
				}
			}	
		};		
		Thread one = new Thread(thr1interface);
		Thread two = new Thread(thr2interface);
		one.start();
		two.start();
	}

}
