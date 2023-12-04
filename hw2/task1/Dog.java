package hw2.task1;

public class Dog extends Animal {
    private String ability;

    public Dog(String name, int age,String ability) {
        this.name=name;
        this.age=age;
        this.ability = ability;
    }

    void makeSound() {
        System.out.println( "Гав");
    }

    void cheer() {
        System.out.println("машет хвостом");
    }

    void goAway() {
        System.out.println("хочет на улицу");
    }

    public String getAbility() {
        return ability;
    }

}
