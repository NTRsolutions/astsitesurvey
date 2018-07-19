package com.telecom.ast.sitesurvey.fragment.newsurveyfragment.surveyIntcallback;

import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;

import java.util.ArrayList;

public interface ImagePickerCallback {
    void onSuccess(ArrayList<MediaFile> files);


    void onError();
}
