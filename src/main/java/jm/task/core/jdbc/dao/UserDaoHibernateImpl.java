package jm.task.core.jdbc.dao;

import com.mysql.cj.xdevapi.SessionFactory;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    private static final SessionFactory sessionFactory = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query =  session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), lastname VARCHAR(50), age TINYINT)").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            session.getTransaction().commit();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            allUsers = session.createQuery("SELECT * FROM users", User.class).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
