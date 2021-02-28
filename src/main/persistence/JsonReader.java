package persistence;

import model.Person;
import model.VisitorsList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public VisitorsList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseVisitorsList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private VisitorsList parseVisitorsList(JSONObject jsonObject) {
        //String name = jsonObject.getString("tracking");
        VisitorsList vl = new VisitorsList();
        addPersons(vl, jsonObject);
        return vl;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addPersons(VisitorsList vl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tracking");
        for (Object json : jsonArray) {
            JSONObject nextPerson = (JSONObject) json;
            addPerson(vl, nextPerson);
        }
    }

    // MODIFIES: vl
    // EFFECTS: parses thingy from JSON object and adds it to visitorslist
    private void addPerson(VisitorsList vl, JSONObject jsonObject) {

        String name = jsonObject.getString("name");
        String phoneNumber = jsonObject.getString("phoneNumber");
        String date = jsonObject.getString("date");
        String time = jsonObject.getString("time");
        Boolean status = jsonObject.getBoolean("status");

        Person p = new Person(name, phoneNumber);
        p.setTime(time);
        p.setDate(date);

        if (status) {
            p.setStatusPositive();
        }

        vl.addPerson(p);
    }
}
