package hw1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Task {
    public static void main(String[] args) {
        // Напишите программу, которая использует Stream API для обработки списка чисел.
        // Программа должна вывести на экран среднее значение всех четных чисел в  списке.
        List<Integer> m1 = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 });
        double average = m1.stream().filter(n -> n % 2 == 0).collect(Collectors.averagingInt(value -> value));
        System.out.println("среднее значение "+average);
    }
}

