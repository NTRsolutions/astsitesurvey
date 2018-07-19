package com.telecom.ast.sitesurvey.filepicker.listener;

import android.view.View;

import com.telecom.ast.sitesurvey.FNObject;
import com.telecom.ast.sitesurvey.filepicker.AndroidDeviceFile;


/**
 * Created 05-06-2017
 *
 * @author Altametrics Inc.
 */
public interface OnItemClickListener {
	void onFileClick(View view, FNObject object);

	boolean isSelected(AndroidDeviceFile file);
}
