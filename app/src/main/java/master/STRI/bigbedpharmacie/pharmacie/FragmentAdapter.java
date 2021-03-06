package master.STRI.bigbedpharmacie.pharmacie;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import master.STRI.bigbedpharmacie.pharmacie.ChatFragment;
import master.STRI.bigbedpharmacie.pharmacie.ServiceFragment;
import master.STRI.bigbedpharmacie.pharmacie.StatusFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new ChatFragment();
            case 1:
                return new ServiceFragment();
            default:
                return new StatusFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
