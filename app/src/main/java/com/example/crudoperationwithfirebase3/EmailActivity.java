package com.example.crudoperationwithfirebase3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmailActivity extends AppCompatActivity {
    EditText editTextTo,editTextSubject,editTextMessage;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        editTextTo= findViewById(R.id.edit_test_to);
        editTextSubject= findViewById(R.id.txtSubject);
        editTextMessage= findViewById(R.id.txtMessage);
        editTextTo.setText("henrycharles360@gmail.com");
        btnSend= findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    private void sendMail() {
        String recipientList= editTextTo.getText().toString();
        String [] recipient = recipientList.split(",");
        String message =editTextMessage.getText().toString();
        String subject = editTextSubject.getText().toString();
        Intent inent = new Intent(Intent.ACTION_SEND);
        inent.putExtra(Intent.EXTRA_EMAIL,"henrycharles360@gmail.com");
        inent.putExtra(Intent.EXTRA_SUBJECT,subject);
        inent.putExtra(Intent.EXTRA_TEXT,message);
        inent.setType("message/rfc822");
        startActivity(Intent.createChooser(inent,"email client already available"));


    }
}