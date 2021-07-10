package com.cupcakes.kandycupcakes.IT19152110;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class kas extends AppCompatActivity {

    EditText question;
    EditText answer;
    Button buttonAdd;

    DatabaseReference databaseFAQ;

    ListView faqList;

    List<FAQ> FAQList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kas);


        databaseFAQ = FirebaseDatabase.getInstance().getReference("faq");

        question = (EditText) findViewById(R.id.question);
        answer = (EditText) findViewById(R.id.answer);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        faqList = (ListView) findViewById(R.id.faqList);

        FAQList = new ArrayList<>();

        FAQList = new ArrayList<>();

        buttonAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                addFaq();
            }
        });

        faqList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                FAQ Faq = FAQList.get(i);

                showUpdate(Faq.getFaqId(), Faq.getQue(), Faq.getAns());

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseFAQ.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                FAQList.clear();

                for(DataSnapshot faqSnapshop : dataSnapshot.getChildren()){
                    FAQ faqL = faqSnapshop.getValue(FAQ.class);

                    FAQList.add(faqL);
                }

                com.cupcakes.kandycupcakes.IT19152110.faqList adapter = new faqList(kas.this, FAQList);
                faqList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdate(final String Id , final String question , String answer) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View view = inflater.inflate(R.layout.update,null);

        builder.setView(view);

        final EditText questionU = (EditText) view.findViewById(R.id.questionU);
        TextView que = view.findViewById(R.id.questionU);
        que.setText(question);
        final EditText answerU = (EditText) view.findViewById(R.id.answerU);
        TextView ans = view.findViewById(R.id.answerU);
        ans.setText(answer);
        final Button button = (Button) view.findViewById(R.id.update);
        final Button buttonDelete = (Button) view.findViewById(R.id.delete);

        builder.setTitle(""+question);

        final AlertDialog alert = builder.create();
        alert.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = questionU.getText().toString().trim();
                String answer = answerU.getText().toString().trim();

                if(TextUtils.isEmpty(question)) {
                    questionU.setError("Required");
                    answerU.setError("Required");
                    return;
                }

                if(TextUtils.isEmpty(answer)) {
                    answerU.setError("Required");
                    return;
                }
                updateFaq(Id, question, answer);

                alert.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFaq(Id);

                alert.dismiss();
            }
        });

    }

    private void deleteFaq(String Id) {
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("faq").child(Id);

        dbref.removeValue();

        Toast.makeText(this, "FAQ is deleted", Toast.LENGTH_LONG).show();
    }

    private void updateFaq(String Id , String question , String answer) {

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("faq").child(Id);

        FAQ faq = new FAQ(Id, question, answer);

        dbReference.setValue(faq);

        Toast.makeText(this, "FAQ updated Successfully", Toast.LENGTH_LONG).show();
    }

    public void addFaq() {

        String Question = question.getText().toString().trim();
        String Answer = answer.getText().toString().trim();

        //if(!TextUtils.isEmpty(Question)) {


            if(TextUtils.isEmpty(Question)) {
                question.setError("Required");
                return;
            }
            if(TextUtils.isEmpty(Answer)) {
                answer.setError("Required");
                return;
            }

            String Id = databaseFAQ.push().getKey();

            FAQ faq = new FAQ(Id , Question , Answer);

            databaseFAQ.child(Id).setValue(faq);
            Toast.makeText(this , "FAQ added" , Toast.LENGTH_LONG).show();
       // }
       // else
           // Toast.makeText(this, "You should enter a answer", Toast.LENGTH_LONG).show();
    }
}