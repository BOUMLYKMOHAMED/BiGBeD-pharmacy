package master.STRI.bigbedpharmacie.pharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import master.STRI.bigbedpharmacie.AboutUsActivity;
import master.STRI.bigbedpharmacie.R;

public class PharmacieProfile extends AppCompatActivity {



    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacie_profile);
        ViewPager2 viewPager2=(ViewPager2) findViewById(R.id.viewpager2);
        viewPager2.setAdapter(new FragmentAdapter(this));
        TabLayout tabLayout=(TabLayout) findViewById(R.id.tablayout);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch(position){
                    case 0:{
                        //tab.setText(R.string.messanger);
                        tab.setIcon(R.drawable.ic_chat);
                        break;
                    }
                    case 1:{
                        //tab.setText(R.string.ajouter_un_service);
                        tab.setIcon(R.drawable.ic_medical_services);
                        break;
                    }
                    case 2:{
                        //tab.setText(R.string.status);
                        tab.setIcon(R.drawable.ic__status);
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pharmacie,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.langage:

                //// a faire

                break;

            case R.id.aide:

                // a faire

                break;

            case R.id.profile:

                // a faire

                break;

            case R.id.log_out:


                 // a faire

                break;

            case R.id.about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}