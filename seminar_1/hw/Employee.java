package seminar_1.hw;

public class Employee {
@Override
public boolean equals(Object obj) {
    return this==obj;
}

@Override
public String toString() {
    return String.format("%s возраст %d %s зарплата %.2f",name,age,department,salary);
}

public int getAge() {
    return age;
}
public String getDepartment() {
    return department;
}
public String getName() {
    return name;
}

public double getSalary() {
    return salary;
}

public void setSalary(double salary) {
    this.salary = salary;
}
    String name;
    int age;
    double salary;
    String department;
 
    Employee(String name, int age, double salary, String department) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.department = department;
    }


}
