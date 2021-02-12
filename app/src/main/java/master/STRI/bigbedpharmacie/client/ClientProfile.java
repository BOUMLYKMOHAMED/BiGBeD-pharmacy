package master.STRI.bigbedpharmacie.client;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import master.STRI.bigbedpharmacie.R;
import master.STRI.bigbedpharmacie.helpMe;

public class ClientProfile extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView name,email;
    private FirebaseFirestore fstore;
    private FirebaseAuth fauth;
    DrawerLayout drawer;
    private View nView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        fstore=FirebaseFirestore.getInstance();
        fauth=FirebaseAuth.getInstance();

        nView=navigationView.getHeaderView(0);
        name=(TextView)nView.findViewById(R.id.nameclient);
        email=(TextView)nView.findViewById(R.id.emailclient);

        navigationView.setNavigationItemSelectedListener(item1 -> {
            int id=item1.getItemId();
            if (id==R.id.nav_helpC) {
                Intent intent = new Intent(ClientProfile.this, helpMe.class);
                startActivity(intent);

            }
            else if(id==R.id.nav_languageC) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);

            }
            else if(id==R.id.nav_shareC){

                 //afire

            }
            else if(id==R.id.log_out_actionC){
                fauth.signOut();
                finish();
            }

            drawer.closeDrawer(GravityCompat.START);

            return true;
        });
        String id=fauth.getCurrentUser().getUid();
        fstore.collection("Users").document(id).get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String n= (String) documentSnapshot.get("fullName");
                String m= (String) documentSnapshot.get("Email");
                name.setText(n);
                email.setText(m);
            }
        });


        toggle =new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}