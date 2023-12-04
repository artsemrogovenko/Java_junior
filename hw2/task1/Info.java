package hw2.task1;

import java.lang.reflect.Constructor;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class Info {
    
    protected static void getInfo(Animal[] elements){
    StringBuilder myBuilder = new StringBuilder();
    for (Animal currentAnimal : elements) {
        myBuilder.append("\n");
        myBuilder.append(currentAnimal + "\n");
        myBuilder.append("Конструкторы:" + "\n");

        for (Constructor constr : currentAnimal.getClass().getDeclaredConstructors()) {
            myBuilder.append(constr.getName() + " (");
            myBuilder.append(showParameters(constr));
            myBuilder.append(")" + "\n");
        }

        myBuilder.append("Поля родителя:" + "\n");
        for (Field field : currentAnimal.getClass().getSuperclass().getDeclaredFields()) {
            myBuilder.append(showFields(field, currentAnimal) + "\n");
        }

        myBuilder.append("Поля:" + "\n");
        for (Field field : currentAnimal.getClass().getDeclaredFields()) {
            myBuilder.append(showFields(field, currentAnimal) + "\n");
        }
        
        myBuilder.append("Методы:" + "\n");

        for (Method method : currentAnimal.getClass().getDeclaredMethods()) {
            myBuilder.append(String.format("%s %s(", method.getReturnType(), method.getName()));
            myBuilder.append(showParameters(method));
            myBuilder.append(");" + "\n");
        }
    }
    System.out.println(myBuilder.toString());

}

private static String showParameters(Executable m) {
    String tmp = "";
    for (Parameter pa : m.getParameters()) {
        tmp += pa.getType().getSimpleName() + " " + pa.getName() + ",";
    }
    return tmp.replaceAll(".$", "");
}

private static String showFields(Field f, Animal animal) {
    f.setAccessible(true);
    try {
        return String.format("%s %s %s : %s",
        Modifier.toString(f.getModifiers()), f.getType().getSimpleName(), f.getName(), f.get(animal));
    } catch (IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
    }
    return null;
}
}
