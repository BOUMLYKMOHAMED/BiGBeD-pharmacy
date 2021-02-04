package master.STRI.bigbedpharmacie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EspaceActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView client,pharmacy,seconnecter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace);

        client=(TextView)findViewById(R.id.Espace_client);
        client.setOnClickListener(this);
        pharmacy=(TextView)findViewById(R.id.Espace_pharmacie);
        pharmacy.setOnClickListener(this);
        seconnecter=(TextView)findViewById(R.id.ESe_connecter);
        seconnecter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Espace_client:
                startActivity(new Intent(this,RegisterClient.class));
                break;
            case R.id.Espace_pharmacie:
                startActivity(new Intent(this,RegisterPharmacie.class));
                break;
            case R.id.ESe_connecter:
                startActivity(new Intent(this,MainActivity.class));
        }
    }
}