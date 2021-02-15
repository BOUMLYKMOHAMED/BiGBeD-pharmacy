package master.STRI.bigbedpharmacie.pharmacie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import master.STRI.bigbedpharmacie.Medicament.Med_Info;
import master.STRI.bigbedpharmacie.Medicament.Medicament_Adapter;
import master.STRI.bigbedpharmacie.Medicament.claseUpdateMedicamentAdapter;
import master.STRI.bigbedpharmacie.R;

public class updateService extends AppCompatActivity {

    private RecyclerView updateservice;
    private RecyclerView.Adapter adapter;
    private List<Med_Info> listmed;
    private FirebaseFirestore fstore;
    private FirebaseAuth firebaseAuth;
    private  ProgressBar progressBar6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_service);

        fstore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        updateservice=(RecyclerView)findViewById(R.id.updateservice);
        updateservice.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        listmed=new ArrayList<>();
        updateservice.setHasFixedSize(true);
        adapter=new claseUpdateMedicamentAdapter(listmed,getBaseContext());
        progressBar6=(ProgressBar)findViewById(R.id.progressBar6);
        progressBar6.setVisibility(View.VISIBLE);
        updateservice.setAdapter(adapter);
        String userid=firebaseAuth.getCurrentUser().getUid();

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
                        String idM=d.getId();

                        med.setDescription(des);
                        med.setName(name);
                        med.setId(id);
                        med.setMedId(idM);
                        if (d.get("isMedicament")!=null){
                            med.setType(true);
                        }
                        else{
                            med.setType(false);

                        }
                        if (med.getId().equals(userid)){
                            listmed.add(med);
                        }

                    }
                    adapter.notifyDataSetChanged();
                    progressBar6.setVisibility(View.GONE);
                }
            }
        });
    }
}