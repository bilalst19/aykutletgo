package com.example.a90535.letgo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText ufirstname,ulastname,uemail,upassword,ucpassword,ucontactnum;
    Button btnRegister;
    TextInputLayout userFirstNameWrapper,userLastNameWrapper,userEmailWrapper,userPasswordWrapper,
            userConfPassWrapper,
            userContactWrapper;
     FirebaseAuth mAuth;
     DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");




        ufirstname = findViewById(R.id.registerFirstName);
        ulastname = findViewById(R.id.userLastName);
        uemail = findViewById(R.id.regEmailAddress);
        upassword = findViewById(R.id.userPasswordregister);
        ucpassword = findViewById(R.id.confirmPasswordReg);
        ucontactnum = findViewById(R.id.registerContactNo);

        userFirstNameWrapper = findViewById(R.id.userFirstNameWrapper);
        userLastNameWrapper = findViewById(R.id.lastNameWrapper);
        userEmailWrapper = findViewById(R.id.userEmailWrapper);
        userPasswordWrapper = findViewById(R.id.userPasswordWrapper);
        userConfPassWrapper = findViewById(R.id.confirmPasswordWrapper);
        userContactWrapper = findViewById(R.id.contactNoWrapper);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (mAuth.getCurrentUser() != null) {

                } else {

                    final String firstname = ufirstname.getText().toString().trim();
                    final String lastname = ulastname.getText().toString().trim();
                    final   String email = uemail.getText().toString().trim();
                    final String password = upassword.getText().toString().trim();
                    final   String confpassword = ucpassword.getText().toString().trim();
                    final    String contactno = ucontactnum.getText().toString().trim();

                    if (firstname.isEmpty()) {
                        userFirstNameWrapper.setError("Lütfen Adınızı Girin");
                        userFirstNameWrapper.requestFocus();
                        return;
                    }
                    if (lastname.isEmpty()) {
                        userLastNameWrapper.setError("Lütfen Soy Adınızı Girin");
                        userLastNameWrapper.requestFocus();
                        return;
                    }
                    if (email.isEmpty()) {
                        userEmailWrapper.setError("Lütfen Mail Adresinizi Girin");
                        userEmailWrapper.requestFocus();
                        return;
                    }

                    if (password.isEmpty()) {
                        userPasswordWrapper.setError("Lütfen Şifrenizi Girin");
                        userPasswordWrapper.requestFocus();
                        return;
                    }
                    if (confpassword.isEmpty()) {
                        userConfPassWrapper.setError("Lütfen Şifrenizi Onaylayın");
                        userConfPassWrapper.requestFocus();
                        return;
                    }
                    if (!password.equals(confpassword)) {
                        userConfPassWrapper.setError("Girdiğiniz şifre ile eşleşmiyor ");
                        userConfPassWrapper.requestFocus();
                        return;
                    }
                    if (contactno.isEmpty()) {
                        userContactWrapper.setError("Lütfen Telefon Numaranızı Girin");
                        userContactWrapper.requestFocus();
                        return;
                    }

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override


                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {


                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                String userId = firebaseUser.getUid();

                                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                                HashMap<String,Object> hashMap = new HashMap<>();
                                hashMap.put("id",userId);
                                hashMap.put("isim",firstname);
                                hashMap.put("soyad",lastname);
                                hashMap.put("email",email);
                                hashMap.put("sifre",password);
                                hashMap.put("tel",contactno);

                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(RegisterActivity.this, "Kullanıcı Başarıyla Oluşturuldu.", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });


                            }else{
                                Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });
    }}
