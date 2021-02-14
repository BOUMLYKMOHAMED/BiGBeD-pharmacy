package master.STRI.bigbedpharmacie.Medicament;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import master.STRI.bigbedpharmacie.R;
import master.STRI.bigbedpharmacie.pharmacie.updateService;

public class Medicament_Adapter extends RecyclerView.
        Adapter<master.STRI.bigbedpharmacie.Medicament.Medicament_Adapter.ViewHolder> {

    private List<Med_Info> listmed;
    private Context context;

    public Medicament_Adapter(List<Med_Info> listmed, Context context) {
        this.listmed = listmed;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medicamentitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Med_Info medicament=listmed.get(position);
        holder.NomMed.setText(medicament.getName());
        if (medicament.isType()){
            holder.type.setText(R.string.medicament);
        }
        else{
            holder.type.setText(R.string.service);
        }
        holder.Description.setText(medicament.getDescription());
    }

    @Override
    public int getItemCount() {
        return listmed.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView NomMed,Description,type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NomMed = (TextView) itemView.findViewById(R.id.nomMed);
            type = (TextView) itemView.findViewById(R.id.type);
            Description = (TextView) itemView.findViewById(R.id.description);
        }


    }
}