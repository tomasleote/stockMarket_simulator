package nl.rug.aoop.messagequeue;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * GsonAdapter class.
 */
public class GsonAdapter extends TypeAdapter<Message> {
    /**
     * body.
     */
    public static final String BODY_FIELD = "body";
    /**
     * header.
     */
    public static final String HEADER_FIELD = "header";
    /**
     * time.
     */
    public static final String TIME_FIELD = "time";

    @Override
    public Message read(JsonReader jsonreader) throws IOException {
        jsonreader.beginObject();
        String header = null;
        String body = null;
        LocalDateTime localDateTime = null;
        while (jsonreader.hasNext()) {
            JsonToken token = jsonreader.peek();
            String fieldName = null;
            if (token.equals(JsonToken.NAME)) {
                fieldName = jsonreader.nextName();
            }
            if (fieldName == null) {
                continue;
            }
            switch (fieldName) {
                case HEADER_FIELD -> {
                    header = jsonreader.nextString();
                }
                case BODY_FIELD -> {
                    body = jsonreader.nextString();
                }
                case TIME_FIELD -> {
                    localDateTime = LocalDateTime.parse(jsonreader.nextString());
                }
            }
        }
        jsonreader.endObject();
        return new Message(header, body, localDateTime);
    }

    @Override
    public void write(JsonWriter writer, Message jsonExample) throws IOException {
        writer.beginObject();
        writer.name(HEADER_FIELD);
        writer.value(jsonExample.getHeader());
        writer.name(BODY_FIELD);
        writer.value(jsonExample.getBody());
        writer.name(TIME_FIELD);
        writer.value(jsonExample.getTimestamp().toString());
        writer.endObject();
    }
}
