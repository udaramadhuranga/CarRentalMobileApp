package com.cupcakes.kandycupcakes.IT19210698;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.cupcakes.kandycupcakes.IT19210520.credit;
import com.cupcakes.kandycupcakes.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class booking extends AppCompatActivity {

    EditText bdate,rdate,name,nic,mobile,vehicleid;
    Button btnNext;
    DatabaseReference reff;
    Bookings bookings;
    DatePickerDialog.OnDateSetListener setListener;
    long maxid=0;
    RadioButton cash,online;
    RadioGroup radioGroup;
    Integer selectedmethod;
    Double total;
    Date date1;
    Date date2;
    String key;
    

    private AwesomeValidation awesomeValidation;
    private DatePickerDialog.OnDateSetListener mDateSetListener1;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);



        name = (EditText) findViewById(R.id.name);
        nic = (EditText) findViewById(R.id.nic);
        mobile = (EditText) findViewById(R.id.mobile);
        bdate = (EditText) findViewById(R.id.bdate);
        rdate = (EditText) findViewById(R.id.rdate);
        btnNext = (Button) findViewById(R.id.btnNext);
        reff = FirebaseDatabase.getInstance().getReference().child("Bookings");
        bookings = new Bookings();
        cash = (RadioButton) findViewById(R.id.radio_one);
        online = (RadioButton) findViewById(R.id.radio_two);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        Intent newintent = getIntent();
        final String price =  newintent.getStringExtra("vehi_price");
        key = newintent.getStringExtra("key");

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.name,RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.mobile,"[0]{1}[0-9]{9}$", R.string.invalid_mobile);
        awesomeValidation.addValidation(this, R.id.nic,"[0-9]{9}[vV]$", R.string.invalid_nic);
        awesomeValidation.addValidation(this, R.id.bdate,RegexTemplate.NOT_EMPTY, R.string.invalid_bdate);
        awesomeValidation.addValidation(this, R.id.rdate,RegexTemplate.NOT_EMPTY, R.string.invalid_rdate);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        booking.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener1,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                bdate.setText(date);
            }
        };

        rdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        booking.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener2,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                rdate.setText(date);
            }
        };



        btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                 if(awesomeValidation.validate()) {
                    bookings.setName(name.getText().toString().trim());
                    bookings.setNic(nic.getText().toString().trim());
                    bookings.setMobile(mobile.getText().toString().trim());
                    bookings.setVehicleid(key);
                    bookings.setBookingDate(bdate.getText().toString().trim());
                    bookings.setReturnDate(rdate.getText().toString().trim());
                    reff.child(String.valueOf(maxid + 1)).setValue(bookings);


                    String CurrentDate = bdate.getText().toString();
                    String FinalDate = rdate.getText().toString();


                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        date1 = dates.parse(CurrentDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        date2 = dates.parse(FinalDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long difference = Math.abs(date1.getTime() - date2.getTime());
                    long differenceDates = difference / (24 * 60 * 60 * 1000);
                    String dayDifference = Long.toString(differenceDates);
                     Double fprice = Double.parseDouble(price);
                     Integer fdayDifference = Integer.parseInt(dayDifference);

                     final DatabaseReference upref = FirebaseDatabase.getInstance().getReference().child("uploads").child(key).child("available");
                     upref.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {
                             String availability = "Not Available";
                               upref.setValue(availability);
                         }

                         @Override
                         public void onCancelled(DatabaseError databaseError) {

                         }
                     });


                    if (selectedmethod == 1) {
                        Intent i1 = new Intent(getApplicationContext(), com.cupcakes.kandycupcakes.IT19210520.cash.class);
                        i1.putExtra("NIC", nic.getText().toString());
                        i1.putExtra("amount", String.valueOf(calculateTotal(fprice,fdayDifference)));
                        i1.putExtra("date", bdate.getText().toString());

                        startActivity(i1);
                    } else if (selectedmethod == 2) {
                        Intent i2 = new Intent(getApplicationContext(), credit.class);
                        i2.putExtra("NIC", nic.getText().toString());
                        i2.putExtra("amount", String.valueOf(calculateTotal(fprice,fdayDifference)));
                        i2.putExtra("date", bdate.getText().toString());

                        startActivity(i2);
                    }

                }else{
                     Toast.makeText(getApplicationContext(), "Validation Faild!", Toast.LENGTH_SHORT).show();
                 }
            }
        });



    }


    public static Double calculateTotal(Double fprice, Integer fdayDifference){
        return fprice * fdayDifference;
    }


    public void checkButton1(View v){
        selectedmethod = 1;

    }
    public void checkButton2(View v){
        selectedmethod = 2;

    }



    }


