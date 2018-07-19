package com.telecom.ast.sitesurvey.filepicker.helper;

import android.content.Context;
import android.os.AsyncTask;

import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.utils.ASTUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created 05-06-2017
 *
 * @author Altametrics Inc.
 */
public class FileLoaderTask extends AsyncTask<ArrayList<MediaFile>, Void, ArrayList<MediaFile>> {

	private Context context;
	//private boolean isCloudUpload;
	private ASTProgressBar _progressBar;

	public FileLoaderTask(Context context/*, boolean isCloudUpload*/) {
		this.context = context;
		//this.isCloudUpload = isCloudUpload;
	}

	@Override
	protected ArrayList<MediaFile> doInBackground(ArrayList<MediaFile>... params) {
		ArrayList<MediaFile> files = params[0];
		for (MediaFile deviceFile : files) {
			if (FNObjectUtil.isNonEmptyStr(deviceFile.getCompressFilePath())) {
				File compressPath = new File(deviceFile.getCompressFilePath());
				if (compressPath.exists()) {
					deviceFile.setEncodedString(ASTUtil.encodeFileTobase64(compressPath));
					compressPath.delete();
				}
			} else if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
				deviceFile.setEncodedString(ASTUtil.encodeFileTobase64(deviceFile.getFilePath()));
				if (deviceFile.isfromCamera() || deviceFile.isCropped()) {
					deviceFile.getFilePath().delete();
				}
			}
		}
		return files;
	}

	@Override
	protected void onPreExecute() {
		showProgressDialog();
	}

	@Override
	protected void onPostExecute(ArrayList<MediaFile> deviceFiles) {
		dismissProgressBar();
	}

	public void showProgressDialog() {
		this._progressBar = new ASTProgressBar(this.context);
		this._progressBar.show();
	}

	public void dismissProgressBar() {
		try {
			if (this._progressBar != null) {
				this._progressBar.dismiss();
			}
		} catch (Exception e) {
		}
	}
}
