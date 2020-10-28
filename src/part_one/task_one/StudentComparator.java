package part_one.task_one;

import java.util.Comparator;

class StudentComparator implements Comparator<Student> {

	@Override
	public int compare(Student stud1, Student stud2) {

		int groupCompared = Integer.compare(stud1.getGroup(), stud2.getGroup());
		int markCompared = Double.compare(stud1.getAverageMark(), stud2.getAverageMark());
		int surnameCompared = stud1.getSurname().compareTo(stud2.getSurname());

		if (groupCompared != 0) {
			return groupCompared;
		}
		if (markCompared != 0) {
			return -markCompared;
		}

		return surnameCompared;
	}

}
