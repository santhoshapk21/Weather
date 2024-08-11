package hrms.hrms.fragment.markAttandance;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.hris365.R;
import com.hris365.databinding.FragmentAttendanceImageBinding;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceImageFragment extends DialogFragment {

    private FragmentAttendanceImageBinding mBinding;

    public AttendanceImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_attendance_image, null, false);
        // Inflate the layout for this fragment
        String abc = getArguments().getString("uri");
        Picasso.get().load(new File(abc)).into(mBinding.frgAttendanceIvUserimage);

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

}
