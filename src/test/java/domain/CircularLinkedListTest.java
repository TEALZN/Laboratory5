package domain;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class CircularLinkedListTest {

    @Test
    void test() {
        CircularLinkedList list = new CircularLinkedList();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2005, 6, 21);
        list.add(new Employee(111111111,"Campos", "Ana", "Informatica", calendar.getTime()));
        calendar.set(2002, 10, 5);
        list.add(new Employee(222222222,"Gutierrez", "David", "Administración", calendar.getTime()));
        calendar.set(2000, 8, 15);
        list.add(new Employee(333333333,"Sanchez", "Carlos", "Inglés", calendar.getTime()));
        System.out.println(list);
        try {
            System.out.println(showAgeList(list,
                    "Empleados con rango de edad entre 18 y 25", 18, 25));

        System.out.println(showAgeList(list,
                "Empleados con rango de edad entre 26 y 40", 26, 40));
        System.out.println(showAgeList(list,
                "Empleados con rango de edad entre 41 y 55", 41, 55));
        System.out.println(showAgeList(list,
                "Empleados con rango de edad mayor a 55", 56, 100));

        CircularLinkedList my_list = getTitleList(list, "Informatica");
            System.out.println("Empleados con la profesión: Informática:\n" + my_list.show());
            System.out.println("");
            my_list=getTitleList(list,"Administración");
            System.out.println("Empleados con la profesión: Administración:\n" + my_list.show());
            System.out.println("");
            my_list=getTitleList(list,"Inglés");
            System.out.println("Empleados con la profesión: Inglés:\n" + my_list.show());
            System.out.println("");



        } catch (ListException e) {
            throw new RuntimeException(e);
        }

    }

    private CircularLinkedList getTitleList(CircularLinkedList list, String title) throws ListException {
        CircularLinkedList resultList = new CircularLinkedList();
        for (int i = 1; i <= list.size(); i++){
            Employee employee = (Employee) list.getNode(i).data;
            if(util.Utility.compare(employee.getTitle(), title)==0)
                resultList.add(employee);
        }
        return resultList;
    }

    private String showAgeList(CircularLinkedList list, String msg, int low, int high) throws ListException {
        String result = msg + "\n";
        for (int i = 1; i <= list.size(); i++){
            Employee employee = (Employee) list.getNode(i).data;
            int age = employee.getAge();
            if(age >= low && age <= high)
                result += employee+"\n";
        }
        return result;
    }
}