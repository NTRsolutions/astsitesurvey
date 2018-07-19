package com.telecom.ast.sitesurvey.filepicker.animation;

/**
 * Created 15-06-2017
 *
 * @author Altametrics Inc.
 */
public interface SimpleValueAnimator {
	void startAnimation(long duration);

	void cancelAnimation();

	boolean isAnimationStarted();

	void addAnimatorListener(SimpleValueAnimatorListener animatorListener);
}
