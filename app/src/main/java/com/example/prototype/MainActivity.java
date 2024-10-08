package com.example.prototype;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    DatabaseConnector connectionClass;
    Connection con;
    ResultSet rs;
    String name, str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        connectionClass = new DatabaseConnector();
        connect();
    }

    public void click(View view) {
    }
    public void connect() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
      //  executorService.execute(() -> {
            try {

                try {
                    con = connectionClass.CONN();
                } catch (Exception e) {
                    Log.e("Connection Error", "Error occurred while calling CONN()", e);
                }

                if (con == null) {
                    str = "Error connecting to database";
                } else {
                    str = "Connected to database";
                }
            } catch (Exception e) {
                Log.e("Connection Error", "Error occurred while connecting to database", e);
                str = "Connection failed";
            }

            runOnUiThread(() -> {
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            });
       // });







    }
}