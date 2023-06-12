package com.example.crudoperationwithfirebase3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private TextView dateAdded,Title,Descriptions;
    private ImageView DetailImage;
    private ImageButton imageButton;
    String key ="";
    String imageUrl="";
    private Toolbar toolbar;
   // String ImageUriForDelete;
    //AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dateAdded =  findViewById(R.id.dateadded);
        Title= findViewById(R.id.details_txtTitle);
        Descriptions= findViewById(R.id.detailDescript);
        toolbar = new Toolbar(this);
        imageButton = findViewById(R.id.delete);
        DetailImage = findViewById(R.id.detail_image);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            dateAdded.setText(bundle.getString("Date"));
            Title.setText(bundle.getString("Topic"));
            Descriptions.setText(bundle.getString("Des"));
            key= bundle.getString("key");
            imageUrl= bundle.getString("Image");
           // key= bundle.getString("key");
          //  ImageUriForDelete= bundle.getString("Image");
            Picasso.get().load(bundle.getString("Image")).into(DetailImage);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("HenryData");
                FirebaseStorage storage= FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),DisplayActivity.class));
                        finish();
                    }
                });
            }
        });

    }
}