package json;

import entities.AstronautEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class ReadFromDB {

    public static void printAllAstrosWithCraft(Session session) {
        List<AstronautEntity> astroList = session.createQuery("FROM AstronautEntity a").list();
        for (AstronautEntity astronaut : astroList) {
            System.out.println("Astronaut: " + astronaut.getName() + " is on craft: " + astronaut.getCraft().getName());
        }
        System.out.println("************************************************");
    }

    public static void printAstroById(Session session, Integer astroId) {
        AstronautEntity astronaut = session.find(AstronautEntity.class, astroId);
        System.out.println("Astronaut: " + astronaut.getName() + " with ID: " + astroId
                + " is on craft: " + astronaut.getCraft().getName());

    }

    public static void printAstroByCraft(Session session) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Select which ship you want to see the crew from / type 'ISS' or 'Tiangong'");
        String craft = scan.nextLine();
        if (craft.equals("ISS")) {
            List<AstronautEntity> astrosOnIss = session.createQuery("FROM AstronautEntity astro WHERE astro.craft=2").list();
            astrosOnIss.forEach(astronautEntity -> {
                System.out.println("On craft ISS is: " + astronautEntity.getName());
            });
        } else if (craft.equals("Tiangong")) {
            List<AstronautEntity> astrosOnIss = session.createQuery("FROM AstronautEntity astro WHERE astro.craft=1").list();
            astrosOnIss.forEach(astronautEntity -> {
                System.out.println("On craft Tiangong is: " + astronautEntity.getName());
            });
        }
    }

    public static void printAstroByName(Session session) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Type name of astronaut:");
        String astroName = scan.nextLine();
        Query query = session.createQuery("FROM AstronautEntity WHERE name = :astronautName");
        query.setParameter("astronautName", astroName);
        AstronautEntity astronautEntity = (AstronautEntity) query.uniqueResult();
        System.out.println("Selected astronaut: " + astroName + " with ID: " + astronautEntity.getId()
                + " is on: " + astronautEntity.getCraft().getName());

    }


}
