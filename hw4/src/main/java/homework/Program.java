package homework;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;

public class Program {

    /**
     * Задание
     * =======
     * Создайте базу данных (например, SchoolDB).
     * В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
     * Настройте Hibernate для работы с вашей базой данных.
     * Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
     * Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
     * Убедитесь, что каждая операция выполняется в отдельной транзакции.
     */
    public static void main(String[] args) {
        String decor="~~~~~~~~~~~~~~~~~~~~~";
        System.out.println("hello");
        CourseRepository repository = new CourseRepository();

        repository.getAll().forEach(System.out::println);
        System.out.println("это был блок печати пустой тааблицы");

        repository.add(new Course("Веб-дизайнер", 12));
        repository.add(new Course("Инженер по тестированию", 7));
        repository.add(new Course("SQL для анализа данных", 2));
        repository.add(new Course("Substance painter для игр", 3));
        repository.getAll().forEach(System.out::println);
        System.out.printf("%s это был блок печати таблицы %s\n",decor,decor);

        Course byId = repository.getById(2);
        System.out.println(byId);
        System.out.printf("%s это был блок чтения по id %s\n",decor,decor);

        repository.getByTitle("для").forEach(System.out::println);;
        System.out.printf("%s это был блок на совпадение в названии %s\n",decor,decor);

        repository.update(byId);
        repository.getAll().forEach(System.out::println);
        System.out.printf("%s это был блок обновление обьекта %s\n",decor,decor);

        repository.delete(repository.getById(3));
        repository.getAll().forEach(System.out::println);
        System.out.printf("%s это был блок на удаление обьекта (я выбрал id 3) %s\n",decor,decor);

        //ленивый тест на дубликат
        repository.add(new Course("Веб-дизайнер", 3));
    }
}
