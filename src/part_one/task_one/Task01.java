package part_one.task_one;

import java.util.*;

/**
 * Есть массив, в котором хранится информация об итогах сессии. Сведения о
 * каждом студенте — это фамилия, номер группы и результаты экзаменов по трем
 * дисциплинам. Вывести в алфавитном порядке по группам информацию по студентам
 * в порядке убывания их средней успеваемости (Группа — Успеваемость — Фамилия).
 * Задача подразумевает использование некоторой коллекции.
 */
public class Task01 {

	public static void main(String[] args) {
		
		Comparator<Student> comp = new StudentComparator();
		TreeSet<Student> students = new TreeSet<Student>(comp);
		students.add(new Student("Avanov", 2, 5, 5, 5));
		students.add(new Student("Vvanov", 1, 5, 5, 5));
		students.add(new Student("Getrov", 1, 4, 4, 4));
		students.add(new Student("Getrova", 1, 3, 5, 5));
		students.add(new Student("Antonov", 2, 4, 3, 4));
		students.add(new Student("Bantonov", 2, 5, 4, 4));
		students.add(new Student("Rantonov", 3, 4, 5, 5));
		students.add(new Student("Santonov", 21, 6, 4, 3));
		students.add(new Student("Pantonov", 2, 3, 5, 6));
		for (Student stud : students) {
			System.out.println(stud);
		}
	}

}
