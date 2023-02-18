import org.opensky.api.client.OpenSkyClient;
import org.opensky.api.model.StateVector;
import org.opensky.api.model.StateVectorBase;
import org.opensky.api.model.TrackBase;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class TestOpenSkyApi {

    public static void main(String[] args) throws IOException {
        OpenSkyClient osc = new OpenSkyClient("","");

        Response<StateVectorBase> portland_states = osc.Api().getStatesAll(0,null,45.087,-123.645,45.998,-121.684,null).execute();


        StateVector first_plane = portland_states.body().states.get(0);

        Response<TrackBase> first_plane_track = osc.Api().getTracks(0,first_plane.icao24).execute();

    }
}
