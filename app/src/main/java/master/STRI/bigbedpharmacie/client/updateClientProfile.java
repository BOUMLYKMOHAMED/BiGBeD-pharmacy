package master.STRI.bigbedpharmacie.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import master.STRI.bigbedpharmacie.R;
import master.STRI.bigbedpharmacie.pharmacie.PharmacieProfile;
import master.STRI.bigbedpharmacie.pharmacie.Pharmacy_info;
import master.STRI.bigbedpharmacie.pharmacie.updatProfile;

public class updateClientProfile extends AppCompatActivity {

    private FirebaseAuth fauth;
    private FirebaseFirestore fstore;
    private TextView updateC;
    private EditText namC,emailC,telC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_client_profile);
        fauth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        updateC=(TextView)findViewById(R.id.updateC);
        namC=(EditText)findViewById(R.id.namC);
        emailC=(EditText)findViewById(R.id.emailC);
        telC=(EditText)findViewById(R.id.teleC);

        String id = fauth.getCurrentUser().getUid();
        fstore.collection("Users").document(id).get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String n = (String) documentSnapshot.get("fullName");
                        String m = (String) documentSnapshot.get("Email");
                        String t = (String) documentSnapshot.get("phone");
                        namC.setText(n);
                        emailC.setText(m);
                        telC.setText(t);
                    }
                });
        updateC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=namC.getText().toString();
                String email=emailC.getText().toString();
                String tele=telC.getText().toString();
                if (name.isEmpty()){
                    namC.setError(getText(R.string.name_empty).toString());
                    namC.requestFocus();
                    return;
                }
                if (email.isEmpty()){
                    emailC.setError(getText(R.string.email_empty).toString());
                    emailC.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailC.setError(getText(R.string.email_error).toString());
                    emailC.requestFocus();
                    return;
                }
                if(tele.isEmpty()){
                    telC.setError(getText(R.string.tel_empty).toString());
                    telC.requestFocus();
                    return;
                }
                Client_Info data=new Client_Info(name,email,tele);
                String id=fauth.getCurrentUser().getUid();
                fstore.collection("Users").document(id)
                        .update("fullName",data.getFullName(),
                                "Email",data.getEmail(),
                                "phone",data.getTelephone()
                        )
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @SuppressLint("ShowToast")
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getBaseContext(),getText(R.string.update_profile_message).toString(),Toast.LENGTH_SHORT);
                                startActivity(new Intent(updateClientProfile.this, ClientProfile.class));
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