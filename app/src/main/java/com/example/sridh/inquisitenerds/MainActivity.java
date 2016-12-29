package com.example.sridh.inquisitenerds;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.altbeacon.beacon.service.BeaconService.TAG;

public class MainActivity extends AppCompatActivity implements BeaconConsumer, RangeNotifier {

    private BeaconManager mBeaconManager;
    private ArrayList<String> list;
    private  ArrayAdapter<String> adapter;
    private  ListView listView;
    private TextToSpeech t1;
    private Button play;
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.replay_button);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        // Detect the main Eddystone-UID frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        mBeaconManager.bind(this);
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
            if(beacons.size()>0) {
                if (namespace.compareTo("0x996a4b276250c5a82ba4") == 0 && instance.compareTo("0x000000000000") == 0) {
                    //mBeaconManager.bind(MainActivity.this);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // startActivity(new Intent(MainActivity.this,Main2Activity.class));
                            ((TextView) MainActivity.this.findViewById(R.id.text_view)).setText("The beacon detected has namespaceId = " +namespace
                            + "You are at the first beacon");}});

                    if(i==0) {
                        //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                        String speech = "if you press right you can play the directions again,if you press left you can call helpline ";
                        //text_View.setText(speech);
                        t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
                        i=1;
                    }
                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //String toSpeak = ed1.getText().toString();
                            //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                            String speech="if you press right you can play the directions again,if you press left you can call helpline ";
                            //text_View.setText(speech);
                            t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    });
                          //  list.add("You are tat the first beacon");
                          //  adapter.notifyDataSetChanged();
                          //  listView.setAdapter(adapter);
                      //      mBeaconManager.unbind(MainActivity.this);
//                        adapter.notifyDataSetChanged();



                        }



                    }



                if (namespace.compareTo("0x524cba7394a8fb191969") == 0 && instance.compareTo("0x000000000000") == 0) {
                    mBeaconManager.bind(MainActivity.this);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            // startActivity(new Intent(MainActivity.this,Main2Activity.class));
                            //((TextView) MainActivity.this.findViewById(R.id.text_view)).setText("The beacon detected has namespaceId = " +namespace
                            //       + "You are at the second beacon");}});

                            // list.add("You are tat the second beacon");
                            // adapter.notifyDataSetChanged();
                            // listView.setAdapter(adapter);
                            // mBeaconManager.unbind(MainActivity.this); }
                        }});}
                }

            }




    @Override
    public void onPause() {
        super.onPause();
        mBeaconManager.unbind(this);
    }
}