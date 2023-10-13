package parser;

import entities.AstronautEntity;
import entities.CraftsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbConnect {

    public static Session getSession() {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(CraftsEntity.class)
                .addAnnotatedClass(AstronautEntity.class)
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        return session;
    }
}
