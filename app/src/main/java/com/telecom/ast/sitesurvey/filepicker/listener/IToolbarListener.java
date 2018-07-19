package com.telecom.ast.sitesurvey.filepicker.listener;


import com.telecom.ast.sitesurvey.filepicker.view.FNCropImageView;

/**
 * Created 16-06-2017
 *
 * @author Altametrics Inc.
 */
public interface IToolbarListener {
	void onDone();

	void onCameraClick();

	void rotateImage(FNCropImageView.RotateDegrees degrees);

	void openCropFragment();
}
