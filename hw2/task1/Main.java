package hw2.task1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Создайте массив объектов типа "Animal"
        Animal[] animals = new Animal[] {
                new Cat("Барсик", 2, "гоняется за мышами"),
                new Cat("Персик", 3, "залазит на люстру"),
                new Dog("Лео", 2, "попрошайничает еду"),
                new Dog("Рич", 1, "выполняет команды") };
        // и с использованием Reflection API выполните следующие действия:
        // Выведите на экран информацию о каждом объекте.
        Info.getInfo(animals);

        // Вызовите метод "makeSound()" у каждого объекта, если такой метод присутствует.
        Arrays.stream(animals).forEach(t -> {
            System.out.print(t + " ");
            for (Method m : t.getClass().getDeclaredMethods()) {
                if (m.getName().equals("makeSound")) {
                    try {
                        m.invoke(t);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
