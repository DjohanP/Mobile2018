package com.example.mbpadmin.sqllite;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Base64;
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
import com.williamww.silkysignature.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignaturePredictAbsentFragment extends Fragment {

    private SignaturePad mSignaturePad;
    private Button mClearButton,mSendButton;
    protected static String PREDICT_URL = "http://etc.if.its.ac.id/signin_TTD/";
    private String nrp,password;
    private String image_base64;
    private int adaTTD=0;
    private EditText etNRP,etPassword;
    private double latKelas=0,lngKelas=0;
    private double lat,lng;
    private String idAgenda="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Predict Signature");
        View view=inflater.inflate(R.layout.activity_send_ttd,container,false );
        init();
        mSendButton=(Button)view.findViewById(R.id.btn_capture);
        mClearButton=(Button)view.findViewById(R.id.btn_clear);
        mSendButton.setEnabled(false);
        mClearButton.setEnabled(false);

        etNRP=(EditText) view.findViewById(R.id.nrpPrediksi);
        etPassword=(EditText)view.findViewById(R.id.passwordMhsPrediksi);

        mSignaturePad = (SignaturePad) view.findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Toast.makeText(getContext(), "OnStartSigning", Toast.LENGTH_SHORT).show();
                adaTTD=1;
            }

            @Override
            public void onSigned() {
                //mSaveButton.setEnabled(true);
                mSendButton.setEnabled(true);
                mClearButton.setEnabled(true);
                //mCompressButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                adaTTD=0;
                //mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
                mSendButton.setEnabled(false);
                //mCompressButton.setEnabled(false);
            }
        });



        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
                adaTTD=0;
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
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
                if(adaTTD==0)
                {
                    Toast.makeText(getContext(),"Belum Ada TTD",Toast.LENGTH_SHORT).show();
                    checkError=1;
                }
                if(checkError==1)
                {
                    return;
                }
                nrp=etNRP.getText().toString();
                password=etPassword.getText().toString();


                Bitmap result = mSignaturePad.getSignatureBitmap();
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                byte[] byteArrayImage;


                result.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byteArrayImage = baos.toByteArray();
                image_base64 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

                //Toast.makeText(getContext(),image_base64,Toast.LENGTH_LONG).show();
                postData();
                mSignaturePad.clear();
                adaTTD=0;
            }
        });

        return view;
    }

    protected void init() {
        //nrp="05111540000067";
        //password="123456";
        latKelas=this.getArguments().getDouble("latKelas");
        lngKelas=this.getArguments().getDouble("lngKelas");
        lat=this.getArguments().getDouble("latCurrent");
        lng=this.getArguments().getDouble("lngCurrent");
        idAgenda=getArguments().getString("idAgenda");
    }

    public void postData(){
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, PREDICT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("Responnya Adalah",response);
                        //Toast.makeText(getContext(),"Response Server "+response,Toast.LENGTH_SHORT).show();
                        if(response.contains("ACCEPTED"))
                        {
                            Toast.makeText(getContext(),"Signature Accepted",Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(),"Terimakasih Anda Telah Absen Hari Ini",Toast.LENGTH_LONG).show();
                            Fragment fragment= null;
                            try {
                                fragment = (Fragment) CameraFragment.class.newInstance();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (java.lang.InstantiationException e) {
                                e.printStackTrace();
                            }

                            /*Bundle extras = new Bundle();
                            extras.putDouble("latCurrent",lat);
                            extras.putDouble("lngCurrent",lng);
                            extras.putDouble("latKelas",latKelas);
                            extras.putDouble("lngKelas",lngKelas);
                            extras.putString("idAgenda",idAgenda);

                            fragment.setArguments(extras);*/

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                        }
                        else
                        {
                            Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(), "erorrnya ini" + volleyError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Adding parameters
                //params.put("imagefile", "data:image/jpeg;base64,"+image);
                params.put("idUser",nrp);
                params.put("password",password);
                params.put("image","data:image/jpeg;base64,"+image_base64);
                params.put("nrp", nrp);
                params.put("Lat",String.valueOf(latKelas));
                params.put("Lon",String.valueOf(lngKelas));
                params.put("idAgenda",String.valueOf(idAgenda));


                //Log.d("Kirim Sesuatu",image);

                //returning parameters
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
