package com.cupcakes.kandycupcakes.IT19207100;

import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.cupcakes.kandycupcakes.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class addvehical extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText pass,price,bag;
    private Spinner trans,avail;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;

    private TextInputLayout textvehical;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vehical);
        mButtonChooseImage = findViewById(R.id.mButtonChooseImage);
        mButtonUpload = findViewById(R.id.mButtonUpload);
        mTextViewShowUploads = findViewById(R.id.mTextViewShowUploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        //mProgressBar = findViewById(R.id.progress_bar);
        pass=findViewById(R.id.passengers);
        price=findViewById(R.id.dayprice);
        trans=findViewById(R.id.spinner);
        bag=findViewById(R.id.bag);
        avail=findViewById(R.id.spinner2);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.image_view,
                RegexTemplate.NOT_EMPTY, R.string.fill);


        awesomeValidation.addValidation(this, R.id.edit_text_file_name,
                RegexTemplate.NOT_EMPTY, R.string.fill);

        awesomeValidation.addValidation(this, R.id.edit_text_file_name,"[a-zA-Z ]{1,20}", R.string.invalid);

        awesomeValidation.addValidation(this, R.id.passengers,
                RegexTemplate.NOT_EMPTY, R.string.fill);

        awesomeValidation.addValidation(this, R.id.spinner,
                RegexTemplate.NOT_EMPTY, R.string.fill);

        awesomeValidation.addValidation(this, R.id.bag,
                RegexTemplate.NOT_EMPTY, R.string.fill);

        awesomeValidation.addValidation(this, R.id.dayprice,
                RegexTemplate.NOT_EMPTY, R.string.fill);




        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {
                    if(trans.getSelectedItem().equals("Select One")) {
                        Toast.makeText(addvehical.this, "Select the Transmission Type", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (mImageUri != null) {
                            if (mUploadTask != null && mUploadTask.isInProgress()) {
                                Toast.makeText(addvehical.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                            } else {
                                uploadFile();
                            }
                        } else {
                            Toast.makeText(addvehical.this, "Select the image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{

                    Toast.makeText(addvehical.this, "Please Fill The All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
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
            mImageView.setImageURI(mImageUri);
            Picasso.with(this).load(mImageUri).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
       final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (mImageUri != null) {

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
                                  //  mProgressBar.setProgress(0);
                                }
                            }, 500);

                            pd.dismiss();
                            Toast.makeText(addvehical.this, "Upload successful", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString(),Integer.parseInt(pass.getText().toString().trim()),
                                    Integer.parseInt(bag.getText().toString().trim()),
                                    Double.parseDouble(price.getText().toString().trim()),
                                    (String) trans.getSelectedItem(),(String)avail.getSelectedItem());

                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);

                            clearfields();
                        }
                    })




                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addvehical.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openImagesActivity() {
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
    }

    public void clearfields(){
        mEditTextFileName.setText("");
        pass.setText("");
        bag.setText("");
        price.setText("");
       // mImageView.setImageURI(null);
        Picasso.with(this).load(R.drawable.upd2).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(mImageView);

        List<String> category = new ArrayList<>();

        category.add("Select One");
        category.add("Auto");
        category.add("Manual");


        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,category);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        trans.setAdapter(dataAdapter);


        List<String> category1 = new ArrayList<>();
        category1.add("Select One");
        category1.add("Available");
        category1.add("Not Available");


        ArrayAdapter<String> dataAdaptera;
        dataAdaptera = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,category1);
        dataAdaptera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        avail.setAdapter(dataAdaptera);

    }
}