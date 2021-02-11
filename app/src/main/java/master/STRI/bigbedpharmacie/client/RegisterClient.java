package master.STRI.bigbedpharmacie.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import master.STRI.bigbedpharmacie.AboutUsActivity;
import master.STRI.bigbedpharmacie.MainActivity;
import master.STRI.bigbedpharmacie.R;
import master.STRI.bigbedpharmacie.helpMe;
import master.STRI.bigbedpharmacie.pharmacie.PharmacieProfile;

public class RegisterClient extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private ProgressBar progressBar;

    private EditText fullName, email, phone, password, copassword;
    private TextView valide, seconnecter;
    public static final String TAG="TAG";
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);
        // initialize the firebase and firestore
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        // initialize the edit text

        fullName = (EditText) findViewById(R.id.CfullName);
        email = (EditText) findViewById(R.id.Cemail);
        phone = (EditText) findViewById(R.id.Ctelephone);
        password = (EditText) findViewById(R.id.Cpassword);
        copassword = (EditText) findViewById(R.id.CpasswordConfi);
        // initialize the text view
        progressBar = (ProgressBar) findViewById(R.id.CprogressBar);

        valide = (TextView) findViewById(R.id.Cvalider);
        seconnecter = (TextView) findViewById(R.id.CSe_connecter);
        seconnecter.setOnClickListener(this);
        valide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mpassword = password.getText().toString().trim();
                String mphone = phone.getText().toString();
                String mFullname = fullName.getText().toString();
                String mcopassword = copassword.getText().toString();
                if (mFullname.isEmpty()) {
                    fullName.setError(getText(R.string.name_empty).toString());
                    fullName.requestFocus();
                    return;
                }
                if (mEmail.isEmpty()) {
                    email.setError(getText(R.string.email_empty).toString());
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                    email.setError(getText(R.string.email_error).toString());
                    email.requestFocus();
                    return;
                }
                if (mphone.isEmpty()) {
                    phone.setError(getText(R.string.tel_empty).toString());
                    phone.requestFocus();
                    return;
                }
                if (mpassword.isEmpty()) {
                    password.setError(getText(R.string.password_empty).toString());
                    password.requestFocus();
                    return;
                }
                if (mpassword.length() < 6) {
                    password.setError(getText(R.string.password_small));
                    password.requestFocus();
                    return;
                }
                if (mcopassword.isEmpty() || !mcopassword.equals(mpassword)) {
                    copassword.setError(getText(R.string.password_incorect).toString());
                    copassword.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(mEmail, mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), getText(R.string.userCreate).toString(), Toast.LENGTH_SHORT).show();
                            userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                            Client_Info user = new Client_Info(mFullname, mEmail, mphone);
                            DocumentReference documentReference = fstore.collection("Users").document(userId);
                            Map<String, Object> client = new HashMap<>();
                            client.put("isClient", 1);
                            client.put("fullName", user.getFullName());
                            client.put("Email", user.getEmail());
                            client.put("phone", user.getTelephone());
                            documentReference.set(client).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "le profile de client" + userId + " est bien crée");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "failure" + userId + e.toString());

                                }
                            });
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(getApplicationContext(), ClientProfile.class);
                            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), getText(R.string.error).toString(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        }

                    };
                });
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CSe_connecter:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.langage:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);

                break;
            case R.id.help_me:
                startActivity(new Intent(this, helpMe.class));
                break;


            case R.id.about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
