package repository;

import model.Show;
import model.Theater;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ShowHibernateRepository implements IShowRepository{
    private SessionFactory sessionFactory;

    public ShowHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Show> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                List<Show> shows = session.createQuery("from Show", Show.class).list();
                transaction.commit();
                return shows;
            } catch (RuntimeException ex) {
                if (transaction != null)
                    transaction.rollback();
                return null;
            }
        }
    }

    @Override
    public Show create(Show entity) {
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
    public Show delete(Integer integer) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query<Show> query = session.createQuery("from Show as s where s.id = :d", Show.class);
                query.setParameter("d", integer);
                Show show = query.getSingleResult();
                session.delete(show);
                tx.commit();
                return show;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Show update(Show entity, Integer integer) {
        return null;
    }

    @Override
    public Show findOne(Integer integer) {
        return null;
    }

    @Override
    public List<Show> findByDate(String date) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query<Show> query = session.createQuery("from Show as s where s.date = :d", Show.class);
                query.setParameter("d", date);
                List<Show> shows = query.list();
                tx.commit();
                return shows;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Theater getTheater() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                List<Theater> teatre = session.createQuery("from Theater", Theater.class).list();
                transaction.commit();
                return teatre.get(0);
            } catch (RuntimeException ex) {
                if (transaction != null)
                    transaction.rollback();
                return null;
            }
        }
    }
}
