package com.cupcakes.kandycupcakes.IT19210520;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cupcakes.kandycupcakes.MainActivity;
import com.cupcakes.kandycupcakes.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cash extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    float amo;

    long maxid =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);

        Intent i = getIntent();
        final String Nic =i.getStringExtra("NIC");
        final String date =i.getStringExtra("date");
        final String amout = i.getStringExtra("amount");


        TextView nic =  findViewById(R.id.txtnic);
        TextView day =findViewById(R.id.txtdate);
        TextView tot = findViewById(R.id.txtamount);
        amo =Float.parseFloat(amout);

      nic.setText(""+Nic);

        if(amo >=15000){
            amo = amo - discount(amo);

        }

      day.setText(""+date);
      tot.setText(""+amo);








        rootNode= FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Payment");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxid =(long) dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button confirm = findViewById(R.id.btnconfirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                paymentHelper helper = new paymentHelper(String.valueOf(maxid+1),Nic,date,amo,"Not Paid","Cash");

                reference.child(String.valueOf(maxid+1)).setValue(helper);

                Toast.makeText(cash.this,"Payment added succesfully",Toast.LENGTH_LONG).show();

                Intent i =new Intent(cash.this, MainActivity.class);
                startActivity(i);
            }
        });




    }

    private float discount(float value){
        return(((value*3)/100));
    }
}