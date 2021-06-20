package ru.vtb.java.lesson9.repositories;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import ru.vtb.java.lesson9.repositories.entities.OrderEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderRepository extends BaseRepository<OrderEntity, Long> {
    public OrderRepository() {
        super(OrderEntity.class, null);

        this.factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public ArrayList<OrderEntity> getAll() {
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();

            List result = session.createQuery("select o from OrderEntity o").list();

            session.getTransaction().commit();

            return (ArrayList<OrderEntity>) result;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null)
                session.close();
        }
    }
}
