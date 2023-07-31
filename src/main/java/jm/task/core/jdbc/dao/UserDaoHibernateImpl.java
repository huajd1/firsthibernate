}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

clojure
Copy
private final SessionFactory sessionFactory;

public UserDaoHibernateImpl() {
    sessionFactory = Util.getSessionFactory();
}

@Override
public void createUsersTable() {
    try (Session session = sessionFactory.openSession()) {
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(45) NOT NULL," +
                "lastName VARCHAR(45) NOT NULL," +
                "age TINYINT NOT NULL," +
                "PRIMARY KEY (id)" +
                ")").executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@Override
public void dropUsersTable() {
    try (Session session = sessionFactory.openSession()) {
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@Override
public void saveUser(String name, String lastName, byte age) {
    try (Session session = sessionFactory.openSession()) {
        session.save(new User(name, lastName, age));
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@Override
public void removeUserById(long id) {
    try (Session session = sessionFactory.openSession()) {
        session.createQuery("DELETE FROM User WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@Override
public List<User> getAllUsers() {
    List<User> userList = null;
    try (Session session = sessionFactory.openSession()) {
        userList = session.createQuery("FROM User").list();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return userList;
}

@Override
public void cleanUsersTable() {
    try (Session session = sessionFactory.openSession()) {
        session.createQuery("DELETE FROM User").executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
