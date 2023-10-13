package json;

import entities.AstronautEntity;
import entities.CraftsEntity;
import org.hibernate.Session;

public class Adder {
    public static void addCraft(Session session, String craftName) {
        CraftsEntity crafts = new CraftsEntity();
        crafts.setName(craftName);
        session.persist(crafts);
    }
    public static AstronautEntity addAstronaut(Session session, String astronauName, Integer craftID){
        CraftsEntity craftsEntity = session.find(CraftsEntity.class,craftID);
        AstronautEntity astronautEntity = new AstronautEntity();
        astronautEntity.setName(astronauName);
        astronautEntity.setCraft(craftsEntity);
        AstronautEntity saveAstronaut = (AstronautEntity) session.merge(astronautEntity);
        return saveAstronaut;
    }
}
