package master.STRI.bigbedpharmacie;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;

public class Menu_bar extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
         drawerLayout=findViewById(R.id.nav_bar);
        Toolbar toolbar=findViewById(R.id.toolbar);
        ActionBarDrawerToggle action=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.ajouter,R.string.app_name);
        drawerLayout.addDrawerListener(action);
        action.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }
}