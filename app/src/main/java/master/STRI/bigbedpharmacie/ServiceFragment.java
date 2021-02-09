package master.STRI.bigbedpharmacie;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class ServiceFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore fstore;

    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service, container, false);

        fstore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        EditText ajouterMed = (EditText) view.findViewById(R.id.ajouterMedicament);
        EditText ajouterMedDes = (EditText) view.findViewById(R.id.ajouterMedicamentDescription);
        EditText ajouterSer = (EditText) view.findViewById(R.id.ajouterService);
        EditText ajouterSerDes = (EditText) view.findViewById(R.id.ajouterServiceDescription);
        TextView ajouterBMed = (TextView) view.findViewById(R.id.AjouterBMedicament);
        TextView ajouterBSer = (TextView) view.findViewById(R.id.AjouterBService);
        ajouterBMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = ajouterMed.getText().toString();
                String desc = ajouterMedDes.getText().toString();

                if (Name.isEmpty()) {
                    ajouterMed.setError(getString(R.string.medicamen_empty));
                    ajouterMed.requestFocus();
                    return;
                }
                if (desc.isEmpty()) {
                    ajouterMedDes.setError(getString(R.string.Description_empty));
                    ajouterMedDes.requestFocus();
                    return;
                }


                String userId = auth.getCurrentUser().getUid();
                CollectionReference documentReference = fstore.collection("Medicaments");
                Map<String, Object> client = new HashMap<>();
                client.put("Description", desc);
                client.put("Medicament_Name", Name);
                client.put("Pharmacie", userId);
                documentReference.add(client).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), getText(R.string.medicament_ajouter), Toast.LENGTH_LONG).show();
                        ajouterMed.getText().clear();
                        ajouterMedDes.getText().clear();
                    }
                });
            }
        });
        ajouterBSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = ajouterSer.getText().toString();
                String desc = ajouterSerDes.getText().toString();

                if (Name.isEmpty()) {
                    ajouterSer.setError(getString(R.string.service_empty));
                    ajouterSer.requestFocus();
                    return;
                }
                if (desc.isEmpty()) {
                    ajouterSerDes.setError(getString(R.string.Description_empty));
                    ajouterSerDes.requestFocus();
                    return;
                }
                String userId = auth.getCurrentUser().getUid();
                CollectionReference documentReference = fstore.collection("Services");
                Map<String, Object> client = new HashMap<>();
                client.put("Description", desc);
                client.put("Service_Name", Name);
                client.put("Pharmacie", userId);
                documentReference.add(client).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), getText(R.string.service_ajouter), Toast.LENGTH_LONG).show();
                        ajouterSer.getText().clear();
                        ajouterSerDes.getText().clear();
                    }
                });
            }
        });

        return view;
    }
}