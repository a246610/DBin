package com.example.administrator.dbin.ClassLibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.dbin.Activity.MainActivity;
import com.example.administrator.dbin.Activity.ShowDataActivity;
import com.example.administrator.dbin.Adapter.DataAdapter;
import com.example.administrator.dbin.Adapter.RouteAdapter;
import com.example.administrator.dbin.Model.Data;
import com.example.administrator.dbin.Model.JsonDBin;
import com.example.administrator.dbin.Model.Route;
import com.example.administrator.dbin.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class AsyncData extends AsyncTask<Void,Void,JsonDBin>{

    private Activity context;
    private ProgressDialog dialog;
    private  View view;
    //private String httpURL = "http://192.168.78.1:8080/sendData";
    private String httpURL = "http://192.168.0.100:8080/sendData";
    private int codeProcess;

    String first_floor;
    String end_floor;
    String max_weight;

    public AsyncData(Activity context_, int codeProcess_, View view_) {
        this.context = context_;
        this.codeProcess = codeProcess_;
        this.view = view_;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.myPreferences,  Context.MODE_PRIVATE);
        first_floor = String.valueOf( sharedPreferences.getInt("floor_first", 1));
        end_floor = String.valueOf(sharedPreferences.getInt("floor_end", 1));
        max_weight = String.valueOf(sharedPreferences.getInt("floor_end", 1));

        httpURL += "/" + first_floor + "/" + end_floor + "/" + max_weight;

        dialog = new ProgressDialog(context);
        dialog.setTitle(R.string.str_progress_title);
        dialog.setMessage(context.getResources().getString(R.string.str_progress_message));
        dialog.show();
    }

    @Override
    protected JsonDBin doInBackground(Void... params) {

        try {
            URL url = new URL(httpURL);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonDBin jsonDBin = new Gson().fromJson(bufferedReader, JsonDBin.class);
            return jsonDBin;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

            return null;
    }

    @Override
    protected void onPostExecute(JsonDBin jsonDBin) {
        super.onPostExecute(jsonDBin);

        if (jsonDBin == null) return;
        int floor_flag;

        if (codeProcess == ShowDataActivity.CODE_DATA){

            List<Data> tmp = jsonDBin.getData();
            List<Data> data = new ArrayList<Data>();

            floor_flag = tmp.get(0).getId();
            data.add(new Data(floor_flag," ",-100));

            for (Data item : tmp){

                if (floor_flag != item.getId()){
                    floor_flag = item.getId();
                    data.add(new Data(floor_flag, "", -100));
                }
                data.add(item);
            }

            ListView listView = (ListView)view.findViewById(R.id.listViewData);
            DataAdapter adapter = new DataAdapter(context,R.layout.listview_content_row, data);
            listView.setAdapter(adapter);

        }
        else if (codeProcess == ShowDataActivity.CODE_ROUTE ){
            List<Route> tmp = jsonDBin.getRoute();
            if (!(tmp.isEmpty())){
                List<Route> route = new ArrayList<Route>();

                floor_flag = tmp.get(0).getId();
                route.add(new Route(floor_flag, " ", -100));

                for (Route item : tmp){

                    if (floor_flag != item.getId()){
                        floor_flag = item.getId();
                        route.add(new Route(floor_flag, "", -100));
                    }
                    route.add(item);
                }

                ListView listView = (ListView)view.findViewById(R.id.listViewRoute);
                RouteAdapter adapter = new RouteAdapter(context, R.layout.listview_content_row, route);
                listView.setAdapter(adapter);
            }
        }

        dialog.dismiss();
    }
}
