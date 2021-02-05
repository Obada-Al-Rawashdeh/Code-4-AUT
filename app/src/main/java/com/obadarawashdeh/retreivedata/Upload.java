package com.obadarawashdeh.retreivedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.obadarawashdeh.retreivedata.Activity.AdsActivity;
import com.obadarawashdeh.retreivedata.Model.AdsModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class Upload extends AppCompatActivity {

    Button btnbrowse, btnupload;
    EditText txtdata,txtdetail ;
    ImageView imgview;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    String cTitle,cDescr,cImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        storageReference = FirebaseStorage.getInstance().getReference("ads");
        databaseReference = FirebaseDatabase.getInstance().getReference("Data");
        btnbrowse = (Button)findViewById(R.id.btnbrowse);
        btnupload= (Button)findViewById(R.id.btnupload);
        txtdata = (EditText)findViewById(R.id.txtdata);
        txtdetail = (EditText)findViewById(R.id.txtdetail);
        imgview = (ImageView)findViewById(R.id.image_view);

        Bundle intentt=getIntent().getExtras();
        if(intentt!=null){
            cTitle=intentt.getString("cTitle");
            cDescr=intentt.getString("cDescr");
            cImage=intentt.getString("cImage");
            txtdata.setText(cTitle);
            txtdetail.setText(cDescr);
            Picasso.get().load(cImage).into(imgview);
            btnupload.setText(R.string.update);

        }

        progressDialog = new ProgressDialog(Upload.this);// context name as per your project name
        progressDialog.setCancelable(false);


        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.select_image)), Image_Request_Code);

            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(txtdata.getText().toString().equals("") ||txtdetail.getText().toString().equals("") )){
                    if(btnupload.getText().equals(getString(R.string.upload))){
                        UploadImage();
                    }else {
                        beginUpdate();
                    }
                }else {
                    Toast.makeText(Upload.this,R.string.empty,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void beginUpdate() {
        progressDialog.setTitle(getString(R.string.updating));
        progressDialog.show();
        deletePreviousImage();
    }

    private void deletePreviousImage() {
        StorageReference mPictureRefe=getInstance().getReferenceFromUrl(cImage);
        mPictureRefe.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Upload.this, R.string.prev_img_del,Toast.LENGTH_SHORT).show();
                uploadNewImage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Upload.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }
        });
    }

    private void uploadNewImage() {
        if (FilePathUri != null) {

            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), R.string.upload_done, Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            updateDatabase(downloadUrl.toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Upload.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        }
        else {

            Toast.makeText(Upload.this, R.string.select_image, Toast.LENGTH_LONG).show();

        }
    }

    private void updateDatabase(final String s) {
        final String title=txtdata.getText().toString();
        final String descr=txtdetail.getText().toString();
        FirebaseDatabase mFirebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference mRef =mFirebaseDatabase.getReference("Data");

        Query query=mRef.orderByChild("title").equalTo(cTitle);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    ds.getRef().child("title").setValue(title);
                    ds.getRef().child("description").setValue(descr);
                    ds.getRef().child("image").setValue(s);
                }
                progressDialog.dismiss();
                Toast.makeText(Upload.this, R.string.db_update, Toast.LENGTH_LONG).show();
                startActivity(new Intent(Upload.this, AdsActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();
            //InputStream inputStream=(InputStream) data.getData();
            imgview.setImageURI(FilePathUri);
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    public void UploadImage() {

        if (FilePathUri != null) {

            progressDialog.setTitle(getString(R.string.uploadImage));
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            /*String TempImageName = txtdata.getText().toString().trim();
                            String TempImageDetail = txtdetail.getText().toString().trim();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            Model imageUploadInfo = new Model(TempImageName, taskSnapshot.getUploadSessionUri().toString(),TempImageDetail);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);*/
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),  R.string.upload_done, Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();

                            //Log.d(TAG, "onSuccess: firebase download url: " + downloadUrl.toString()); //use if testing...don't need this line.
                            AdsModel upload = new AdsModel(txtdata.getText().toString().trim(),downloadUrl.toString(),txtdetail.getText().toString().trim());

                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);
                        }
                    });
        }
        else {

            Toast.makeText(Upload.this, R.string.select_image, Toast.LENGTH_LONG).show();

        }
    }
}
