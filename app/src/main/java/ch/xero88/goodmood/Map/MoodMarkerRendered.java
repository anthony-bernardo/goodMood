package ch.xero88.goodmood.Map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import ch.xero88.goodmood.Mood.Mood;
import ch.xero88.goodmood.R;

/**
 * Created by Xero88 on 05/07/2016.
 */
public class MoodMarkerRendered extends DefaultClusterRenderer<Mood> {

    public MoodMarkerRendered(Context context, GoogleMap map,
                              ClusterManager<Mood> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(Mood item, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_goodmood));
        markerOptions.title(item.getId());
        super.onBeforeClusterItemRendered(item, markerOptions);
    }
}