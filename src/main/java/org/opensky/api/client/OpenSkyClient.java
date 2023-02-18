package org.opensky.api.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.opensky.api.model.StateVector;
import org.opensky.api.model.StateVectorBase;
import org.opensky.api.model.Track;
import org.opensky.api.model.TrackBase;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.List;

public class OpenSkyClient {

    private static final String BASE_URL = "https://opensky-network.org/api/";
    private OpenSkyEndpoints api;
    private Retrofit retrofit;
    private String password = "";
    private String username = "";
    private OkHttpClient ok_http;
    private Gson custom_gson;

    public OpenSkyClient(String username, String password){
        this.username = username;
        this.password = password;

        init();
    }

    public OpenSkyClient(){
        init();
    }

    private void init() {
        this.ok_http = new OkHttpClient.Builder()
                .addInterceptor(new OpenSkyBasicAuth(username,password))
                .build();
        this.custom_gson = new GsonBuilder()
                .registerTypeAdapter(StateVector.class, new StateVector.StateVectorDeserializer() )
                .registerTypeAdapter(Track.class, new Track.TrackDeserializer() )
                .create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(custom_gson))
                .client(ok_http)
                .build()
        ;
        this.api = retrofit.create(OpenSkyEndpoints.class);
    }

    public OpenSkyEndpoints Api(){
        return this.api;
    }

    public class OpenSkyBasicAuth implements Interceptor{
        private final String credentials;

        public OpenSkyBasicAuth(String username, String password){
            this.credentials = Credentials.basic(username, password);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }
    }

    public interface OpenSkyEndpoints {

        // there is a reason why we are using primitives for required parameters
        // and objects for optional parameters.
        // Optional parameters are ignored by retrofit

        @GET("states/all")
        Call<StateVectorBase> getStatesAll(
                @Query("time")
                int unix_time,
                @Query("icao24")
                String icao24,
                @Query("lamin")
                Double lat_min,
                @Query("lomin")
                Double lon_min,
                @Query("lamax")
                Double lat_max,
                @Query("lomax")
                Double lon_max,
                @Query("extended")
                Integer extended
        );

        @GET("states/own")
        Call<StateVectorBase> getStatesOwn(
                @Query("time")
                int unix_time,
                @Query("icao24")
                List<String> icao24,
                @Query("serials")
                int serials
        );

        @GET("states/own")
        Call<StateVectorBase> getStatesOwn(
                @Query("time")
                int unix_time,
                @Query("icao24")
                String icao24,
                @Query("serials")
                int serials
        );

        @GET("flights/all")
        Call<Object> getFlightsAll(
                @Query("begin")
                int unix_time_begin,
                @Query("end")
                int unix_time_end
        );

        @GET("flights/aircraft")
        Call<Object> getFlightsByAircraft(
                @Query("icao24")
                String icao24,
                @Query("begin")
                int unix_time_begin,
                @Query("end")
                int unix_time_end
        );

        @GET("flights/arrival")
        Call<Object> getFlightsByArrival(
                @Query("airport")
                String airport,
                @Query("begin")
                int unix_time_begin,
                @Query("end")
                int unix_time_end
        );

        @GET("flights/departure")
        Call<Object> getFlightsByDeparture(
                String airport,
                @Query("begin")
                int unix_time_begin,
                @Query("end")
                int unix_time_end
        );

        @GET("tracks/all")
        Call<TrackBase> getTracks(
                @Query("time")
                int unix_time,
                @Query("icao24")
                String icao24
        );
    }
}
