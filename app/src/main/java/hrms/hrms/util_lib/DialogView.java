package hrms.hrms.util_lib;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

public class DialogView extends LinearLayout {

    private TextView tvTitle, tvDescription, tvOk, tvCancel;

    public DialogView(Context context) {
        super(context);
        init(context);
    }

    public DialogView(Context context, AttributeSet attrs) throws IllegalAccessException {
        super(context, attrs);
        throw new IllegalAccessException("Unable to create view using XML");
    }

    public DialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        setOrientation(VERTICAL);
        setPadding(10, 10, 10, 10);
        LayoutParams topparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        topparams.setMargins(16, 16, 16, 16);
        setLayoutParams(topparams);

        // ok cancel
        LinearLayout mainlayout = new LinearLayout(context);
        mainlayout.setOrientation(VERTICAL);
        mainlayout.setBackgroundColor(Color.WHITE);
        LayoutParams mainparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mainparams.setMargins(30, 30, 30, 30);
        mainlayout.setPadding(30, 30, 30, 30);
        mainlayout.setLayoutParams(mainparams);

        // Title
        tvTitle = new TextView(context);
        tvTitle.setTextSize(20);
        LayoutParams titleparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleparams.setMargins(10, 10, 0, 0);
        tvTitle.setLayoutParams(titleparams);
        tvTitle.setPadding(20, 0, 20, 0);
        tvTitle.setText("Title");
        mainlayout.addView(tvTitle);

        // Description
        tvDescription = new TextView(context);
        tvDescription.setTextSize(17);
        LayoutParams descriptionparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        descriptionparams.setMargins(10, 10, 0, 0);
        tvDescription.setLayoutParams(descriptionparams);
        tvDescription.setPadding(20, 20, 20, 20);
        tvDescription.setText("Title");
        tvDescription.setTextColor(Color.parseColor("#d9524154"));
        mainlayout.addView(tvDescription);

        Space space = new Space(context);
        ViewGroup.LayoutParams spaceparams = new ViewGroup.LayoutParams(6000, 1);
        space.setLayoutParams(spaceparams);
        mainlayout.addView(space);

        // ok cancel
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(HORIZONTAL);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;
        layout.setLayoutParams(params);

        // OK
        tvOk = new TextView(context);
        tvOk.setTextSize(17);
        tvOk.setPadding(50, 20, 50, 20);
        tvOk.setText("OK");
        tvOk.setTextColor(Color.parseColor("#524154"));
        layout.addView(tvOk);

        // Cancel
        tvCancel = new TextView(context);
        tvCancel.setTextSize(17);
        LayoutParams cancelparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        cancelparams.setMargins(10, 0, 0, 0);
        tvCancel.setLayoutParams(cancelparams);
        tvCancel.setPadding(20, 20, 20, 20);
        tvCancel.setText("Cencel");
        tvCancel.setTextColor(Color.parseColor("#524154"));
        layout.addView(tvCancel);

        // click event
//        TypedValue outValue = new TypedValue();
//        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
//        tvOk.setBackgroundResource(outValue.resourceId);
//        tvCancel.setBackgroundResource(outValue.resourceId);

        mainlayout.addView(layout);
        addView(mainlayout);
    }

    public void setTitle(String title) {
        if (title.length() > 0) {
            tvTitle.setText(title);
        } else
            tvTitle.setVisibility(View.GONE);
    }

    public void setMessage(String message) {
        if (message.length() > 0) {
            tvDescription.setText(message);
        } else
            tvDescription.setVisibility(View.GONE);
    }

    public void setOkClickListener(String okText, boolean isOkBold, OnClickListener mOkClickListener) {
        if (okText.length() > 0) {
            tvOk.setText(okText);
            if (isOkBold)
                tvOk.setTypeface(tvOk.getTypeface(), Typeface.BOLD);
            if (mOkClickListener != null)
                tvOk.setOnClickListener(mOkClickListener);
        } else
            tvOk.setVisibility(View.GONE);
    }

    public void setCancleClickListener(String cancelText, boolean isCancleBold, OnClickListener mCancleClickListener) {
        if (cancelText.length() > 0) {
            tvCancel.setText(cancelText);
            if (isCancleBold)
                tvCancel.setTypeface(tvCancel.getTypeface(), Typeface.BOLD);
            if (mCancleClickListener != null)
                tvCancel.setOnClickListener(mCancleClickListener);
        } else
            tvCancel.setVisibility(View.GONE);
    }
}
