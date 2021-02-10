package master.STRI.bigbedpharmacie.pharmacie;

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

public class ChatFragment extends Fragment {

    public ChatFragment() {
        // Required empty public constructor
    }


    private TextView setename,setemail,settele,setlatitude,setlongitude,setville;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    private ProgressBar progressBar5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        TextView textView=(TextView)view.findViewById(R.id.text);
        setename=(TextView)view.findViewById(R.id.setNom);
        setemail=(TextView)view.findViewById(R.id.setemail);
        settele=(TextView)view.findViewById(R.id.setTele);
        setlatitude=(TextView)view.findViewById(R.id.setlatitude);
        setlongitude=(TextView)view.findViewById(R.id.setlongitude);
        setville=(TextView)view.findViewById(R.id.setville);
        progressBar5=(ProgressBar) view.findViewById(R.id.progressBar5);

        progressBar5.setVisibility(View.VISIBLE);
        firebaseAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        String userid=firebaseAuth.getCurrentUser().getUid();
        fstore.collection("Pharmacies").document(userid)
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
            }
        });
        return view;
    }
}