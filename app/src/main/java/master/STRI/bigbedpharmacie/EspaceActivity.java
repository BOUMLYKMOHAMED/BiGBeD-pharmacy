package master.STRI.bigbedpharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import master.STRI.bigbedpharmacie.client.RegisterClient;
import master.STRI.bigbedpharmacie.pharmacie.RegisterPharmacie;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.langage:
                Intent intent=new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case R.id.about_us:
                startActivity(new Intent(this,AboutUsActivity.class));
                break;
            case R.id.help_me:
                startActivity(new Intent(this,helpMe.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Espace_client:
                startActivity(new Intent(this, RegisterClient.class));
                break;
            case R.id.Espace_pharmacie:
                startActivity(new Intent(this, RegisterPharmacie.class));
                break;
            case R.id.ESe_connecter:
                startActivity(new Intent(this,MainActivity.class));
        }
    }
}