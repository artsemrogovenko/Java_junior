package seminar_1.hw;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Homework {
// https://gbcdn.mrgcdn.ru/uploads/record/295101/attachment/fe092293aa0a6bbd70e86e9d3c95ff4e.mp4
  /**
   * 0.1. Посмотреть разные статьи на Хабр.ру про Stream API
   * 0.2. Посмотреть видеоролики на YouTube.com Тагира Валеева про Stream API
   *
   * 1. Создать список из 1_000 рандомных чисел от 1 до 1_000_000
   * 1.1 Найти максимальное
   * 2.2 Все числа, большие, чем 500_000, умножить на 5, отнять от них 150 и просуммировать
   * 2.3 Найти количество чисел, квадрат которых меньше, чем 100_000
   *
   * 2. Создать класс Employee (Сотрудник) с полями: String name, int age, double salary, String department
   * 2.1 Создать список из 10-20 сотрудников
   * 2.2 Вывести список всех различных отделов (department) по списку сотрудников
   * 2.3 Всем сотрудникам, чья зарплата меньше 10_000, повысить зарплату на 20%
   * 2.4 * Из списка сотрудников с помощью стрима создать Map<String, List<Employee>> с отделами и сотрудниками внутри отдела
   * 2.5 * Из списока сорудников с помощью стрима создать Map<String, Double> с отделами и средней зарплатой внутри отдела
   */
  private static List<Integer> list = new LinkedList<>();
  private static List<Employee> employees = new ArrayList<>();
  private static Random random =new Random();
  
  public static void main(String[] args) {
    
    // Создать список из 1_000 рандомных чисел от 1 до 1_000_000
    for (int i = 0; i < 1000; i++) {
      list.add(Integer.valueOf(new Random().nextInt(1000000-1)) + 1);
    } 

    int maxxx = -1;
    for (Integer integer : list) {
      if (integer > maxxx)
        maxxx = integer;
    }
    // 1.1 Найти максимальное
    System.out.println(maxxx);
    System.out.println(list.stream().max(Integer::compareTo).get());

    // 2.2 Все числа, большие, чем 500_000, умножить на 5, отнять от них 150 и просуммировать
     System.out.println("резульат "+list.stream().filter(n -> n.intValue() > 500000).map(n -> (n * 5) - 150).mapToInt(Integer::intValue).sum());
    
     // 2.3 Найти количество чисел, квадрат которых меньше, чем 100_000
     System.out.println("количество чисел "+list.stream().map(n -> (n*n)).filter(n -> n<100000).count());
    
     // Создать список из 10-20 сотрудников
    initList();

    // Вывести список всех различных отделов (department) по списку сотрудников
    employees.stream().collect(Collectors.groupingBy(Employee::getDepartment)).forEach((t, u) ->System.out.println(t));

    // Всем сотрудникам, чья зарплата меньше 10_000, повысить зарплату на 20%
    List<Employee> mylist=employees.stream().filter(e -> e.getSalary() < 10000).collect(Collectors.toList());

    mylist.stream().forEach(e->System.out.println(e.toString()));
    System.out.println();
    mylist.stream().forEach(s->s.setSalary(s.getSalary() *1.2));
    mylist.stream().forEach(e->System.out.println(e.toString()));
    
    // Из списка сотрудников с помощью стрима создать Map<String, List<Employee>> с отделами и сотрудниками внутри отдела
    Map<String, List<Employee>> map1=employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.toList()));
    map1.entrySet().stream().forEach(t -> System.out.println(t.getKey()+t.getValue()+"\n")); 
    
    //Из списока сорудников с помощью стрима создать Map<String, Double> с отделами и средней зарплатой внутри отдела
    Map<String, Double> map2=employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,Collectors.averagingDouble(Employee::getSalary)));
    map2.entrySet().stream().forEach(i ->System.out.printf("%s средняя з/п %.2f\n",i.getKey(),i.getValue()) ); 

}

  private static int randAge() {
    return random.nextInt(20) + 2;
  }

  private static int randSalary() {
    return random.nextInt(20000);
  }

  private static String randDepartment() {
    String[] str = new String[] { "Rifle", "Pistol", "Power", "Spray", "Shotgun" };
    return str[random.nextInt(str.length)];
  }

  private static void initList(){
    String[] names = new String[] { "Ferris", "Fred", "George", "Graham", "Harvey", "Irwin", 
        "Lester", "Marvin", "Neil", "Oliver", "Opie", "Toby", "Ulric", "Ulysses", "Uri",
         "Waldo", "Wally", "Walt", "Wesley", "Yanni", "Yogi", "Jean" };

         for (int i = 0; i < names.length; i++) {
          employees.add(new Employee(names[i],randAge(),randSalary(),randDepartment()));
         }
    
  }

}
