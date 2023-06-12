package com.example.crudoperationwithfirebase3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    private EditText UploadTitle,UploadDescription,date;
    private Button btnUpload;
    private ImageView uploadImage;
    Uri uri;
    String ImageUri;
    FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        UploadTitle= findViewById(R.id.topic);
        UploadDescription= findViewById(R.id.description);
        firebaseDatabase= FirebaseDatabase.getInstance();
        date= findViewById(R.id.date);
        uploadImage= findViewById(R.id.image_upload);
        btnUpload= findViewById(R.id.btnUpload);
        SimpleDateFormat dateFormat= new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());
        String datee= dateFormat.format(Calendar.getInstance().getTime());
        date.setText(datee);

        ActivityResultLauncher<Intent> activityResultLauncher=  registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri= data.getData();
                            uploadImage.setImageURI(uri);

                        }else {
                            Toast.makeText(AddActivity.this, "You did not select any image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photo=new Intent(Intent.ACTION_PICK);
                photo.setType("image/*");
                activityResultLauncher.launch(photo);
            }
        });

    }
    private void saveData(){
        StorageReference storageReference= FirebaseStorage.getInstance().getReference("HenryData").child(uri.getLastPathSegment());
        ProgressDialog pd = new ProgressDialog(AddActivity.this);
        pd.setMessage("Uploading");
        pd.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                ImageUri=  urlImage.toString();
                uploadData();
                pd.dismiss();

            }
        });

    }

    private void uploadData() {
        User modal = new User(UploadTitle.getText().toString(),UploadDescription.getText().toString(),date.getText().toString(),ImageUri);
        firebaseDatabase.getReference("HenryData").child(UploadTitle.getText().toString()).setValue(modal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    Toast.makeText(AddActivity.this, "failed...Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}