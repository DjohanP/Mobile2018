package com.example.mbpadmin.sqllite;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mbpadmin.sqllite.db.FotooEntity;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class CameraActivity2 extends AppCompatActivity {
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
    protected static String UPLOAD_URL = "http://etc.if.its.ac.id/sendImg/";
    private int requestCounter = 0;
    private boolean hasRequestFailed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        init();
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
                        uploadFIle();
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
                    Toast.makeText(CameraActivity2.this,"TRY AWAL",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Eroorr","TRY AWAL2");
                    Toast.makeText(CameraActivity2.this,"TRY AWAL2",Toast.LENGTH_SHORT).show();
                } finally {

                }
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        };
        camerad = (CameraView) findViewById(R.id.camerad);
        camerad.addCameraKitListener(cameraListener);

        btnCapture = (Button) findViewById(R.id.btn_capture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //camerad.captureImage();
                long lStartTime = System.nanoTime();
                FotooEntity fotooEntity=new FotooEntity();
                fotooEntity.setJudul(judul);
                fotooEntity.setFoto("wkwkwk");
                /*List<FotooEntity> fotooEntityList=MainActivity2.appDatabase.fotooDao().findCaptionWithJudul(judul);
                String hsl;
                if(fotooEntityList.isEmpty())
                {
                    hsl="Ya Tidak Ada";
                    Log.d("Hasilnyawkwkw","Kosong");
                }
                else
                {
                    hsl="Ya Ada";
                    Log.d("Hasilnyawkwkw","Ada");
                    String waktu="";
                    for(FotooEntity fotoo:fotooEntityList)
                    {
                        waktu=fotoo.getWaktu();
                    }
                }
                Toast.makeText(CameraActivity2.this,hsl,Toast.LENGTH_SHORT).show();*/
                Toast.makeText(CameraActivity2.this,"Upload",Toast.LENGTH_SHORT).show();
                camerad.captureImage();
                long lEndTime = System.nanoTime();
                long output = lEndTime - lStartTime;
                double seconds = (double)output / 1000000000.0;
                String outs=String.valueOf(seconds);
                fotooEntity.setWaktu(outs);
                MainActivity2.appDatabase.fotooDao().addFotoo(fotooEntity);
                Toast.makeText(CameraActivity2.this,"Selesai Upload "+outs+" s",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        camerad.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camerad.stop();
    }

    protected void init() {
        judul = this.getIntent().getStringExtra("Judul");
        nrp="05111540000067";
        //nrp="43";
        counter=this.getIntent().getIntExtra("position",0);
        listPathFile = new ArrayList<>();
        encodedImagesList = new ArrayList<>();
        tvHint = (TextView) findViewById(R.id.tvHint);
        tvHint.setText(judul);
        //Log.d("Judul",judul);
        //showHintDialog();
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

    protected void uploadFIle() {
        Log.d("Oke?","?");
        //loadingDialog.setTitleText("Uploading images");
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        requestCounter--;

                        if (requestCounter == 0 && !hasRequestFailed) {
                            //closeLoadingDialog();
                            //showSuccessDialog();
                            Toast.makeText(CameraActivity2.this,"Sukses Upload"+response,Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(CameraActivity2.this,"Sukses Upload"+response,Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hasRequestFailed = true;

                        //Showing toast
                        Toast.makeText(CameraActivity2.this, "erorrnya ini" + volleyError.getMessage(),
                                Toast.LENGTH_LONG).show();
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
                //params.put("imagefile", "data:image/jpeg;base64," + image);
                params.put("image", "data:image/jpeg;base64,"+image);
                params.put("idUser", nrp);
                params.put("password","123456");

                //returning parameters
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
