package space.lopstory.zettelkasten.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import space.lopstory.zettelkasten.R;

import static space.lopstory.zettelkasten.constant.User;

public class RegisterActivity extends AppCompatActivity {


    EditText etEmail, etPassword;
    Button btnRegister;
    FirebaseAuth mAuth;
    private DatabaseReference myDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        init();
        btnRegister.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createUser();
            }
        });



    }

    private void init(){
        etEmail = findViewById( R.id.editTextRegisterPhoneNumber );
        etPassword = findViewById( R.id.editTextRegisterPassword );
        btnRegister = findViewById( R.id.buttonRegister);
        mAuth = FirebaseAuth.getInstance();
        myDataBase = FirebaseDatabase.getInstance().getReference();
    }



    private void createUser(){
        String email = etEmail.getText().toString(),
               password = etPassword.getText().toString();
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher( email ).matches()) {
            if( !password.isEmpty() ) {

                mAuth.createUserWithEmailAndPassword( email, password ).addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        sendEmailVer();
                        finish();
                    }
                } ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                } ).addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText( RegisterActivity.this, e.getMessage(),Toast.LENGTH_SHORT ).show();
                    }
                } );
            }
        }
    }

    private void sendEmailVer(){
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification().addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText( RegisterActivity.this,"Проверте почту",Toast.LENGTH_SHORT ).show();
                }
                else{
                    Toast.makeText( RegisterActivity.this,"failed",Toast.LENGTH_SHORT ).show();

                }
            }
        } );
    }

}