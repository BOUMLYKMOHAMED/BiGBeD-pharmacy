package master.STRI.bigbedpharmacie.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import master.STRI.bigbedpharmacie.R;
import master.STRI.bigbedpharmacie.pharmacie.Pharmacy_info;


public class adapterRecycleview extends RecyclerView.Adapter<adapterRecycleview.ViewHolder> {

    private List<Pharmacy_info> listpharmacie;
    private Context context;

    public adapterRecycleview(List<Pharmacy_info> listpharmacie, Context context) {
        this.listpharmacie = listpharmacie;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itempharmacie,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pharmacy_info pharmacie=listpharmacie.get(position);
        holder.textNom.setText(pharmacie.getFullName());
        holder.textemail.setText(pharmacie.getEmail());
        holder.villepharmaacie.setText(pharmacie.getVille());
        holder.imageicon.setImageResource(R.drawable.logo);
        if (pharmacie.getPstatus()){
            holder.imageactive.setImageResource(R.drawable.ic_active);
            holder.textactive.setText(R.string.active);
        }
        else{
            holder.imageactive.setImageResource(R.drawable.inactive);
            holder.textactive.setText(R.string.inactive);
        }

    }

    @Override
    public int getItemCount() {
        return listpharmacie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

       public TextView textNom,textemail,textactive,villepharmaacie;
        public ImageView imageicon,imageactive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNom =(TextView)itemView.findViewById(R.id.nomPharmacie);
            textemail =(TextView)itemView.findViewById(R.id.emailPharmacie);
            textactive =(TextView)itemView.findViewById(R.id.textActive);
            imageicon =(ImageView)itemView.findViewById(R.id.imageIcon);
            imageactive =(ImageView)itemView.findViewById(R.id.activeImage);
            villepharmaacie =(TextView)itemView.findViewById(R.id.villePharmacie);
        }
    }
}
