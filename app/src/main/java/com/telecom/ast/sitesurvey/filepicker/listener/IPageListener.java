package com.telecom.ast.sitesurvey.filepicker.listener;


import com.telecom.ast.sitesurvey.filepicker.FNFilePickerConfig;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;

/**
 * Created 07-06-2017
 *
 * @author Altametrics Inc.
 */
public interface IPageListener {
	boolean isValidToAddFile();

	FNFilePickerConfig config();

	void openCropFragment(MediaFile file);

	void updateHeader();
}
