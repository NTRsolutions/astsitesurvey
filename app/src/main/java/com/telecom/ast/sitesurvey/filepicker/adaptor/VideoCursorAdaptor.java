package com.telecom.ast.sitesurvey.filepicker.adaptor;

import android.content.Context;
import android.database.Cursor;


import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.filepicker.listener.OnItemClickListener;
import com.telecom.ast.sitesurvey.filepicker.listener.VideoRequestHandler;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;


/**
 * Created 22-06-2017
 *
 * @author AST Inc.
 */
public class VideoCursorAdaptor extends MediaCursorAdaptor {

	public VideoCursorAdaptor(Context context, Cursor cursor, int imageSize, int mediaType, OnItemClickListener itemClickListener) {
		super(context, cursor, imageSize, mediaType, itemClickListener);
	}

	@Override
	public void onBindViewHolder(MediaViewHolder viewHolder, Cursor cursor, int position) {
		MediaFile file = MediaFile.deviceFile(cursor, mediaType);
		viewHolder.file = file;
		viewHolder.imageView.setSize(imageSize, imageSize);
		viewHolder.imageView.setRequestHandler(new VideoRequestHandler());
		viewHolder.imageView.setURL(file.videoFilePath(), R.drawable.video_placeholder);
		viewHolder.setSelectionView();
	}
}
