package com.example.administrator.dbin.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.dbin.Activity.ShowDataActivity;
import com.example.administrator.dbin.ClassLibrary.AsyncData;
import com.example.administrator.dbin.Model.Data;
import com.example.administrator.dbin.Model.JsonDBin;
import com.example.administrator.dbin.R;

import java.util.List;

public class DataFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View dataFragment = inflater.inflate(R.layout.data_fragment, container, false);

        AsyncData asyncData = new AsyncData(getActivity(),ShowDataActivity.CODE_DATA,dataFragment);
        asyncData.execute();

        return dataFragment;
    }
}
