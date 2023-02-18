package org.opensky.api.model;

import com.google.gson.*;

import java.lang.reflect.Type;

public class Track {
    public int time ;
    public float latitude ;
    public float longitude ;
    public float baro_altitude ;
    public float true_track ;
    public boolean on_ground ;

    public static class TrackDeserializer implements JsonDeserializer<Track>
    {

        @Override
        public Track deserialize(JsonElement json_element, Type type, JsonDeserializationContext json_context) throws JsonParseException {
            Track ret = new Track();

            JsonArray value_array = json_element.getAsJsonArray();

            ret.time = value_array.get(0).getAsInt();
            ret.latitude = value_array.get(1).getAsFloat();
            ret.longitude = value_array.get(2).getAsFloat();
            ret.baro_altitude = value_array.get(3).getAsFloat();
            ret.true_track = value_array.get(4).getAsFloat();
            ret.on_ground = value_array.get(5).getAsBoolean();

            return ret;
        }
    }
}
