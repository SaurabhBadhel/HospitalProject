package com.example.patient_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Patient_homescreen extends AppCompatActivity{
    Button logout;
    CardView CliPrescription,bookAppoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_patient_homescreen );
        logout=findViewById( R.id.logout );
        CliPrescription=findViewById( R.id.CliPrescription );
        bookAppoint=findViewById( R.id.bookAppoint );
        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        } );

        CliPrescription.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( Patient_homescreen.this,PatientPrescription.class );
                startActivity( intent );
            }
        } );
        bookAppoint.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( Patient_homescreen.this,BookAppointment.class );
                startActivity( intent );
            }
        } );


    }
}
