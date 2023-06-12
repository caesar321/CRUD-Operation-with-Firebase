package com.example.crudoperationwithfirebase3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private Spinner spinner;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private myAdapter adapter;
    private ArrayList<User> user;
    private DatabaseReference databaseReference;
   SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        toolbar= new Toolbar(this);
        spinner = findViewById(R.id.spinner);
        searchView= findViewById(R.id.searchView);
        fab= findViewById(R.id.fab);
        recyclerView=  findViewById(R.id.recyclerView);
        user= new ArrayList<>();
        adapter = new myAdapter(this,user);
        databaseReference= FirebaseDatabase.getInstance().getReference("HenryData");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User user1= dataSnapshot.getValue(User.class);
                    user1.setKey(dataSnapshot.getKey());
                    user.add(user1);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DisplayActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
        ArrayAdapter<CharSequence> spinAdapter =  ArrayAdapter.createFromResource(this, R.array.options,android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinAdapter);
        spinner.setOnItemSelectedListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DisplayActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

    }
    public void searchList(String text){
        ArrayList<User> search= new ArrayList<>();
        for(User mymodalclass:user){
            if(mymodalclass.getTitle().toLowerCase().contains(text.toLowerCase())){
                search.add(mymodalclass);
            }
        }
        adapter.searchmyModalClass(search);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text= adapterView.getItemAtPosition(i).toString();
        if(text.equals("Default")){

        }
        else if(text.equals("Add")){
            Intent intent = new Intent(getApplicationContext(),AddActivity.class);
            startActivity(intent);
        }
        else if(text.equals("Contact")){
            Toast.makeText(this, "Contact Clicked", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(getApplicationContext(),EmailActivity.class);
            startActivity(intent);
        }else if(text.equals("About Dev")){
            Toast.makeText(this, "Developer Documentation", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}