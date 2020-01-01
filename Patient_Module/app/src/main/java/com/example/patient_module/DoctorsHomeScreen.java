package com.example.patient_module;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.patient_module.Doctor.HistoryAdapter;
import com.example.patient_module.Doctor.ListAdapter;
import com.example.patient_module.Module.History;
import com.example.patient_module.Module.List;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorsHomeScreen extends AppCompatActivity implements View.OnClickListener{

    EditText date,medName1,medName2,medName3,age,height,weight,quantity1,quantity2,quantity3,remark;
    Button sendPrescription;
    FirebaseFirestore Store;
    ArrayList<String>selection=new ArrayList<String>(  );
    public static final String TAG = "TAG";



    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference reff=db.collection( "Users" );
    private CollectionReference reference=db.collection( "Prescription" );
    private ListAdapter adapter;
    private HistoryAdapter adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_doctors_home_screen );
        date=findViewById( R.id.date );
        medName1=findViewById( R.id.medName1 );
        medName2=findViewById( R.id.medName2 );
        medName3=findViewById( R.id.medName3 );
        quantity1=findViewById( R.id.quantity1 );
        quantity2=findViewById( R.id.quantity2 );
        quantity3=findViewById( R.id.quantity3 );
        remark=findViewById( R.id.remark );
        age=findViewById( R.id.age );
        height=findViewById( R.id.hight );
        weight=findViewById( R.id.weight );
        sendPrescription=findViewById( R.id.sendPrescription );
        Store=FirebaseFirestore.getInstance();


        setUpRecyclerViewList();

        setUpRecyclerViewHistory();



        sendPrescription.setOnClickListener(this);

    }

    private void setUpRecyclerViewHistory() {
        Query query1=reference.orderBy( "date", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<History> history=new FirestoreRecyclerOptions.Builder<History>().setQuery( query1,History.class ).build();
        adapter1=new HistoryAdapter( history );
        RecyclerView recyclerView1=findViewById( R.id.history );
        recyclerView1.setHasFixedSize( true );
        recyclerView1.setLayoutManager( new LinearLayoutManager( this) );
        recyclerView1.setAdapter( adapter1);

    }

    private void setUpRecyclerViewList(){
        Query query=reff.orderBy( "dob", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<List> options=new FirestoreRecyclerOptions.Builder<List>().setQuery( query,List.class ).build();
        adapter=new ListAdapter( options );
        RecyclerView recyclerView=findViewById( R.id.list );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this));
        recyclerView.setAdapter( adapter);
    }

    private void setPrescription(){
        final String Medicine1 = medName1.getText().toString().trim();
        final String Medicine2 = medName2.getText().toString().trim();
        final String Medicine3 = medName3.getText().toString().trim();
        final String Quantity1 = quantity1.getText().toString().trim();
        final String Quantity2 = quantity2.getText().toString().trim();
        final String Quantity3 = quantity3.getText().toString().trim();
        final String Date = date.getText().toString().trim();
        final String Age = age.getText().toString().trim();
        final String Height = height.getText().toString().trim();
        final String Weight = weight.getText().toString().trim();
        final String Remark = remark.getText().toString().trim();


        if (Date.isEmpty()) {
            date.setError("Date required");
            date.requestFocus();
            return;
        }
        if (Medicine1.isEmpty()) {
            medName1.setError("Medicine required");
            medName1.requestFocus();
            return;
        }
        if (Medicine2.isEmpty()) {
            medName2.setError("Medicine required");
            medName2.requestFocus();
            return;
        }
        if (Medicine3.isEmpty()) {
            medName3.setError("Medicine required");
            medName3.requestFocus();
            return;
        }
        if (Quantity1.isEmpty()) {
            quantity1.setError("Quantity required");
            quantity1.requestFocus();
            return;
        }

        if (Quantity2.isEmpty()) {
            quantity2.setError("Quantity required");
            quantity2.requestFocus();
            return;

        } if (Quantity3.isEmpty()) {
            quantity3.setError("Quantity required");
            quantity3.requestFocus();
            return;
        }

        if (Age.isEmpty()) {
            age.setError("Age required");
            age.requestFocus();
            return;
        }
        if (Height.isEmpty()) {
            height.setError("Height required");
            height.requestFocus();
            return;
        }

        if (Weight.isEmpty()) {
            weight.setError("Weight required");
            weight.requestFocus();
            return;
        }
        if (Remark.isEmpty()) {
            remark.setError("Remark required");
            remark.requestFocus();
            return;
        }
        String finalSelection="";
        for (String Selection : selection) {
            finalSelection = finalSelection + Selection + "/";
        }



        DocumentReference documentReference = Store.collection("Prescription").document();
        Map<String,Object> prescription = new HashMap<>();
        prescription.put("Medicine-1",Medicine1);
        prescription.put("Medicine-2",Medicine2);
        prescription.put("Medicine-3",Medicine3);
        prescription.put("Quantity-1",Quantity1);
        prescription.put("Quantity-2",Quantity2);
        prescription.put("Quantity-3",Quantity3);
        prescription.put("Date",Date);
        prescription.put("Age",Age);
        prescription.put("Height",Height);
        prescription.put("Weight",Weight);
        prescription.put("Remark",Remark);
        prescription.put("Medicine Time",finalSelection);
        documentReference.set(prescription).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Prescription is send");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });
        startActivity(new Intent(getApplicationContext(),DoctorsHomeScreen.class));

}



    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter1.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter1.stopListening();
    }




    public void selectItem(View view) {
        boolean checked=((CheckBox)view).isChecked();
        switch (view.getId())
        {
            case R.id.checkMor:
                if (checked){
                selection.add("Morning");}
                else{
                    selection.remove( "Morning" );
                }
                break;

            case R.id.checkAft:
                if (checked){
                    selection.add("Afternoon");}
                else{
                    selection.remove( "Afternoon" );
                }
                break;

            case R.id.checkEve:
                if (checked){
                    selection.add("Evening");}
                else{
                    selection.remove( "Evening" );
                }
                break;

            case R.id.checkNight:
                if (checked){
                    selection.add("Night");}
                else{
                    selection.remove( "Night" );
                }
                break;

            case R.id.checkStomatch:
                if (checked){
                    selection.add("Medicine before Food");}
                else{
                    selection.remove( "Medicine before Food" );
                }
                break;

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendPrescription:
                setPrescription();
                break;
        }
    }
}
