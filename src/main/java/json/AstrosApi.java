package json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CraftsEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import parser.DbConnect;

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
                System.out.println("Počet ľudí v medzinárodnej vesmírnej stanici: " + number);
                JsonNode people = rootNode.get("people");

                Session session = DbConnect.getSession();
                Transaction transaction = session.beginTransaction();

                for (JsonNode person : people) {
                    String name = person.get("name").asText();
                    String craft = person.get("craft").asText();
                    System.out.println(name + " na palube " + craft);


                    Adder.addCraft(session, craft);
                    Adder.addAstronaut(session, name, craft);


                }
                transaction.commit();
                session.close();
            } else {
                System.out.println("Chyba pri získavaní dát. Kód odpovede: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
