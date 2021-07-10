package com.cupcakes.kandycupcakes.IT19210520;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cupcakes.kandycupcakes.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class updatepayment extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    List<paymentHelper> paymenthelperlist;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepayment);

        paymenthelperlist = new ArrayList<>();
         list = findViewById(R.id.list);


    }


    protected void onStart() {
        super.onStart();


        Intent i = getIntent();

        final String nic = i.getStringExtra("nic");
        final String day = i.getStringExtra("day");

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Payment");

        Query query = rootNode.getReference("Payment").orderByChild("nic").equalTo(nic);

        query.addValueEventListener(new ValueEventListener() {
            @Override


            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                paymenthelperlist.clear();
                for (DataSnapshot snaphot : dataSnapshot.getChildren()) {

                    paymentHelper pay = snaphot.getValue(paymentHelper.class);
                    paymenthelperlist.add(pay);



                }

                searchdetails listadapter = new searchdetails(updatepayment.this, paymenthelperlist);
                list.setAdapter(listadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final paymentHelper payment =paymenthelperlist.get(i);

                Showupdatedialog(payment.getMaxid(),payment.getNIC(),payment.getDate(),payment.getTotalamount(),payment.getPaiedState(),payment.getPaymenttype());
                return false;
            }
        });
    }


    public void Showupdatedialog(final String maxid,final String nic, final String date,final float totalamount,final String paiedState,final String paymenttype){

        AlertDialog.Builder dialogbuilder =new AlertDialog.Builder(this);
        LayoutInflater inflater =getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_payment,null);
        dialogbuilder.setView(dialogView);
        final EditText paidState = (EditText)dialogView.findViewById(R.id.txtupdatestate);

        final Button update = (Button)dialogView.findViewById(R.id.updatebtn);

        final Button delete  = (Button)dialogView.findViewById(R.id.deletbtn);
        TextView NIC = dialogView.findViewById(R.id.txtNic);
        NIC.setText(nic);
        TextView Date = dialogView.findViewById(R.id.txtdate);
        Date.setText(date);
        TextView tamount = dialogView.findViewById(R.id.txtamount);
        tamount.setText(""+totalamount);



        dialogbuilder.setTitle("            update patment state");
        final AlertDialog alertDialog=dialogbuilder.create();
        alertDialog.show();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String paystate = paidState.getText().toString();

                if(TextUtils.isEmpty(paystate)){
                    paidState.setError("payment State  Required");
                    return;
                }

                updatepayment(maxid, nic, date, totalamount,  paystate,  paymenttype);
                alertDialog.dismiss();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletepayment(maxid);
            }
        });

    }

    private boolean updatepayment(String maxid,String nic, String date, float totalamount, String paiedState, String paymenttype){
        reference=rootNode.getReference("Payment").child(maxid);
        paymentHelper pay = new paymentHelper(maxid,nic,date,totalamount,paiedState,paymenttype);
        reference.setValue(pay);
        Toast.makeText(this,"Payment updated",Toast.LENGTH_LONG).show();
        return true;

    }

    private void deletepayment(String maxid){

        reference=rootNode.getReference("Payment").child(maxid);
        reference.removeValue();
        Toast.makeText(this,"Payment deleted successfully",Toast.LENGTH_LONG).show();
    }
}