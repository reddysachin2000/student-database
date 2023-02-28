package hi;

import java.util.Scanner;

import org.hibernate.internal.build.AllowSysOut;

import costomexception.InvalidChoiceException;
import costomexception.StudentNotFoundException;
import costomsorting.SortStudentByName;

import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class StudentManagementSystemImpl implements StudentManagementSystem {

	Scanner ip = new Scanner(System.in);
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hi");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();

	public void addStudent() {
		System.out.println("Enter student name");
		ip.nextLine();
		String name = ip.nextLine();
		System.out.println("Enter studenrt age");
		int age = ip.nextInt();
		System.out.println("Enter student marks");
		int marks = ip.nextInt();
		Student1 s = new Student1();
		s.setAge(age);
		s.setMarks(marks);
		s.setName(name);
		et.begin();
		em.persist(s);
		et.commit();
	}

	public void displayStudent() {
		System.out.println("Enter id to display student details");
		int id = ip.nextInt();
		et.begin();
		Query q = em.createQuery("select s from Student1 s where s.id=?1 ");
		q.setParameter(1, id);
		List<Student1> s = q.getResultList();
		System.out.printf("%15s%15s%15s%15s","id","name","age","marks");
		System.out.println();
		System.out.println("==================================================================");
		for (Student1 student : s) {
			System.out.printf("%15s%15s%15s%15s",student.getId(),student.getName(),student.getAge(),student.getMarks());
		}
		System.out.println();
		System.out.println("----------------------------------------------------------------");
		et.commit();

	}

	public void displayAllStudents() {
		et.begin();
		Query q = em.createQuery("select s from Student1 s ");
		List<Student1> s = q.getResultList();
		System.out.printf("%15s%15s%15s%15s","id","name","age","marks");
		System.out.println();
		System.out.println("==================================================================");
		for (Student1 student : s) {

			System.out.printf("%15s%15s%15s%15s",student.getId(),student.getName(),student.getAge(),student.getMarks());
			System.out.println();
			System.out.println("-------------------------------------------------------------");
		}

		et.commit();

	}

	public void removeStudent() {
		System.out.println("Enter id to display student details");
		int id = ip.nextInt();
		et.begin();
		Student1 s = em.find(Student1.class, id);
		em.remove(s);
		et.commit();
		System.out.println("student with the id "+id+" is deleted");

	}

	public void removeAllStudents() {

		et.begin();
		Query q = em.createQuery("select s from Student1 s ");
		List<Student1> s=q.getResultList();
		for (Student1 student1 : s) {
			em.remove(student1);
		}
		et.commit();
        System.out.println("All Students records from Database is Deleted");
	}

	public void updateStudent() {

		System.out.println("Enter student id");
		int id=ip.nextInt();
		Student1 s = em.find(Student1.class, id);
		System.out.println("1.Update Name\n2.Update Age\n3.Update Marks");
		System.out.println("Enter choice");
		int choice=ip.nextInt();

		et.begin();
		switch(choice) {
		case 1:System.out.println("Enter Name to update");
		ip.nextLine();
		s.setName(ip.nextLine());
		System.out.println("Name updated successfully");
		break;
		case 2:System.out.println("Enter Age to update");
		s.setAge(ip.nextInt());
		System.out.println("Age updated successfully");
		break;
		case 3:System.out.println("Enter Marks to update");
		s.setMarks(ip.nextInt());
		System.out.println("Marks updated successfully");
		break;
		default :
			try {
				new InvalidChoiceException("Invalid choice, Kindly enter valid choice!!!!!!!!!!!!!");
			}
			catch(InvalidChoiceException e) {
				System.out.println(e.getMessage());
			}
		}
		et.commit();
	}

	public void countStudent() {
		et.begin();
		Query q = em.createQuery("select s from Student1 s ");
		List<Student1> s = q.getResultList();
		System.out.println("Total no of Students Records ->"+s.size());
		et.commit();
	}

	public void sortStudent() {

		System.out.println("1.Sort Student by Name\n2.Sort Student by Age\n3.Sort Student by Marks");
		System.out.println("Enter choice");
		int choice=ip.nextInt();
		et.begin();
		switch(choice) {
		case 1:Query q = em.createQuery("select s from Student1 s order by s.name asc");
		       List<Student1> s = q.getResultList();
		       System.out.printf("%15s%15s%15s%15s","id","name","age","marks");
			   System.out.println();
			   System.out.println("==================================================================");
		       for (Student1 student1 : s) {
		    	   System.out.printf("%15s%15s%15s%15s",student1.getId(),student1.getName(),student1.getAge(),student1.getMarks());
		    	   System.out.println();
				   System.out.println("----------------------------------------------------------------");
				}
		       
		       break;
		case 2:Query q1 = em.createQuery("select s from Student1 s order by s.age asc");
		       List<Student1> s1 = q1.getResultList();
		       System.out.printf("%15s%15s%15s%15s","id","name","age","marks");
			   System.out.println();
			   System.out.println("==================================================================");
		       for (Student1 student1 : s1) {
		    	   System.out.printf("%15s%15s%15s%15s",student1.getId(),student1.getName(),student1.getAge(),student1.getMarks());
		    	   System.out.println();
				   System.out.println("----------------------------------------------------------------");
				}
		       break;
		case 3:Query q2 = em.createQuery("select s from Student1 s order by s.marks asc");
	           List<Student1> s2 = q2.getResultList();
	           System.out.printf("%15s%15s%15s%15s","id","name","age","marks");
			   System.out.println();
			   System.out.println("==================================================================");
	           for (Student1 student1 : s2) {
	        	   System.out.printf("%15s%15s%15s%15s",student1.getId(),student1.getName(),student1.getAge(),student1.getMarks());
	        	   System.out.println();
				   System.out.println("----------------------------------------------------------------");
				}
		       break;
		default :
			     try {
			    	 throw new InvalidChoiceException(null);
			     }
			     catch(Exception e) {
			    	 System.out.println(e.getMessage());
			     }
	           
		}

		et.commit();
	}

	public void getStudentWithHiestMarks() {
		et.begin();
		Query q = em.createQuery("select s from Student1 s order by s.marks asc");
		List<Student1> s = q.getResultList();

		//Collections.sort(s, (o1,o2)-> -o1.getMarks().compareTo(o2.getMarks()));

		System.out.println("Student Witt the Highest Marks is");
		System.out.println(s.get(s.size()-1));
		et.commit();
	}

	public void getStudentWithLowestMarks() {

		et.begin();
		Query q = em.createQuery("select s from Student1 s");
		List<Student1> s = q.getResultList();

		Collections.sort(s, (o1,o2)-> o1.getMarks().compareTo(o2.getMarks()));

		System.out.println("Student With the lowest Marks is");
		System.out.println(s.get(0));
		et.commit();
	}

}
