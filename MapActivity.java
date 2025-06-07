package com.example.waterchecker;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_map);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(8.0);
        GeoPoint sriLankaCenter = new GeoPoint(7.8731, 80.7718);
        mapController.setCenter(sriLankaCenter);

        addRedMarkers();
        addGreenMarkers();
    }

    /** @noinspection deprecation*/
    private void addRedMarkers() {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable redIcon = getResources().getDrawable(R.drawable.redmap);
        List<GeoPoint> redLocations = new ArrayList<>();

        // Add red map points (drinkable water)
        redLocations.add(new GeoPoint(7.071868, 79.926596));  // https://maps.app.goo.gl/chr4eQ6CT5qswVJq6
        redLocations.add(new GeoPoint(7.2459423, 80.1484336)); // https://maps.app.goo.gl/qs413CRayvWJfpNU7
        redLocations.add(new GeoPoint(6.8660333, 80.9600054)); // https://maps.app.goo.gl/oEd7Z1piYt6iXq8T7
        redLocations.add(new GeoPoint(7.0879782, 80.0147897)); // https://maps.app.goo.gl/si3FKxCkvgce3wTv9
        redLocations.add(new GeoPoint(7.0864568, 79.899729));  // https://maps.app.goo.gl/J4q771XcpgZ2fX1w7

        // Add more from your redmap link coordinates...

        for (GeoPoint point : redLocations) {
            Marker marker = new Marker(map);
            marker.setPosition(point);
            marker.setIcon(redIcon);
            marker.setTitle("Drinkable Water Location");
            map.getOverlays().add(marker);
        }
    }

    /** @noinspection deprecation*/
    private void addGreenMarkers() {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable greenIcon = getResources().getDrawable(R.drawable.greenicon);
        List<GeoPoint> greenLocations = new ArrayList<>();

        // Add green map points (treatment plants)
        greenLocations.add(new GeoPoint(6.902786, 79.861320));   // https://maps.app.goo.gl/qxxgU6SFoqHuCpJS9
        greenLocations.add(new GeoPoint(6.930579, 79.865226));   // https://maps.app.goo.gl/JSB6cFkiAVcohc2j7
        greenLocations.add(new GeoPoint(6.9111733, 79.8666032)); // https://maps.app.goo.gl/pCJBXKQQuNppf4CWA
        greenLocations.add(new GeoPoint(6.9050266, 79.873035));  // https://maps.app.goo.gl/TReuyPnD8ZrBXCaD6
        greenLocations.add(new GeoPoint(6.9306833, 79.8651462)); // https://maps.app.goo.gl/Rp6BQu2CLUbueSVz6
        greenLocations.add(new GeoPoint(6.9303201, 79.866517));  // https://maps.app.goo.gl/gjt1ECTMhqNRWsRu5
        greenLocations.add(new GeoPoint(6.9303201, 79.866517));  // https://maps.app.goo.gl/ePe1ru4Bd3cEoX7T7
        greenLocations.add(new GeoPoint(6.9283644, 79.863657));  // https://maps.app.goo.gl/e5K6QfnTDQw6fRfK7
        greenLocations.add(new GeoPoint(6.9320312, 79.8566217)); // https://maps.app.goo.gl/QfMur33gcTqFdPzs5
        greenLocations.add(new GeoPoint(6.9290921, 79.8617073)); // https://maps.app.goo.gl/AT4SXQyxycAJQBu78
        greenLocations.add(new GeoPoint(6.9249276, 79.859088));  // https://maps.app.goo.gl/fxwnGMDSc8KZehH26
        greenLocations.add(new GeoPoint(6.927371, 79.8618263));  // https://maps.app.goo.gl/GTqByY7YLHtcAXrb8
        greenLocations.add(new GeoPoint(6.9295275, 79.8617513)); // https://maps.app.goo.gl/mAqYN2Y1hdChHRH78
        greenLocations.add(new GeoPoint(6.9293035, 79.8616328)); // https://maps.app.goo.gl/ABdVvBSRsXRGxMXK7


        // Add more from your greenmap link coordinates...

        for (GeoPoint point : greenLocations) {
            Marker marker = new Marker(map);
            marker.setPosition(point);
            marker.setIcon(greenIcon);
            marker.setTitle("Water Treatment Plant");
            map.getOverlays().add(marker);
        }
    }
}
