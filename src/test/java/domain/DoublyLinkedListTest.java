package domain;

import org.junit.jupiter.api.Test;


import java.util.Calendar;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {

    @Test
    void test() {

        CircularDoublyLinkedList list = new CircularDoublyLinkedList();

        //Definir a , b , c , d
        CircularDoublyLinkedList a = new CircularDoublyLinkedList();
        CircularDoublyLinkedList b = new CircularDoublyLinkedList();
        CircularDoublyLinkedList c = new CircularDoublyLinkedList();
        CircularDoublyLinkedList d = new CircularDoublyLinkedList();

        //Empleados
        Calendar calendar = Calendar.getInstance();
        calendar.set(2005, 6, 21);
        list.add(new Employee(11111111 , "Rios " , "Valentina ", "Informatica", calendar.getTime()));
        calendar.set(2002, 2, 21);
        list.add(new Employee(222222222 , " Fernandez " , " Mateo ", " Administraccion " , calendar.getTime()));
        calendar.set(2000, 3, 14);
        list.add(new Employee(333333333 , "Torres" , "Isabella  ", "Ingles " , calendar.getTime()));
        calendar.set(1997, 3, 21);
        list.add(new Employee(444444444 , "Molina " , "Santiago", "Tursimo " , calendar.getTime()));
        calendar.set(1995, 3, 27);
        list.add(new Employee(555555555 , "Vargas " , "Camila ", "Agronomia " , calendar.getTime()));
        calendar.set(1991, 4, 01);
        list.add(new Employee(666666666 , "Herrera " , "Emiliano ", "Diseño publicitario" , calendar.getTime()));
        calendar.set(1988, 2, 01);
        list.add(new Employee(777777777 , " Navarro " , " Luciana ", "Diseño web " , calendar.getTime()));
        calendar.set(19986,  1, 30);
        list.add(new Employee(888888888 , " Castro " , "Benjamin ", "Asesor" , calendar.getTime()));
        calendar.set(1977, 01, 25);
        list.add(new Employee(999999999 , "Salazar " , "Antonella ", "Doctora" , calendar.getTime()));
        calendar.set(1965, 02, 11);
        list.add(new Employee(101010101 , " Mendez " , " Thiago ", "Abogado " , calendar.getTime()));
        System.out.println(list);
        System.out.println("");

        try {

            // Llenar las listas a, b, c, d
            for (int i = 1; i <= list.size(); i++) {
                Employee employee = (Employee) list.getNode(i).data;
                int age = employee.getAge();
                if (age >= 18 && age <= 25) {
                    a.add(employee);
                } else if (age >= 26 && age <= 40) {
                    b.add(employee);
                } else if (age >= 41 && age <= 55) {
                    c.add(employee);
                } else if (age >=55 && age <=100) {
                    d.add(employee);
                }
            }

            //Mostrar la lista
            System.out.println(showAgeList(a,
                    "Employees between the ages of 18 and 25", 18, 25));

            System.out.println(showAgeList(b,
                    "Employees between the ages of 26 and 40", 26, 40));

            System.out.println(showAgeList(c,
                    "Employees between the ages of 41 and 55", 41, 55));

            System.out.println(showAgeList(d,
                    "Employees between the ages of 56 and 100", 56, 100));

            System.out.println();

            System.out.println(generateRandomNumber());

        } catch (ListException e) {
            throw new RuntimeException(e);
     }
    }

    private String showAgeList( CircularDoublyLinkedList list, String msg, int low, int high) throws ListException {
        String result = msg + "\n";
        for (int i = 1; i <= list.size(); i++){
            Employee employee = (Employee) list.getNode(i).data;
            int age = employee.getAge();
            if(age >= low && age <= high)
                result += employee+"\n";
        }
        return result;
    }

    public static int generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(11);
        return randomNumber + 40;
    }
}