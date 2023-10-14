package json;

import entities.AstronautEntity;
import entities.CraftsEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class Updater {

    public static void updateAstronautName(Session session, String oldName, String newName) {
        Query query = session.createQuery("FROM AstronautEntity WHERE name = :astronautName");
        query.setParameter("astronautName", oldName);
        AstronautEntity astronautEntity = (AstronautEntity) query.uniqueResult();
        if (astronautEntity != null) {
            astronautEntity.setName(newName);
        }
    }

    public static void updateCraftName(Session session, String oldName, String newName) {
        Query query = session.createQuery("FROM CraftsEntity WHERE name = :craftName");
        query.setParameter("craftName", oldName);
        CraftsEntity craftsEntity = (CraftsEntity) query.uniqueResult();
        if (craftsEntity != null) {
            craftsEntity.setName(newName);
        }
    }

    public static void updateCraftofAstronaut(Session session, String astronautName, String newCraftName) {
        // Najprv načítajte entitu AstronautEntity podľa mena astronauta
        Query astronautQuery = session.createQuery("FROM AstronautEntity WHERE name = :astronautName");
        astronautQuery.setParameter("astronautName", astronautName);
        AstronautEntity astronautEntity = (AstronautEntity) astronautQuery.uniqueResult();

        if (astronautEntity != null) {
            // Načítajte entitu CraftsEntity podľa mena craftu
            Query craftQuery = session.createQuery("FROM CraftsEntity WHERE name = :craftName");
            craftQuery.setParameter("craftName", newCraftName);
            CraftsEntity craftsEntity = (CraftsEntity) craftQuery.uniqueResult();

            if (craftsEntity != null) {
                // Nastavte vzťah astronauta k novému craftu
                astronautEntity.setCraft(craftsEntity);
            } else {
                // Ak entita CraftsEntity s novým menom neexistuje, môžete vytvoriť novú loď.
                craftsEntity = new CraftsEntity();
                craftsEntity.setName(newCraftName);
                session.persist(craftsEntity); // Uložte novú loď do databázy
                astronautEntity.setCraft(craftsEntity); // Nastavenie vzťahu k novému craftu
            }
        }
    }
}


