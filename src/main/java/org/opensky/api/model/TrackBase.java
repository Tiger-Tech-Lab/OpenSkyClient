package org.opensky.api.model;

import java.util.List;

public class TrackBase {
    public String icao24;
    public String callsign;
    public int startTime;
    public int endTime;
    public List<Track> path;
}
