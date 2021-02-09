package master.STRI.bigbedpharmacie.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import master.STRI.bigbedpharmacie.R;
import master.STRI.bigbedpharmacie.client.adapterRecycleview;
import master.STRI.bigbedpharmacie.pharmacie.Pharmacy_info;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Pharmacy_info> listpharmacie;
    private FirebaseFirestore fstore;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        fstore=FirebaseFirestore.getInstance();
        recyclerView=(RecyclerView)root.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listpharmacie=new ArrayList<>();

        adapter=new adapterRecycleview(listpharmacie,getContext());
        recyclerView.setAdapter(adapter);

        fstore.collection("Pharmacies").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot:list){
                        Pharmacy_info pharmacie=documentSnapshot.toObject(Pharmacy_info.class);
                        boolean status = (boolean)documentSnapshot.get("status");
                        String email=(String)documentSnapshot.get("Email");
                        pharmacie.setPstatus(status);
                        pharmacie.setEmail(email);
                        listpharmacie.add(pharmacie);
                    }
                    adapter.notifyDataSetChanged();
                }

            }
        });
        return root;
    }
}