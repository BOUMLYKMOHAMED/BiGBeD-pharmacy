package master.STRI.bigbedpharmacie.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import master.STRI.bigbedpharmacie.Medicament.Med_Info;
import master.STRI.bigbedpharmacie.Medicament.Medicament_Adapter;
import master.STRI.bigbedpharmacie.R;
import master.STRI.bigbedpharmacie.pharmacie.Pharmacy_info;

public class DesplayPharmacieInfo extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView.Adapter adapter;
    private List<Med_Info> listmed;
    private FirebaseFirestore fstore;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar3;
    private TextView texSetnom;
    private ImageView search,map,callMe;
    private RecyclerView recycleViewMedicament;
    private double latitude,longitude;
    private String pharmacyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desplay_pharmacie_info);
        progressBar3=(ProgressBar)findViewById(R.id.progressBar3);
        progressBar3.setVisibility(View.VISIBLE);
        Pharmacy_info pharmacy= (Pharmacy_info) getIntent().getSerializableExtra("pharmacie");
        texSetnom=(TextView)findViewById(R.id.texSetnom);

        latitude=pharmacy.getLatitude();
        longitude=pharmacy.getLongitude();


         pharmacyId=pharmacy.getParmacieId();
        texSetnom.setText(pharmacy.getFullName());
        search=(ImageView)findViewById(R.id.search);
        map=(ImageView)findViewById(R.id.maps);
        callMe=(ImageView)findViewById(R.id.callMe);
        map.setOnClickListener(this);
        search.setOnClickListener(this);

        callMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tele=pharmacy.getTelephone();
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+tele));
                startActivity(intent);
            }
        });


        fstore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        recycleViewMedicament=(RecyclerView)findViewById(R.id.recycleViewMedicament);
        recycleViewMedicament.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        listmed=new ArrayList<>();
        recycleViewMedicament.setHasFixedSize(true);
        adapter=new Medicament_Adapter(listmed,getBaseContext());
        recycleViewMedicament.setAdapter(adapter);

        fstore.collection("Services").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> listMed=queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d:listMed){
                        Med_Info med=d.toObject(Med_Info.class);
                        String id=(String)d.get("Pharmacie");
                        String des=(String)d.get("Description");
                        String name=(String)d.get("Name");

                        med.setDescription(des);
                        med.setName(name);
                        med.setId(id);
                        if (d.get("isMedicament")!=null){
                            med.setType(true);
                        }
                        else{
                            med.setType(false);

                        }
                        if (med.getId().equals(pharmacyId)){
                            listmed.add(med);
                        }

                    }
                        adapter.notifyDataSetChanged();
                        progressBar3.setVisibility(View.INVISIBLE);

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.maps:
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:"+latitude+","+longitude));
                Intent choiser=intent.createChooser(intent,"Launch Maps");
                startActivity(choiser);
                break;
        }

    }

    private void searchData(String message) {

        progressBar3.setVisibility(View.VISIBLE);
        fstore.collection("Services").whereEqualTo("Name",message.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        listmed.clear();
                        for (DocumentSnapshot d : task.getResult()) {
                            Med_Info med=d.toObject(Med_Info.class);
                            String id=(String)d.get("Pharmacie");
                            String des=(String)d.get("Description");
                            String name=(String)d.get("Name");
                            med.setDescription(des);
                            med.setName(name);
                            med.setId(id);
                            if (d.get("isMedicament")!=null){
                                med.setType(true);
                            }
                            else{
                                med.setType(false);

                            }
                            if (med.getId().equals(pharmacyId)){
                                listmed.add(med);
                            }

                        }
                        if (listmed.isEmpty())
                            Toast.makeText(DesplayPharmacieInfo.this,
                                    getText(R.string.there_no_item).toString(),
                                    Toast.LENGTH_LONG).show();

                            adapter.notifyDataSetChanged();
                            progressBar3.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pharmacie,menu);

        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}