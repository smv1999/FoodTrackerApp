package com.example.exam.foodtrack;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GPS extends AppCompatActivity {
    Button b1;
    LocationListener ll;
    LocationManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);



        String per[]={Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
        if(ContextCompat.checkSelfPermission(getApplicationContext(),per[0])!= PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(),per[1])!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(GPS.this,per,101);
        }
        lm=(LocationManager) getSystemService(LOCATION_SERVICE);

        ll= new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i= new Intent(GPS.this,MapsActivity.class);
                i.putExtra("latitude",location.getLatitude());
                i.putExtra("longitude",location.getLongitude());
                startActivity(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                Toast.makeText(getApplicationContext(),"GPS Enabled",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(getApplicationContext(),"Enable GPS",Toast.LENGTH_LONG).show();
                Intent j= new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(j);
            }
        };

                try{ 
                    Toast.makeText(GPS.this,"Locating...",Toast.LENGTH_LONG).show();
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,20000,100,ll);
                }
                catch(SecurityException e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }


    }
    @Override
    public void onBackPressed() {


    }
}
