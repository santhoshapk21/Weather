package hrms.hrms.util_lib.imagechooser;

import android.content.Context;

public class ChoosePhoto {
    public static PhotoRequest getPhoto(Context context, ChooseType type) {
        return new PhotoRequest(context, type);
    }
}
