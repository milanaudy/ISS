package json;

import entities.AstronautEntity;
import entities.CraftsEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class Adder {
       public static void addCraft(Session session, String craftName) {
        // Skontrolujte, či loď s daným menom už existuje v databáze
        Query query = session.createQuery("SELECT c FROM CraftsEntity c WHERE c.name = :craftName");
        query.setParameter("craftName", craftName);
        CraftsEntity existingCraft = (CraftsEntity) query.uniqueResult();

        // Ak loď neexistuje, pridajte ju do databázy
        if (existingCraft == null) {
            CraftsEntity crafts = new CraftsEntity();
            crafts.setName(craftName);
            session.persist(crafts);
        }
    }

    public static void addAstronaut(Session session, String astronautName, String craftName) {
        // Najprv načítajte entitu CraftsEntity podľa mena craftu
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<CraftsEntity> query = builder.createQuery(CraftsEntity.class);
        Root<CraftsEntity> root = query.from(CraftsEntity.class);
        query.select(root).where(builder.equal(root.get("name"), craftName));
        CraftsEntity craftsEntity = session.createQuery(query).getSingleResult();

        // Vytvorte novú entitu AstronautEntity a nastavte craft_id
        AstronautEntity astronautEntity = new AstronautEntity();
        astronautEntity.setName(astronautName);
        astronautEntity.setCraft(craftsEntity); // Nastavenie vzťahu k craftu

        // Uložte astronauta do databázy
        session.persist(astronautEntity);

    }
}
