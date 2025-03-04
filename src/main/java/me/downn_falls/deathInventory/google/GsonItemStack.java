package me.downn_falls.deathInventory.google;

import com.google.gson.*;
import me.downn_falls.deathInventory.utils.ItemStackSerializer;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;

public class GsonItemStack implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return ItemStackSerializer.deserialize(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext jsonSerializationContext) {

        return new JsonPrimitive(ItemStackSerializer.serialize(itemStack));
    }
}
