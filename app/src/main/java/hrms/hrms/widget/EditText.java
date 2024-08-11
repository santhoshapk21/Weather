/*
 * 
 */
package hrms.hrms.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;


import androidx.appcompat.widget.AppCompatEditText;

import com.hris365.R;

import java.util.HashMap;
import java.util.Map;


/**
 * The Class TextView.
 */
public class EditText extends AppCompatEditText {

    /*
     * Caches typefaces based on their file path and name, so that they don't have to be created
     * every time when they are referenced.
     */
    private static Map<String, Typeface> mTypefaces;

    public EditText(final Context context) {
        this(context, null);
    }

    public EditText(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditText(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        if (mTypefaces == null) {
            mTypefaces = new HashMap<String, Typeface>();
        }

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.textview);
        if (array != null) {
            final String typefaceAssetPath = array.getString(R.styleable.textview_font_type);

            if (typefaceAssetPath != null) {
                Typeface typeface;
                if (mTypefaces.containsKey(typefaceAssetPath)) {
                    typeface = mTypefaces.get(typefaceAssetPath);
                } else {
                    AssetManager assets = context.getAssets();
                    typeface = Typeface.createFromAsset(assets, typefaceAssetPath);
                    mTypefaces.put(typefaceAssetPath, typeface);
                }
                EditText.this.setTypeface(typeface);
            }
            array.recycle();
        }
    }


}
