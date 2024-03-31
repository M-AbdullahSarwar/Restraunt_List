package com.example.smd_assignment_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Add_Restraunt extends AppCompatActivity {

    EditText etName, etLocation, etPhoneNo, etDesc, etRatings;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_restraunt);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, loc, phone, desc, rate;

                name = etName.getText().toString().trim();
                loc = etLocation.getText().toString().trim();
                phone = etPhoneNo.getText().toString().trim();
                desc = etDesc.getText().toString().trim();
                rate = etRatings.getText().toString().trim();

                if(name.isEmpty() || loc.isEmpty() || phone.isEmpty() || desc.isEmpty() || rate.isEmpty()) {

                    Toast.makeText(Add_Restraunt.this, "Input can not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                double rating = Double.parseDouble(rate);
                Toast.makeText(Add_Restraunt.this, "Added!", Toast.LENGTH_SHORT).show();

                Restraunt newRestraunt = new Restraunt(name, loc, phone, desc, rating);
                MainActivity.restrauntArrayList.add(newRestraunt);
                Log.d("Add_Restaurant", "New restaurant added: " + newRestraunt.toString());

                MainActivity.rvAdapter.notifyDataSetChanged();

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                clear();
                finish();
            }
        });
    }

    private void init(){

        etName = findViewById(R.id.etName);
        etLocation = findViewById(R.id.etLocation);
        etPhoneNo = findViewById(R.id.etPhoneNo);
        etDesc = findViewById(R.id.etDiscription);
        etRatings = findViewById(R.id.etRatings);
        btnAdd = findViewById(R.id.btnAdd);

    }
    private void clear(){
        etName.setText("");
        etLocation.setText("");
        etPhoneNo.setText("");
        etDesc.setText("");
    }
}