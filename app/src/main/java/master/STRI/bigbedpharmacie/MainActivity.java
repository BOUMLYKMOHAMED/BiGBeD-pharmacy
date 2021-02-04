package master.STRI.bigbedpharmacie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email,password;
    private TextView signin, signup;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);

        signin=(TextView)findViewById(R.id.connexionB);
        signin.setOnClickListener(this);
        signup=(TextView)findViewById(R.id.inscrire);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.connexionB:
                /// a faire
                break;
            case R.id.inscrire:
                startActivity(new Intent(this,EspaceActivity.class));
                break;
        }

    }
}