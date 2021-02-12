package master.STRI.bigbedpharmacie.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private EditText searchMed;
    private ImageView search,map,callMe;
    private RecyclerView recycleViewMedicament;
    private double latitude,longitude;

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


        String pharmacyId=pharmacy.getParmacieId();
        texSetnom.setText(pharmacy.getFullName());
        search=(ImageView)findViewById(R.id.search);
        map=(ImageView)findViewById(R.id.maps);
        callMe=(ImageView)findViewById(R.id.callMe);
        map.setOnClickListener(this);
        search.setOnClickListener(this);
        searchMed=(EditText)findViewById(R.id.searchMed);


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
                    progressBar3=(ProgressBar)findViewById(R.id.progressBar3);
                    progressBar3.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.search:

                // a faire

                break;
            case R.id.maps:
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:"+latitude+","+longitude));
                Intent choiser=intent.createChooser(intent,"Launch Maps");
                startActivity(choiser);
                break;

        }

    }
}