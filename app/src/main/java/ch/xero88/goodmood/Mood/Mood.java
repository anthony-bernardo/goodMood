package ch.xero88.goodmood.Mood;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;
import com.google.maps.android.clustering.ClusterItem;

import ch.xero88.goodmood.Data.Position;

/**
 * Created by Xero88 on 14/06/2016.
 */
public class Mood implements ClusterItem {

    private String id;
    private String name;
    @Exclude
    private LatLng latLng;
    private Position position;
    private String email;
    private String displayName;

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

    @Exclude
    public Position getMoodPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.latLng = new LatLng(position.getLatitude(), position.getLongitude());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
