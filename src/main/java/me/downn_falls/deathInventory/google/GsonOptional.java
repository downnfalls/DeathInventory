package me.downn_falls.deathInventory.google;

import com.google.gson.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public class GsonOptional implements JsonSerializer<Optional<?>>, JsonDeserializer<Optional<?>> {
    @Override
    public JsonElement serialize(Optional<?> src, Type typeOfSrc, JsonSerializationContext context) {
        return src.isPresent() ? context.serialize(src.get()) : JsonNull.INSTANCE;
    }

    @Override
    public Optional<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonNull()) {
            return Optional.empty();
        }
        return Optional.ofNullable(context.deserialize(json, ((ParameterizedType) typeOfT).getActualTypeArguments()[0]));
    }
}
