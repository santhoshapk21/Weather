package hrms.hrms.fragment.empdir;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentDirectoryBinding;

import hrms.hrms.RecylerListner.EndlessRecyclerOnScrollListener;
import hrms.hrms.RecylerListner.onLastItem;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.adapter.employeeDirectory.EmployeeDirectoryAdapter;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.companyDirectory.ResponseCompanyDirectory;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;


/**
 * Created by Yudiz on 14/10/16.
 */
public class EmployeeDirectoryFragment extends BaseFragment implements TextWatcher, View.OnClickListener, OnApiResponseListner, onLastItem {


    private FragmentDirectoryBinding mBinding;
    private List<ResponseCompanyDirectory> employeesList, tmpEmployeesList;
    private EmployeeDirectoryAdapter employeeDirectoryAdapter;
    private LinearLayoutManager linearLayout;
    private Call<?> searchApi;
    private Call<?> contactList;
    private int pageNo = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_directory, container, false);
        getDirectoryApi(pageNo, 25);
        return mBinding.getRoot();
    }

    private void setUpEmpDir() {
        tmpEmployeesList = new ArrayList<>();
        linearLayout = new LinearLayoutManager(getActivity());
        mBinding.fragmentDirectoryRv.setLayoutManager(linearLayout);
        employeeDirectoryAdapter = new EmployeeDirectoryAdapter(getActivity(), this, employeesList, this);
        setListner();
        mBinding.fragmentDirectoryRv.setAdapter(new ScaleInAnimationAdapter(employeeDirectoryAdapter));
        mBinding.fragmentDirectoryEdSearch.addTextChangedListener(this);
    }

    private void setListner() {
        mBinding.fragmentDirectoryRv.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayout, mBinding.fragmentDirectoryRv, EmployeeDirectoryFragment.this));
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int n, int i1, int i2) {

        if (charSequence.length() == 0) {
            if (searchApi != null)
                searchApi.cancel();
            mBinding.fragmentDirectoryRv.setVisibility(View.VISIBLE);
            mBinding.frgTvNoData.setVisibility(View.GONE);
            mBinding.frgDirectoryLvLoadingSearch.setVisibility(View.GONE);
            employeeDirectoryAdapter = new EmployeeDirectoryAdapter(getActivity(), this, employeesList, this);
            mBinding.fragmentDirectoryRv.setAdapter(new ScaleInAnimationAdapter(employeeDirectoryAdapter));
            setListner();
            hideSoftKeyboard(mBinding.getRoot().findFocus());
        } else {
            if (searchApi != null)
                searchApi.cancel();
            tmpEmployeesList.clear();
            mBinding.fragmentDirectoryRv.setVisibility(View.GONE);
            mBinding.fragmentDirectoryRv.setOnScrollListener(null);
            mBinding.frgDirectoryLvLoadingSearch.setVisibility(View.VISIBLE);
            doSearch(charSequence.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {


    }

    private void getDirectoryApi(int pageNumber, int ilimit) {
        if (employeesList == null) {
            showDialog();
        } else {
            mBinding.frgDirectoryLvLoadingMore.setVisibility(View.VISIBLE);
        }
        contactList = ((HomeActivity) getActivity()).getApiTask().doGetCompanyDirectory(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                pageNumber,
                ilimit,
                this
        );
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode,int responseCode) {
        if (employeesList == null) {
            dismissDialog();
        } else {
            mBinding.frgDirectoryLvLoadingMore.setVisibility(View.GONE);
        }

        if (clsGson != null && requestCode == RequestCode.COMPANYDIRECTORY) {
            List<ResponseCompanyDirectory> directories = (List<ResponseCompanyDirectory>) clsGson;
            if (employeesList == null) {
                employeesList = directories;
                setUpEmpDir();
            } else {
                employeesList.addAll(directories);
                employeeDirectoryAdapter.notifyDataSetChanged();
            }
        } else if (clsGson != null && requestCode == RequestCode.SEARCHEMPLOYEE) {
            List<ResponseCompanyDirectory> directories = (List<ResponseCompanyDirectory>) clsGson;
            tmpEmployeesList = directories;
            if (tmpEmployeesList.size() > 0) {
                mBinding.fragmentDirectoryRv.setVisibility(View.VISIBLE);
                mBinding.frgTvNoData.setVisibility(View.GONE);
                employeeDirectoryAdapter = new EmployeeDirectoryAdapter(getActivity(), this, tmpEmployeesList, this);
                mBinding.fragmentDirectoryRv.setAdapter(new ScaleInAnimationAdapter(employeeDirectoryAdapter));
                mBinding.frgDirectoryLvLoadingSearch.setVisibility(View.GONE);
            } else {
                mBinding.fragmentDirectoryRv.setVisibility(View.GONE);
                mBinding.frgTvNoData.setVisibility(View.VISIBLE);
                mBinding.frgDirectoryLvLoadingSearch.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {


        if (employeesList == null && requestCode == RequestCode.COMPANYDIRECTORY) {
            dismissDialog();
        } else {
            mBinding.frgDirectoryLvLoadingMore.setVisibility(View.GONE);
        }
        if (errorMessage != null && errorMessage.equals("Socket closed") || errorMessage.equals("Canceled")) {

        } else if (requestCode == RequestCode.SEARCHEMPLOYEE) {
            mBinding.frgDirectoryLvLoadingSearch.setVisibility(View.GONE);
            mBinding.frgTvNoData.setVisibility(View.VISIBLE);
            if (errorMessage != null && getActivity() != null)
                ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
        } else if (errorMessage != null && getActivity() != null)
            ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideSoftKeyboard(mBinding.getRoot().findFocus());
        if (searchApi != null)
            searchApi.cancel();
        if (contactList != null)
            contactList.cancel();
    }

    @Override
    public void onLastItem(int pageNumber, int ilimit) {
        pageNo++;
        getDirectoryApi(pageNo, ilimit);
    }

    public void doSearch(String keyword) {
        searchApi = ((HomeActivity) getActivity()).getApiTask().doSearchEmployee(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                keyword,
                this
        );
    }
}
