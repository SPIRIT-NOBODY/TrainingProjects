package part_three.task_four;

import part_three.task_four.client.EchoClient;

/**
 * Реализовать эхо — повтор переданной строки и реверс. Сделать 2 метода: на
 * реверс и на эхо.
 */
public class Task04 {

	public static void main(String[] args) {
		EchoClient client = new EchoClient(args.length > 0? args[0] : null);
	}

}
