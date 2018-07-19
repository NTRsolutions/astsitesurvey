package com.telecom.ast.sitesurvey.filepicker.camera;


import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;

import java.util.ArrayList;

/**
 * Created 05-06-2017
 *
 * @author Altametrics Inc.
 */
public interface OnImageReadyListener {
	void onImageReady(ArrayList<MediaFile> image);
}
