package com.example.mbpadmin.sqllite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TrainFragment extends Fragment {

    private EditText etNRP,etPassword;
    private String nrp="";
    private String password="";

    private Button btnTrain;
    protected static String TRAIN_URL = "http://etc.if.its.ac.id/doTrain/";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Train");
        View view=inflater.inflate(R.layout.activity_train,container,false );

        etNRP=(EditText) view.findViewById(R.id.nrptrain);
        etPassword=(EditText)view.findViewById(R.id.passwordMhstrain);

        NavigationView nvDrawer=(NavigationView) getActivity().findViewById(R.id.nvView);
        nvDrawer.setCheckedItem(R.id.nav_train);

        btnTrain=(Button)view.findViewById(R.id.btn_train);
        btnTrain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int checkError=0;
                    if(TextUtils.isEmpty(etNRP.getText().toString()))
                    {
                        etNRP.setError("NRP Wajib Diisi");
                        checkError=1;
                    }
                    if(TextUtils.isEmpty(etPassword.getText().toString()))
                    {
                        etPassword.setError("Password Wajib Diisi");
                        checkError=1;
                    }
                    if(checkError==1)
                    {
                        return;
                    }
                    nrp=etNRP.getText().toString();
                    password=etPassword.getText().toString();
                    postData();
                }
            }
        );
        return view;
    }


    public void postData(){
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, TRAIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Responnya Adalah",response);
                        Toast.makeText(getContext(),"Sukses Upload "+response,Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(),volleyError.toString(),Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idUser",nrp);
                params.put("password",password);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
