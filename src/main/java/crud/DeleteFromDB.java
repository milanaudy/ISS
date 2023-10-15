package crud;

import entities.AstronautEntity;
import entities.CraftsEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DeleteFromDB {
    public static void deleteAll(Session session) {
        session.createQuery("DELETE FROM AstronautEntity").executeUpdate();
        session.createQuery("DELETE FROM CraftsEntity").executeUpdate();
    }

    public static void deleteAstronaut(Session session, String name) {
        Query query = session.createQuery("FROM AstronautEntity WHERE name = :astronautName");
        query.setParameter("astronautName", name);
        AstronautEntity astronautEntity = (AstronautEntity) query.uniqueResult();
        if (astronautEntity != null) {
            session.remove(astronautEntity);
        }
    }
    public static void deleteCraftwithAstronauts(Session session, String name) {
        // Najprv načítajte entitu CraftsEntity podľa mena
        Query query = session.createQuery("FROM CraftsEntity WHERE name = :craftsName");
        query.setParameter("craftsName", name);
        CraftsEntity craftsEntity = (CraftsEntity) query.uniqueResult();
        if (craftsEntity != null) {
            // Načítajte všetkých astronautov, ktorí používajú identifikátor tejto lode (craft)
            Query astronautsQuery = session.createQuery("FROM AstronautEntity WHERE craft = :craftsEntity");
            astronautsQuery.setParameter("craftsEntity", craftsEntity);
            List<AstronautEntity> astronauts = astronautsQuery.list();
            // Odstráňte všetkých astronautov
            for (AstronautEntity astronaut : astronauts) {
                session.remove(astronaut);
            }
            // Odstráňte loď
            session.remove(craftsEntity);
        }
    }
}


