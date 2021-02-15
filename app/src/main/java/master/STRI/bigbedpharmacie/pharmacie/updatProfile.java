package master.STRI.bigbedpharmacie.pharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import master.STRI.bigbedpharmacie.R;

public class updatProfile extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private TextView update;
    private EditText namp,emailp,villep,latip,lonp,telp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_updat_profile);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        update=(TextView)findViewById(R.id.update);
        namp=(EditText)findViewById(R.id.namp);
        emailp=(EditText)findViewById(R.id.emailp);
        telp=(EditText)findViewById(R.id.telep);
        villep=(EditText)findViewById(R.id.villep);
        latip=(EditText)findViewById(R.id.latip);
        lonp=(EditText)findViewById(R.id.longp);


        String nom=getIntent().getStringExtra("name");
        String emi=getIntent().getStringExtra("email");
        String Te=getIntent().getStringExtra("tele");
        String v=getIntent().getStringExtra("ville");
        String lat=getIntent().getStringExtra("lati");
        String lo=getIntent().getStringExtra("long");

        namp.setText(nom);
        emailp.setText(emi);
        telp.setText(Te);
        villep.setText(v);
        latip.setText(lat);
        lonp.setText(lo);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=namp.getText().toString();
                String email=emailp.getText().toString();
                String tele=telp.getText().toString();
                String ville=villep.getText().toString().toLowerCase();
                String lati=latip.getText().toString();
                String longit=lonp.getText().toString();
                if (name.isEmpty()){
                    namp.setError(getText(R.string.name_empty).toString());
                    namp.requestFocus();
                    return;
                }
                if (email.isEmpty()){
                    emailp.setError(getText(R.string.email_empty).toString());
                    emailp.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailp.setError(getText(R.string.email_error).toString());
                    emailp.requestFocus();
                    return;
                }
                if(tele.isEmpty()){
                    telp.setError(getText(R.string.tel_empty).toString());
                    telp.requestFocus();
                    return;
                }
                if(ville.isEmpty()){
                    villep.setError(getText(R.string.ville_empty).toString());
                    villep.requestFocus();
                    return;
                }
                if(lati.isEmpty()){
                    latip.setError(getText(R.string.latitude_empty).toString());
                    latip.requestFocus();
                    return;
                }
                if(longit.isEmpty()){
                    lonp.setError(getText(R.string.longitude_empty).toString());
                    lonp.requestFocus();
                    return;
                }

                Pharmacy_info data=new Pharmacy_info(name,email,tele,ville,Double.parseDouble(lati),Double.parseDouble(longit),false);
                String id=firebaseAuth.getCurrentUser().getUid();
                firebaseFirestore.collection("Users").document(id)
                        .update("fullName",data.getFullName(),
                                "Email",data.getEmail(),
                                "phone",data.getTelephone(),
                                "ville",data.getVille(),
                                "latitude",data.getLatitude(),
                                "longitude",data.getLongitude(),
                                "status",false,
                                "isPharmacie",1
                        )
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getBaseContext(),getText(R.string.update_profile_message).toString(),Toast.LENGTH_SHORT);
                                startActivity(new Intent(updatProfile.this,PharmacieProfile.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getBaseContext(),getText(R.string.update_profile_message_error).toString(),Toast.LENGTH_SHORT);
                            }
                        });


            }
        });

    }
}