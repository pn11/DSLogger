package io.github.pn11.dslogger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class lsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ls);
    }


    public File[] listDir(View view){ // using http://stackoverflow.com/a/8752200
        //String pathToScan = "../../../..";
        String pathToScan = "./";
        String fileThatYouWantToFilter;
        String list = "";
        //File folderToScan = new File(pathToScan); // import -> import java.io.File;
        File folderToScan = new File(getExternalFilesDir(null), pathToScan);
//        File folderToScan = new File(getFilesDir(), pathToScan);
        File[] listOfFiles = folderToScan.listFiles();
        Log.d("test", folderToScan.getAbsolutePath());
        TextView tv = (TextView)findViewById(R.id.textView);
        String scanning = "Scanning...";
        tv.setText(scanning);

        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile()) {
                fileThatYouWantToFilter = listOfFiles[i].getName();
                list += fileThatYouWantToFilter + "\n";
            }
        }

        tv.setText(list);
        return listOfFiles;
    }
}
