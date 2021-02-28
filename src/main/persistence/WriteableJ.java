package persistence;

import org.json.JSONObject;

public interface WriteableJ {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();

}
