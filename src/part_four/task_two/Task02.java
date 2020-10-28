package part_four.task_two;

import part_four.task_one.database.Database;
import part_four.task_one.gui.StartFrame;
import part_four.task_two.gui.StartFrameExt;

/**
 * 2. В базе хранится информация по студентам. С использованием средств SQL
 * показать сразу более успевающих, потом менее успевающих студентов (по
 * принципу задачи 1.1: Группа – Успеваемость – Фамилия).
 */

public class Task02 {

	public static void main(String[] args) {
		try {
			Database db = new Database();
			StartFrameExt startFrame = new StartFrameExt(db);
		} catch (Exception ex) {
			System.out.println("Connection failed...");
			System.out.println(ex);
			System.out.println("Please, check file  \"database.properties\" for access to db");
			ex.printStackTrace();
		} // TODO Auto-generated method stub

	}

}
