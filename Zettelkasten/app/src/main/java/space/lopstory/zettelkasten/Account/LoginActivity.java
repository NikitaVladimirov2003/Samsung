package space.lopstory.zettelkasten.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import space.lopstory.zettelkasten.MainActivity;
import space.lopstory.zettelkasten.R;

public class LoginActivity extends AppCompatActivity {

    static private int checker = 0;
    Context context;
    EditText etEmail, etPassword;
    TextView tvSignUp, tvFPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null && currentUser.isEmailVerified()){
            startActivity(new Intent( LoginActivity.this, MainActivity.class ) );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        mAuth = FirebaseAuth.getInstance();
        checker++;
        if(checker == 2){
            FirebaseAuth.getInstance().signOut();
            checker = 1;
        }
        context = this;

        etEmail = findViewById( R.id.editTextLoginEmail );
        etPassword = findViewById( R.id.editTextLoginPassword );


        tvSignUp = findViewById( R.id.textViewSignUp );
        // TODO Forgoten Password
        tvFPassword = findViewById( R.id.textViewForgotPassword );
        btnLogin = findViewById( R.id.buttonLogin );
        btnLogin.setOnClickListener( onClickListener );

        tvSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(LoginActivity.this, RegisterActivity.class) );
            }
        } );

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            login();
        }
    };

    private void login(){
       String email = etEmail.getText().toString(),
              password = etPassword.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher( email ).matches() ) {
            if (!password.isEmpty()) {
                    mAuth.signInWithEmailAndPassword( email, password ).addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if(mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText( LoginActivity.this, "Successfully", Toast.LENGTH_SHORT ).show();
                                startActivity( new Intent( LoginActivity.this, MainActivity.class ) );
                            }else {
                                Toast.makeText( LoginActivity.this, "Проверте почту", Toast.LENGTH_SHORT ).show();
                                mAuth.signOut();
                            }
                        }
                    } ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    } );
            }else {Toast.makeText( LoginActivity.this,"password",Toast.LENGTH_SHORT ).show();}
        }
        else {Toast.makeText( LoginActivity.this,"Email",Toast.LENGTH_SHORT ).show();}
    }

    @Override
    public void onBackPressed() {

    }

}