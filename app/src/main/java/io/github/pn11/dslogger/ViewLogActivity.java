package io.github.pn11.dslogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewlog);

        //  https://developer.android.com/training/basics/firstapp/starting-activity.html
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_viewlog);
        layout.addView(textView);

    }

    public void share(View view){
        final String message = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE);

        ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(this);

        builder.setChooserTitle("Share to...");
        builder.setSubject("DSLogger content");
        builder.setText(message);
        builder.setType("text/plain");
        builder.startChooser();

    }
}
