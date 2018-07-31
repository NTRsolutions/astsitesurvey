package com.telecom.ast.sitesurvey.filepicker.listener;


import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;

import java.util.ArrayList;

/**
 * Created 16-06-2017
 *
 * @author AST Inc.
 */
public interface FilePickerCallback {

	void onSuccess(ArrayList<MediaFile> files);

	void onError();
}
