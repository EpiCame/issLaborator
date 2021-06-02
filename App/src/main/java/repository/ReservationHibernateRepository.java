package repository;

import model.Reservation;
import model.Show;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ReservationHibernateRepository implements IReservationRepository{

    private static SessionFactory sessionFactory;

    public ReservationHibernateRepository(SessionFactory factory){
        sessionFactory = factory;
    }

    private static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Override
    public List<Reservation> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                List<Reservation> res = session.createQuery("from Reservation", Reservation.class).list();
                transaction.commit();
                return res;
            } catch (RuntimeException ex) {
                if (transaction != null)
                    transaction.rollback();
                return null;
            }
        }
    }

    @Override
    public Reservation create(Reservation entity) {
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
    public Reservation delete(Integer integer) {
        return null;
    }

    @Override
    public Reservation update(Reservation entity, Integer integer) {
        return null;
    }

    @Override
    public Reservation findOne(Integer integer) {
        return null;
    }

    @Override
    public Reservation findByRowSeat(int row, int seat, Show show) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query<Reservation> query = session.createQuery("from Reservation as r where r.show = :s and r.seat = :st and r.row = :rw", Reservation.class);
                query.setParameter("s", show);
                query.setParameter("st", seat);
                query.setParameter("rw", row);
                Reservation reservationList = query.getSingleResult();
                tx.commit();
                return reservationList;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public List<Reservation> findByShow(Show show) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query<Reservation> query = session.createQuery("from Reservation as r where r.show = :s", Reservation.class);
                query.setParameter("s", show);
                List<Reservation> reservationList = query.getResultList();
                tx.commit();
                return reservationList;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }
}
