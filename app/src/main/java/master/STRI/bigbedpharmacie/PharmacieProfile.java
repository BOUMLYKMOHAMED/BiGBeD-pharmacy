package master.STRI.bigbedpharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PharmacieProfile extends AppCompatActivity {



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

}