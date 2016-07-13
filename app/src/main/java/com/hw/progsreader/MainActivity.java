package com.hw.progsreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "MainActivity";
    private Button chooseFileBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseFileBt = (Button) findViewById(R.id.choose_file_bt);

        chooseFileBt.setOnClickListener(this);
    }

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this, FileChooser.class);
        startActivity(intent);
    }

}
