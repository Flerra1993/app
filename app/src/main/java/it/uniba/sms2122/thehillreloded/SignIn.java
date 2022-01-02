package it.uniba.sms2122.thehillreloded;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import android.view.Window;
import android.view.WindowManager;


public class SignIn extends AppCompatActivity implements View.OnClickListener {

    private EditText eName;

    String userName = "";
    private final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private int height;
    private int widht;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);





        //Login con username
        eName = findViewById(R.id.Username);
        Button eLogin = findViewById(R.id.btnLogin);
        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = eName.getText().toString();

                if (userName.isEmpty()){
                    Toast.makeText(SignIn.this, "Inserire username per giocare", Toast.LENGTH_LONG).show();
                }

                else{
                    startActivity(new Intent(SignIn.this, MainActivity.class));
                }


            }
        });







    //login con google
        // Setto le dimensioni del bottone sign in
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(this);


        // Configuro google sing in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Inizializzo Firebase Authetication
        mAuth = FirebaseAuth.getInstance();
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {

            signInButton.setVisibility(View.GONE);


        } else {

            signInButton.setVisibility(View.VISIBLE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                // Google SingIn ha avuto successo
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Intent Main = new Intent(SignIn.this, MainActivity.class);
                startActivity(Main);
                firebaseAuthWithGoogle(account.getIdToken());


            } catch (ApiException e) {

                // Google SignIn ha fallito
                Snackbar.make(findViewById(R.id.Main_Layout), e.getMessage(), Snackbar.LENGTH_SHORT).show();

                updateUI(null);


            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {


        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            Snackbar.make(findViewById(R.id.Main_Layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();

                        }

                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;

        }

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



}
