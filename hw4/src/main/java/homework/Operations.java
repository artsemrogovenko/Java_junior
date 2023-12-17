package homework;

import java.util.List;

public interface Operations {
    void add(Course item);
    void update(Course item);
    void delete(Course item);
    Course getById(int id);
    List<Course>  getByTitle(String title);
    List<Course> getAll();
}
