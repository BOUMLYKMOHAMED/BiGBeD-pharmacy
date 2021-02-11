package master.STRI.bigbedpharmacie.pharmacie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import master.STRI.bigbedpharmacie.R;

public class StatusFragment extends Fragment {

    private FirebaseFirestore fstore;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar2;

    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_status, container, false);
        fstore=FirebaseFirestore.getInstance();
        progressBar2=(ProgressBar)view.findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.VISIBLE);
        fAuth=FirebaseAuth.getInstance();
        TextView On=(TextView)view.findViewById(R.id.FOn);
        TextView Off=(TextView)view.findViewById(R.id.FOff);
        TextView textStatus=(TextView)view.findViewById(R.id.textStatus);
        String userid=fAuth.getCurrentUser().getUid();



        fstore.collection("Users").document(userid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        boolean status = (boolean)documentSnapshot.get("status");
                        if (status){
                            On.setBackgroundResource(R.color.ouvert);
                            Off.setBackgroundResource(R.color.fermee);
                            textStatus.setText(R.string.l_agence_est_ouvert);
                            progressBar2.setVisibility(View.INVISIBLE);
                        }
                        else{
                            Off.setBackgroundResource(R.color.ouvert);
                            On.setBackgroundResource(R.color.fermee);
                            textStatus.setText(R.string.l_agence_est_fermer);
                            progressBar2.setVisibility(View.INVISIBLE);

                        }
                    }
                });




        On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setVisibility(View.VISIBLE);
                On.setBackgroundResource(R.color.ouvert);
                Off.setBackgroundResource(R.color.fermee);
                textStatus.setText(R.string.l_agence_est_ouvert);
                fstore.collection("Users").document(userid)
                        .update("status",true)
                        .addOnCompleteListener(task -> {
                            progressBar2.setVisibility(View.INVISIBLE);
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(),getText(R.string.message_update).toString(),Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
            }
        });
        Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setVisibility(View.VISIBLE);
                Off.setBackgroundResource(R.color.ouvert);
                On.setBackgroundResource(R.color.fermee);
                textStatus.setText(R.string.l_agence_est_fermer);
                String userid=fAuth.getCurrentUser().getUid();
                fstore.collection("Users").document(userid)
                        .update("status",false)
                        .addOnCompleteListener(task -> {
                            progressBar2.setVisibility(View.INVISIBLE);
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(),getText(R.string.message_update).toString(),Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

            }
        });
        return view;
    }
}