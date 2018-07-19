package com.telecom.ast.sitesurvey.filepicker.animation;

/**
 * Created 15-06-2017
 *
 * @author Altametrics Inc.
 */
public interface SimpleValueAnimatorListener {
	void onAnimationStarted();

	void onAnimationUpdated(float scale);

	void onAnimationFinished();
}
