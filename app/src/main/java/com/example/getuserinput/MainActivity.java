package com.example.getuserinput;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper database;
    Button addData, viewData, updateData, deleteData;
    EditText eName, eEmail, eDegree, eID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DatabaseHelper(this);
        eID = (EditText) findViewById(R.id.inputID);
        eName = (EditText) findViewById(R.id.inputName);
        eEmail = (EditText) findViewById(R.id.inputEmail);
        eDegree = (EditText) findViewById(R.id.inputDegree);
        addData = (Button) findViewById(R.id.addBtn);
        viewData = (Button) findViewById(R.id.viewBtn);
        updateData = (Button) findViewById(R.id.updateBtn);
        deleteData = (Button) findViewById(R.id.deleteBtn);
        addData();
        viewData();
        updateData();
        deleteData();
    }

    public void addData() {
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = eName.getText().toString();
                String email = eEmail.getText().toString();
                String degree = eDegree.getText().toString();

                boolean insertData = database.addData(name, email, degree);

                if (insertData) {
                    Toast.makeText(MainActivity.this, "Successfully inserted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to insert", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewData() {
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor data = database.showData();

                if (data.getCount() == 0) {
                    display("Error", "No data to display");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (data.moveToNext()) {
                    buffer.append("ID " + data.getString(0) + "\n");
                    buffer.append("Name: " + data.getString(1) + "\n");
                    buffer.append("Email: " + data.getString(2) + "\n");
                    buffer.append("Degree: " + data.getString(3) + "\n");
                    display("All stored data:",buffer.toString());
                }
            }
        });
    }

    public void display(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateData() {
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = eID.getText().toString().length();
                if (temp > 0) {
                    boolean update = database.updateData(eID.getText().toString(), eName.getText().toString(), eEmail.getText().toString(), eDegree.getText().toString());
                    if (update) {
                        Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to update", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter ID", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void deleteData() {
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = eID.getText().toString().length();
                if (temp > 0) {
                    Integer delete = database.deleteData(eID.getText().toString());
                    if (delete > 0) {
                        Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to delete", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter ID", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}