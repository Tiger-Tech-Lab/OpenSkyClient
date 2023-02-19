package org.opensky.api.model;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Optional;

public class StateVector {
    public String icao24;
    public String callsign;
    public String origin_country;
    public Optional<Integer> time_position;
    public Optional<Integer> last_contact;
    public Optional<Float> longitude;
    public Optional<Float> latitude;
    public Optional<Float> baro_altitude;
    public Optional<Boolean> on_ground;
    public Optional<Float> velocity;
    public Optional<Float> true_track;
    public Optional<Float> vertical_rate;
    public int[] sensors;
    public Optional<Float> geo_altitude;
    public Optional<String> squawk;
    public Optional<Boolean> spi;
    public Optional<Integer> position_source;
    public Optional<Integer> category;


    public static class StateVectorDeserializer implements JsonDeserializer<StateVector>
    {

        @Override
        public StateVector deserialize(JsonElement json_element, Type type, JsonDeserializationContext json_context) throws JsonParseException {
            StateVector ret = new StateVector();

            JsonArray value_array = json_element.getAsJsonArray();

            ret.icao24          = value_array.get(0).getAsString();
            ret.callsign        = value_array.get(1).getAsString();
            ret.origin_country  = value_array.get(2).getAsString();
            ret.time_position   = optionalInteger(value_array,3);
            ret.last_contact    = optionalInteger(value_array, 4);
            ret.longitude       = optionalFloat(value_array,5);
            ret.latitude        = optionalFloat(value_array,6);
            ret.baro_altitude   = optionalFloat(value_array,7);
            ret.on_ground       = optionalBoolean(value_array,8);
            ret.velocity        = optionalFloat(value_array,9);
            ret.true_track      = optionalFloat(value_array,10);
            ret.vertical_rate   = optionalFloat(value_array,11);
            ret.sensors         = null; // 12
            ret.geo_altitude    = optionalFloat(value_array,13);
            ret.squawk          = optionalString(value_array, 14);
            ret.spi             = optionalBoolean(value_array,15);
            ret.position_source = optionalInteger(value_array, 16);
            ret.category        = optionalInteger(value_array, 17);

            return ret;
        }

        public Optional<Boolean> optionalBoolean(JsonArray values, int pos){
            // If the values exist, and its not JSON Null
            if(values.size() > pos && !values.get(pos).isJsonNull()){
                return Optional.of(new Boolean(values.get(pos).getAsBoolean()));
            }else{
                return Optional.empty();
            }
        }

        public Optional<String> optionalString(JsonArray values, int pos){
            // If the values exist, and its not JSON Null
            if(values.size() > pos && !values.get(pos).isJsonNull()){
                return Optional.of(values.get(pos).getAsString());
            }else{
                return Optional.empty();
            }
        }

        public Optional<Integer> optionalInteger(JsonArray values, int pos)
        {
            // If the values exist, and its not JSON Null
            if(values.size() > pos && !values.get(pos).isJsonNull()){
                return Optional.of(new Integer(values.get(pos).getAsInt()));
            }else{
                return Optional.empty();
            }
        }

        public Optional<Float> optionalFloat(JsonArray values, int pos){
            // If the values exist, and its not JSON Null
            if(values.size() > pos && !values.get(pos).isJsonNull()){
                return Optional.of(new Float(values.get(pos).getAsFloat()));
            }else{
                return Optional.empty();
            }
        }
    }
}
