

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Struct;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Main {

    private static final String FILE_JSON = "student.json";
    private static final String FILE_BIN = "student.bin";
    private static final String FILE_XML = "student.xml";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();
    
    public static void main(String[] args) throws ClassNotFoundException {
        // Создайте объект класса Student и инициализируйте его данными
        StudentV2 student = new StudentV2("Олег", 18, 50.2); //Externalizable
        StudentV2 newObj = null;
        try {
            serialize(student, "studentdata.bin");
            newObj = (StudentV2) deserialize("studentdata.bin");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Выведите все поля объекта, включая GPA
        System.out.println(newObj);

        // 2. * Выполнить задачу 1 используя другие типы сериализаторов (в xml и json документы).
        savetofile(FILE_JSON,new Student("Дмитрий",20,4.5));
        savetofile(FILE_BIN,new Student("Александр",19,4.7));
        savetofile(FILE_XML,new Student("Михаил",22,4.8));

        LinkedList<Student> students =new LinkedList<Student>();
        students.add((Student) loadfile(FILE_JSON));
        students.add((Student) loadfile(FILE_BIN));
        students.add((Student) loadfile(FILE_XML));

        students.stream().forEach(System.out::println);
    }

    private static void savetofile(String fileName, Object obj){
        try {
            if (fileName.endsWith(".json")) {
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                objectMapper.writeValue(new File(fileName), obj);
            } else if (fileName.endsWith(".bin")) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                    oos.writeObject(obj);
                }
            } else if (fileName.endsWith(".xml")) {
                xmlMapper.writeValue(new File(fileName), obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object loadfile(String fileName) {
        Object task =null;

        File file = new File(fileName);
        if (file.exists()) {
            try {
                if (fileName.endsWith(".json")) {
                    task =objectMapper.readValue(file,Student.class);
                } else if (fileName.endsWith(".bin")) {
                    task = deserialize(fileName);
                } else if (fileName.endsWith(".xml")) {
                    task = xmlMapper.readValue(file, Student.class);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return task;
    }

    // Сериализуйте этот объект в файл.
    private static void serialize(Object obj, String file) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(file);
        ObjectOutputStream objOutput = new ObjectOutputStream(fileOutput);
        objOutput.writeObject(obj);
        objOutput.close();
        fileOutput.close();
        System.out.println(obj.getClass().getSimpleName()+" сериализация выполнена");
    }

    // Десериализуйте объект обратно в программу из файла.
    private static Object deserialize(String file) throws ClassNotFoundException, IOException {
        Object temp=null;
        FileInputStream fileinput = new FileInputStream(file);
        ObjectInputStream objinput = new ObjectInputStream(fileinput);
        temp=objinput.readObject();
        System.out.println(temp.getClass().getSimpleName()+ " десериализация выполнена");
        return temp;
    }

}
