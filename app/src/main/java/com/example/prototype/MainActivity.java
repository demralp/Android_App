package com.example.prototype;

import static android.system.Os.connect;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.*;


public class MainActivity extends AppCompatActivity {
    String  str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       connect();
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

}
