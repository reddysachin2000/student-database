package costomsorting;

import java.util.Comparator;

import hi.Student1;


public class SortStudentByName implements Comparator<Student1> {

	public int compare(Student1 x,Student1 y) {
		return x.getName().compareTo(y.getName());
	}
}
