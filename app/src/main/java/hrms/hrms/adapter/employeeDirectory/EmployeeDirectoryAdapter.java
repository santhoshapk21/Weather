package hrms.hrms.adapter.employeeDirectory;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowEmpDirectoryBinding;

import java.util.List;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.fragment.empdir.EmployeeDetailsFragment;
import hrms.hrms.retrofit.model.companyDirectory.ResponseCompanyDirectory;


/**
 * Created by gjz on 9/4/16.
 */
public class EmployeeDirectoryAdapter extends
        RecyclerView.Adapter<EmployeeDirectoryAdapter.ContactsViewHolder> {

    private List<ResponseCompanyDirectory> contacts;
    private Context context;
    private Fragment fragment;

    public EmployeeDirectoryAdapter(Context context,
                                    Fragment fragment,
                                    List<ResponseCompanyDirectory> contacts,
                                    View.OnClickListener onClickListener) {
        this.context = context;
        this.contacts = contacts;
        this.fragment = fragment;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_emp_directory, parent, false);
        return new ContactsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, final int position) {

        //header

        String currentTitle = contacts.get(position).getFirstName().charAt(0) + "";
        if (position != 0) {
            String lastTitle = contacts.get(position - 1).getFirstName().charAt(0) + "";
            currentTitle = currentTitle.toLowerCase();
            lastTitle = lastTitle.toLowerCase();

            if (currentTitle.equals(lastTitle))
                holder.mBinding.tvIndex.setVisibility(View.GONE);
            else
                holder.mBinding.tvIndex.setVisibility(View.VISIBLE);
        } else if (position == 0)
            holder.mBinding.tvIndex.setVisibility(View.VISIBLE);

        //set Text
        holder.mBinding.tvIndex.setText(currentTitle.toUpperCase());
        holder.mBinding.rowEmpDirTvName.setText(contacts.get(position).getFullName());
        holder.mBinding.rowEmpDirTvDept.setText(contacts.get(position).getDesignation());
        holder.mBinding.rowEmpDirClick.setTag(position);
        if (contacts.get(position).getProfileImage().equals("")) {
            holder.mBinding.rowEmpDirIvAvatar.setImageResource(R.drawable.ic_no_image);
        } else {
            ((HomeActivity) (context)).setImage(context,contacts.get(position)
                    .getProfileImage(),holder.mBinding.rowEmpDirIvAvatar);
        }

        ViewCompat.setTransitionName(holder.mBinding.rowEmpDirIvAvatar,
                String.valueOf(position) + "_image");
        ViewCompat.setTransitionName(holder.mBinding.rowEmpDirTvName,
                String.valueOf(position) + "_tvName");
        ViewCompat.setTransitionName(holder.mBinding.rowEmpDirTvDept,
                String.valueOf(position) + "_tvDept");

        holder.mBinding.rowEmpDirClick.setTag(position);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    private String getString(int id) {
        return context.getResources().getString(id);
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowEmpDirectoryBinding mBinding;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);


            mBinding.rowEmpDirClick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();

            EmployeeDetailsFragment employeeDetailsFragment = new EmployeeDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("empId", contacts.get(position).getEmployeeId());
            bundle.putString("isPresent", contacts.get(position).getIsPresent());
            employeeDetailsFragment.setArguments(bundle);
            ((HomeActivity) context).addFragment(employeeDetailsFragment, context.getResources().getString(R.string.profile));
        }
    }
}