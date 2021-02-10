package master.STRI.bigbedpharmacie.Medicament;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import master.STRI.bigbedpharmacie.R;

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
        holder.Description.setText(medicament.getDescription());
    }

    @Override
    public int getItemCount() {
        return listmed.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView NomMed,Description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NomMed =(TextView)itemView.findViewById(R.id.nomMed);
            Description =(TextView)itemView.findViewById(R.id.description);

        }

    }
}