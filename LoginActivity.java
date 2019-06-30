package com.example.a90535.letgo;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout userLoginEmailWrapper,userLoginPasswordWrapper;

    EditText userLogEmail,userLogPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLoginEmailWrapper=findViewById(R.id.userLoginEmailWrapper);
        userLoginPasswordWrapper=findViewById(R.id.userLoginPasswordWrapper);

        userLogEmail=findViewById(R.id.userLogEmail);
        userLogPassword=(EditText)findViewById(R.id.userLogPassword);

        btnLogin=findViewById(R.id.btnLogin);

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email= userLogEmail.getText().toString().trim();
                String password= userLogPassword.getText().toString().trim();

                if(email.isEmpty())
                {
                    userLoginEmailWrapper.setError("E-mail adresinizi girin.");
                    userLoginEmailWrapper.requestFocus();
                    return;
                }
                if(password.isEmpty())
                {
                    userLoginPasswordWrapper.setError("Lütfen şifrenizi girin.");
                    userLoginPasswordWrapper.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            id=mAuth.getCurrentUser().getUid();
                            DatabaseReference newReference = firebaseDatabase.getReference("Users").child(id);
                            ValueEventListener listener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User user;
                                    user = dataSnapshot.getValue(User.class);


                                    loginPrefsEditor.putString("id", user.getId());
                                    loginPrefsEditor.putString("email", email);
                                    loginPrefsEditor.putString("sifre", user.getPassword());

                                    loginPrefsEditor.commit();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            };

                            Intent intent=new Intent(LoginActivity.this,ShopActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });




    }
}
