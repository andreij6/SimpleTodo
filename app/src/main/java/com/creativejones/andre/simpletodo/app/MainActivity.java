package com.creativejones.andre.simpletodo.app;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.creativejones.andre.simpletodo.R;
import com.creativejones.andre.simpletodo.databinding.ActivityMainBinding;
import com.creativejones.andre.simpletodo.viewmodels.TodoVM;

import icepick.Icepick;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;

    TodoVM mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mViewModel = savedInstanceState != null ? TodoVM.recreate(this, mBinding, savedInstanceState) : TodoVM.createSetup(this, mBinding);

        mBinding.setModel(mViewModel);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewModel.saveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}
