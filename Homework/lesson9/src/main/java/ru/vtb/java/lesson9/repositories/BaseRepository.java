package ru.vtb.java.lesson9.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class BaseRepository<T, U extends Long> {
    protected SessionFactory factory;

    protected Class<T> typeOfT;
    protected Session session;

    public BaseRepository() { }

    public BaseRepository(Class<T> type, SessionFactory factory) {
        this.factory = factory;

        this.typeOfT = type;
    }

    public T get(U id) {
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();

            var result = session.get(typeOfT, id);

            session.getTransaction().commit();

            return (T) result;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null)
                session.close();
        }
    }

    public void update(T inputEntity) {
        sessionWrapper(() -> session.update(inputEntity));
    }

    public void insert(T inputEntity) {
        sessionWrapper(() -> session.save(inputEntity));
    }

    public void delete(T inputEntity) {
        sessionWrapper(() -> session.delete(inputEntity));
    }

    private void sessionWrapper(Runnable inputAction) {
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();

            inputAction.run();

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null)
                session.close();
        }
    }
}