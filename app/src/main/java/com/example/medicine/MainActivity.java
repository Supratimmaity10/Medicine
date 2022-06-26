package com.example.medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText medicineName, medicineDate,showdata;
    TextView textViewMed;
    Button insertButton, fetchButton;
    Spinner dayTimeSpinner;
    Switch swtch;
    DbConnection dbConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewMed = findViewById(R.id.textView4);
        medicineName = findViewById(R.id.medname);
        medicineDate = findViewById(R.id.meddate);
        insertButton = findViewById(R.id.insert);
        fetchButton = findViewById(R.id.fetch);
        fetchButton.setVisibility(View.INVISIBLE);
        dayTimeSpinner = findViewById(R.id.medtime);
        swtch = findViewById(R.id.switch1);
        showdata=findViewById(R.id.editTextTextMultiLine);
        showdata.setVisibility(View.INVISIBLE);
        dbConnection=new DbConnection(this);
        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    fetchButton.setVisibility(View.INVISIBLE);
                    insertButton.setVisibility(View.VISIBLE);
                    medicineName.setVisibility(View.VISIBLE);
                    textViewMed.setVisibility(View.VISIBLE);
                    showdata.setVisibility(View.INVISIBLE);
                } else {
                    //Enable some of the buttons and components (Views) as Visible
                    medicineName.setVisibility(View.INVISIBLE);
                    insertButton.setVisibility(View.INVISIBLE);
                    textViewMed.setVisibility(View.INVISIBLE);
                    fetchButton.setVisibility(View.VISIBLE);
                    showdata.setVisibility(View.VISIBLE);

                }
            }
        });
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = medicineName.getText().toString();
                String date = medicineDate.getText().toString();
                String time = dayTimeSpinner.getSelectedItem().toString();

                boolean insert = dbConnection.insertValues(name,date,time);
                if(insert==true){
                    Toast.makeText(getApplicationContext(),"Data inserted Successfully", Toast.LENGTH_SHORT).show();
                    medicineName.setText(null);
                    medicineDate.setText(null);//This allows us to insert new data
                }
                else
                    Toast.makeText(getApplicationContext(), "Data insertion unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });
       fetchButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               String date = medicineDate.getText().toString();
                                               String time = dayTimeSpinner.getSelectedItem().toString();
                                              StringBuilder med=new StringBuilder();
                                               Cursor cu = dbConnection.RetrieveData(date, time);
                                                                                              cu.moveToFirst();
                                               do{
                                                   med.append(String.valueOf(cu.getString(cu.getColumnIndex("medicineName"))));
                                                  med.append("\n");
                                               }while (cu.moveToNext());
                                              showdata.setText(med);
                                           }
                                       }
        );
    }
}