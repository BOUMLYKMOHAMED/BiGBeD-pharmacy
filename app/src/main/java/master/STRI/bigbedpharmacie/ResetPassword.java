package master.STRI.bigbedpharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText Resetemail;
    private TextView envoyer;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        fAuth=FirebaseAuth.getInstance();


        envoyer=(TextView)findViewById(R.id.envoyer);
        Resetemail=(EditText)findViewById(R.id.ResetEmail);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
    }

    public void sendResetPasword(View view) {
        String email=Resetemail.getText().toString();

        if (email.isEmpty()){
            Resetemail.setError(getText(R.string.email_empty).toString());
            Resetemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Resetemail.setError(getText(R.string.email_error).toString());
            Resetemail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ResetPassword.this," verifiy votre Email address",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ResetPassword.this,"address email est invalid",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }
        });
    }
}