package lesson2;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException, SecurityException   {
        /*
         * Car car = new Car("MAN");
         * System.out.println(car.name);
         */

        Class<?> car = Class.forName("lesson2.Car");
        Constructor<?>[] constructors = car.getConstructors();
        System.out.println(constructors);

        Object gaz = constructors[0].newInstance("Газ");
        System.out.println(gaz);
        // изменение скорости               
       
        Field speedField = car.getDeclaredField("maxSpeed");
        speedField.setAccessible(true);
        speedField.setInt(gaz, 5000);

        System.err.println(gaz);

        Method[] methods=gaz.getClass().getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
    }
}
