package com.qzl.criminalintent.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.qzl.criminalintent.R;
import com.qzl.criminalintent.bean.Crime;
import com.qzl.criminalintent.fragment.CrimeFragment;
import com.qzl.criminalintent.fragment.CrimeListFragment;

/**
 * Crime对象的展示fragment
 * Created by Qzl on 2016-09-04.
 */
public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks ,CrimeFragment.Callbacks{

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment() ;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCreameSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null){
            Intent intent = CrimePagerActivity.newIntent(this,crime.getId());
            startActivity(intent);
        }else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container,newDetail).commit();
        }
    }

    //更新crime条目
    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
