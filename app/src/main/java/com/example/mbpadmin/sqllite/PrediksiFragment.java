package com.example.mbpadmin.sqllite;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static com.android.volley.VolleyLog.TAG;

public class PrediksiFragment extends Fragment {
    private String judul;
    private String nrp;
    private int counter=0;
    private List<String> listPathFile;
    private ArrayList<String> encodedImagesList;
    private TextView tvHint;
    private CameraKitEventListener cameraListener;
    private CameraView camerad;
    private Button btnCapture;
    private static String BASE_DIR = "camtest/";
    protected static String PREDICT_URL = "http://etc.if.its.ac.id/signin/";
    private int requestCounter = 0;
    private boolean hasRequestFailed = false;
    private String res="";
    private double latKelas=0,lngKelas=0;
    private double lat,lng;
    private String idAgenda="";
    private String password;
    private EditText etNRP,etPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle("Prediksi");
        View view=inflater.inflate(R.layout.activity_predict,container,false );
        init();
        etNRP=(EditText) view.findViewById(R.id.nrpPrediksi);
        etPassword=(EditText)view.findViewById(R.id.passwordMhsPrediksi);

        cameraListener=new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                if (pictureFile == null) {
                    Log.d("cek", "Error creating media file, check storage permissions.");
                    return;
                }
                try {
                    FileOutputStream outStream = new FileOutputStream(pictureFile);
                    byte[] picture = cameraKitImage.getJpeg();
                    Bitmap result = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                    result = Bitmap.createScaledBitmap(result, 512,512, true);
                    result.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();

                    //counter++;
                    //if (counter >=1) {
                    //showLoadingDialog();
                    //getEncodedImage();
//                        closeLoadingDialog();
//                        showSuccessDialog();
                    if (getEncodedImage()) {
                        postData();
                        Log.d("Foto","benar");
                        //  closeLoadingDialog();
                        //showSuccessDialog();
                    }
                    else
                    {
                        Log.d("Foto","salah");
                    }
                    //}
                    //}
                    //else showHintDialog();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.d("Eroorr","TRY AWAL");
                    Toast.makeText(getContext(),"TRY AWAL",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Eroorr","TRY AWAL2");
                    Toast.makeText(getContext(),"TRY AWAL2",Toast.LENGTH_SHORT).show();
                } finally {

                }
            }
            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        };
        camerad = (CameraView)view.findViewById(R.id.camerad);
        camerad.addCameraKitListener(cameraListener);

        btnCapture = (Button)view.findViewById(R.id.btn_capture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
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
                //Toast.makeText(getContext(),"Klik Ini",Toast.LENGTH_SHORT).show();
                camerad.captureImage();
                //RequestQueue requestQueue=Volley.newRequestQueue(getContext());
                //JsonObjectRequest jsonObjectRequest=new
            }
        });
        return view;
        //return inflater.inflate(R.layout.fragment_prediksi,container,false );
    }

    @Override
    public void onResume() {
        super.onResume();
        camerad.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        camerad.stop();
    }

    protected void init() {
        //nrp="05111540000067";
        //password="123456";
        latKelas=this.getArguments().getDouble("latKelas");
        lngKelas=this.getArguments().getDouble("lngKelas");
        lat=this.getArguments().getDouble("latCurrent");
        lng=this.getArguments().getDouble("lngCurrent");
        idAgenda=getArguments().getString("idAgenda");

        encodedImagesList = new ArrayList<>();
        listPathFile = new ArrayList<>();
    }

    protected File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), BASE_DIR + nrp);

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("tag", "sikemas: failed to create directory");
                return null;
            }
        }

        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            Random rand = new Random();
            int  n = rand.nextInt(500) + 1;
            int x = rand.nextInt(1500) + 1;
            String filepath = mediaStorageDir.getPath() + File.separator  +
                    nrp +"_"+x +"_" + counter +"_"+n+".png";
            listPathFile.add(filepath);
            mediaFile = new File(filepath);
        } else {
            return null;
        }

        return mediaFile;
    }

    protected boolean getEncodedImage() {
        //loadingDialog.setTitleText("Encoding images");

        Bitmap image;
        ByteArrayOutputStream baos;
        byte[] byteArrayImage;
        String image_base64;

        for (String imagepath : listPathFile) {
            image = BitmapFactory.decodeFile(imagepath);
            baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byteArrayImage = baos.toByteArray();
            image_base64 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            encodedImagesList.add(image_base64);
        }

        return true;
    }

    public void postData(){
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, PREDICT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Responnya Adalah",response);
                        requestCounter--;

                        /*if (requestCounter == 0 && !hasRequestFailed) {
                            //closeLoadingDialog();
                            //showSuccessDialog();
                            Toast.makeText(getContext(),"Sukses Upload "+response,Toast.LENGTH_SHORT).show();
                        }*/
                        Toast.makeText(getContext(),"Sukses Upload "+response,Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hasRequestFailed = true;
                        //Log.e ("rediksi", new String(volleyError.networkResponse.data));
                        //Showing toast
                        //Toast.makeText(getContext(), "Error",
                          //      Toast.LENGTH_LONG).show();
                        //closeLoadingDialog();
                        //showSuccessDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Get encoded Image
                String image = encodedImagesList.get(0);
                Map<String, String> params = new HashMap<>();
                // Adding parameters
                //params.put("imagefile", "data:image/jpeg;base64,"+image);
                params.put("idUser",nrp);
                params.put("password",password);
                params.put("image","data:image/jpeg;base64,"+image);
                params.put("nrp", nrp);
                params.put("Lat",String.valueOf(latKelas));
                params.put("Lon",String.valueOf(lngKelas));
                params.put("idAgenda",String.valueOf(idAgenda));


                Log.d("Kirim Sesuatu",image);

                //returning parameters
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}
