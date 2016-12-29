package com.example.sridh.inquisitenerds;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

import static org.altbeacon.beacon.service.BeaconService.TAG;

public class MainActivity extends AppCompatActivity implements BeaconConsumer, RangeNotifier {

    private BeaconManager mBeaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        // Detect the main Eddystone-UID frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        mBeaconManager.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        }


    public void onBeaconServiceConnect() {
        Region region = new Region("all-beacons-region", null, null, null);
        try {
            mBeaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mBeaconManager.addRangeNotifier(this);
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        final Collection<Beacon> beacons1=beacons;

        for (Beacon beacon : beacons) {
      //      if (beacon.getServiceUuid() == 0xfeaa && beacon.getBeaconTypeCode() == 0x00) {
                // This is a Eddystone-UID frame
            Identifier namespaceId = beacon.getId1();
            final String namespace =namespaceId.toString();
            Log.d(TAG, "String I see a beacon transmitting namespace id: " + namespace );


            final Identifier instanceId = beacon.getId2();
            final String instance =instanceId.toString();
            if(beacons.size()>0){
            if(namespace.compareTo("0x996a4b276250c5a82ba4")==0 && instance.compareTo("0x000000000000")==0) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        // startActivity(new Intent(MainActivity.this,Main2Activity.class));
                        ((TextView) MainActivity.this.findViewById(R.id.text_view)).setText("The beacon detected has namespaceId = " +namespace
                        + "You are at the first beacon");}});}
                }


            if(namespace.compareTo("0x524cba7394a8fb191969")==0 && instance.compareTo("0x000000000000")==0) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        // startActivity(new Intent(MainActivity.this,Main2Activity.class));
                        ((TextView) MainActivity.this.findViewById(R.id.text_view)).setText("The beacon detected has namespaceId = " +namespace
                               + "You are at the second beacon");}});}
        }


    }


    @Override
    public void onPause() {
        super.onPause();
        mBeaconManager.unbind(this);
    }
}