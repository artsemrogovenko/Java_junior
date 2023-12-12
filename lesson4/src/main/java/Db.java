import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class Db {
    private static final String URL = "jdbc:mysql://192.168.110.129:3306";
    private static final String USER = "user";
    private static final String PASSWD = "123456";

    public static void con() {
/*
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWD)) {
            Statement statement = con.createStatement();
            statement.execute("DROP SCHEMA `test`");
            statement.execute("CREATE SCHEMA `test`");
            statement.execute("CREATE TABLE `test`.`table` (`id` INT PRIMARY KEY NOT NULL, `firstname` VARCHAR(45) NULL, `lastname` VARCHAR(45) NULL);");
            statement.execute("INSERT INTO `test`.`table` (`id`,`firstname`,`lastname`)\n" +
                    "VALUES " +
                    "(1,'вася','васин')," +
                    "(2,'петр','петров')," +
                    "(3,'леонид','леонидов');");

            ResultSet set = statement.executeQuery("select * from test.table;");

            while (set.next()) {
                System.out.println(set.getString(3) + " " + set.getString(2) + " " + set.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
        /*********/ // добавить элементы
/*
        Connector connector = new Connector();
        Session session = connector.getSession();
        session.beginTransaction();

        session.save(new Magic("Волшебная стрела", 10, 0, 0));
        session.save(new Magic("Молния", 25, 0, 0));
        session.save(new Magic("Каменная кожа", 0, 0, 6));
        session.save(new Magic("Жажда крови", 0, 6, 0));
        session.save(new Magic("Проклятие", 0, -3, 0));
        session.save(new Magic("Лечение", -30, 0, 0));

        session.getTransaction().commit();
        session.close();
*/
        /**************/ // получить список
/*
        Connector connector=new Connector();
        try (Session session = connector.getSession()){
            List<Magic> books = session.createQuery("FROM Magic ",Magic.class).getResultList();
            books.forEach(b->{
                System.out.println(b);
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        */
/*********/ // изменение обьекта
/*
        Connector connector = new Connector();
        try (Session session = connector.getSession()) {
            String hql = "FROM Magic WHERE id = :id";
            Query<Magic> query = session.createQuery(hql, Magic.class);
            query.setParameter("id", 4);

            Magic obj = query.getSingleResult();
            System.out.println(obj);
            obj.setAttBonus(100);
            obj.setName("Ярость");

            session.beginTransaction();
            session.update(obj);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
/************/ // удаление
        Connector connector = new Connector();
        try (Session session = connector.getSession()) {
            Transaction t = session.beginTransaction();
            List<Magic> magics = session.createQuery("FROM Magic ", Magic.class).getResultList();
            magics.forEach(session::delete);
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
