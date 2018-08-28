package com.telecom.ast.sitesurvey.filepicker.fragment;

import android.graphics.Bitmap;
import android.view.View;


import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.filepicker.AndroidDeviceFile;
import com.telecom.ast.sitesurvey.filepicker.FNFilePicker;
import com.telecom.ast.sitesurvey.filepicker.helper.BitmapConverterTask;
import com.telecom.ast.sitesurvey.filepicker.helper.FNImageUtil;
import com.telecom.ast.sitesurvey.filepicker.listener.CropCallback;
import com.telecom.ast.sitesurvey.filepicker.listener.IPageListener;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.filepicker.view.FNCropImageView;
import com.telecom.ast.sitesurvey.fragment.MainFragment;

import java.util.ArrayList;

/**
 * Created 14-06-2017
 *
 * @author AST Inc.
 */
public class FNCropImageFragment extends MainFragment {

    private FNCropImageView cropImageView;
    private MediaFile imageFile;
    private boolean isProfilePic;

    @Override
    protected void getArgs() {
        this.imageFile = this.getParcelable("imageFile");
        //this.isProfilePic = this.getArgsBoolean(FNFilePicker.EXTRA_CROP_MODE, false);
        isProfilePic = false;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.picker_crop_view;
    }

    @Override
    protected void loadView() {
        cropImageView = this.findViewById(R.id.cropImageView);
        cropImageView.setCropMode(isProfilePic ? FNCropImageView.CropMode.CIRCLE_SQUARE : FNCropImageView.CropMode.SQUARE);
        //cropImageView.setCropMode(FNCropImageView.CropMode.CIRCLE);

    }

    @Override
    protected void setClickListeners() {
    }

    @Override
    protected void setAccessibility() {
    }

    @Override
    protected void dataToView() {
        ArrayList<MediaFile> mediaFiles = new ArrayList<>();
        mediaFiles.add(imageFile);
    /*    float height = FNImageUtil.SINGLE_MODE_MAX_HEIGHT;
        float width = FNImageUtil.SINGLE_MODE_MAX_WIDTH;
        Bitmap bitmap = null;
        for (MediaFile deviceFile : mediaFiles) {
            if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                bitmap = FNImageUtil.compressImage(deviceFile.getPath(), deviceFile.getOrientation(), width, height);
            }
            if (bitmap != null) {
                deviceFile.setPhoto(bitmap);
            }*/
        BitmapConverterTask converterTask = new BitmapConverterTask(this.getContext()) {
            @Override
            protected void onPostExecute(ArrayList<? extends AndroidDeviceFile> fileList) {
                this.dismissProgressBar();
                if (fileList.size() > 0) {
                    cropImageView.setOrientation(imageFile.getOrientation());
                    cropImageView.setImageBitmap(fileList.get(0).getPhoto());
                }
            }
        };
        converterTask.execute(mediaFiles);
    }




    @Override
    public boolean onBackPressed() {
        getHostActivity().redirectToHomeMenu();
        return false;
    }

    public void rotateImage(FNCropImageView.RotateDegrees degrees) {
        cropImageView.rotateImage(degrees);
    }

    public void startCrop(CropCallback cropCallback) {
        if (activityListener() == null) {
            return;
        }
        this.cropImageView.startCrop(imageFile, cropCallback);
    }

    private IPageListener activityListener() {
        return getHostActivity() != null ? (IPageListener) getHostActivity() : null;
    }
}
