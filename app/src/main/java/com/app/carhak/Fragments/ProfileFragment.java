package com.app.carhak.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.carhak.BuildConfig;
import com.app.carhak.Model.ProfileData;
import com.app.carhak.Model.ResponseData;
import com.app.carhak.Model.ViewProfile;
import com.app.carhak.R;
import com.app.carhak.Retrofit.ApiInterface;
import com.app.carhak.Retrofit.ApiUtils;
import com.app.carhak.SharedPrefence.DataProccessor;
import com.app.carhak.Utils.MyGps;
import com.app.carhak.Utils.NetworkUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.FileProvider.getUriForFile;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    private ProgressBar progressBar;
    private int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    List<ProfileData> list = new ArrayList<>();
    private EditText et_name, et_email, et_no, et_address, et_zipcode;
    private ImageView img_edit, img_profile;
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int REQUEST_GALLERY_IMAGE = 1;
    private boolean lockAspectRatio = false, setBitmapMaxWidthHeight = false;
    private int ASPECT_RATIO_X = 16, ASPECT_RATIO_Y = 9, bitmapMaxWidth = 800, bitmapMaxHeight = 800;
    private int IMAGE_COMPRESSION = 85;
    private String currentPhotoPath;
    File file = null;
    byte[] byteArray;
    private Button bt_save;
    private String userid;
    private String address1;
    private boolean setValue = false;
    private MyGps gpsTracker;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        img_edit = view.findViewById(R.id.img_edit);
        img_profile = view.findViewById(R.id.img_profile);
        et_name = view.findViewById(R.id.et_name);
        et_email = view.findViewById(R.id.et_email);
        et_no = view.findViewById(R.id.et_no);
        et_address = view.findViewById(R.id.et_address);
        et_zipcode = view.findViewById(R.id.et_zipcode);
        bt_save = view.findViewById(R.id.bt_save);
        img_edit.setOnClickListener(this);
        bt_save.setOnClickListener(this);


        DataProccessor dataProccessor = new DataProccessor(getContext());
        userid = dataProccessor.getUserid("userId");
      //  Toast.makeText(getContext(), ""+userid, Toast.LENGTH_SHORT).show();


        if (NetworkUtils.isConnected(getContext())) {
            if (userid != null) {
                //   Toast.makeText(getContext(), ""+userid, Toast.LENGTH_SHORT).show();
                GetProfile(userid);
            }
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        gpsTracker = new MyGps(getContext());
        if (checkPermissions()) {
            startLocationUpdates();
        } else if (!checkPermissions()) {
            requestPermissions();
        }
        et_address.setText(address1);
    }


    private void GetProfile(String userids) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(getContext());
            apiInteface.GetProfile(userids).enqueue(new Callback<ViewProfile>() {
                @Override
                public void onResponse(Call<ViewProfile> call, Response<ViewProfile> response) {
                    if (response.isSuccessful()) {

                        progressBar.setVisibility(View.GONE);
                        ViewProfile data = response.body();
                        if (data != null) {
                            if (data.getCode().equalsIgnoreCase("201")) {

                                if (data.getData().get(0).getFirstName() != null ) {
                                    et_name.setText(data.getData().get(0).getFirstName());
                                }

                                if (data.getData().get(0).getEmail() != null) {
                                    et_email.setText(data.getData().get(0).getEmail());
                                }

                                if (data.getData().get(0).getPhoneNo() != null) {
                                    et_no.setText(data.getData().get(0).getPhoneNo());
                                }


                                if (data.getData().get(0).getFullAddress() != null) {
                              //      et_address.setText(data.getData().get(0).getFullAddress());
                                }


                                if (data.getData().get(0).getZipcode() != null) {
                                    et_zipcode.setText(data.getData().get(0).getZipcode());
                                }

                                if (data.getData().get(0).getProfileImg() != null && !data.getData().get(0).getProfileImg().isEmpty()) {
                                    Glide.with(getContext()).load(ApiUtils.IMAGE_URL + data.getData().get(0).getProfileImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(img_profile);
                                }


                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "" + data.getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ViewProfile> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("failure", "" + t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Editprofile(String userids, String name, String address, String Zipcod, String phone) {
        try {

//            Log.e("file",file.toString());


            MultipartBody.Part profile_img = null;

            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), userids);

            RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"), name);

            RequestBody full_address = RequestBody.create(MediaType.parse("text/plain"), address);

            RequestBody zipcode = RequestBody.create(MediaType.parse("text/plain"), Zipcod);

            RequestBody phone_no = RequestBody.create(MediaType.parse("text/plain"), phone);

            if (file != null) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                profile_img = MultipartBody.Part.createFormData("profile_img", file.getName(), requestFile);
            } else {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                profile_img = MultipartBody.Part.createFormData("profile_img", "", requestFile);

            }


            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInteface;
            apiInteface = ApiUtils.getAPIService(getContext());
            apiInteface.EditProfile(userId, first_name, full_address, zipcode, phone_no, profile_img).enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    if (response.isSuccessful()) {

                        progressBar.setVisibility(View.GONE);
                        ResponseData data = response.body();
                        if (data != null) {
                            if (data.getCode().equalsIgnoreCase("201")) {
                                Toast.makeText(getContext(), ""+data.getMessage(), Toast.LENGTH_SHORT).show();

                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("failure", "" + t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void setImage() {
        final CharSequence[] option = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo !");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (option[i].equals("Take Photo")) {
                    takeCameraImage();
                } else if (option[i].equals("Choose from Gallery")) {
                    chooseImageFromGallery();
                } else {
                    dialog.cancel();
                }
            }
        });
        builder.show();
    }


    private void chooseImageFromGallery() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            Intent pictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            pictureIntent.setType("image/*");
                            pictureIntent.addCategory(Intent.CATEGORY_OPENABLE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                String[] mimeTypes = new String[]{"image/jpeg", "image/png"};
                                pictureIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                            }
                            startActivityForResult(Intent.createChooser(pictureIntent, "Select Picture"), REQUEST_GALLERY_IMAGE);

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void takeCameraImage() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File file;
                            try {
                                file = getImageFile(); // 1
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                            Uri uri;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) // 2
                                uri = getUriForFile(getContext(), BuildConfig.APPLICATION_ID.concat(".provider"), file);
                            else
                                uri = Uri.fromFile(file); // 3
                            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri); // 4
                            startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private File getImageFile() throws IOException {
        String imageFileName = "JPEG_" + System.currentTimeMillis() + "_";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM
                ), "Camera"
        );
        System.out.println(storageDir.getAbsolutePath());
        if (storageDir.exists())
            System.out.println("File exists");
        else
            System.out.println("File not exists");
        File file = File.createTempFile(
                imageFileName, ".jpg", storageDir
        );
        currentPhotoPath = "file:" + file.getAbsolutePath();
        return file;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_edit:
                setImage();
                break;

            case R.id.bt_save:
                Editprofile(userid, et_name.getText().toString()
                        , et_address.getText().toString()
                        , et_zipcode.getText().toString()
                        , et_no.getText().toString());
                break;
        }
    }

    private void setResultCancelled() {
        Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    try {
                        //  cropImage(getCacheImagePath(fileName));
                        Uri uri = Uri.parse(currentPhotoPath);
                        Log.e("currentPhotoPath", "" + currentPhotoPath);
                        cropImage(uri, uri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //  Toast.makeText(getContext(), fileName.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    setResultCancelled();
                }
                break;
            case REQUEST_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri sourceUri = data.getData();
                        File file = getImageFile();
                        Log.e("Source", "" + file.getAbsolutePath());
                        Uri destinationUri = Uri.fromFile(file);
                        cropImage(sourceUri, destinationUri);
                    } catch (Exception e) {
                        Log.e("ss", "Please select another image");
                    }

                } else {
                    setResultCancelled();
                }
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    handleUCropResult(data);
                    if (file != null) {
                        Glide.with(this).load(file).diskCacheStrategy(DiskCacheStrategy.ALL).into(img_profile);
                        String name = System.currentTimeMillis() + "_video.jpeg";
                    }
                } else {
                    setResultCancelled();
                }
                break;
            case UCrop.RESULT_ERROR:
                final Throwable cropError = UCrop.getError(data);
                Log.e("error", "Crop error: " + cropError);
                setResultCancelled();
                break;
            default:
                setResultCancelled();
        }
    }

    private void cropImage(Uri sourceUri, Uri destinationUri) {
//        Uri destinationUri = Uri.fromFile(new File(getActivity().getCacheDir(), queryName(getActivity().getContentResolver(), sourceUri)));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(IMAGE_COMPRESSION);
        // options.setMaxBitmapSize(10000);


//        // applying UI theme
        options.setToolbarColor(ContextCompat.getColor(getContext(), R.color.appcolor));
        options.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.appcolor));
        options.setActiveWidgetColor(ContextCompat.getColor(getContext(), R.color.appcolor));

//
        if (lockAspectRatio)
            options.withAspectRatio(ASPECT_RATIO_X, ASPECT_RATIO_Y);
//
        if (setBitmapMaxWidthHeight)
            options.withMaxResultSize(bitmapMaxWidth, bitmapMaxHeight);
//
        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(getActivity(), this);
    }

    private void handleUCropResult(Intent data) {
        if (data == null) {
            setResultCancelled();
            return;
        }
        final Uri resultUri = UCrop.getOutput(data);
        setResultOk(resultUri);
    }

    private void setResultOk(Uri imagePath) {


        try {
            String path = String.valueOf(imagePath);
            file = new File(new URI(path));
            byteArray = getStreamByteFromImage(file);


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }


    public static byte[] getStreamByteFromImage(final File imageFile) {

        Bitmap photoBitmap = BitmapFactory.decodeFile(imageFile.getPath());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        int imageRotation = getImageRotation(imageFile);

        if (imageRotation != 0)
            photoBitmap = getBitmapRotatedByDegree(photoBitmap, imageRotation);

        photoBitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream);

        return stream.toByteArray();
    }

    private static int getImageRotation(final File imageFile) {

        ExifInterface exif = null;
        int exifRotation = 0;

        try {
            exif = new ExifInterface(imageFile.getPath());
            exifRotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (exif == null)
            return 0;
        else
            return exifToDegrees(exifRotation);
    }

    private static int exifToDegrees(int rotation) {
        if (rotation == ExifInterface.ORIENTATION_ROTATE_90)
            return 90;
        else if (rotation == ExifInterface.ORIENTATION_ROTATE_180)
            return 180;
        else if (rotation == ExifInterface.ORIENTATION_ROTATE_270)
            return 270;

        return 0;
    }

    private static Bitmap getBitmapRotatedByDegree(Bitmap bitmap, int rotationDegree) {
        Matrix matrix = new Matrix();
        matrix.preRotate(rotationDegree);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i("tag", "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i("tag", "Requesting permission");
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(getView().findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }


    private void startLocationUpdates() {
        if (gpsTracker.getLocation() != null) {
            if (gpsTracker.getLatitude() != 0 && gpsTracker.getLongitude() != 0) {
                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses != null && addresses.size() > 0) {
                  //      latitud = String.valueOf(latitude);
                   //     longitud = String.valueOf(longitude);


                        if (addresses.get(0).getAddressLine(0) != null) {
                            address1 = addresses.get(0).getAddressLine(0);
                            Log.e("address1",""+address1);

                        }
                        if (addresses.get(0).getLocality() != null) {
                      ///      address2 = addresses.get(0).getLocality();
                         //   Log.e("address2",""+address2);

                        } else {
                            if (addresses.get(0).getSubAdminArea() != null) {
                         //       address3 = addresses.get(0).getSubAdminArea();
                            } else {
                         //       address3 = "";
                            }
                        }
//                        if (addresses.get(0).getAdminArea() != null) {
//                            myLocation.setUserState(addresses.get(0).getAdminArea());
//                        }
                        if (addresses.get(0).getCountryName() != null) {
                      //      countr = addresses.get(0).getCountryName();
                        }
//                        if (addresses.get(0).getCountryCode() != null) {
//                            myLocation.setUserCountryCode(addresses.get(0).getCountryCode());
//                        }

                        setValue = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                setValue = false;
            }
        } else {

            gpsTracker.showSettingsAlert();
            setValue = false;
        }
    }
}