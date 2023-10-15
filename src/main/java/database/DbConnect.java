package database;

import entities.AstronautEntity;
import entities.CraftsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbConnect {

    public static Session getSession() {
        String dbUsername = System.getenv("DB_USERNAME_ENV_VARIABLE");
        String dbPassword = System.getenv("DB_PASSWORD_ENV_VARIABLE");
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .setProperty("hibernate.connection.username", dbUsername)
                .setProperty("hibernate.connection.password", dbPassword)
                .addAnnotatedClass(CraftsEntity.class)
                .addAnnotatedClass(AstronautEntity.class)
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        return session;
    }
}
