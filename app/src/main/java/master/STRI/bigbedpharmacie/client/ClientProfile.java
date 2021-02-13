package master.STRI.bigbedpharmacie.client;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import master.STRI.bigbedpharmacie.MainActivity;
import master.STRI.bigbedpharmacie.R;
import master.STRI.bigbedpharmacie.helpMe;
import master.STRI.bigbedpharmacie.pharmacie.Pharmacy_info;

import static java.security.AccessController.getContext;

public class ClientProfile extends AppCompatActivity {

    public RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Pharmacy_info> listpharmacie;
    public ProgressBar progressBar4;

    private TextView name, email;
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
        fstore = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();


        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClientProfile.this));
        listpharmacie = new ArrayList<>();

        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
        progressBar4.setVisibility(View.VISIBLE);

        adapter = new adapterRecycleview(listpharmacie, ClientProfile.this);
        recyclerView.setAdapter(adapter);

        fstore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot : list) {
                        if (documentSnapshot.get("isPharmacie") != null) {
                            Pharmacy_info pharmacie = documentSnapshot.toObject(Pharmacy_info.class);
                            pharmacie.setParmacieId(documentSnapshot.getId());
                            boolean status = (boolean) documentSnapshot.get("status");
                            String email = (String) documentSnapshot.get("Email");
                            String tele = (String) documentSnapshot.get("phone");
                            double latitude = (double) documentSnapshot.get("latitude");
                            double longitude = (double) documentSnapshot.get("longitude");
                            pharmacie.setPstatus(status);
                            pharmacie.setEmail(email);
                            pharmacie.setTelephone(tele);
                            pharmacie.setLatitude(latitude);
                            pharmacie.setLongitude(longitude);
                            listpharmacie.add(pharmacie);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    progressBar4.setVisibility(View.INVISIBLE);
                }

            }
        });


        nView = navigationView.getHeaderView(0);
        name = (TextView) nView.findViewById(R.id.nameclient);
        email = (TextView) nView.findViewById(R.id.emailclient);

        navigationView.setNavigationItemSelectedListener(item1 -> {
            int id = item1.getItemId();
            if (id == R.id.nav_helpC) {
                Intent intent = new Intent(ClientProfile.this, helpMe.class);
                startActivity(intent);

            } else if (id == R.id.nav_languageC) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);

            } else if (id == R.id.nav_shareC) {
                ApplicationInfo api = getApplicationContext().getApplicationInfo();
                String apkpath = api.sourceDir;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("application/vnd.android.package-archive");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
                startActivity(Intent.createChooser(intent, "shareVia"));
            } else if (id == R.id.log_out_actionC) {
                fauth.signOut();
                finish();
            }

            drawer.closeDrawer(GravityCompat.START);

            return true;
        });
        String id = fauth.getCurrentUser().getUid();
        fstore.collection("Users").document(id).get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String n = (String) documentSnapshot.get("fullName");
                        String m = (String) documentSnapshot.get("Email");
                        name.setText(n);
                        email.setText(m);
                    }
                });


        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pharmacie, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // called when we press on the icon search
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // called when we type something for search

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void searchData(String query) {

        progressBar4.setVisibility(View.VISIBLE);
        fstore.collection("Users").whereEqualTo("ville", query.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        listpharmacie.clear();
                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
                            if (documentSnapshot.get("isPharmacie") != null) {
                                Pharmacy_info pharmacie = documentSnapshot.toObject(Pharmacy_info.class);
                                pharmacie.setParmacieId(documentSnapshot.getId());
                                boolean status = (boolean) documentSnapshot.get("status");
                                String email = (String) documentSnapshot.get("Email");
                                String tele = (String) documentSnapshot.get("phone");
                                double latitude = (double) documentSnapshot.get("latitude");
                                double longitude = (double) documentSnapshot.get("longitude");
                                pharmacie.setPstatus(status);
                                pharmacie.setEmail(email);
                                pharmacie.setTelephone(tele);
                                pharmacie.setLatitude(latitude);
                                pharmacie.setLongitude(longitude);
                                listpharmacie.add(pharmacie);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        progressBar4.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {

        }
        return super.onOptionsItemSelected(item);
    }
}