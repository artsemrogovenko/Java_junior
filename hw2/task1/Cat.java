package hw2.task1;

import java.util.Random;

public class Cat extends Animal {
    private String trick;
    private String ability;

    public Cat(String name, int age, String ability) {
        this.name = name;
        this.age = age;
        this.trick = randomTrick();
        this.ability = ability;
    }

    void makeSound() {
        System.out.println("Мяу");
    }

    void rage() {
        System.out.println(trick);
    }

    String todo() {
        return ability;
    }

    private String randomTrick(){
        String[] t = new String[]{"Беготня из одного конца дома в другой.","Подглядывание","Прячется в тесные места"};
        return t[new Random().nextInt(t.length)];
    }

}
