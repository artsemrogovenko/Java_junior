package lesson1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.lang.model.type.ArrayType;

public class Main {
    public static void main(String[] args) {
        // PlainInterface plainInterface=new PlainInterface() {
        // @Override
        // public String action(int x, int y) {
        // return String.valueOf(x+y);
        // }
        // };
        // PlainInterface plainInterface=(x,y)->String.valueOf(x+y);
        // PlainInterface plainInterface = (x, y) -> {
        // int a = 3+5;
        // return String.valueOf(x + y);
        // };

        // PlainInterface plainInterface1=(x,y)-> Integer.compare(x,y);
        // PlainInterface plainInterface1=Integer::compare;

        // System.out.println(plainInterface.action(5, 3));
        // System.out.println(plainInterface1.action(1, 5));
        /** */
        // List<String> list = Arrays.asList("Привет", "я", "лунтик", "я", "родился");
        // for (String string : list) {
        // System.out.printf(string + " ");
        // }
        // System.out.println();

        // list = list.stream().filter(str -> str.length() <
        // 2).collect(Collectors.toList()); // я я
        // for (String string : list) {
        // System.out.printf(string + " ");
        // }
        // System.out.println();
        // list.stream().filter(str -> str.length() > 4).forEach(s ->
        // System.out.printf(s));//Приветлунтикродился
        // list.stream().filter(str -> str.length() >
        // 4).forEach(System.out::printf);//Приветлунтикродился

        // list.stream().filter(str -> str.length() > 4).filter(str ->
        // str.contains("у")).forEach(System.out::println); //лунтик

        // Arrays.asList(1, 2, 3, 4, 5).stream().map(chislo -> chislo *
        // chislo).forEach(System.out::println);
        // Arrays.asList(1, 2, 3, 4, 5).stream().map(chislo -> "число: " + chislo + ".
        // квадрат числа - " + chislo * chislo)
        // .forEach(System.out::println);

        // Arrays.asList(1, 10, 5, 0, 7,
        // 5).stream().sorted().distinct().forEach(System.out::println);

        // System.out.println(Arrays.asList(1, 10, 5, 0, 7,
        // 5).stream().sorted().distinct().findFirst().get());
        /** */

        List<User> list2 = Arrays.asList(new User("Павел", 25), new User("Аркадий", 40), new User("Валера", 30));
        list2.stream().map(user -> new User(user.name, user.age - 5)).forEach(System.out::println);
        System.out.println();
        list2.stream().map(user -> new User(user.name, user.age - 5)).filter(u -> u.age <= 25) .forEach(System.out::println);
    }
}
