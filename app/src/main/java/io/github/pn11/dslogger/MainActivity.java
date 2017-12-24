package io.github.pn11.dslogger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private LocationManager mLocationManager;
    public final static String EXTRA_MESSAGE = "io.github.pn11.dslogger.MESSAGE";
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        boolean gpsFlg = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        Log.d("GPS Enabled", gpsFlg?"OK":"NG");
    }



    public void saveLog(View view) { // from https://developer.android.com/training/basics/data-storage/files.html?hl=ja
        Context context = getApplicationContext();
        String currentTime = Util.getCurrentDate();
        String sdPath = new File(getExternalFilesDir(null), ".").getAbsolutePath();
//        System.out.println(sdPath);

        String filename = sdPath + "/log" + currentTime + ".txt";
        Resources res = getResources();
        String log_content = getContentFromPreference();
        FileOutputStream outputStream;
        //System.out.println(filename);

        if (isExternalStorageWritable()) {
            try {
                //outputStream = openFileOutput(filename, Context.MODE_PRIVATE)
                File file = new File(filename);
                outputStream = new FileOutputStream(file, true);
                outputStream.write(log_content.getBytes());
                outputStream.close();

                // show message https://developer.android.com/guide/topics/ui/notifiers/toasts.html

                CharSequence text = filename + " saved.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(), "file IO Error", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "SD card cannot be writable.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // from https://developer.android.com/training/basics/firstapp/starting-activity.html

    /**
     * Called when the user clicks the Send button
     */
    public void addToLog(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        //http://www.javadrive.jp/android/xml_layout/index5.html
        String log_content = getContentFromPreference();
        log_content += message;
        log_content += "\n";
        writeToPreference(log_content);
        editText.setText("");
    }

    public void viewLog(View view) {
        Intent intent = new Intent(this, ViewLogActivity.class);
        String log_content = getContentFromPreference();
        intent.putExtra(EXTRA_MESSAGE, log_content);
        startActivity(intent);
    }


    public void lsDir(View view) {
        Intent intent = new Intent(this, LsActivity.class);
        startActivity(intent);
    }


    //https://developer.android.com/training/basics/data-storage/files.html?hl=ja
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void writeToPreference(String log_content) {
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.pref_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("log_content", log_content);
        editor.commit();
    }

    public String getContentFromPreference() {
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.pref_file), Context.MODE_PRIVATE);
        String get_str = sharedPref.getString("log_content", "");
        System.out.println(get_str);
        return get_str;
    }

    public void resetPreference(View view) {
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.pref_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("log_content", "");
        editor.commit();
    }

    public void enterTimeStamp(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        editText.setText(Util.getCurrentDateHuman() + " ");
        String str = editText.getText().toString();
        editText.setSelection(str.length());
    }


/*
    public void enterLocationStamp(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        editText.setText(getGPSLocation(view) + " ");
        String str = editText.getText().toString();
        editText.setSelection(str.length());
    }


    public String getGPSLocation(View view) {
        if (locationManager == null){
            Log.d("GPS", "lm is null");
            return "NOGPS";
        }
        System.out.println("test1");
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
          System.out.println(LocationProvider.AVAILABLE);
          System.out.println(LocationProvider.OUT_OF_SERVICE);
          String provider0 = locationManager.getAllProviders().get(0);
          System.out.println(provider0);
          return provider0;
        //System.out.println("test2");
//        double lat = location.getLatitude();
        //System.out.println("test3");
//        double longt = location.getLongitude();
        //return String.valueOf(lat) + " " + String.valueOf(longt) + " ";
        //return "test";
    }
*/

// See how to determine which provider is precise from
// https://developer.android.com/guide/topics/location/strategies.html


}