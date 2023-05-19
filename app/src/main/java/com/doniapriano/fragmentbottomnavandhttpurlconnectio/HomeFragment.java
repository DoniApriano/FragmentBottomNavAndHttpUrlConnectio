package com.doniapriano.fragmentbottomnavandhttpurlconnectio;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    List<ModelWarDetails> listWarDetails=new ArrayList<>();
    RecyclerViewAdapter adapter;
    LinearLayoutManager llm;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView= view.findViewById(R.id.rv_data);

        llm=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        new FetchWarsInfo().execute();
    }

    public class FetchWarsInfo extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;
        static final String URL_STRING = "https://blogurl-3f73f.firebaseapp.com/";
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait. Fetching data..");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            response = creatingURLConnection(URL_STRING);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Connection successful.",Toast.LENGTH_SHORT).show();

            try{
                if(response!=null && !response.equals("")){
                    JSONArray responseArray = new JSONArray(response);
                    if(responseArray.length()>0){
                        for(int i=0;i<responseArray.length();i++){
                            JSONObject battleObj = responseArray.getJSONObject(i);
                            ModelWarDetails modelWarDetails = new ModelWarDetails();
                            modelWarDetails.setAttacker_king(battleObj.optString("attacker_king"));
                            modelWarDetails.setName(battleObj.optString("name"));
                            modelWarDetails.setDefender_king(battleObj.optString("defender_king"));
                            modelWarDetails.setLocation(battleObj.optString("location"));
                            listWarDetails.add(modelWarDetails);

                        }
                        adapter = new RecyclerViewAdapter(getActivity(), listWarDetails);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }else {
                    Toast.makeText(getActivity(),
                            "Error in fetching data.",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String creatingURLConnection (String GET_URL) {
        String response = "";
        HttpURLConnection conn ;
        StringBuilder jsonResults = new StringBuilder();
        try {
            URL url = new URL(GET_URL);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
            response = jsonResults.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
}