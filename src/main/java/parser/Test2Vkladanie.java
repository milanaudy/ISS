package parser;

import entities.AstronautEntity;
import entities.CraftsEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Test2Vkladanie {
    public static void main(String[] args) {
        Session session = DbConnect.getSession();
        Transaction transaction = session.beginTransaction();
        addCraft(session, "ISS");
        addAstronaut(session,"palo astronaut",1);
        transaction.commit();
        session.close();
    }
    public static void addCraft(Session session, String craftName) {
        CraftsEntity crafts = new CraftsEntity();
        crafts.setName(craftName);
        session.persist(crafts);
    }
    public static void addAstronaut(Session session, String astronauName, Integer craftID){
        CraftsEntity craftsEntity = session.find(CraftsEntity.class,craftID);
        AstronautEntity astronautEntity = new AstronautEntity();
        astronautEntity.setName(astronauName);
        astronautEntity.setCraft(craftsEntity);
        session.persist(astronautEntity);
    }
}
