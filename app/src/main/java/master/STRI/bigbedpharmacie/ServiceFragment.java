package master.STRI.bigbedpharmacie;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ServiceFragment extends Fragment {



    public ServiceFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_service, container, false);


        TextView On=(TextView)view.findViewById(R.id.FOn);
        TextView Off=(TextView)view.findViewById(R.id.FOff);
        TextView textStatus=(TextView)view.findViewById(R.id.textStatus);
/*
        On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On.setBackgroundResource(R.color.ouvert);
                //Off.setBackgroundResource(R.color.fermee);
                textStatus.setText(R.string.l_agence_est_ouvert);
            }
        });
        Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Off.setBackgroundResource(R.color.ouvert);
                //On.setBackgroundResource(R.color.fermee);
                textStatus.setText(R.string.l_agence_est_fermer);
            }
        });

 */

        return view;
    }

}
