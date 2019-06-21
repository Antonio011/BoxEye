package it.uniparthenope.studenti.boxeye;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends AppCompatActivity {
    Sensor sensoreprossimita;
    TextView Rilevazione;
    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    SensorManager mySensorManager;
    static public String numeroditelefono;
    public static final String numerosalv = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Rilevazione =  findViewById(R.id.Rilevazione);
        mySensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);
        sensoreprossimita= mySensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        if (sensoreprossimita == null) {
            Rilevazione.setText("Non hai un sensore di prossimità!");
        } else {
            mySensorManager.registerListener(listenerprossimita,
                    sensoreprossimita,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        findViewById(R.id.informazioni).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Info.class));
            }
        });
        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), impostazioni.class));
            }
        });
        findViewById(R.id.sos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(numerosalv, MODE_PRIVATE);
                String restoredText = prefs.getString("Numero", null);
                if (restoredText==null)
                    Rilevazione.setText("Non hai impostato ancora un numero per l'SOS, clicca sull'ingranaggio in alto a destra.");
                else
                chiamataSos(restoredText);
            }
        });
    }

    private void chiamataSos(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phoneNumber, null)));
    }

    SensorEventListener listenerprossimita
            = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] < sensoreprossimita.getMaximumRange()) {

                    toneGen1.startTone(ToneGenerator.TONE_DTMF_6);

                    ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(55555,100));

                    Rilevazione.setText("Rilevato");
                } else {

                    toneGen1.stopTone();
                    ((Vibrator) getSystemService(VIBRATOR_SERVICE)).cancel();
                    Rilevazione.setText("Libero");
                }

            }
        }
    };

}

