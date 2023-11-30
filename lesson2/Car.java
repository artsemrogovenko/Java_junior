package lesson2;

public class Car {
    private String name;
    private String price,engineType,engPower;
    private int maxSpeed;
    
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Car(String name) {
        this.name=name;
        this.engPower="123";
        this.engineType="V8";
        this.maxSpeed=100;
        this.price="100000";
    }
    @Override
    public String toString() {
        return String.format("Car{%s макс скорость %d}",name,maxSpeed);
    }
}
