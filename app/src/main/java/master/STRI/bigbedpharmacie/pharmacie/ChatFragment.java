package master.STRI.bigbedpharmacie.pharmacie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import master.STRI.bigbedpharmacie.R;

public class ChatFragment extends Fragment {

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        TextView textView=(TextView)view.findViewById(R.id.text);
        return view;
    }
}