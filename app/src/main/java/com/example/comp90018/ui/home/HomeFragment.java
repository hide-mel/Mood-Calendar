package com.example.comp90018.ui.home;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Build;

import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.comp90018.R;
import com.example.comp90018.databinding.FragmentHomeBinding;
import com.example.comp90018.ui.home.recface.FaceRecognition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class HomeFragment extends Fragment implements View.OnClickListener {

    public static final int CAMARA_REQUEST = 1;
    public static final int PHOTO_REQUEST = 2;
    private View root;
    private int layout = R.layout.fragment_home;
    private String mode;
    private Button selfie;
    private Button photo;
    private Button manual;
    private Button cont;
    private File tempFile;
    private Uri imageUri;
    private Activity activity;
//    private HomeViewModel model;
    private ImageView icon;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(layout,container,false);
//        model = new ViewModelProvider(this).get(HomeViewModel.class);
        mode = "selfie";
        selfie = root.findViewById(R.id.selfie_button);
        selfie.setOnClickListener(this);
        icon = root.findViewById(R.id.home_icon);

        photo = root.findViewById(R.id.photo_button);
        photo.setOnClickListener(this);
        manual = root.findViewById(R.id.manual_button);
        manual.setOnClickListener(this);

        cont = root.findViewById(R.id.continue_button);
        cont.setOnClickListener(this);
        activity = (Activity) root.getContext();


//        Button botton = root.findViewById((R.id.button));
//        botton.setOnClickListener(this);

        return root;


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.selfie_button:
                selfie.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_change_button_emotion) );
                photo.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_blank_button) );
                manual.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_blank_button) );
                mode = "selfie";
                icon.setBackgroundResource(R.drawable.smile_face);
                break;
            case R.id.photo_button:
                photo.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_change_button_emotion) );
                selfie.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_blank_button) );
                manual.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_blank_button) );
                mode = "photo";

                icon.setBackgroundResource(R.drawable.gallery_icon);
                break;
            case R.id.manual_button:
                manual.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_change_button_emotion) );
                photo.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_blank_button) );
                selfie.setBackground(ContextCompat.getDrawable(
                        root.getContext(),
                        R.drawable.color_blank_button) );
                mode = "manual";
                icon.setBackgroundResource(R.drawable.profile_icon);
                break;

//            case R.id.button:
//                Intent intent = new Intent(view.getContext(), AddingActivities.class);
//                view.getContext().startActivity(intent);
//                break;

            case R.id.continue_button:
                if (mode.equals("selfie")){
                    try {
                        toCamera(activity);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(mode.equals("photo")){
                    toPhoto();
                }
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path = null;
        if (requestCode == CAMARA_REQUEST){
            Log.d("TAG", "onActivityResult: " + resultCode + "r:" +requestCode);
            Log.d("TAG", "onActivityResult: finish");
            Log.d("TAG", tempFile.getPath());
            path = tempFile.getPath();
        }else if (requestCode == PHOTO_REQUEST){
            if (data != null){
//                MediaStore.createWriteRequest()
                Log.d("photo", "onActivityResult: " + data.toString());
                path = copyFileToInternalStorage(data.getData(),"");

                Log.d("photo", "onActivityResult: path: " + path);
            }
        }

        String finalPath = path;
        if (finalPath != null){
            // face recognition thread
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try  {
                        //Your code goes here
                        Thread.sleep(1000);
                        FaceRecognition f = new FaceRecognition();
                        Map<String,String> res = f.recognition(finalPath);
                        StaticLiveData.getInstance().postValue(res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        if (data != null){
            // call emotion result ui
            Intent intent = new Intent(this.getActivity().getBaseContext(), EmotionResultActivity.class);
            intent.putExtra("path",finalPath);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            startActivity(intent);
        }



//        File file = new File("~/.aws/credentials");
//        File file1 = new File("file://android_asset/test");
//        Log.d("TAG", "onActivityResult: " + file.getPath());
//        Log.d("TAG", "onActivityResult: " + file1.getPath());
//        InputStream fis = null;
//        FileOutputStream fos = null;
//        try {
//            fis = root.getContext().getAssets().open("test");
//
//            fos = new FileOutputStream(file);
//            byte[] temp = new byte[10];
//            int res = 1;
//            while (res>0){
//                res = fis.read(temp);
//                fos.write(temp);
//                Log.d("TAG", "onActivityResult: " + temp);
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (fis != null && fos != null){
//                    fis.close();
//                    fos.flush();
//                    fos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        Log.d("TAG", "onActivityResult: file done");
//
//        File file = new File("file://android_asset/test");
//
//        Log.d("TAG", "onActivityResult: "+file.getPath());
//        try {
//            File file = new File(url.toURI());
//            Log.d("TAG", "onActivityResult: file");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }


    }

    private void toCamera(Activity activity) throws IOException {
        Log.d("zisen","toCamera start");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String fileName = "pic_" + System.currentTimeMillis();
        // Get external dir
        File dir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Create folder to save users images
        File folder = new File(dir,"mood_pictures");
        if (!folder.exists()){
            folder.mkdir();
        }

        // pic file
        tempFile = new File(folder,fileName + ".jpg");

        // compatible with android N
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            imageUri = Uri.fromFile(tempFile);
        }else{
            imageUri = FileProvider.getUriForFile(activity,activity.getPackageName() + ".fileprovider",tempFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        tempFile.getParentFile().mkdirs();
        tempFile.createNewFile();
        startActivityForResult(intent,CAMARA_REQUEST);

    }

    private void toPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent,PHOTO_REQUEST);
    }

//    private void requestPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            try {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                intent.addCategory("android.intent.category.DEFAULT");
//                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
//                startActivityForResult(intent, 2296);
//            } catch (Exception e) {
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                startActivityForResult(intent, 2296);
//            }
//        } else {
//            //below android 11
//            ActivityCompat.requestPermissions(HomeFragment.this, new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//        }
//    }

    private String copyFileToInternalStorage(Uri uri, String newDirName) {
        Uri returnUri = uri;

        Cursor returnCursor = root.getContext().getContentResolver().query(returnUri, new String[]{
                OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE
        }, null, null, null);


        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));

        File output;
        if (!newDirName.equals("")) {
            File dir = new File(root.getContext().getFilesDir() + "/" + newDirName);
            if (!dir.exists()) {
                dir.mkdir();
            }
            output = new File(root.getContext().getFilesDir() + "/" + newDirName + "/" + name);
        } else {
            output = new File(root.getContext().getFilesDir() + "/" + name);
        }
        try {
            InputStream inputStream = root.getContext().getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(output);
            int read = 0;
            int bufferSize = 1024;
            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }

            inputStream.close();
            outputStream.close();

        } catch (Exception e) {

            Log.e("Exception", e.getMessage());
        }

        return output.getPath();
    }
}