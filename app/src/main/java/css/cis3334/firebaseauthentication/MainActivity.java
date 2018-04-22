package css.cis3334.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
//initializes all of the fields
    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sets variables to the data keyed/selected in the user interface
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);
        mAuth = FirebaseAuth.getInstance();

// When the buttonLogin is selected, gets email and password
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CIS3334", "normal login ");
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });
// When the buttonCreateLogin is selected, gets email and password
        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CIS3334", "Create Account ");
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });
// When the buttonGoogleLogin is selected, calls googleSignIn method
        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //Log.d("CIS3334", "Google login ");
                googleSignIn();
            }
        });
// When the buttonSignOut is selected, calls signOut method
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Log.d("CIS3334", "Logging out - signOut ");
                signOut();
            }
        });


    }

    //Checks for sign in info
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and sets textViewStatus accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser); NOT USED... the Signed In and Signed Out messages are set accordingly
        if (currentUser != null){
            //User is signed in
          //  Log.d("CIS3334", "onAuthStateChanged:signed_in" + currentUser.getUid());
          //  Toast.makeText(MainActivity.this, "User Signed In", Toast.LENGTH_LONG).show();
            textViewStatus.setText("Signed In"); //sets status to Signed In
        }
        else{
            //User is signed out
          //  Log.d("CIS3334","onAuthStateChanged:signed_out");
           // Toast.makeText(MainActivity.this, "User Signed Out", Toast.LENGTH_LONG).show();
            textViewStatus.setText("Signed Out"); //sets status to Signed Out
        }
    }

    //Creates new user, if authorized then the user is signed in. If not, a toast message displays and the user is signed out
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password) //checks for authorization
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                          //  Log.d("CIS3334", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            textViewStatus.setText("Signed In"); //sets status to signed in
                        } else {
                            // If sign in fails, display a message to the user.
                          //  Log.w("CIS3334", "createUserWithEmail:failure", task.getException());
                          //  Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            textViewStatus.setText("Signed Out"); //sets status to signed out
                        }

                        // ...
                    }
                });
    }

    //signs in the user, if authorized, otherwise the user is signed out and a toast message displays
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                          //  Log.d("CIS3334", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            textViewStatus.setText("Signed In");
                        } else {
                            // If sign in fails, display a message to the user.
                          //  Log.w("CIS3334", "signInWithEmail:failure", task.getException());
                          //  Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            textViewStatus.setText("Signed Out");
                        }

                        // ...
                    }
                });
    }
//the user is signed out
    private void signOut () {
        mAuth.signOut();
        textViewStatus.setText("Signed Out");
    }
//signs in via google
    private void googleSignIn() {

    }




}
