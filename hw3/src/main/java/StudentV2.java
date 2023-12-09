import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

/*Разработайте класс Student с полями String name, int age, transient double GPA (средний балл).*/
/** Обеспечьте поддержку сериализации для этого класса */
public class StudentV2 implements Externalizable {
    private String name;
    private int age;
    private transient double GPA;

    public StudentV2(String name, int age, double GPA) {
        this.name = name;
        this.age = age;
        this.GPA = GPA;
    }
    public StudentV2(){}

    @Override
    public String toString() {
        return "StudentV2 [name=" + name + ", age=" + age + ", GPA=" + GPA + "]";
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setName((String) in.readObject());
        setAge(in.readInt());
        setGPA(in.readDouble());
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
        out.writeInt(getAge());
        out.writeDouble(getGPA());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double gPA) {
        GPA = gPA;
    }

}
