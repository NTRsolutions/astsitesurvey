package com.telecom.ast.sitesurvey.filepicker.listener;

import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;

/**
 * Created 16-06-2017
 *
 * @author Altametrics Inc.
 */
public interface CropCallback {
	void onSuccess(MediaFile file);

	void onError();
}
