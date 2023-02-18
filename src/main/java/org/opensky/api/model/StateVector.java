package org.opensky.api.model;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StateVector {
    public String icao24;
    public String callsign;
    public String origin_country;
    public int time_position;
    public int last_contact;
    public float longitude;
    public float latitude;
    public float baro_altitude;
    public boolean on_ground;
    public float velocity;
    public float true_track;
    public float vertical_rate;
    public int[] sensors;
    public float geo_altitude;
    public String squawk;
    public boolean spi;
    public int position_source;
    public int category;


    public static class StateVectorDeserializer implements JsonDeserializer<StateVector>
    {

        @Override
        public StateVector deserialize(JsonElement json_element, Type type, JsonDeserializationContext json_context) throws JsonParseException {
            StateVector ret = new StateVector();

            JsonArray value_array = json_element.getAsJsonArray();

            ret.icao24 = value_array.get(0).getAsString();
            ret.callsign = value_array.get(1).getAsString();
            ret.origin_country = value_array.get(2).getAsString();
            ret.time_position = value_array.get(3).getAsInt();
            ret.last_contact = value_array.get(4).getAsInt();
            ret.longitude = value_array.get(5).getAsFloat();
            ret.latitude = value_array.get(6).getAsFloat();
            ret.baro_altitude = value_array.get(7).getAsFloat();
            ret.on_ground = value_array.get(8).getAsBoolean();
            ret.velocity = value_array.get(9).getAsFloat();
            ret.true_track = value_array.get(10).getAsFloat();
            ret.vertical_rate = value_array.get(11).getAsFloat();
            ret.sensors = null;
            ret.geo_altitude = value_array.get(13).getAsFloat();
            ret.squawk = value_array.get(14).isJsonNull() ? null : value_array.get(14).getAsString();
            ret.spi = value_array.get(15).getAsBoolean();
            ret.position_source = value_array.get(16).getAsInt();
            if(value_array.size()>17) {
                ret.category = value_array.get(17).isJsonNull() ? null : value_array.get(17).getAsInt();
            }
            return ret;
        }
    }
}
