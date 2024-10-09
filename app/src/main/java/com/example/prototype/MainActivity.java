package com.example.prototype;

import static android.system.Os.connect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.*;


public class MainActivity extends AppCompatActivity {
    String  str;
    Button b1  ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.welcome_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       connect();
       b1 = findViewById(R.id.login_button);
        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchActivities();
                    }
                    }
        );

    }
    protected void connect(){
        try {
            Connection con = DatabaseConnector.getConnection();
            str = "Database Connected";
        } catch (SQLException e) {
            str = "Database Connection Failed";
        }
        runOnUiThread(() -> {
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
        });

    }
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, login.class);
        startActivity(switchActivityIntent);
    }
}
