package master.STRI.bigbedpharmacie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterClient extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private ProgressBar progressBar;

    private EditText fullName, email,phone,password,copassword;
    private TextView valide, seconnecter;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);
        // initialize the firebase and firestore
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        // initialize the edit text

        fullName=(EditText)findViewById(R.id.CfullName);
        email=(EditText)findViewById(R.id.Cemail);
        phone=(EditText)findViewById(R.id.Ctelephone);
        password=(EditText)findViewById(R.id.Cpassword);
        copassword=(EditText)findViewById(R.id.CpasswordConfi);
        // initialize the text view
        progressBar=(ProgressBar)findViewById(R.id.CprogressBar);

        valide=(TextView)findViewById(R.id.Cvalider);
        valide.setOnClickListener(this);
        seconnecter=(TextView)findViewById(R.id.CSe_connecter);
        seconnecter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Cvalider:
                registerClient();
                break;
            case R.id.CSe_connecter:
                startActivity(new Intent(this,MainActivity.class));
                break;
        }

    }

    private void registerClient() {
        String mEmail=email.getText().toString().trim();
        String mpassword=password.getText().toString().trim();
        String mphone=phone.getText().toString();
        String mFullname=fullName.getText().toString();
        String mcopassword=copassword.getText().toString();
        if (mFullname.isEmpty()){
            fullName.setError(getText(R.string.name_empty).toString());
            fullName.requestFocus();
        }
        if (mEmail.isEmpty()){
            email.setError(getText(R.string.email_empty).toString());
            email.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
            email.setError(getText(R.string.email_error).toString());
            email.requestFocus();
        }
        if(mphone.isEmpty()){
            phone.setError(getText(R.string.tel_empty).toString());
            phone.requestFocus();
        }
        if (mpassword.isEmpty()){
            password.setError(getText(R.string.password_empty).toString());
            password.requestFocus();
        }
        if (mpassword.length()<6){
            password.setError(getText(R.string.password_small));
            password.requestFocus();
        }
        if(mcopassword.isEmpty() || !mcopassword.equals(mpassword)){
            copassword.setError(getText(R.string.password_incorect).toString());
            copassword.requestFocus();
        }
        progressBar.setVisibility(View.VISIBLE);
        fAuth.createUserWithEmailAndPassword(mEmail,mpassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this,getText(R.string.userCreate).toString(),Toast.LENGTH_SHORT).show();
                userId=fAuth.getCurrentUser().getUid();
                Client_Info user=new Client_Info(mFullname,mEmail,mphone);
                DocumentReference documentReference=fstore.collection("Clients").document(userId);
               Map<String,Object> client= new HashMap<>();
                         client.put("fullName",user.getFullName());
                         client.put("Email",user.getEmail());
                         client.put("phone",user.getTelephone());
                documentReference.set(client).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(getAttributionTag(),"le profile de client"+ userId + " est bien crée");
                    }
                });
                /// profile de user add

                progressBar.setVisibility(View.INVISIBLE);
            }
            else{
                Toast.makeText(this,"Erreur !! ",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);

            }

        });

    }
}