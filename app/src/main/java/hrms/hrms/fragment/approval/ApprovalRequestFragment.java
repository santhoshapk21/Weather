package hrms.hrms.fragment.approval;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentApprovalBinding;

import hrms.hrms.activity.HomeActivity;
import hrms.hrms.adapter.approvals.ApprovalHeaderAdapter;
import hrms.hrms.adapter.approvals.ApprovalsAttendanceCorrectionRequestAdapter;
import hrms.hrms.adapter.approvals.ApprovalsDataLeaveAdapter;
import hrms.hrms.adapter.approvals.ApprovalsExpenseClaimAdapter;
import hrms.hrms.adapter.approvals.ApprovalsMarkAttendanceAdapter;
import hrms.hrms.adapter.approvals.ApprovalsReqularizeAttendanceAdapter;
import hrms.hrms.adapter.approvals.ApprovalsShiftChangeAdapter;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.fragment.DashBoaredFragment;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.retrofit.model.approvals.leave.ResponseActionApproval;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalAttendanceCorrection;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalCount;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalExpenseClaim;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalLeave;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalMarkAttendance;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalRegularizationAttendance;
import hrms.hrms.retrofit.model.approvals.leave.ResponseApprovalShiftChange;
import hrms.hrms.widget.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;

import static android.view.View.GONE;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


public class ApprovalRequestFragment extends BaseFragment implements View.OnClickListener, OnApiResponseListner {

    private FragmentApprovalBinding mBinding;
    private ApprovalHeaderAdapter approvalHeaderAdapter;
    private Calendar calendar;
    private LinearLayoutManager horizontalHeaderlayoutManager, linearLayoutManager;
    private Call<?> leaveCall;
    private boolean isDateChange;
    private Animation transition_up, transition_down;
    private Animation invisibleToVisible;
    private boolean isCancelIcon;
    private ApprovalsDataLeaveAdapter leaveAdapter;
    private ApprovalsShiftChangeAdapter shiftChangeAdapter;
    private ApprovalsAttendanceCorrectionRequestAdapter attendanceCorrectionAdapter;
    private ApprovalsExpenseClaimAdapter approvalsExpenseClaimAdapter;
    private List<ResponseApprovalExpenseClaim.Detail> responseApprovalExpenseClaims;
    private List<ResponseApprovalLeave.Details> responseApprovalLeaves;
    private List<ResponseApprovalShiftChange.Details> responseApprovalShiftChnage;
    private List<ResponseApprovalAttendanceCorrection.Details> responseApprovalAttendanceCorrections;
    private List<ResponseApprovalRegularizationAttendance.Details> responseApprovalRegularizationAttendances;
    private List<ResponseApprovalMarkAttendance.Details> responseApprovalMarkAttendance;
    private Call getRequestLeaveCancel;
    private String leaveid;
    private String typeofapproval;
    private boolean isOpened = false;
    private Call<?> shiftChangeCall;
    private Call<?> attendanceCorrectionCall;
    private ApprovalsReqularizeAttendanceAdapter attendanceRegulariztionAdapter;
    private Call<?> attendanceRegularizationCall;
    private boolean isContinue = false;
    private String type = "leave";
    private int temp;
    private Call<?> markAttendanceCall;
    private Call<?> expenseClaimCall;
    private ApprovalsMarkAttendanceAdapter attendanceMarkAdapter;
    private Call<?> getRequestMarkAttendanceCancel;
    private int pageNumber = 1;
    private int limit = 25;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_approval, container, false);
        ButterKnife.bind(this, mBinding.getRoot());

        approvalCountApi();

        linearLayoutManager = new LinearLayoutManager(getContext());
        mBinding.frgApprovalsRvListRequests.setLayoutManager(linearLayoutManager);

        mBinding.frgApprovalsRvListRequests.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager,
                mBinding.frgApprovalsRvListRequests, new EndlessRecyclerOnScrollListener.onLastItem() {
            @Override
            public void onLastItem() {
                if (isContinue && type.toLowerCase().equals(getString(R.string.approval_type_leave))) {
                    pageNumber = pageNumber + 1;
                    onLeaveApiCall(pageNumber);

                } else if (isContinue && type.toLowerCase().equals(getString(R.string.approval_type_shift))) {
                    pageNumber = pageNumber + 1;
                    onShiftChangepiCall(pageNumber);

                } else if (isContinue && type.toLowerCase().equals(getString(R.string.approval_type_correction))) {
                    pageNumber = pageNumber + 1;
                    doGetApprovalCorrectionList(pageNumber);

                } else if (isContinue && type.toLowerCase().equals(getString(R.string.approval_type_regularization))) {
                    pageNumber = pageNumber + 1;
                    doGetApprovalRegularisationList(pageNumber);

                } else if (isContinue && type.toLowerCase().equals(getString(R.string.approval_type_mark_attendence))) {
                    pageNumber = pageNumber + 1;
                    doGetApprovalMarkAttendanceList(pageNumber);

                } else if (isContinue && type.toLowerCase().equalsIgnoreCase(getString(R.string.approval_type_expense_claim))) {
                    pageNumber = pageNumber + 1;
                    doGetApprovalExpenseClaimList(pageNumber);

                }
            }
        }));

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        setHeaderAdapter();
        setOnAnimationListner();
        onLeaveApiCall(1);
//        onShiftChangepiCall(1);
//        doGetApprovalCorrectionList(1);
//        doGetApprovalRegularisationList(1);
//        doGetApprovalMarkAttendanceList(1);
    }


    private void onLeaveApiCall(int itemCount) {
        showDialog();
        cancelCall();
        leaveCall = ((HomeActivity) getActivity()).getApiTask().doGetApprovalLeaveList(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN)
                , itemCount
                , limit
                , this
        );
    }

    private void onShiftChangepiCall(int itemCount) {
        showDialog();
        cancelCall();
        shiftChangeCall = ((HomeActivity) getActivity()).getApiTask().doGetApprovalShiftList(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN)
                , itemCount
                , limit
                , this
        );
    }

    private void doGetApprovalCorrectionList(int itemCount) {
        showDialog();
        cancelCall();

        attendanceCorrectionCall = ((HomeActivity) getActivity()).getApiTask().doGetApprovalCorrectionList(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN)
                , itemCount
                , limit
                , this
        );
    }

    private void doGetApprovalRegularisationList(int itemCount) {
        showDialog();
        cancelCall();

        attendanceRegularizationCall = ((HomeActivity) getActivity()).getApiTask().doGetApprovalRegularisationList(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN)
                , itemCount
                , limit
                , this
        );
    }

    private void doGetApprovalMarkAttendanceList(int itemCount) {
        showDialog();
        cancelCall();

        markAttendanceCall = ((HomeActivity) getActivity()).getApiTask().doGetApprovalMarkAttendanceList(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN)
                , itemCount
                , 10
                , this
        );
    }

    private void doGetApprovalExpenseClaimList(int itemCount) {
        showDialog();
        cancelCall();
        expenseClaimCall = ((HomeActivity) getActivity()).getApiTask().getApprovalExpenseClaimList(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN)
                , itemCount
                , 10
                , this);
    }

    private void setOnAnimationListner() {
        transition_up.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mBinding.frgApprovalsLlDialogLeaveSelected.setVisibility(View.VISIBLE);
                if (mBinding.fragmentApprovalsRequestCbSelectAll.isChecked()) {
                    mBinding.textView.setText("All Record Selected");
                } else {
                    mBinding.textView.setText(temp + " Record Selected");
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        transition_down.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBinding.frgApprovalsLlDialogLeaveSelected.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void startAnimationSnackBarView(boolean isTransitionUp) {
        if (isTransitionUp) {
            isOpened = true;
            mBinding.frgApprovalsLlDialogLeaveSelected.startAnimation(transition_up);
            mBinding.frgApprovalsTvAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(HomeActivity.context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(HomeActivity.context);
                    }
                    builder.setTitle("Are you sure you want to approve?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (type.toLowerCase().equals("markattendance")) {
                                        getMarkAttendanceActionApproval("Approve");
                                    } else {
                                        getActionApproval("Approve");
                                    }
                                    // continue with delete
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dismissDialog();
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
            mBinding.frgApprovalsTvReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(HomeActivity.context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(HomeActivity.context);
                    }
                    builder.setTitle("Are you sure you want to reject?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (type.toLowerCase().equals("markattendance")) {
                                        getMarkAttendanceActionApproval("Reject");
                                    } else {
                                        getActionApproval("Reject");
                                    }
                                    // continue with delete
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dismissDialog();
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });

        } else {
            isOpened = false;
            mBinding.frgApprovalsLlDialogLeaveSelected.startAnimation(transition_down);
        }
    }

    private void init() {
        responseApprovalMarkAttendance = new ArrayList<>();
        responseApprovalExpenseClaims = new ArrayList<>();
        responseApprovalAttendanceCorrections = new ArrayList<>();
        responseApprovalLeaves = new ArrayList<>();
        responseApprovalShiftChnage = new ArrayList<>();
        responseApprovalRegularizationAttendances = new ArrayList<>();
        calendar = Calendar.getInstance();
//        mBinding.frgApprovalsTvFilterMonth.setText(getDate());
        transition_up = AnimationUtils.loadAnimation(getContext(), R.anim.transition_up);
        transition_down = AnimationUtils.loadAnimation(getContext(), R.anim.transition_down);
        invisibleToVisible = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_invisible_to_visible);

        mBinding.fragmentApprovalsRequestCbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (approvalHeaderAdapter.getSelectedPosition()) {
                    case 0:
                        if (leaveAdapter != null) {
                            if (isChecked) {
                                leaveAdapter.selectAll();
                                startAnimationSnackBarView(true);
                            } else {
                                leaveAdapter.deSelectAll();
                                startAnimationSnackBarView(false);
                            }
                        }
                        break;

                    case 1:
                        if (shiftChangeAdapter != null) {
                            if (isChecked) {
                                shiftChangeAdapter.selectAll();
                                startAnimationSnackBarView(true);
                            } else {
                                shiftChangeAdapter.deSelectAll();
                                startAnimationSnackBarView(false);
                            }
                        }
                        break;

                    case 2:
                        if (attendanceCorrectionAdapter != null) {
                            if (isChecked) {
                                attendanceCorrectionAdapter.selectAll();
                                startAnimationSnackBarView(true);
                            } else {
                                attendanceCorrectionAdapter.deSelectAll();
                                startAnimationSnackBarView(false);
                            }
                        }
                        break;

                    case 3:
                        if (attendanceRegulariztionAdapter != null) {
                            if (isChecked) {
                                attendanceRegulariztionAdapter.selectAll();
                                startAnimationSnackBarView(true);
                            } else {
                                attendanceRegulariztionAdapter.deSelectAll();
                                startAnimationSnackBarView(false);
                            }
                        }
                        break;

                    case 4:
                        if (attendanceMarkAdapter != null) {
                            if (isChecked) {
                                attendanceMarkAdapter.selectAll();
                                startAnimationSnackBarView(true);
                            } else {
                                attendanceMarkAdapter.deSelectAll();
                                startAnimationSnackBarView(false);
                            }
                        }
                        break;
                    case 5:
                        if (approvalsExpenseClaimAdapter != null) {
                            if (isChecked) {
                                approvalsExpenseClaimAdapter.selectAll();
                                startAnimationSnackBarView(true);
                            } else {
                                approvalsExpenseClaimAdapter.deSelectAll();
                                startAnimationSnackBarView(false);
                            }
                        }
                        break;
                }
            }
        });
    }

    private void setHeaderAdapter() {
        approvalHeaderAdapter = new ApprovalHeaderAdapter(getContext(), ApprovalRequestFragment.this);
        horizontalHeaderlayoutManager = new LinearLayoutManager(getContext());
        horizontalHeaderlayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.frgApprovalsRvHeader.setLayoutManager(horizontalHeaderlayoutManager);
        mBinding.frgApprovalsRvHeader.setAdapter(approvalHeaderAdapter);
    }

//    private String getDate() {
//        String date = null;
//        try {
//            date = getFormatDate(
//                    "MM-yyyy",
//                    "MMMM yyyy",
//                    String.format("%02d", calendar.get(Calendar.MONTH) + 1)
//                            + "-" + calendar.get(Calendar.YEAR));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return (date == null) ? "" : date;
//    }

    @Override
    public void onClick(View v) {
        if (isDateChange)
            isDateChange = false;
        else
            calendar = Calendar.getInstance();

        if (leaveCall != null)
            leaveCall.cancel();
        pageNumber = 1;
        switch ((Integer) v.getTag()) {
            case 0:
                type = getString(R.string.approval_type_leave);
                onLeaveApiCall(pageNumber);
                mBinding.fragmentApprovalsRequestCbSelectAll.setChecked(false);
                startAnimationSnackBarView(false);
                if (leaveAdapter == null)
                    setNoDataView();
                else
                    setLeaveAdapter();
                break;

            case 1:
                type = getString(R.string.approval_type_shift);
                onShiftChangepiCall(pageNumber);
                mBinding.fragmentApprovalsRequestCbSelectAll.setChecked(false);
                startAnimationSnackBarView(false);
                if (shiftChangeAdapter == null)
                    setNoDataView();
                else
                    setShiftChangeAdapter();
                break;

            case 2:
                type = getString(R.string.approval_type_correction);
                doGetApprovalCorrectionList(pageNumber);
                mBinding.fragmentApprovalsRequestCbSelectAll.setChecked(false);
                startAnimationSnackBarView(false);
                if (responseApprovalAttendanceCorrections == null)
                    setNoDataView();
                else
                    setAttendanceCorrectionAdapter();
                break;

            case 3:
                type = getString(R.string.approval_type_regularization);
                doGetApprovalRegularisationList(pageNumber);
                mBinding.fragmentApprovalsRequestCbSelectAll.setChecked(false);
                startAnimationSnackBarView(false);
                if (responseApprovalRegularizationAttendances == null)
                    setNoDataView();
                else
                    setRegularizationAttendanceAdapter();
                break;

            case 4:
                type = getString(R.string.approval_type_mark_attendence);
                doGetApprovalMarkAttendanceList(pageNumber);
                mBinding.fragmentApprovalsRequestCbSelectAll.setChecked(false);
                startAnimationSnackBarView(false);
                if (responseApprovalMarkAttendance == null)
                    setNoDataView();
                else
                    setMarkAttendanceAdapter();
                break;
            case 5:
                type = getString(R.string.approval_type_expense_claim);
                doGetApprovalExpenseClaimList(pageNumber);
                mBinding.fragmentApprovalsRequestCbSelectAll.setChecked(false);
                startAnimationSnackBarView(false);
                if (responseApprovalExpenseClaims == null)
                    setNoDataView();
                else
                    setExpenseClaimAdapter();
                break;

        }
//        mBinding.frgApprovalsTvFilterMonth.setText(getDate());
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {
        dismissDialog();
        if (requestCode == RequestCode.APPROVALLEAVE) {
            responseApprovalLeaves.addAll(((ResponseApprovalLeave) clsGson).getDetails());

            isContinue = ((ResponseApprovalLeave) clsGson).getIsNextPage();
            if (responseApprovalLeaves != null && responseApprovalLeaves.size() > 0) {
                if (leaveAdapter == null) {
                    setLeaveAdapter();
                } else {
                    leaveAdapter.set(responseApprovalLeaves);
                }
                leaveAdapter.deSelectAll();
            } else {
                setNoDataView();
            }
        }

        if (requestCode == RequestCode.APPROVALSHIFT) {
            responseApprovalShiftChnage.addAll(((ResponseApprovalShiftChange) clsGson).details);

            isContinue = ((ResponseApprovalShiftChange) clsGson).IsNextPage;
            if (responseApprovalShiftChnage != null && responseApprovalShiftChnage.size() > 0) {
                if (shiftChangeAdapter == null) {
                    setShiftChangeAdapter();
                } else {
                    shiftChangeAdapter.set(responseApprovalShiftChnage);
                }
                shiftChangeAdapter.deSelectAll();
            } else {
                setNoDataView();
            }
        }

        if (requestCode == RequestCode.APPROVALCORRECTION) {
            responseApprovalAttendanceCorrections.addAll(((ResponseApprovalAttendanceCorrection) clsGson).details);

            isContinue = ((ResponseApprovalAttendanceCorrection) clsGson).IsNextPage;

            if (responseApprovalAttendanceCorrections != null && responseApprovalAttendanceCorrections.size() > 0) {
                if (attendanceCorrectionAdapter == null) {
                    setAttendanceCorrectionAdapter();
                } else {
                    attendanceCorrectionAdapter.set(responseApprovalAttendanceCorrections);
                }
                attendanceCorrectionAdapter.deSelectAll();
            } else {
                setNoDataView();
            }
        }

        if (requestCode == RequestCode.APPROVALREGULARISATION) {
            responseApprovalRegularizationAttendances.addAll(((ResponseApprovalRegularizationAttendance) clsGson).details);

            isContinue = ((ResponseApprovalRegularizationAttendance) clsGson).IsNextPage;
            if (responseApprovalRegularizationAttendances != null && responseApprovalRegularizationAttendances.size() > 0) {
                if (attendanceRegulariztionAdapter == null) {
                    setRegularizationAttendanceAdapter();
                } else {
                    attendanceRegulariztionAdapter.set(responseApprovalRegularizationAttendances);
                }
                attendanceRegulariztionAdapter.deSelectAll();
            } else {
                setNoDataView();
            }
        }

        if (requestCode == RequestCode.APPROVALMARKATTENDANCE) {
            responseApprovalMarkAttendance.addAll(((ResponseApprovalMarkAttendance) clsGson).details);
            isContinue = ((ResponseApprovalMarkAttendance) clsGson).IsNextPage;
            if (responseApprovalMarkAttendance != null && responseApprovalMarkAttendance.size() > 0) {
                if (attendanceMarkAdapter == null) {
                    setMarkAttendanceAdapter();
                } else {
                    attendanceMarkAdapter.set(responseApprovalMarkAttendance);
                }
                attendanceMarkAdapter.deSelectAll();
            } else {
                setNoDataView();
            }
        }

        if (requestCode == RequestCode.APPROVAL_EXPENSE_CLAIM) {
            responseApprovalExpenseClaims.addAll(((ResponseApprovalExpenseClaim) clsGson).details);
            isContinue = ((ResponseApprovalExpenseClaim) clsGson).isNextPage;
            if (responseApprovalExpenseClaims != null && responseApprovalExpenseClaims.size() > 0) {
                if (approvalsExpenseClaimAdapter == null) {
                    setExpenseClaimAdapter();
                } else {
                    approvalsExpenseClaimAdapter.set(responseApprovalExpenseClaims);
                }
                approvalsExpenseClaimAdapter.deSelectAll();
            } else {
                setNoDataView();
            }
        }


        if (requestCode == RequestCode.ACTIONAPPROVAL) {
            ResponseActionApproval user = new ResponseActionApproval();
            if (responseCode == 200) {
                if (type.toLowerCase().equals("leave")) {
                    responseApprovalLeaves.clear();
                    leaveAdapter = null;
                    onLeaveApiCall(1);
                    startAnimationSnackBarView(false);
                } else if (type.toLowerCase().equals("shift")) {
                    responseApprovalShiftChnage.clear();
                    shiftChangeAdapter = null;
                    onShiftChangepiCall(1);
                    startAnimationSnackBarView(false);
                } else if (type.toLowerCase().equals("correction")) {
                    responseApprovalAttendanceCorrections.clear();
                    attendanceCorrectionAdapter = null;
                    doGetApprovalCorrectionList(1);
                    startAnimationSnackBarView(false);
                } else if (type.toLowerCase().equals("regularization")) {
                    responseApprovalRegularizationAttendances.clear();
                    attendanceRegulariztionAdapter = null;
                    doGetApprovalRegularisationList(1);
                    startAnimationSnackBarView(false);
                }
                approvalCountApi();
            }
        }

        if (requestCode == RequestCode.ACTIONAPPROVALMARKATTENDANCE) {
            ResponseActionApproval user = new ResponseActionApproval();
            if (responseCode == 200) {
                responseApprovalMarkAttendance = null;
                attendanceMarkAdapter = null;
                approvalCountApi();
                doGetApprovalMarkAttendanceList(1);
                startAnimationSnackBarView(false);
            }
        }


        if (requestCode == RequestCode.APPROVALCOUNT) {
            ResponseApprovalCount model = (ResponseApprovalCount) clsGson;
            approvalHeaderAdapter.setCounts(model.details.get(0).PendingLeave,
                    model.details.get(0).Shift,
                    model.details.get(0).Correction,
                    model.details.get(0).Regularisation,
                    model.details.get(0).Attendance,
                    model.details.get(0).ExpenseClaim );
        }

    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        dismissDialog();
        if (!isContinue) {
            setNoDataView();
        }
        Log.d("onResponseError", "onResponseError: " + errorMessage);
        if (errorMessage != null && errorMessage.equals("Socket closed") || errorMessage.equals("Canceled")) {

        } else if ((HomeActivity) getActivity() != null) {

            ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), errorMessage, Snackbar.LENGTH_SHORT);
        }
    }

    private void setLeaveAdapter() {
        setDataView();
        leaveAdapter = new ApprovalsDataLeaveAdapter(getContext(),
                responseApprovalLeaves,
                this, (HomeActivity) getActivity(), new ApprovalsDataLeaveAdapter.ApprovalClickListener() {
            @Override
            public void onApprovalClicked(String type) {
                typeofapproval = type;
                leaveid = null;
                for (int i = 0; i < responseApprovalLeaves.size(); i++) {
                    if (responseApprovalLeaves.get(i).isSelected()) {
                        if (leaveid != null) {
                            leaveid = leaveid + "," + responseApprovalLeaves.get(i).getLeaveAppId();
                            temp = temp + 1;
                        } else {
                            leaveid = responseApprovalLeaves.get(i).getLeaveAppId();
                            temp = 1;
                        }
                    }
                }
/*
                if (temp == responseApprovalLeaves.size()) {
                    mBinding.fragmentApprovalsRequestCbSelectAll.setChecked(true);
                }
*/
                if (leaveid != null) {
                    startAnimationSnackBarView(true);
                } else {
                    startAnimationSnackBarView(false);
                }
                Log.v("OkHttp", leaveid + "");
                Log.v("OkHttp", temp + "");
            }
        });

        mBinding.frgApprovalsRvListRequests.setAdapter(leaveAdapter);
    }

    private void setShiftChangeAdapter() {
        setDataView();
        shiftChangeAdapter = new ApprovalsShiftChangeAdapter(getContext(),
                responseApprovalShiftChnage,
                this, (HomeActivity) getActivity(), new ApprovalsShiftChangeAdapter.ApprovalClickListener() {
            @Override
            public void onApprovalClicked(String type) {
                typeofapproval = type;
                leaveid = null;
                for (int i = 0; i < responseApprovalShiftChnage.size(); i++) {
                    if (responseApprovalShiftChnage.get(i).isSelected()) {
                        if (leaveid != null) {
                            leaveid = leaveid + "," + responseApprovalShiftChnage.get(i).ShiftRequestId;
                            temp = temp + 1;
                        } else {
                            leaveid = responseApprovalShiftChnage.get(i).ShiftRequestId;
                            temp = 1;
                        }
                    }
                }
                if (leaveid != null) {
                    startAnimationSnackBarView(true);
                } else {
                    startAnimationSnackBarView(false);
                }
                Log.v("OkHttp", leaveid + "");
                Log.v("OkHttp", temp + "");
            }
        });
        mBinding.frgApprovalsRvListRequests.setAdapter(shiftChangeAdapter);
    }

    private void setAttendanceCorrectionAdapter() {
        setDataView();
        attendanceCorrectionAdapter = new ApprovalsAttendanceCorrectionRequestAdapter(getContext(),
                responseApprovalAttendanceCorrections,
                this, (HomeActivity) getActivity(), new ApprovalsAttendanceCorrectionRequestAdapter.ApprovalClickListener() {
            @Override
            public void onApprovalClicked(String type) {
                typeofapproval = type;
                leaveid = null;
                for (int i = 0; i < responseApprovalAttendanceCorrections.size(); i++) {
                    if (responseApprovalAttendanceCorrections.get(i).isSelected()) {
                        if (leaveid != null) {
                            leaveid = leaveid + "," + responseApprovalAttendanceCorrections.get(i).CorrectionReqId;
                            temp = temp + 1;
                        } else {
                            leaveid = responseApprovalAttendanceCorrections.get(i).CorrectionReqId;
                            temp = 1;
                        }
                    }
                }
                if (leaveid != null) {
                    startAnimationSnackBarView(true);
                } else {
                    startAnimationSnackBarView(false);
                }
                Log.v("OkHttp", leaveid + "");
                Log.v("OkHttp", temp + "");
            }
        });
        mBinding.frgApprovalsRvListRequests.setAdapter(attendanceCorrectionAdapter);
    }

    private void setRegularizationAttendanceAdapter() {
        setDataView();
        attendanceRegulariztionAdapter = new ApprovalsReqularizeAttendanceAdapter(getContext(),
                responseApprovalRegularizationAttendances,
                this, (HomeActivity) getActivity(),
                new ApprovalsReqularizeAttendanceAdapter.ApprovalClickListener() {
                    @Override
                    public void onApprovalClicked(String type) {
                        typeofapproval = type;
                        leaveid = null;
                        for (int i = 0; i < responseApprovalRegularizationAttendances.size(); i++) {
                            if (responseApprovalRegularizationAttendances.get(i).isSelected()) {
                                if (leaveid != null) {
                                    leaveid = leaveid + "," + responseApprovalRegularizationAttendances.get(i).RegularisationRequestId;
                                    temp = temp + 1;

                                } else {
                                    leaveid = responseApprovalRegularizationAttendances.get(i).RegularisationRequestId;
                                    temp = 1;
                                }
                            }
                        }
                        if (leaveid != null) {
                            startAnimationSnackBarView(true);
                        } else {
                            startAnimationSnackBarView(false);
                        }
                        Log.v("OkHttp", leaveid + "");
                        Log.v("OkHttp", temp + "");
                    }
                });
        mBinding.frgApprovalsRvListRequests.setAdapter(attendanceRegulariztionAdapter);
    }

    private void setMarkAttendanceAdapter() {
        setDataView();
        attendanceMarkAdapter = new ApprovalsMarkAttendanceAdapter(getContext(),
                responseApprovalMarkAttendance,
                this, (HomeActivity) getActivity(),
                new ApprovalsMarkAttendanceAdapter.ApprovalClickListener() {
                    @Override
                    public void onApprovalClicked(String type) {
                        typeofapproval = type;
                        leaveid = null;
                        for (int i = 0; i < responseApprovalMarkAttendance.size(); i++) {
                            if (responseApprovalMarkAttendance.get(i).isSelected()) {
                                if (leaveid != null) {
                                    leaveid = leaveid + "," + responseApprovalMarkAttendance.get(i).GPSAttendanceID;
                                    temp = temp + 1;

                                } else {
                                    leaveid = responseApprovalMarkAttendance.get(i).GPSAttendanceID;
                                    temp = 1;
                                }
                            }
                        }
                        if (leaveid != null) {
                            startAnimationSnackBarView(true);
                        } else {
                            startAnimationSnackBarView(false);
                        }
                        Log.v("OkHttp", leaveid + "");
                        Log.v("OkHttp", temp + "");
                    }
                });
        mBinding.frgApprovalsRvListRequests.setAdapter(attendanceMarkAdapter);
    }

    private void setExpenseClaimAdapter() {
        setDataView();
        approvalsExpenseClaimAdapter = new ApprovalsExpenseClaimAdapter(getContext(),
                responseApprovalExpenseClaims,
                this, (HomeActivity) getActivity(),
                new ApprovalsExpenseClaimAdapter.ApprovalClickListener() {
                    @Override
                    public void onApprovalClicked(String type) {
                        typeofapproval = type;
//                        leaveid = null;
//                        for (int i = 0; i < responseApprovalExpenseClaims.size(); i++) {
//                            if (responseApprovalExpenseClaims.get(i).isSelected) {
//                                if (leaveid != null) {
//                                    leaveid = leaveid + "," + responseApprovalMarkAttendance.get(i).GPSAttendanceID;
//                                    temp = temp + 1;
//
//                                } else {
//                                    leaveid = responseApprovalMarkAttendance.get(i).GPSAttendanceID;
//                                    temp = 1;
//                                }
//                            }
//                        }
//                        if (leaveid != null) {
//                            startAnimationSnackBarView(true);
//                        } else {
//                            startAnimationSnackBarView(false);
//                        }
//                        Log.v("OkHttp", leaveid + "");
//                        Log.v("OkHttp", temp + "");
                    }
                });
        mBinding.frgApprovalsRvListRequests.setAdapter(approvalsExpenseClaimAdapter);
    }

    private void setNoDataView() {
        mBinding.fragmentApprovalsRequestCbSelectAll.setClickable(false);
        mBinding.frgLlNoData.setVisibility(View.VISIBLE);
        mBinding.frgApprovalsRvListRequests.setVisibility(View.GONE);
    }

    private void setDataView() {
        mBinding.fragmentApprovalsRequestCbSelectAll.setClickable(true);
        mBinding.frgLlNoData.setVisibility(View.GONE);
        mBinding.frgApprovalsRvListRequests.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (currentFragment instanceof DashBoaredFragment) {
            ((DashBoaredFragment) currentFragment).requestCountApi();
            ((DashBoaredFragment) currentFragment).approvalCountApi();
        }
    }

    private void getActionApproval(String status) {
        showDialog();
        getRequestLeaveCancel = ((HomeActivity) getActivity()).getApiTask().getActionApproval(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                "",
                leaveid,
                status,
                typeofapproval,
                this
        );
    }

    private void getMarkAttendanceActionApproval(String status) {
        showDialog();
        getRequestMarkAttendanceCancel = ((HomeActivity) getActivity()).getApiTask().getMarkAttendanceActionApproval(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                "",
                leaveid,
                status,
                status,
                this
        );
    }

    public void approvalCountApi() {
        ((HomeActivity) getActivity()).getApiTask().getApprovalCount(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                this
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        cancelCall();
    }

    private void cancelCall() {
        if (leaveCall != null)
            leaveCall.cancel();
        if (attendanceCorrectionCall != null)
            attendanceCorrectionCall.cancel();
        if (attendanceRegularizationCall != null)
            attendanceRegularizationCall.cancel();
        if (expenseClaimCall != null)
            expenseClaimCall.cancel();
        if (shiftChangeCall != null)
            shiftChangeCall.cancel();
        if (markAttendanceCall != null)
            markAttendanceCall.cancel();

    }
}
