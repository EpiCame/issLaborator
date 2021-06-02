package repository;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import java.util.List;

public class UserHibernateRepository implements IUserRepository{

    private static SessionFactory sessionFactory;

    public UserHibernateRepository(SessionFactory factory){
        sessionFactory = factory;
    }

    private static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                List<User> users = session.createQuery("from User", User.class).list();
                transaction.commit();
                return users;
            } catch (RuntimeException ex) {
                if (transaction != null)
                    transaction.rollback();
                return null;
            }
        }
    }

    @Override
    public User create(User entity) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
                return entity;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public User delete(String s) {
        return null;
    }

    @Override
    public User update(User entity, String s) {
        return null;
    }

    @Override
    public User findOne(String s) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query<User> query = session.createQuery("from User as u where u.id = :s", User.class);
                query.setParameter("s", s);
                User user = query.getSingleResult();
                tx.commit();
                return user;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }
}
