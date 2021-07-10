package com.cupcakes.kandycupcakes.IT19207100;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.cupcakes.kandycupcakes.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class updatevehical extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView updimage;
    EditText uptxt;
    EditText pass,daypprice,bag;
    Spinner trans,upavail;
    Button update,chs;
    String name;
    int ppasen,bagn;
    Double ppricee;
    String pspinner,pavailable;

    String myimage,myimage2,myimage3;
    private Context mContext;
    String key;
    Upload up;
    DatabaseReference dref;
    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatevehical);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        updimage = findViewById(R.id.updateimage);
        uptxt = findViewById(R.id.updatetext);
        update = findViewById(R.id.btnupdate);
        chs = findViewById(R.id.chooseimage);
        pass = findViewById(R.id.passengers1);
        bag = findViewById(R.id.bagss);
        daypprice = findViewById(R.id.dayprice1);
        trans=findViewById(R.id.spinner1);
        upavail=findViewById(R.id.spinner3);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.updateimage,
                RegexTemplate.NOT_EMPTY, R.string.fill);


        awesomeValidation.addValidation(this, R.id.updatetext,
                RegexTemplate.NOT_EMPTY, R.string.fill);

        awesomeValidation.addValidation(this, R.id.updatetext,"[a-zA-Z ]{1,20}", R.string.invalid);

        awesomeValidation.addValidation(this, R.id.passengers1,
                RegexTemplate.NOT_EMPTY, R.string.fill);

        awesomeValidation.addValidation(this, R.id.bagss,
                RegexTemplate.NOT_EMPTY, R.string.fill);

        awesomeValidation.addValidation(this, R.id.dayprice1,
                RegexTemplate.NOT_EMPTY, R.string.fill);


        up = new Upload();

        getData();
        setData();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()) {
                uploadFile();
                }
                else{
                    Toast.makeText(updatevehical.this, "Please Fill The All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        });


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            updimage.setImageURI(mImageUri);
            Picasso.with(this).load(mImageUri).into(updimage);
           // mImageUri.
        }


    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
     /*   final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("uploading");
        pd.show();
*/
       if (mImageUri != null) {

           final ProgressDialog pd = new ProgressDialog(this);
           pd.setMessage("uploading");
           pd.show();
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                //    mProgressBar.setProgress(0);
                                }
                            }, 500);
                           // Toast.makeText(updatevehical.this, "Upload successful", Toast.LENGTH_LONG).show();
                          //  Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                 myimage2=   taskSnapshot.getDownloadUrl().toString();


                            DatabaseReference upref = FirebaseDatabase.getInstance().getReference().child("uploads").child(key);
                            upref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // if(dataSnapshot.hasChild(key)){

                                    up.setName(uptxt.getText().toString().trim());
                                    up.setImageUrl(myimage2.toString().trim());
                                    up.setPassengers(Integer.parseInt(pass.getText().toString().trim()));
                                    up.setBags(Integer.parseInt(bag.getText().toString().trim()));
                                    up.setPrice(Double.parseDouble(daypprice.getText().toString().trim()));
                                    up.setTransmisson((String) trans.getSelectedItem());
                                    up.setAvailable((String) upavail.getSelectedItem());

                                    dref=FirebaseDatabase.getInstance().getReference().child("uploads").child(key);
                                    dref.setValue(up);
                                    pd.dismiss();
                                    Toast.makeText(updatevehical.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(updatevehical.this, "Not Updated successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });


       }else{

           final ProgressDialog pd = new ProgressDialog(this);
           pd.setMessage("uploading");
           pd.show();
           pd.show();
           pd.show();
           pd.show();


           DatabaseReference upref = FirebaseDatabase.getInstance().getReference().child("uploads").child(key);
           upref.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   // if(dataSnapshot.hasChild(key)){

                   up.setName(uptxt.getText().toString().trim());
                   up.setImageUrl(myimage.toString().trim());
                   up.setPassengers(Integer.parseInt(pass.getText().toString().trim()));
                   up.setBags(Integer.parseInt(bag.getText().toString().trim()));
                   up.setPrice(Double.parseDouble(daypprice.getText().toString().trim()));
                   up.setTransmisson((String) trans.getSelectedItem());
                   up.setAvailable((String) upavail.getSelectedItem());

                   dref=FirebaseDatabase.getInstance().getReference().child("uploads").child(key);
                   dref.setValue(up);



                   pd.dismiss();

                   Toast.makeText(updatevehical.this, "Updated successfully", Toast.LENGTH_SHORT).show();

               }

               @Override
               public void onCancelled(DatabaseError databaseError) {
                   Toast.makeText(updatevehical.this, "Not Updated successfully", Toast.LENGTH_SHORT).show();
               }
           });


       }


}








    private void getData(){
        if(getIntent().hasExtra("name") && getIntent().hasExtra("photo") ){


            name = getIntent().getStringExtra("name");
            myimage = getIntent().getStringExtra("photo");
             key = getIntent().getStringExtra("KEY");
             ppasen = getIntent().getIntExtra("passenger",1);
             bagn = getIntent().getIntExtra("bag",1);
             ppricee = getIntent().getDoubleExtra("price",5.00);
             pspinner = getIntent().getStringExtra("transmission");
             pavailable = getIntent().getStringExtra("available");
        }
        else
            Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
    }

    private void setData(){



   //   updimage.setImageResource(Integer.parseInt(myimage));
        Picasso.with(mContext)
                .load(myimage)
               // .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(updimage);

//updimage.setImageURI(myimage);


        uptxt.setText(name);
        pass.setText(String.valueOf(ppasen));
        bag.setText(String.valueOf(bagn));
        daypprice.setText(String.valueOf(ppricee));
        //trans.getSelectedItem()=pspinner;

        //get cursor to end of the text position
        uptxt.setSelection(uptxt.getText().length());
        pass.setSelection(pass.getText().length());
        daypprice.setSelection(daypprice.getText().length());

        if(pspinner.equals("Auto")){

            List<String> category = new ArrayList<>();
            category.add("Auto");
            category.add("Manual");


            ArrayAdapter<String> dataAdapter;
            dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,category);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            trans.setAdapter(dataAdapter);



        }
        if(pspinner.equals("Manual")){


            List<String> category = new ArrayList<>();
            category.add("Manual");
            category.add("Auto");


            ArrayAdapter<String> dataAdapter;
            dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,category);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            trans.setAdapter(dataAdapter);


        }


        if(pavailable.equals("Available")){

            List<String> category1 = new ArrayList<>();
            category1.add("Available");
            category1.add("Not Available");


            ArrayAdapter<String> dataAdapter;
            dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,category1);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            upavail.setAdapter(dataAdapter);



        }
        else{


            List<String> category1 = new ArrayList<>();
            category1.add("Not Available");
            category1.add("Available");


            ArrayAdapter<String> dataAdapter;
            dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,category1);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            upavail.setAdapter(dataAdapter);


        }

    }




}