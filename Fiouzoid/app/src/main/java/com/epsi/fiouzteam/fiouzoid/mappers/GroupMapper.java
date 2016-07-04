package com.epsi.fiouzteam.fiouzoid.mappers;

import android.util.Log;

import com.epsi.fiouzteam.fiouzoid.model.Group;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by ThaZalman on 30/06/2016.
 */
public class GroupMapper implements JsonSerializer<Group>, JsonDeserializer<Group> {
    @Override
    public JsonElement serialize(Group src, Type typeOfSrc, JsonSerializationContext context) {
        // This method gets involved whenever the parser encounters the Group
        // object (for which this serializer is registered)
        // we create the json object for the group and send it back to the
        // Gson serializer

        JsonObject object = new JsonObject();
        String name = src.getName(),
            description = src.getDescription(),
            id = String.valueOf(src.getId());


        object.addProperty("repo", name);
        object.addProperty("id", id);
        object.addProperty("$id", id);


        return object;
    }

    @Override
    public Group deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
