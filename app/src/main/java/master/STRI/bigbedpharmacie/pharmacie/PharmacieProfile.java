package master.STRI.bigbedpharmacie.pharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

import master.STRI.bigbedpharmacie.AboutUsActivity;
import master.STRI.bigbedpharmacie.R;
import master.STRI.bigbedpharmacie.client.ClientProfile;
import master.STRI.bigbedpharmacie.helpMe;

public class PharmacieProfile extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    private DrawerLayout drawer;
    private View nView;
    private ActionBarDrawerToggle toggle;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacie_profile);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layoutp);
        NavigationView navigationView = findViewById(R.id.nav_viewpharmacie);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        nView = navigationView.getHeaderView(0);
        name = (TextView) nView.findViewById(R.id.namepharmacie);
        email = (TextView) nView.findViewById(R.id.emailpharmacie);
        String curent_id = firebaseAuth.getCurrentUser().getUid();
        fstore.collection("Users").document(curent_id).get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String n = (String) documentSnapshot.get("fullName");
                        String m = (String) documentSnapshot.get("Email");
                        name.setText(n);
                        email.setText(m);
                    }
                });
        ViewPager2 viewPager2 = (ViewPager2) findViewById(R.id.viewpager2);

        navigationView.setNavigationItemSelectedListener(item1 -> {
            int id = item1.getItemId();
            if (id == R.id.nav_help) {
                Intent intent = new Intent(PharmacieProfile.this, helpMe.class);
                startActivity(intent);

            } else if (id == R.id.nav_language) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);

            } else if (id == R.id.nav_share) {
                ApplicationInfo api = getApplicationContext().getApplicationInfo();
                String apkpath = api.sourceDir;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/vnd.android.package-archive");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
                startActivity(Intent.createChooser(intent, "shareVia"));
            } else if (id == R.id.log_out_actionC) {
                firebaseAuth.signOut();
                finish();
            }
            drawer.closeDrawer(GravityCompat.START);

            return true;
        });


        viewPager2.setAdapter(new FragmentAdapter(this));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        switch (position) {
                            case 0: {
                                //tab.setText(R.string.messanger);
                                tab.setIcon(R.drawable.ic_home);
                                break;
                            }
                            case 1: {
                                //tab.setText(R.string.ajouter_un_service);
                                tab.setIcon(R.drawable.ic_medical_services);
                                break;
                            }
                            case 2: {
                                //tab.setText(R.string.status);
                                tab.setIcon(R.drawable.ic__status);
                                break;
                            }
                        }
                    }
                });
        tabLayoutMediator.attach();

    }
}