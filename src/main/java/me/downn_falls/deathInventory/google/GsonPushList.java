package me.downn_falls.deathInventory.google;

import com.google.gson.*;
import me.downn_falls.deathInventory.utils.PushList;

import java.lang.reflect.Type;
import java.util.List;

public class GsonPushList<T> implements JsonSerializer<PushList<T>>, JsonDeserializer<PushList<T>> {
    @Override
    public JsonElement serialize(PushList<T> src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getList()); // Convert PushList to a JSON array
    }

    @Override
    public PushList<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<T> list = context.deserialize(json, List.class); // Convert JSON array to List<T>
        return PushList.fromList(list); // Use the fromList method to create a PushList<T>
    }
}
