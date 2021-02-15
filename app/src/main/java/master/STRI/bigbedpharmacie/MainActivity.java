package master.STRI.bigbedpharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

import master.STRI.bigbedpharmacie.client.ClientProfile;
import master.STRI.bigbedpharmacie.pharmacie.PharmacieProfile;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email,password;
    private TextView signin, signup,error,passwordForget,privacy;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private ProgressBar progressBar;
    private CheckBox checkBox;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);

        checkBox=(CheckBox)findViewById(R.id.checkBox);
        error=(TextView)findViewById(R.id.error);

        signin=(TextView)findViewById(R.id.connexionB);
        signin.setOnClickListener(this);
        privacy=(TextView)findViewById(R.id.privacy);
        privacy.setOnClickListener(this);
        signup=(TextView)findViewById(R.id.inscrire);
        signup.setOnClickListener(this);
        passwordForget=(TextView)findViewById(R.id.passwordForget);
        passwordForget.setOnClickListener(this);
        progressBar=(ProgressBar)findViewById(R.id.IprogressBar);
        
        if (fAuth.getCurrentUser()!=null){
            String userid=fAuth.getCurrentUser().getUid();
            fStore.collection("Users").document(userid).
                    get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.get("isPharmacie")!=null){
                        Intent intent=new Intent(MainActivity.this, PharmacieProfile.class);
                        startActivity(intent);

                    }
                    else{
                        Intent intent=new Intent(MainActivity.this, ClientProfile.class);
                        startActivity(intent);
                    }

                }
            });

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
                startActivity(new Intent(this,helpMe.class));
                break;

            case R.id.about_us:
                startActivity(new Intent(this,AboutUsActivity.class));
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
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
            case R.id.passwordForget:
                startActivity(new Intent(this,ResetPassword.class));
                break;
            case R.id.privacy:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.url_privacy)));
                startActivity(browserIntent);
        }

    }

    private void Autauntification() {
        String mEmail=email.getText().toString().trim();
        String mpassword=password.getText().toString().trim();

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

        progressBar.setVisibility(View.VISIBLE);
        fAuth.signInWithEmailAndPassword(mEmail,mpassword).addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               String userid=fAuth.getCurrentUser().getUid();

               fStore.collection("Users").document(userid).
                       get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                   @Override
                   public void onSuccess(DocumentSnapshot documentSnapshot) {
                       if (documentSnapshot.get("isPharmacie")!=null){
                           Toast.makeText(MainActivity.this,getText(R.string.bienautentifi).toString(),Toast.LENGTH_LONG).show();
                           progressBar.setVisibility(View.INVISIBLE);
                           Intent intent=new Intent(MainActivity.this, PharmacieProfile.class);
                           startActivity(intent);

                       }
                       else{
                           Toast.makeText(MainActivity.this,getText(R.string.bienautentifi).toString(),Toast.LENGTH_LONG).show();
                           progressBar.setVisibility(View.INVISIBLE);
                           Intent intent=new Intent(MainActivity.this, ClientProfile.class);
                           startActivity(intent);
                       }

                   }
               });

           }
           else{
               error.setVisibility(View.VISIBLE);
               progressBar.setVisibility(View.INVISIBLE);
               Toast.makeText(this,getText(R.string.error).toString(),Toast.LENGTH_LONG).show();
           }
        });
    }

    @Override
    protected void onStart() {
            super.onStart();
    }

    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }
}