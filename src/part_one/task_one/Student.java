package part_one.task_one;

import java.util.Comparator;

class Student implements Comparable<Student> {
	private final String surname;
	private final int group;
	private final int[] mark;

	public Student(String surname, int group, int... mark) {
		this.surname = surname;
		this.group = group;
		this.mark = mark;
	}

	@Override
	public int compareTo(Student stud) {

		return Double.compare(group, stud.group);
	}

	public String getSurname() {
		return surname;
	}
	
	public int getGroup() {
		return group;
	}

	@Override
	public String toString() {
		return group + " - "+ String.format("%.2f", getAverageMark())  + " - " + surname;

	}
	
	
	public double getAverageMark() {
		double average = 0.0;
		for(int i = 0; i < mark.length; i++) {
			average += (double)mark[i];
		}
		return  average / mark.length;
	}
}
