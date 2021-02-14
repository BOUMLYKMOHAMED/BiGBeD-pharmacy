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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import master.STRI.bigbedpharmacie.R;
import master.STRI.bigbedpharmacie.pharmacie.updateService;

public class claseUpdateMedicamentAdapter extends RecyclerView.
        Adapter<claseUpdateMedicamentAdapter.ViewHolder> {
    private List<Med_Info> listmed;
    private Context context;
    private FirebaseFirestore firestore;

    public claseUpdateMedicamentAdapter(List<Med_Info> listmed, Context context) {
        this.listmed = listmed;
        this.context = context;
    }
    @NonNull
    @Override
    public claseUpdateMedicamentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medicamentitem,parent,false);
        return new claseUpdateMedicamentAdapter.ViewHolder(view);
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
            firestore=FirebaseFirestore.getInstance();
            type = (TextView) itemView.findViewById(R.id.type);
            Description = (TextView) itemView.findViewById(R.id.description);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.are_you_sure);
                    builder.setMessage(R.string.deleting_is);
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Med_Info medInfo=listmed.get(which);
                            String id=medInfo.getMedId();
                            /*
                            firestore.collection("Service").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(v.getContext(),R.string.deleting,Toast.LENGTH_LONG).show();
                                    context.startActivity(new Intent(v.getContext(), updateService.class));
                                }
                            });

                             */
                        }
                    });
                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog bui=builder.create();
                    bui.show();
                    return true;
                }
            });
        }


    }

}
