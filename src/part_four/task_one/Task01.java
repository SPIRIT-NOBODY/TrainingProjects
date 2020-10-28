package part_four.task_one;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

import part_four.task_one.database.Database;
import part_four.task_one.gui.StartFrame;

/**
 * Task text: <blockquote> В базе хранится информация по студентам. Уметь
 * добавлять и удалять по заданным критериям (определить самостоятельно, минимум
 * два критерия) соответствующую информацию. </blockquote>
 * 
 * @author Denis Samuilik
 */

public class Task01 {
	/**
	 * Start application Task01
	 * 
	 * @param args command lines parameters
	 */
	public static void main(String[] args) {
		try {
			Database db = new Database();
			StartFrame startFrame = new StartFrame(db);
		} catch (Exception ex) {
			System.out.println("Connection failed...");
			System.out.println(ex);
			System.out.println("Please, check file  \"database.properties\" for access to db");
		}
	}

}
