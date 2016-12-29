package com.example.sridh.inquisitenerds;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sridh.inquisitenerds.database.NerdsContract;
import com.example.sridh.inquisitenerds.database.NerdsDatabase;

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

import static com.example.sridh.inquisitenerds.database.NerdsContract.NerdsEntry.TABLE_NAME;
import static org.altbeacon.beacon.service.BeaconService.TAG;

public class MainActivity extends AppCompatActivity implements BeaconConsumer, RangeNotifier {

    private BeaconManager mBeaconManager;
    private ArrayList<String> list;
    private  ArrayAdapter<String> adapter;
    private  ListView listView;
    private TextToSpeech t1;
    private Button play;
    private int i=0,j=0,k=0;
    private Button call;
    private SQLiteDatabase db;
    private NerdsDatabase dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.replay_button);
        call = (Button) findViewById(R.id.call_button);

        dbhelper = new NerdsDatabase(this);
        db = dbhelper.getWritableDatabase();

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
        j=0;
        for (Beacon beacon : beacons) {
      //      if (beacon.getServiceUuid() == 0xfeaa && beacon.getBeaconTypeCode() == 0x00) {
                // This is a Eddystone-UID frame
            Identifier namespaceId = beacon.getId1();
            final String namespace =namespaceId.toString();
            Log.d(TAG, "String I see a beacon transmitting namespace id: " + namespace );

            ContentValues values = new ContentValues();
            final Identifier instanceId = beacon.getId2();
            final String instance =instanceId.toString();
            if(beacons.size()>0) {
                if (namespace.compareTo("0x996a4b276250c5a82ba4") == 0 && instance.compareTo("0x000000000000") == 0) {
                    //mBeaconManager.bind(MainActivity.this);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // startActivity(new Intent(MainActivity.this,Main2Activity.class));
                            ((TextView) MainActivity.this.findViewById(R.id.text_view)).setText("You are at the first beacon . Move towards Left to encounter 2nd Beacon");}});

                    if(i==0) {
                        //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                        String speech = "You are at the first beacon! To replay press the left button and to call helpline press the right button";
                        //text_View.setText(speech);
                        t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
                        values.put(NerdsContract.NerdsEntry.COLUMN_BEACON,"Checked in at 1st beacon");
                        db.insert(NerdsContract.NerdsEntry.TABLE_NAME, null, values);
                        i=1;


                    }

                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:9899012345"));
                            startActivity(callIntent);
                        }
                    });

                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //String toSpeak = ed1.getText().toString();
                            //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                            String speech="You are at the first beacon";
                            //text_View.setText(speech);
                            t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    });
                }
   }



                if (namespace.compareTo("0x524cba7394a8fb191969") == 0 && instance.compareTo("0x000000000000") == 0) {

                    runOnUiThread(new Runnable() {
                        public void run() {
                            // startActivity(new Intent(MainActivity.this,Main2Activity.class));
                            ((TextView) MainActivity.this.findViewById(R.id.text_view)).setText("You are at the second beacon . Move towards Right to encounter 3rd Beacon");}});

                    if(j==0) {
                        //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                        String speech = "You are at the second beacon! To replay press the left button and to call helpline press the right button";
                        //text_View.setText(speech);
                        t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
                        values.put(NerdsContract.NerdsEntry.COLUMN_BEACON,"Checked in at 2nd beacon");
                        db.insert(NerdsContract.NerdsEntry.TABLE_NAME, null, values);

                        j=1;
                    }

                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:9899012345"));
                            startActivity(callIntent);
                        }
                    });

                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String speech="You are at the first beacon";
                            t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    });
               }

            if (namespace.compareTo("0x9cb6e329d950506df137") == 0 && instance.compareTo("0x000000000000") == 0) {

                runOnUiThread(new Runnable() {
                    public void run() {
                        // startActivity(new Intent(MainActivity.this,Main2Activity.class));
                        ((TextView) MainActivity.this.findViewById(R.id.text_view)).setText("You are at the third beacon . Move towards Right to encounter further Beacon");}});

                if(k==0) {
                    //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                    String speech = "You are at the third beacon! To replay press the left button and to call helpline press the right button";
                    //text_View.setText(speech);
                    t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
                    values.put(NerdsContract.NerdsEntry.COLUMN_BEACON,"Checked in at 3rd beacon");
                    db.insert(NerdsContract.NerdsEntry.TABLE_NAME, null, values);

                    k=1;
                }

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:9899012345"));
                        startActivity(callIntent);
                    }
                });

                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String speech="You are at the third beacon";
                        t1.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
                    }
                });
            }
        }
    }

   @Override
   public void onPause() {
        super.onPause();
        mBeaconManager.unbind(this);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    int item_id = item.getItemId();
        if(item_id==R.id.new_subject)
        {
            startActivity(new Intent(MainActivity.this,MenuActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}

