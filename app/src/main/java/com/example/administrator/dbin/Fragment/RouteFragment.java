package com.example.administrator.dbin.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.dbin.Activity.ShowDataActivity;
import com.example.administrator.dbin.ClassLibrary.AsyncData;
import com.example.administrator.dbin.R;

public class RouteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View routeFragment = inflater.inflate(R.layout.route_fragment, container, false);

        AsyncData asyncData = new AsyncData(getActivity(), ShowDataActivity.CODE_ROUTE,routeFragment);
        asyncData.execute();

        return routeFragment;
    }

}
