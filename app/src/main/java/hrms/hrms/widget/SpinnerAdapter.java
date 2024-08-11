package hrms.hrms.widget;

import android.content.Context;

import android.graphics.Typeface;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hris365.R;

import java.util.List;


/**
 * Created by Yudiz on 11/10/16.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    private Typeface font;

    public SpinnerAdapter(Context context, int resource) {
        super(context, resource);
    }

    public SpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    public SpinnerAdapter(Context context, int resource, String[] items) {
        super(context, resource, items);
    }

    public void setFont(String name) {
        font = Typeface.createFromAsset(getContext().getAssets(),
                name);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        android.widget.TextView
                view = (android.widget.TextView) super.getView(position, convertView, parent);
        if (font != null) view.setTypeface(font);
        view.setEllipsize(TextUtils.TruncateAt.END);
//        view.setTextSize(getContext().getResources().getDimension(R.dimen._6sdp));

        view.setTextSize(14);
        return view;
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        android.widget.TextView view = (android.widget.TextView) super.getDropDownView(position, convertView, parent);
        if (font != null) view.setTypeface(font);
        view.setEllipsize(TextUtils.TruncateAt.END);
//        view.setTextSize(getContext().getResources().getDimension(R.dimen._6sdp));

        view.setTextSize(14);
        return view;
    }
}
