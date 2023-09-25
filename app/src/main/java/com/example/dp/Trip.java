package com.example.dp;

public class Trip {
    private String tripname,trip_id;

    public Trip(String trip_id, String tripname) {
        this.tripname = tripname;
        this.trip_id = trip_id;
    }

    public String getTrip_id() {

        return trip_id;
    }

    public void setTrip_id(String trip_id) {

        this.trip_id = trip_id;
    }
    public String getTripname() {

        return tripname;
    }

    public void setTripname(String tripname) {

        this.tripname = tripname;
    }


}
