package com.example.prototype;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataGatherActivity extends AppCompatActivity {
    EditText BloodInput, DoseInput;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    int a,b;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_gather);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BloodInput = (EditText) findViewById(R.id.Blood);
        DoseInput = (EditText) findViewById(R.id.Dose);
        Button button = (Button) findViewById(R.id.SubmitButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            a = Integer.valueOf(BloodInput.getText().toString());
            b = Integer.valueOf(DoseInput.getText().toString());

            executor.execute(() -> {
             DatabaseSend(a,b);
            });
            }
        });
    }
    public void DatabaseSend(int a, int b) {
        try {
            Connection con = DatabaseConnector.getConnection();

            PreparedStatement userStatement = con.prepareStatement("INSERT INTO data (targetblood, unit) VALUES (?, ?);");
            userStatement.setInt(1, a);
            userStatement.setInt(2, b);
            userStatement.executeUpdate();

        } catch (SQLException e) {
            runOnUiThread(() ->{
                Toast.makeText(DataGatherActivity.this,
                        "Unable to connect to database", Toast.LENGTH_LONG).show();
            });

            // Other SQL Exception
        }
    }
}
