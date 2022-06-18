package com.municipality.jdeidetmarjeyoun.complain;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.municipality.jdeidetmarjeyoun.R;
import com.municipality.jdeidetmarjeyoun.language.SelectLanguageActivity;
import com.municipality.jdeidetmarjeyoun.objects.SimpleResponse;
import com.municipality.jdeidetmarjeyoun.services.ApiManager;
import com.municipality.jdeidetmarjeyoun.utils.AppUtil;
import com.municipality.jdeidetmarjeyoun.utils.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Charbel on 9/16/2017.
 */

public class Complain_fragment extends Fragment {

    EditText txtFullName, txtPhone, txtMessage;
    private View parentView;
    Button btnUpload, btnSend;
    TextView lblUpload;
    MaterialSpinner spinnerType;
//    private ImageView imageview;
    private Button btnSelectImage;
    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2, REQUEST_READ_EXTERNAL_STORAGE = 3;

    public static Complain_fragment newInstance() {
        Complain_fragment fragment = new Complain_fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if ("ar".equals(SelectLanguageActivity.lang)) {
            parentView = inflater.inflate(R.layout.complain_fragment_ar, container, false);
        } else {
            parentView = inflater.inflate(R.layout.complain_fragment_en, container, false);
        }

        try {
            initView();
            styleView();
            initListeners();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error : " + e, Toast.LENGTH_SHORT).show();
        }

        return parentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath = destination.getAbsolutePath();
//                imageview.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            try {
                if(data != null) {
                    Uri selectedImage = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                    imgPath = getRealPathFromURI(selectedImage);
                    destination = new File(imgPath.toString());
//                imageview.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void initView() throws Exception {
        txtFullName = (EditText) parentView.findViewById(R.id.txtFullName);
        txtPhone = (EditText) parentView.findViewById(R.id.txtPhone);
        txtMessage = (EditText) parentView.findViewById(R.id.txtMessage);
        btnSend = (Button) parentView.findViewById(R.id.btnSend);
        btnUpload = (Button) parentView.findViewById(R.id.btnUpload);
        lblUpload = (TextView) parentView.findViewById(R.id.lblUpload);
        spinnerType = (MaterialSpinner) parentView.findViewById(R.id.spinnerType);
        spinnerType.setItems(AppUtil.transform(getResources().getString(R.string.select_category),AppUtil.globalVariable.getComplaintsType()));
    }

    private void styleView() throws Exception {
        txtFullName.setTypeface(AppUtil.setFontRegular(getActivity()));
        txtPhone.setTypeface(AppUtil.setFontRegular(getActivity()));
        txtMessage.setTypeface(AppUtil.setFontRegular(getActivity()));
        btnSend.setTypeface(AppUtil.setFontRegular(getActivity()));
        lblUpload.setTypeface(AppUtil.setFontRegular(getActivity()));
        spinnerType.setTypeface(AppUtil.setFontRegular(getActivity()));
    }

    private void initListeners() throws Exception {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    callService();
                }
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageDialog();
            }
        });
    }

    private void callService() {
        if(imgPath == null) {
            sendComplaintWithoutMedia();
        } else {
            sendComplaintWithMedia();
        }
    }

    private void sendComplaintWithoutMedia() {
        sendComplaintWithoutMedia("");
    }

    private void sendComplaintWithoutMedia(String dataId) {
        ApiManager.getService().sendComplaint(dataId,txtFullName.getText().toString().trim(),
                txtPhone.getText().toString().trim(),
                spinnerType.getText().toString().trim() ,
                txtMessage.getText().toString().trim()).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                SimpleResponse  simpleResponse = response.body();
                if (simpleResponse.getStatus() == 1 && response.isSuccessful()){
                    Toast.makeText(getActivity(), simpleResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    resetFields();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {

            }
        });
    }

    private void sendComplaintWithMedia() {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), new File(imgPath));
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imgPath, requestFile);

        ApiManager.getService().uploadImage(body).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    String dataId = ((LinkedTreeMap) response.body()).get("id").toString();
                    sendComplaintWithoutMedia(dataId);
                } catch (Exception ex) {
                    Log.d("", ex.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("", t.getMessage());
            }
        });
    }

    private boolean validate() {
        if(StringUtils.isValid(txtFullName.getText())
                && StringUtils.isValid(txtPhone.getText())
                && StringUtils.isValid(txtMessage.getText())
                && spinnerType.getSelectedIndex() != 0){

            return true;
        }else {
            AppUtil.showError(getActivity(), getResources().getString(R.string.fill_fields_error));
            return false;

        }
    }

    private void resetFields(){
        txtFullName.setText("");
        txtMessage.setText("");
        txtPhone.setText("");
        spinnerType.setSelectedIndex(0);
    }

    private void openImageDialog() {
        try {
            PackageManager packageManager = getContext().getPackageManager();
            int hasPerm = packageManager.checkPermission(Manifest.permission.CAMERA, getContext().getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                hasPerm = packageManager.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, getContext().getPackageName());
                if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                    final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                    builder.setTitle("Select Option");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (options[item].equals("Take Photo")) {
                                dialog.dismiss();
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, PICK_IMAGE_CAMERA);
                            } else if (options[item].equals("Choose From Gallery")) {
                                dialog.dismiss();
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                            } else if (options[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Toast.makeText(getActivity(), "Read External Storage Permission error", Toast.LENGTH_SHORT);
                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                    }
                }
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.CAMERA)) {
                    Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT);
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA}, PICK_IMAGE_CAMERA);
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT);
            e.printStackTrace();
        }
    }
}
