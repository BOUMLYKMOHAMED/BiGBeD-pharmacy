package master.STRI.bigbedpharmacie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email,password;
    private TextView signin, signup,error;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private ProgressBar progressBar;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);

        fAuth=FirebaseAuth.getInstance();
        error=(TextView)findViewById(R.id.error);

        signin=(TextView)findViewById(R.id.connexionB);
        signin.setOnClickListener(this);
        signup=(TextView)findViewById(R.id.inscrire);
        signup.setOnClickListener(this);
        progressBar=(ProgressBar)findViewById(R.id.IprogressBar);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.connexionB:
                Autauntification();
                break;
            case R.id.inscrire:
                startActivity(new Intent(this,EspaceActivity.class));
                break;
        }

    }

    private void Autauntification() {
        String mEmail=email.getText().toString().trim();
        String mpassword=password.getText().toString().trim();

        if (mEmail.isEmpty()){
            email.setError(getText(R.string.email_empty).toString());
            email.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
            email.setError(getText(R.string.email_error).toString());
            email.requestFocus();
        }
        if (mpassword.isEmpty()){
            password.setError(getText(R.string.password_empty).toString());
            password.requestFocus();
        }
        if (mpassword.length()<6){
            password.setError(getText(R.string.password_small));
            password.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        fAuth.signInWithEmailAndPassword(mEmail,mpassword).addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               progressBar.setVisibility(View.INVISIBLE);
               Toast.makeText(this,"bien aurtentifie",Toast.LENGTH_LONG).show();
               // a faire
           }
           else{
               error.setVisibility(View.VISIBLE);
               progressBar.setVisibility(View.INVISIBLE);
               Toast.makeText(this,"error !!",Toast.LENGTH_LONG).show();
           }
        });
    }
}