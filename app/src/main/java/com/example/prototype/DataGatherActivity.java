package com.example.prototype;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataGatherActivity extends AppCompatActivity {
    // Declaring input fields and the executor service
    EditText BloodInput, DoseInput;
    ExecutorService executor = Executors.newSingleThreadExecutor(); // Single-threaded executor to handle background tasks
    int a, b;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Set the layout for this activity
        setContentView(R.layout.activity_data_gather);

        // Adjust padding to accommodate system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize EditText fields for user input
        BloodInput = (EditText) findViewById(R.id.Blood);
        DoseInput = (EditText) findViewById(R.id.Dose);

        // Initialize Submit button
        Button button = (Button) findViewById(R.id.SubmitButton);

        // Set OnClickListener for the Submit button
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Convert input to integers
                a = Integer.valueOf(BloodInput.getText().toString());
                b = Integer.valueOf(DoseInput.getText().toString());

                // Use executor service to run database operation on a background thread
                executor.execute(() -> {
                    // Send data to the database
                    DatabaseSend(a, b);

                    // Show Toast on the main UI thread after database operation
                    runOnUiThread(() -> {
                        Toast.makeText(DataGatherActivity.this,
                                "You have created your account", Toast.LENGTH_LONG).show();
                    });
                });

                // Navigate to MainActivity after submitting data
                Intent i = new Intent(DataGatherActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    // Method to send data to the database
    public void DatabaseSend(int blood, int unit) {
        try {
            // Establish a database connection
            Connection con = DatabaseConnector.getConnection();

            // Prepare an SQL statement to insert the blood and unit values
            PreparedStatement userStatement = con.prepareStatement("SELECT targetblood, unit) FROM data ( VALUES (?, ?);");

            // Set the values for the prepared statement
            userStatement.setInt(1, blood);
            userStatement.setInt(2, unit);

            // Execute the SQL update
            userStatement.executeUpdate();

        } catch (SQLException e) {
            // If an SQL exception occurs, show a Toast on the UI thread
            runOnUiThread(() -> {
                Toast.makeText(DataGatherActivity.this,
                        "Unable to connect to database", Toast.LENGTH_LONG).show();
            });

            // Handle other SQL exceptions if needed
        }
    }
}
