package homework;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.sql.*;
import java.util.*;

public class CourseRepository implements Operations {
    private static Scanner sc;
    private static Connector connector;
    private static Session session;

    public CourseRepository() {
        try {
            createDatabase();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        connector = new Connector();
        //session = connector.getSession();
        //session.beginTransaction();
        //session.getTransaction().commit();
        //session.close();
    }

    private static void createDatabase() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/";
        String user = "user";
        String password = "123456";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS SchoolDB;";
            try (PreparedStatement statement = connection.prepareStatement(createDatabaseSQL)) {
                statement.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean matchName(String name) {
        session = connector.getSession();
        String hql = "FROM Course WHERE название = :find_title";
        Query<Course> query = session.createQuery(hql, Course.class);
        query.setParameter("find_title", name);
        Object course;
        try {
            course = query.getSingleResult();
            return course != null;
        } catch (NoResultException e) {
            return false;
        } finally {
            session.close();
        }
        //session.beginTransaction();
    }

    @Override
    public void add(Course item) {
        if (!matchName(item.getTitle())) {
            session = connector.getSession();
            // Начало транзакции
            session.beginTransaction();
            // Создание объекта
            session.save(item);
            //сохранение
            session.getTransaction().commit();
            session.close();
            System.out.println("обьект добавлен " + item);
        } else {
            System.out.println("такой курс уже есть");
        }
    }

    private static String[] newValues(String title, int duration) {
        sc = new Scanner(System.in);
        String[] value = new String[2];
        System.out.println("чтобы не изменять параметры оставьте поле пустым");
        System.out.println("введите новое имя");
        value[0] = sc.nextLine().trim();
        if (value[0].isEmpty()) {
            value[0] = title;
        }
        System.out.println("введите срок обучения");
        boolean loop = true;
        while (loop) {
            value[1] = sc.nextLine().trim();
            if (!value[1].isEmpty()) {
                try {
                    Integer.parseInt(value[1]);
                    loop = false;
                } catch (NumberFormatException ex) {
                    System.out.println("введите срок обучения");
                }
            } else { value[1] = String.valueOf(duration);loop = false; }
        }
        sc.close();
        return value;
    }

    @Override
    public void update(Course item) {

        System.out.println("сейчас будет изменен " + item);
        String[] p = newValues(item.getTitle(), item.getDuration());

        item.setTitle(p[0]);
        item.setDuration(Integer.parseInt(p[1]));

        session = connector.getSession();
        session.beginTransaction();
        session.update(item);
        session.getTransaction().commit();
        session.close();

        System.out.println("Course updated");
    }

    @Override
    public void delete(Course item) {
        session = connector.getSession();
        session.beginTransaction();
        session.delete(item);
        session.getTransaction().commit();
        session.close();
        System.out.println("Course deleted");
    }

    @Override
    public Course getById(int id) {
        session = connector.getSession();
        session.beginTransaction();
        Course temp = session.get(Course.class, id);
        session.close();
        return temp;
    }


    public List<Course> getByTitle(String n) {
        session = connector.getSession();
        String hql = "FROM Course WHERE название LIKE :find_title";
        Query<Course> query = session.createQuery(hql, Course.class);
        query.setParameter("find_title", "%"+n+"%");
        List<Course> list = query.getResultList();
        session.beginTransaction();
        session.close();
        return list;
    }

    @Override
    public List<Course> getAll() {
        session = connector.getSession();
        String hql = "FROM Course";
        Query query = session.createQuery(hql, Course.class);
        List<Course> obj = query.getResultList();
        session.beginTransaction();
        session.close();
        return obj;
    }
}

