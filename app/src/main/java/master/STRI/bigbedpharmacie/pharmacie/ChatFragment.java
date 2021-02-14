package master.STRI.bigbedpharmacie.pharmacie;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import master.STRI.bigbedpharmacie.R;
import master.STRI.bigbedpharmacie.client.ClientProfile;

public class ChatFragment extends Fragment {

    public ChatFragment() {
        // Required empty public constructor
    }


    private TextView setename,setemail,settele,setlatitude,setlongitude,setville,updatePro,updateMedicamet;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    private Pharmacy_info pharmacy;
    private ProgressBar progressBar5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        setename=(TextView)view.findViewById(R.id.setNom);
        setemail=(TextView)view.findViewById(R.id.setemail);
        settele=(TextView)view.findViewById(R.id.setTele);
        setlatitude=(TextView)view.findViewById(R.id.setlatitude);
        setlongitude=(TextView)view.findViewById(R.id.setlongitude);
        setville=(TextView)view.findViewById(R.id.setville);
        progressBar5=(ProgressBar) view.findViewById(R.id.progressBar5);

        updatePro=(TextView)view.findViewById(R.id.updateprofile);
        updateMedicamet=(TextView)view.findViewById(R.id.updateMedicament);


        progressBar5.setVisibility(View.VISIBLE);
        firebaseAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        String userid=firebaseAuth.getCurrentUser().getUid();
        fstore.collection("Users").document(userid)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name= (String) documentSnapshot.get("fullName");
                String email= (String) documentSnapshot.get("Email");
                String phonr= (String) documentSnapshot.get("phone");
                String ville= (String) documentSnapshot.get("ville");
               double latitude= (double) documentSnapshot.get("latitude");
               double longitude= (double)documentSnapshot.get("longitude");
                setemail.setText(email);
                setename.setText(name);
                setlatitude.setText(Double.toString(latitude));
                setlongitude.setText(Double.toString(longitude));
                settele.setText(phonr);
                setville.setText(ville);
                progressBar5.setVisibility(View.GONE);
                pharmacy=new Pharmacy_info();
                pharmacy.setFullName(name);
                pharmacy.setEmail(email);
                pharmacy.setTelephone(phonr);
                pharmacy.setVille(ville);
                pharmacy.setLatitude(latitude);
                pharmacy.setLongitude(longitude);

            }
        });
        updatePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),updatProfile.class);
                intent.putExtra("name",pharmacy.getFullName());
                intent.putExtra("email",pharmacy.getEmail());
                intent.putExtra("tele",pharmacy.getTelephone());
                intent.putExtra("ville",pharmacy.getVille());
                String latit=Double.toString(pharmacy.getLatitude());
                String longit=Double.toString(pharmacy.getLongitude());
                intent.putExtra("lati",latit);
                intent.putExtra("long",longit);
                startActivity(intent);
            }
        });

        return view;
    }
}