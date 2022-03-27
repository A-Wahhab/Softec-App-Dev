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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class register extends AppCompatActivity {

    RadioGroup radioGroup,radioGroup2;
    EditText inpEmail,inpPassword,inpRepassword,inpName;
    Button btnSignup;
    String emailVerif = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    ProgressDialog progressDialog;

    FirebaseAuth myAuth;
    FirebaseUser myUser;

    RadioButton part,org,spons,head,mentor,exec,amb;

    @Override
    protected void onStart() {
        super.onStart();

        if (myAuth.getCurrentUser() != null){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_register);

        inpEmail = findViewById(R.id.reginpEmail);
        inpPassword = findViewById(R.id.reginpPassword);
        inpRepassword = findViewById(R.id.reginpRepassword);
        inpName = findViewById(R.id.reginpname);
        btnSignup = findViewById(R.id.btnSignup);
        radioGroup = findViewById(R.id.rbtn_group);
        radioGroup2 = findViewById(R.id.rbtn_eventmangerset);
        progressDialog = new ProgressDialog(this);
        //radioGroup.setOnClickListener();
        myAuth = FirebaseAuth.getInstance();
        myUser = myAuth.getCurrentUser();

        part = findViewById(R.id.rbtn_participants);
        spons = findViewById(R.id.rbtn_sponsors);
        org = findViewById(R.id.rbtn_eventmang);

        head = findViewById(R.id.regredbtnhead);
        mentor = findViewById(R.id.regredbtnname);
        exec = findViewById(R.id.regredbtnexec);
        amb = findViewById(R.id.regredbtnamb);





        radioGroup2 = findViewById(R.id.rbtn_eventmangerset);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rbtn_participants:
                        radioGroup2.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.rbtn_eventmang:
                        radioGroup2.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbtn_sponsors:
                        radioGroup2.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 PerformAuth();
            }
        });


    }
    private void sndnextactivity(){
        Intent intent = new Intent(register.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    private void PerformAuth() {
        final String name = inpName.getText().toString().trim();
        final String email = inpEmail.getText().toString().trim();
        String password = inpPassword.getText().toString().trim();
        String repassword = inpRepassword.getText().toString().trim();

        if (email.matches(emailVerif) == false ){
            inpEmail.setError("Invalid Email");
        }
        else if (password.isEmpty() || password.length()<8){
            inpPassword.setError("Invalid Password Format");
        }
        else if(!password.equals(repassword)){
            inpRepassword.setError("Password does not match");
        }
        else if (radioGroup.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(register.this,"Specify your role",Toast.LENGTH_LONG).show();
        }
        else{
            String role = "";
            String sub_role = "";
            progressDialog.setMessage("Please wait a moment");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            if (spons.isChecked()){
                role = "sponsor";
                sub_role = "";
            }
            else if(part.isChecked()){
                role = "participant";
                sub_role = "";
            }
            else if (org.isChecked()){
                role = "organisor";
                if (head.isChecked()){
                    sub_role = "head";
                }
                else if(mentor.isChecked()){
                    sub_role = "mentor";
                }
                else if(exec.isChecked()){
                    sub_role = "executive";
                }
                else if(amb.isChecked()){
                    sub_role = "ambassador";
                }
                else{
                    sub_role = "";
                }
            }
            String temprole = role;
            String tempsubrole = sub_role;
            myAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("*******", "onComplete: in creating realtime database");
                    if (task.isSuccessful()){
                        String temp_role = temprole;
                        String temp_sub_role = tempsubrole;
                        User user = new User(
                                name,email,temp_role,temp_sub_role
                        );
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(register.this,"Success",Toast.LENGTH_LONG).show();
                                    sndnextactivity();

                                }
                                else{
                                    Toast.makeText(register.this,"Unsuccessful",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                    else{
                        //Toast.makeText(,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        Toast.makeText(register.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }
}