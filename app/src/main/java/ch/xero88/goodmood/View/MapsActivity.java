package ch.xero88.goodmood.View;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

import ch.xero88.goodmood.Data.Position;
import ch.xero88.goodmood.Map.MoodMarkerRendered;
import ch.xero88.goodmood.Mood.Mood;
import ch.xero88.goodmood.Mood.MoodRepositories;
import ch.xero88.goodmood.Mood.MoodRepository;
import ch.xero88.goodmood.R;
import ch.xero88.goodmood.Utils.StringUtil;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private String TAG = "MapsActivity";

    // Map
    private GoogleMap mMap;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    private ClusterManager<Mood> mClusterManager;

    // Database access
    private MoodRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // TODO: 06/07/2016 delete
        Mood mood = new Mood();
        mood.setId(StringUtil.randomString(20));
        Position position = new Position(46.233122, 7.360626);
        mood.setPosition(position);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            mood.setEmail(email);
            mood.setDisplayName(user.getDisplayName());
        }

        MoodRepository repository = MoodRepositories.getFirebaseRepoInstance();
        repository.saveMood(mood);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // User location
        checkLocationPermission(false);
        mMap.setMyLocationEnabled(true);

        // Set up cluster
        setUpClusterer();

        repository = MoodRepositories.getFirebaseRepoInstance();
        // get moods
        /*
        repository.getMoods(new MoodRepository.LoadMoodsCallback() {
            @Override
            public void onMoodLoaded(List<Mood> moods) {
                placeMoodsOnMap(moods);
                Log.e(TAG, "Place moods on map (must be called once)");
            }

            @Override
            public void onMoodFailed() {
                Log.e(TAG, "Moods failed");
            }
        });*/

        // get new moods if any
        repository.getChangedMoods(new MoodRepository.ChangedMoodsCallback() {

            @Override
            public void onMoodAdded(Mood mood) {
                placeMoodOnMap(mood);
                Log.e(TAG, "Place a mood");
            }

            @Override
            public void onMoodChanged(Mood mood) {

            }

            @Override
            public void onMoodRemoved(Mood moods) {

            }

            @Override
            public void onMoodMoved(Mood mood) {

            }

            @Override
            public void onCancelled() {

            }
        });
    }

    private void setUpClusterer() {

        // Position the map.
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10)); // TODO: 05/07/2016 get the user lat and lng

        // Initialize the manager with the context and the map.
        mClusterManager = new ClusterManager<Mood>(this, mMap);
        mClusterManager.setRenderer(new MoodMarkerRendered(getApplicationContext(), mMap, mClusterManager));

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
    }

    private void placeMoodOnMap(Mood mood) {
        mClusterManager.addItem(mood);
        mClusterManager.cluster();
    }

    private void placeMoodsOnMap(List<Mood> moods) {

        // place on map moods
        for (Mood mood: moods) {
            placeMoodOnMap(mood);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (permissions.length == 1 &&
                    permissions[0] == "ACCESS_FINE_LOCATION" &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocationPermission(true);
                mMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

    private void checkLocationPermission(boolean ok) {

        if(ok)
            return;

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                "android.permission.ACCESS_FINE_LOCATION")
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    "android.permission.ACCESS_FINE_LOCATION")) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{"android.permission.ACCESS_FINE_LOCATION"},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
