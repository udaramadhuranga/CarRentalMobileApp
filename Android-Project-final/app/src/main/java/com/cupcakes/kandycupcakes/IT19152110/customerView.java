package com.cupcakes.kandycupcakes.IT19152110;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cupcakes.kandycupcakes.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class customerView extends AppCompatActivity {

    EditText question;
    EditText answer;
    Button buttonAdd;

    DatabaseReference databaseFAQ;

    ListView faqList;

    List<FAQ> FAQList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerview);



      faqList = (ListView) findViewById(R.id.faqList);

      FAQList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
            super.onStart();

        databaseFAQ = FirebaseDatabase.getInstance().getReference("faq");

            databaseFAQ.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    FAQList.clear();

                    for(DataSnapshot faqSnapshop : dataSnapshot.getChildren()){
                        FAQ faqL = faqSnapshop.getValue(FAQ.class);

                        FAQList.add(faqL);
                    }

                    com.cupcakes.kandycupcakes.IT19152110.faqList adapter = new faqList(customerView.this, FAQList);
                    faqList.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }
}
