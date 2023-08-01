package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

private final SessionFactory sessionFactory;

public UserDaoHibernateImpl() {
    sessionFactory = Util.getSessionFactory();
}

@Override
public void createUsersTable() {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(45) NOT NULL," +
                "lastName VARCHAR(45) NOT NULL," +
                "age TINYINT NOT NULL," +
                "PRIMARY KEY (id)" +
                ")";
        session.createSQLQuery(sql).executeUpdate();
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        System.err.println("Ошибка при создании таблицы пользователей: " + e.getMessage());
    }
}

@Override
public void dropUsersTable() {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        String sql = "DROP TABLE IF EXISTS users";
        session.createSQLQuery(sql).executeUpdate();
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        System.err.println("Ошибка при удалении таблицы пользователей: " + e.getMessage());
    }
}

@Override
public void saveUser(String name, String lastName, byte age) {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        transaction.commit();
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        System.err.println("Ошибка при сохранении пользователя: " + e.getMessage());
    }
}

@Override
public void removeUserById(long id) {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        String sql = "DELETE FROM User WHERE id = :id";
        session.createQuery(sql).setParameter("id", id).executeUpdate();
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
    }
}

@Override
public List<User> getAllUsers() {
    List<User> userList = null;
    try (Session session = sessionFactory.openSession()) {
        String sql = "FROM User";
        userList = session.createQuery(sql, User.class).list();
    } catch (Exception e) {
        System.err.println("Ошибка при получении списка пользователей: " + e.getMessage());
    }
    return userList;
}

@Override
public void cleanUsersTable() {
    Transaction transaction = null;
    try (Session session = sessionFactory.openSession()) {
        transaction = session.beginTransaction();
        String sql = "DELETE FROM User";
        session.createQuery(sql).executeUpdate();
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        System.err.println("Ошибка при очистке таблицы пользователей: " + e.getMessage());
    }
}
}
