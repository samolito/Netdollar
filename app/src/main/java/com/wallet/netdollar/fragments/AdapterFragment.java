package com.wallet.netdollar.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AdapterFragment extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private Context context;

    public AdapterFragment(FragmentManager fm, Context context) {
        super(fm);

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        // return AllFragment.newInstance(position +1);
        switch (position) {
            case 0:
                return Accueil_fragment.newInstance(position + 1);
            case 1:
                return Transfert_fragment.newInstance(position+1);
            case 2:
                return Payment_fragment.newInstance(position+1);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return tabTitles[position];
        switch (position) {
            case 0:
                return "ACCUEIL";
            case 1:
                return "FACTURE";
            case 2:
                return "PAYEZ";
        }
        return null;
    }



}
