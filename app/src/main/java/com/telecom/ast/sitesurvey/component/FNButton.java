package com.telecom.ast.sitesurvey.component;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.telecom.ast.sitesurvey.utils.ASTEnum;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;


/**
 * <h4>Created</h4> 16/02/17
 *
 * @author Altametrics Inc.
 */
public class FNButton extends AppCompatButton {

    public FNButton(Context context) {
        super(context);
        init();
    }

    public FNButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FNButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (this.isInEditMode()) {
            return;
        }
        if (getTypeface() != null) {
            ASTUIUtil.setFontTypeFace(this, getTypeface().getStyle());
        } else {
            this.setTypeFace(ASTEnum.FONT_REGULAR);
        }
    }

    public void setTypeFace(ASTEnum fontTypeFace) {
        ASTUIUtil.setFontTypeFace(this, fontTypeFace);
    }
}
