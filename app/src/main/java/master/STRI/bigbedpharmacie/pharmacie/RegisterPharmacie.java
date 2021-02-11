package master.STRI.bigbedpharmacie.pharmacie;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import master.STRI.bigbedpharmacie.AboutUsActivity;
import master.STRI.bigbedpharmacie.MainActivity;
import master.STRI.bigbedpharmacie.R;
import master.STRI.bigbedpharmacie.client.ClientProfile;
import master.STRI.bigbedpharmacie.helpMe;

public class RegisterPharmacie extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private ProgressBar progressBar;

    private EditText fullName, email,phone,password,copassword,longitude,latitude,ville;
    private TextView valide, seconnecter;
    String userId;
    public static final String TAG="TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pharmacie);

        // initialize the firebase and firestore
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        // initialize the edit text

        fullName=(EditText)findViewById(R.id.PFullName);
        email=(EditText)findViewById(R.id.Pemail);
        phone=(EditText)findViewById(R.id.Ptelephone);
        password=(EditText)findViewById(R.id.Ppassword);
        copassword=(EditText)findViewById(R.id.PpasswordConfi);
        longitude=(EditText)findViewById(R.id.Plongitude);
        latitude=(EditText)findViewById(R.id.Platitude);
        ville=(EditText)findViewById(R.id.Pville);
        // initialize the text view

        valide=(TextView)findViewById(R.id.Pvalider);
        valide.setOnClickListener(this);
        seconnecter=(TextView)findViewById(R.id.PSe_connecter);
        seconnecter.setOnClickListener(this);

        progressBar=(ProgressBar)findViewById(R.id.PprogressBar);
/*
        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),ClientProfile.class));
            finish();
        }

 */

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Pvalider:
                RegisterPharmacy();
                break;
            case R.id.PSe_connecter:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.langage:
                Intent intent=new Intent(Settings.ACTION_LOCALE_SETTINGS);
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

    private void RegisterPharmacy() {
        String mEmail=email.getText().toString().trim();
        String mpassword=password.getText().toString().trim();
        String mphone=phone.getText().toString();
        String mFullname=fullName.getText().toString();
        String mcopassword=copassword.getText().toString();
        String mlatitude=latitude.getText().toString();
        String mlongitude=longitude.getText().toString();
        String mville=ville.getText().toString();
        if (mFullname.isEmpty()){
            fullName.setError(getText(R.string.name_empty).toString());
            fullName.requestFocus();
            return;
        }
        if (mEmail.isEmpty()){
            email.setError(getText(R.string.email_empty).toString());
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
            email.setError(getText(R.string.email_error).toString());
            email.requestFocus();
            return;
        }
        if(mphone.isEmpty()){
            phone.setError(getText(R.string.tel_empty).toString());
            phone.requestFocus();
            return;
        }
        if(mville.isEmpty()){
            ville.setError(getText(R.string.ville_empty).toString());
            ville.requestFocus();
            return;
        }
        if(mlongitude.isEmpty()){
            longitude.setError(getText(R.string.longitude_empty).toString());
            longitude.requestFocus();
            return;
        }
        if(mlatitude.isEmpty()){
            latitude.setError(getText(R.string.latitude_empty).toString());
            latitude.requestFocus();
            return;
        }

        if (mpassword.isEmpty()){
            password.setError(getText(R.string.password_empty).toString());
            password.requestFocus();
            return;
        }
        if (mpassword.length()<6){
            password.setError(getText(R.string.password_small));
            password.requestFocus();
            return;
        }
        if(mcopassword.isEmpty() || !mcopassword.equals(mpassword)){
            copassword.setError(getText(R.string.password_incorect).toString());
            copassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        fAuth.createUserWithEmailAndPassword(mEmail,mpassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                double Latitude=Double.parseDouble(mlatitude);
                double Longitude=Double.parseDouble(mlongitude);
                boolean status=false;
                Toast.makeText(this,getText(R.string.userCreate).toString(),Toast.LENGTH_SHORT).show();
                userId=fAuth.getCurrentUser().getUid();
               Pharmacy_info user=new Pharmacy_info(mFullname,mEmail,mphone,mville,Latitude,Longitude,status);
                DocumentReference documentReference=fstore.collection("Pharmacies").document(userId);
                DocumentReference documentReference1=fstore.collection("users").document(userId);

                Map<String,Object> client1= new HashMap<>();
                client1.put("isPharmacie",1);
                documentReference1.set(client1);
                Map<String,Object> client= new HashMap<>();
                client.put("fullName",user.getFullName());
                client.put("Email",user.getEmail());
                client.put("phone",user.getTelephone());
                client.put("ville",user.getVille());
                client.put("latitude",user.getLatitude());
                client.put("longitude",user.getLongitude());
                client.put("status",user.getPstatus());
                documentReference.set(client).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"le profile de client"+ userId + " est bien crée");
                    }
                });

                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(RegisterPharmacie.this, PharmacieProfile.class));
                finish();
            }
            else{
                Toast.makeText(this,"Erreur !! ",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }

        });

    }
}