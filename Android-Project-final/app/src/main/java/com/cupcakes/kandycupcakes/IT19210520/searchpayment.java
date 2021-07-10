package com.cupcakes.kandycupcakes.IT19210520;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cupcakes.kandycupcakes.R;

import java.util.regex.Pattern;

public class searchpayment extends AppCompatActivity {

    private static final Pattern bic_numbercheck = Pattern.compile("^[0-9]{9}[Vv]");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpayment);


        Button btfind = findViewById(R.id.find);

        btfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText nic = findViewById(R.id.txtNIC);


                String n = nic.getText().toString();

                if(TextUtils.isEmpty(n)){
                    nic.setError("Searching NIC is required");
                    return;
                }

             else if (!bic_numbercheck.matcher(n).matches()){
                nic.setError("Searching NIC should be like 971267579V ");
                return;
            }



                Intent i = new Intent(searchpayment.this, updatepayment.class);
                i.putExtra("nic",n);

                startActivity(i);
            }
        });
    }
}