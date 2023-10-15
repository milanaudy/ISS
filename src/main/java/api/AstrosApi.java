package api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import crud.AddToDB;
import crud.DeleteFromDB;
import crud.ReadFromDB;
import crud.UpdateDB;
import org.hibernate.Session;
import org.hibernate.Transaction;
import database.DbConnect;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AstrosApi {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://api.open-notify.org/astros.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());

                int number = rootNode.get("number").asInt();
                //System.out.println("Počet ľudí v medzinárodnej vesmírnej stanici: " + number);
                JsonNode people = rootNode.get("people");

                Session session = DbConnect.getSession();
                Transaction transaction = session.beginTransaction();

                for (JsonNode person : people) {
                    String name = person.get("name").asText();
                    String craft = person.get("craft").asText();
                    //System.out.println(name + " na palube " + craft);


                    AddToDB.addCraft(session, craft);
                    AddToDB.addAstronaut(session, name, craft);
                }

                //DeleteFromDB.deleteAll(session); //mazanie celej tabulky
                //DeleteFromDB.deleteAstronaut(session,"Gui Haichow"); //mazanie podla mena astronauta
                //DeleteFromDB.deleteCraftwithAstronauts(session,"ISS"); //odstranuje lod aj s astronautami ktore su na nej

                //UpdateDB.updateAstronautName(session, "Gui Haichow","Howard J. Wolowitz"); // update mena astronauta
                //UpdateDB.updateCraftName(session,"ISS","Medzinarodna vesmirna stanica"); //update mena lode
                //UpdateDB.updateCraftofAstronaut(session,"Gui Haichow","SpaceX"); //zmení loď astronautovi,ak nova lod este neexistuje prida novu


                transaction.commit();
                //ReadFromDB.printAllAstrosWithCraft(session);
                //ReadFromDB.printAstroById(session, 1);
                //ReadFromDB.printAstroByCraft(session);
                //ReadFromDB.printAstroByName(session);
                session.close();
            } else {
                System.out.println("Chyba pri získavaní dát. Kód odpovede: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
