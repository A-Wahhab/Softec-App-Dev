package com.example.softec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText inpPassword,inpEmail;
    Button btnSignin;
    String emailVerif = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    ProgressDialog progressDialog;

    FirebaseAuth myAuth;
    FirebaseUser myUser;

    String finaluser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        inpPassword = findViewById(R.id.logininppassword);
        inpEmail = findViewById(R.id.logininpemail);
        progressDialog = new ProgressDialog(this);
        //radioGroup.setOnClickListener();
        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();
        btnSignin = findViewById(R.id.btn_signin);
        Button signup = findViewById(R.id.btn_signuppage);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(), register.class);
                startActivity(i);
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
    }
    private void sndnextactivity(String type){
        Intent intent = new Intent(MainActivity.this,event_view.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("type",type);
        startActivity(intent);

    }
    private void performLogin(){
        final String email = inpEmail.getText().toString().trim();
        String password = inpPassword.getText().toString().trim();

        if (password.isEmpty() || password.length()<8){
            inpPassword.setError("Invalid Password Format");
        }
        else {
            String role = "";
            String sub_role = "";
            progressDialog.setMessage("Logging in");
            progressDialog.setTitle("Log in");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            DatabaseReference dref = FirebaseDatabase
                    .getInstance("https://softec-789f6-default-rtdb.firebaseio.com/")
                    .getReference("Users");
            dref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds :snapshot.getChildren())
                    {
                        if(ds.child("email").getValue().toString().equals(email))
                        {
                            final String usertype = Objects.requireNonNull(ds.child("type").getValue()).toString();
                            Log.d("********", usertype);
                            finaluser = usertype;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "database failed", Toast.LENGTH_LONG).show();
                }
            });
            myAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                        sndnextactivity(finaluser);
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Unsuccessful",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }
}