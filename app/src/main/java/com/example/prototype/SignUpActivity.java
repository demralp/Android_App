package com.example.prototype;

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
import java.io.Console;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SignUpActivity extends AppCompatActivity {
    String user, pass, pass2, str;
    EditText UserInput, PassInput, Pass2Input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        UserInput = (EditText) findViewById(R.id.SignUpUsername);
        PassInput = (EditText) findViewById(R.id.SignUpPassword);
        Pass2Input = (EditText) findViewById(R.id.SignUpPassword2);

        Button button = (Button) findViewById(R.id.SignUpButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user = UserInput.getText().toString();
                pass = PassInput.getText().toString();
                pass2 = Pass2Input.getText().toString();
                if(pass.equals(pass2)){
                    executor.execute(() -> {
                        DatabaseSend(user, pass); // Run database code on background thread
                    });
                }
                else{
                    Toast.makeText(SignUpActivity.this,
                            "Passwords do not match", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void DatabaseSend(String user, String pass) {
        try {
            Connection con = DatabaseConnector.getConnection();

            PreparedStatement userStatement = con.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?);");
            userStatement.setString(1, user);
            userStatement.setString(2, pass);
            userStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            // Duplicate entry
            Toast.makeText(SignUpActivity.this,
                "Username is already taken", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(SignUpActivity.this,
                    "Unable to connect to database", Toast.LENGTH_LONG).show();
            // Other SQL Exception
        }
    }
}
