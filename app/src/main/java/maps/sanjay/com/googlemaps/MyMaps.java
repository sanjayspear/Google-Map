package maps.sanjay.com.googlemaps;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyMaps extends FragmentActivity {
    long lastUpdate;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private  float last_x, last_y, last_z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_maps);
        setUpMapIfNeeded();

        //setup the accelerometer
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onSensorChanged(SensorEvent event){
        Sensor mySensor = event.sensor;
        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];//x values
            float y = event.values[1];//y values
            float z = event.values[2];//z values

            long curTime = System.currentTimeMillis();

            if(Math.abs(curTime - lastUpdate)>2000){
                SimpleDateFormat date = new SimpleDateFormat("dd-mm-yyyy");
                String currentDateTime = date.format(new Date());

                lastUpdate = curTime;

                if(Math.abs(last_x - x)>10){
                    mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(12.9667, 77.5667))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                    .title("Hey you moved the x-axis on" + currentDateTime));
                }

                if(Math.abs(last_y - y)>10){
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(12.8667, 77.4667))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .title("Hey you moved the y-axis on" + currentDateTime));
                }

                if(Math.abs(last_z - z)>10){
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(12.7667, 77.3667))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .title("Hey you moved the z-axis on" + currentDateTime));
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

    }

    public void onAccuracyChanged(Sensor sensor,int accuracy){

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(12.9285212, 77.5834339)).title("Jayanagar"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.9285212, 77.5834339),14.9f));
    }
}
