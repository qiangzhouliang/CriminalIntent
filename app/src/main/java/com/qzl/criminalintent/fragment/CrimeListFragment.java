package com.qzl.criminalintent.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.qzl.criminalintent.R;
import com.qzl.criminalintent.bean.Crime;
import com.qzl.criminalintent.bean.CrimeLab;

import java.util.List;

/**
 * 创建展示crime对象的fragment
 * Created by Qzl on 2016-09-04.
 */
public class CrimeListFragment extends Fragment {
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private Crime mCrime;

    private boolean mSubtitleVisible;//记录子标题状太
    private Callbacks mCallbacks;
    private List<Crime> mCrimes;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置知道CrimeListFragment需接受选项菜单方法回调
        setHasOptionsMenu(true);
    }

    //创建view试图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycle_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        updateUI();
        return view;

    }

    //保存子标题的状太值
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        //根据子标题状太设置他的标题信息
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //新建条目菜单
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                /*Intent intent = CrimePagerActivity.newIntent(getActivity(),crime.getId());
                startActivity(intent);*/
                updateUI();
                mCallbacks.onCreameSelected(crime);
                return true;
            //显示条目菜单
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                //更新菜单项
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //更新条目数量
    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        //处理单复数问题（这儿手机的语言要设置为英文，因为中文没有复数语法）
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural,crimeCount,crimeCount);
        //实现菜单项标题和子标题的联动
        if (!mSubtitleVisible){
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }
    //更新UI
    public void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        mCrimes = crimeLab.getCrimes();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(getActivity(), mCrimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else {
            //刷新adapter，这样是对可见的item整个刷新，效率低，修改应该刷新指定条目
            mAdapter.setCrimes(mCrimes);
            mAdapter.notifyDataSetChanged();
        }
        //显示最新状太
        updateSubtitle();
    }

    //创建viewHolder内部类
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        public CrimeHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //实现条目的长按监听事件
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteCrime();
                    return true;
                }
            });
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
            mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        //做相应的处理操作
                        System.out.println("true");
                    }else {
                        System.out.println("false");
                    }
                    mCrime.setSolved(isChecked);
                }
            });
        }

        //数据和控件做绑定的方法
        public void bindCrime(final Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(crime.getDate().toString());
            mSolvedCheckBox.setChecked(crime.isSolved());

        }

        @Override
        public void onClick(View v) {
            //启动CrimeActivity
            /*Intent intent = CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);*/
            mCallbacks.onCreameSelected(mCrime);
        }
    }

    private void deleteCrime() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("确定要删除吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CrimeLab.get(getActivity()).deleteCrime(mCrime);
                //这个和下面的比更耗内存
                //mAdapter.setCrimes(CrimeLab.get(getActivity()).getCrimes());
                // mAdapter.notifyDataSetChanged();
                mCrimes.remove(mCrime);
                mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //创建adapter内部类
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;
        private Context mContext;

        public CrimeAdapter(Context context, List<Crime> crimes) {
            mCrimes = crimes;
            mContext = context;
        }

        //创建试图
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_crime, parent, false);

            return new CrimeHolder(view);
        }

        //绑定数据
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            mCrime = mCrimes.get(position);
            holder.bindCrime(mCrime);
        }

        //获取条目总数
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        public void setCrimes(List<Crime> crimes){
            mCrimes = crimes;
        }
    }

    //添加接口回调
    public interface Callbacks{
        void onCreameSelected(Crime crime);
    }
}
