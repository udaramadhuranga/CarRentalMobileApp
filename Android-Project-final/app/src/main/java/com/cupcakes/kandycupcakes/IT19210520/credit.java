package com.cupcakes.kandycupcakes.IT19210520;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.regex.Pattern;

public class credit extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    float amo;

    private static final Pattern credi_number = Pattern.compile("^[0-9]{6}");
    private static final Pattern cvv_number = Pattern.compile("^[0-9]{4}");

    long maxid =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        Intent i = getIntent();
        final String Nic =i.getStringExtra("NIC");
        final String date =i.getStringExtra("date");
        final String amout = i.getStringExtra("amount");

        float am = Float.parseFloat(amout);
         amo =cardtax(am);


        TextView nic =  findViewById(R.id.txtNIC);
        TextView day =findViewById(R.id.txtdate);
        TextView tot = findViewById(R.id.txtTotalamount);


        if(am >=15000){
            amo = amo - discount(am);

        }

        nic.setText(Nic);
        day.setText(date);
        tot.setText(""+amo);





        rootNode= FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Payment");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxid =(long)dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        Button pay = findViewById(R.id.btnpay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final   EditText credinum = findViewById(R.id.editcredinum);
                final String cre =  credinum.getText().toString();

                final EditText Cvv = findViewById(R.id.Evcc);
                final String cvv =  Cvv.getText().toString();

                if(TextUtils.isEmpty(cre)){
                    credinum.setError("Credit card number is  Required");
                    return;
                } else if (!credi_number.matcher(cre).matches()){
                    credinum.setError("credit card number should be 6 digits number ");
                    return;
                }

                if(TextUtils.isEmpty(cvv)){
                   Cvv.setError("CVV number is Required");
                    return;
                } else if (!cvv_number.matcher(cvv).matches()){
                    Cvv.setError("CVV number should be 4 digits number ");
                    return;
                }






                paymentHelper helper = new paymentHelper(String.valueOf(maxid+1),Nic,date,amo,"Paid","Card");

                reference.child(String.valueOf(maxid+1)).setValue(helper);

                Toast.makeText(credit.this,"Payment added succesfully",Toast.LENGTH_LONG).show();

                Intent i =new Intent(credit.this, MainActivity.class);
                startActivity(i);

            }
        });


    }
    public float cardtax(float value){

        return(value+((value *5)/100));
    }

    public float discount(float value){
        return(((value*3)/100));
    }
}