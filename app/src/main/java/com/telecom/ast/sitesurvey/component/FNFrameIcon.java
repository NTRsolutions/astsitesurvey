package com.telecom.ast.sitesurvey.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DimenRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;

/**
 * <h4>Created</h4> 01/28/16
 *
 * @author Altametrics Inc.
 */
public class FNFrameIcon extends FrameLayout {

	private FNFontViewField frame;
	private FNFontViewField icon;

	public FNFrameIcon(Context context) {
		this(context, null);
	}

	public FNFrameIcon(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FNFrameIcon(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}

	private void init(AttributeSet attrs) {
		if (this.isInEditMode()) {
			return;
		}
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.frame_icon_view, this, true);
		frame = view.findViewById(R.id.iconFrame);
		icon = view.findViewById(R.id.icon);
		loadAttributes(attrs);
	}

	private void loadAttributes(AttributeSet attrs) {
		if (attrs == null) {
			return;
		}
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FNFrameIcon);
		float frameSize = a.getDimension(R.styleable.FNFrameIcon_frameSize, 0);
		float iconSize = a.getDimension(R.styleable.FNFrameIcon_iconSize, 0);
		String frameIcon = a.getString(R.styleable.FNFrameIcon_frameIconText);
		String icon = a.getString(R.styleable.FNFrameIcon_iconText);
		int frameColor = a.getColor(R.styleable.FNFrameIcon_frameColor, 0);
		int iconColor = a.getColor(R.styleable.FNFrameIcon_iconColor, 0);
		setFrameSize(frameSize);
		setIconSize(iconSize);
		setFrameIcon(frameIcon);
		setIcon(icon);
		setFrameColor(frameColor);
		setIconColor(iconColor);
		a.recycle();
	}

	public void setFrameSize(float size) {
		if (size <= 0) {
			return;
		}
		this.frame.setTextSize(size);
	}

	public void setIconSize(float size) {
		if (size <= 0) {
			return;
		}
		this.icon.setTextSize(size);
	}

	public void setFrameDimen(@DimenRes int resId) {
		this.frame.setTextDimen(resId);
	}

	public void setIconDimen(@DimenRes int resId) {
		this.icon.setTextDimen(resId);
	}

	public void setIcon(int iconId) {
		if (iconId < 0) {
			return;
		}
		this.icon.setText(this.getResources().getString(iconId));
	}

	public void setIcon(String iconStr) {
		if (FNObjectUtil.isEmptyStr(iconStr)) {
			return;
		}
		this.icon.setText(iconStr);
	}

	public void setFrameIcon(int iconId) {
		if (iconId < 0) {
			return;
		}
		this.frame.setText(this.getResources().getString(iconId));
	}

	public void setFrameIcon(String iconStr) {
		if (FNObjectUtil.isEmptyStr(iconStr)) {
			return;
		}
		this.frame.setText(iconStr);
	}

	public void setFrameColor(int colorId) {
		if (colorId > 0) {
			frame.setTextColor(ASTUIUtil.getColor(getContext(), colorId));
		} else {
			frame.setTextColor(colorId);
		}
	}

	public void setIconColor(int colorId) {
		if (colorId > 0) {
			icon.setTextColor(ASTUIUtil.getColor(getContext(), colorId));
		} else {
			icon.setTextColor(colorId);
		}
	}

	public void startIconAnimation() {
		ASTUIUtil.startAnimation(icon);
	}

	public void cancelIconAnimation() {
		ASTUIUtil.cancelAnimation(icon);
	}

}
