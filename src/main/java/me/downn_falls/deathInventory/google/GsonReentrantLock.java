package me.downn_falls.deathInventory.google;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class GsonReentrantLock extends TypeAdapter<ReentrantLock> {
    @Override
    public void write(JsonWriter out, ReentrantLock value) throws IOException {
        out.beginObject();
        // Serialize relevant fields, but skip the restricted field
        out.endObject();
    }

    @Override
    public ReentrantLock read(JsonReader in) throws IOException {
        in.beginObject();
        // Handle deserialization, ensure the restricted field is not accessed
        in.endObject();
        return new ReentrantLock();
    }
}
