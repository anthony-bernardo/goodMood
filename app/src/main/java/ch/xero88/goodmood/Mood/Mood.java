package ch.xero88.goodmood.Mood;


import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import ch.xero88.goodmood.Data.Position;

/**
 * Created by Xero88 on 14/06/2016.
 */
public class Mood implements ClusterItem {

    private String id;
    private String name;
    private LatLng latLng;
    private Position position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LatLng getPosition() {
        return latLng;
    }

    public Position getMoodPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.latLng = new LatLng(position.getLatitude(), position.getLongitude());
    }
}
