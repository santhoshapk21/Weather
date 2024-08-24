package hrms.hrms.fragment.claims.details;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.hris365.R;
import com.hris365.databinding.FragmentNewClaimBinding;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.widget.Spinner;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import hrms.hris365.pickerview.popwindow.DatePickerPopWin;
import hrms.hrms.activity.HomeActivity;
import hrms.hrms.baseclass.BaseAppCompactActivity;
import hrms.hrms.baseclass.BaseFragment;
import hrms.hrms.enums.TravelMode;
import hrms.hrms.fragment.claims.ClaimRequestType;
import hrms.hrms.interfaces.OnBackPressListerner;
import hrms.hrms.interfaces.OnDateDialogListener;
import hrms.hrms.model.AddClaimDetails;
import hrms.hrms.model.Claim;
import hrms.hrms.model.ClaimDetails;
import hrms.hrms.model.ClaimExpenseResponse;
import hrms.hrms.model.ModeOfTravel;
import hrms.hrms.model.ModelOfTravelDetail;
import hrms.hrms.retrofit.OnApiResponseListner;
import hrms.hrms.retrofit.RequestCode;
import hrms.hrms.utility.Constants;
import hrms.hrms.utility.Utility;
import hrms.hrms.widget.SpinnerAdapter;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Yudiz on 07/10/16.
 */
@SuppressLint("ValidFragment")
public class NewClaimsFragment extends BaseFragment implements View.OnClickListener, OnBackPressListerner, OnApiResponseListner {

    private final Claim claim;
    private FragmentNewClaimBinding mBinding;
    private ClaimDetailsAdapter claimAdapter;
    private List<ClaimDetails> claimDetailsList;
    private SpinnerAdapter arrayAdapter;
    private OnBackPressListerner onBackPressListerner;
    //    private Claim ExpenseClaim;
    private AddClaimDetails addClaimDetails;
    private ClaimExpenseResponse claimExpenseResponse;

    private int claimchoosentype = -1;
    public Calendar calendar;
    ModeOfTravel modeOfTravel;
    String selectedmodeOfTravelID;
    String selectedmodeofTravelName;
    String fromdate;
    String todate;
    String expensemonth;
    String expenseyear;

    public NewClaimsFragment(OnBackPressListerner onBackPressListerner) {
        this.onBackPressListerner = onBackPressListerner;
        claim = new Claim();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_claim, container, false);
        setChoosenType();
        ButterKnife.bind(this, mBinding.getRoot());
        setUpView();
        SetUpVisiblity();
        mBinding.fragmentNewClaimTvFormDate.setText(Utility.getTodayDate());
        mBinding.fragmentNewClaimTvToDate.setText(Utility.getTodayDate());
        fromdate = Utility.getTodayDateinFormat2();
        todate = Utility.getTodayDateinFormat2();
        mBinding.fragmentNewClaimIvAddClaimDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoClaimDetail();
            }
        });
        mBinding.fragmentNewClaimLlPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTravelMode(TravelMode.PLANE);
            }
        });
        mBinding.fragmentNewClaimLlTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTravelMode(TravelMode.PLANE);
            }
        });
        mBinding.fragmentNewClaimLlBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTravelMode(TravelMode.PLANE);
            }
        });
        mBinding.fragmentNewClaimLlBikeCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTravelMode(TravelMode.PLANE);
            }
        });
        mBinding.fragmentNewClaimRlFormDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                if (!mBinding.fragmentNewClaimTvToDate.getText().toString().trim().equals("")) {

                    if (!mBinding.fragmentNewClaimTvFormDate.getText().toString().trim().equals("")) {
                        Calendar start = Utility.getFormatedDate(mBinding.fragmentNewClaimTvFormDate.getText().toString().trim(), Constants.DATE_FORMAT1);
                        Calendar end = Utility.getFormatedDate(mBinding.fragmentNewClaimTvToDate.getText().toString().trim(), Constants.DATE_FORMAT1);
                        Utility.showDateDialogNew(getActivity(), 1, 0, cal.get(Calendar.YEAR) - 50, end.get(Calendar.DAY_OF_MONTH),
                                end.get(Calendar.MONTH), end.get(Calendar.YEAR), start.get(Calendar.DAY_OF_MONTH), start.get(Calendar.MONTH), start.get(Calendar.YEAR),
                                new android.app.DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        Calendar mCalendar = Calendar.getInstance();
                                        mCalendar.set(Calendar.YEAR, year);
                                        mCalendar.set(Calendar.MONTH, month);
                                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                        String date1 = getFormattedDate(mCalendar);
                                        mBinding.fragmentNewClaimTvFormDate.setText(date1);
                                        fromdate = getFormattedDate(mCalendar);
                                    }
                                });

                    } else {
                        Calendar end = Utility.getFormatedDate(mBinding.fragmentNewClaimTvToDate.getText().toString().trim(), Constants.DATE_FORMAT1);
                        Utility.showDateDialogNew(getActivity(), 1, 1, cal.get(Calendar.YEAR) - 50, end.get(Calendar.DAY_OF_MONTH), end.get(Calendar.MONTH), end.get(Calendar.YEAR),
                                cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), new android.app.DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        Calendar mCalendar = Calendar.getInstance();
                                        mCalendar.set(Calendar.YEAR, year);
                                        mCalendar.set(Calendar.MONTH, month);
                                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                        String date1 = getFormattedDate(mCalendar);
                                        mBinding.fragmentNewClaimTvFormDate.setText(date1);
                                        fromdate = getFormattedDate(mCalendar);
                                    }
                                });
                    }
                } else {
                    if (!mBinding.fragmentNewClaimTvFormDate.getText().toString().trim().equals("")) {
                        Calendar start = Utility.getFormatedDate(mBinding.fragmentNewClaimTvFormDate.getText().toString().trim(), Constants.DATE_FORMAT1);
                        Utility.showDateDialogNew(getActivity(), 1, 0, cal.get(Calendar.YEAR) - 50, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
                                start.get(Calendar.DAY_OF_MONTH), start.get(Calendar.MONTH), start.get(Calendar.YEAR), new android.app.DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        Calendar mCalendar = Calendar.getInstance();
                                        mCalendar.set(Calendar.YEAR, year);
                                        mCalendar.set(Calendar.MONTH, month);
                                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                        String date1 = getFormattedDate(mCalendar);
                                        mBinding.fragmentNewClaimTvFormDate.setText(date1);
                                        fromdate = getFormattedDate(mCalendar);
                                    }
                                });
                    } else {
                        Utility.showDateDialogNew(getActivity(), 1, 0, cal.get(Calendar.YEAR) - 50, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
                                cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), new android.app.DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        Calendar mCalendar = Calendar.getInstance();
                                        mCalendar.set(Calendar.YEAR, year);
                                        mCalendar.set(Calendar.MONTH, month);
                                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                        String date1 = getFormattedDate(mCalendar);
                                        mBinding.fragmentNewClaimTvFormDate.setText(date1);
                                        fromdate = getFormattedDate(mCalendar);
                                    }
                                });
                    }
                }
            }
        });
        mBinding.fragmentNewClaimRlToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                if (!mBinding.fragmentNewClaimTvFormDate.getText().toString().trim().equals("")) {

                    if (!mBinding.fragmentNewClaimTvToDate.getText().toString().trim().equals("")) {
                        Calendar start = Utility.getFormatedDate(mBinding.fragmentNewClaimTvFormDate.getText().toString().trim(), Constants.DATE_FORMAT1);
                        Calendar end = Utility.getFormatedDate(mBinding.fragmentNewClaimTvToDate.getText().toString().trim(), Constants.DATE_FORMAT1);

                        Utility.showDateDialogNew(getActivity(), start.get(Calendar.DAY_OF_MONTH), start.get(Calendar.MONTH), start.get(Calendar.YEAR) - 50, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
                                end.get(Calendar.DAY_OF_MONTH), end.get(Calendar.MONTH), end.get(Calendar.YEAR), new android.app.DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        Calendar mCalendar = Calendar.getInstance();
                                        mCalendar.set(Calendar.YEAR, year);
                                        mCalendar.set(Calendar.MONTH, month);
                                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                        String date1 = getFormattedDate(mCalendar);
                                        mBinding.fragmentNewClaimTvToDate.setText(date1);
                                        todate = getFormattedDate(mCalendar);
                                    }
                                });
                    } else {
                        Calendar start = Utility.getFormatedDate(mBinding.fragmentNewClaimTvFormDate.getText().toString().trim(), Constants.DATE_FORMAT1);

                        Utility.showDateDialogNew(getActivity(), start.get(Calendar.DAY_OF_MONTH), start.get(Calendar.MONTH), start.get(Calendar.YEAR) - 50, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
                                start.get(Calendar.DAY_OF_MONTH), start.get(Calendar.MONTH), start.get(Calendar.YEAR), new android.app.DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        Calendar mCalendar = Calendar.getInstance();
                                        mCalendar.set(Calendar.YEAR, year);
                                        mCalendar.set(Calendar.MONTH, month);
                                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                        String date1 = getFormattedDate(mCalendar);
                                        mBinding.fragmentNewClaimTvToDate.setText(date1);
                                        todate = getFormattedDate(mCalendar);
                                    }
                                });
                    }
                } else {
                    if (!mBinding.fragmentNewClaimTvToDate.getText().toString().trim().equals("")) {
                        Calendar end = Utility.getFormatedDate(mBinding.fragmentNewClaimTvToDate.getText().toString().trim(), Constants.DATE_FORMAT1);

                        Utility.showDateDialogNew(getActivity(), 1, 0, cal.get(Calendar.YEAR) - 50, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
                                end.get(Calendar.DAY_OF_MONTH), end.get(Calendar.MONTH), end.get(Calendar.YEAR), new android.app.DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        Calendar mCalendar = Calendar.getInstance();
                                        mCalendar.set(Calendar.YEAR, year);
                                        mCalendar.set(Calendar.MONTH, month);
                                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                        String date1 = getFormattedDate(mCalendar);
                                        mBinding.fragmentNewClaimTvToDate.setText(date1);
                                        todate = getFormattedDate(mCalendar);
                                    }
                                });
                    } else {
                        Utility.showDateDialogNew(getActivity(), 1, 0, cal.get(Calendar.YEAR) - 50, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR),
                                cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), new android.app.DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        Calendar mCalendar = Calendar.getInstance();
                                        mCalendar.set(Calendar.YEAR, year);
                                        mCalendar.set(Calendar.MONTH, month);
                                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                        String date1 = getFormattedDate(mCalendar);
                                        mBinding.fragmentNewClaimTvToDate.setText(date1);
                                        todate = getFormattedDate(mCalendar);
                                    }
                                });
                    }
                }
            }
        });
        mBinding.fragmentNewClaimLlEffMonthYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar currentcalender = Calendar.getInstance();
                DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(getContext(), new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                        calendar.set(Calendar.MONTH, month - 1);
                        calendar.set(Calendar.YEAR, year);

                        mBinding.fragmentNewClaimTvMonthYear.setText(getDate());

//                onGetAllRequestApi(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
//                lastSelectedPosition = -1;
                    }
                }).textConfirm(getResources().getString(R.string.str_confirm)) //text of confirm button
                        .colorConfirm(getContext().getResources().getColor(R.color.black))
                        .textCancel(getResources().getString(R.string.str_cancel)) //text of cancel button
                        .minYear(currentcalender.get(Calendar.YEAR) - 100) //min year in loop
                        .maxYear(currentcalender.get(Calendar.YEAR) + 1) // max year in loop
                        .showDayMonthYear(true) // shows like dd mm yyyy (default is false)
                        .dateChose(calendar.get(Calendar.YEAR) + "-" +
                                (calendar.get(Calendar.MONTH) + 1) + "-"
                                + calendar.get(Calendar.DAY_OF_MONTH))
                        // date chose when init popwindow
                        .build();

                pickerPopWin.showPopWin(getActivity());
            }
        });
        return mBinding.getRoot();
    }

    private void gotoClaimDetail() {
        Bundle bundle = new Bundle();
        bundle.putString("CITYID", String.valueOf(checkradiobutton()));
        NewClaimsDetailsFragment newClaimsFragment = new NewClaimsDetailsFragment(this);
        newClaimsFragment.setArguments(bundle);
        addFragment(newClaimsFragment, getString(R.string.claim_details));
    }

    private void SetUpVisiblity() {
        if (claimchoosentype == ClaimRequestType.TRAVELSELECTED) {
            ModeOfTravelDetailsApiCall();
//            setUpModeOfTravelSpinner();
            mBinding.fragmentNewClaimLlModeoftravel.setVisibility(View.VISIBLE);
            mBinding.fragmentNewClaimRlContainerPlace.setVisibility(View.VISIBLE);
            mBinding.fragmentNewClaimLlContainerDate.setVisibility(View.VISIBLE);
            mBinding.fragmentNewClaimContainerPurposeOfVisit.setVisibility(View.VISIBLE);


        } else {
            mBinding.fragmentNewClaimContainerEffMonthYear.setVisibility(View.VISIBLE);
        }
    }


    private void ModeOfTravelDetailsApiCall() {
        showDialog();
        ((HomeActivity) getActivity()).getApiTask().getModeOfTravelDetails(((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN), this);

    }

    private void setAdapter(List<ModelOfTravelDetail> modelOfTravelDetails) {

        ArrayList<String> categoryList = new ArrayList<>();

        for (ModelOfTravelDetail modeOfTravelDetails : modelOfTravelDetails) {
            categoryList.add(modeOfTravelDetails.getName());
        }

        arrayAdapter = new SpinnerAdapter(
                getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                categoryList
        );
        mBinding.fragmentNewClaimSpnClaimModeoftravel
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedmodeOfTravelID = modeOfTravel.getDetails().get(position).getId();
                        selectedmodeofTravelName = modeOfTravel.getDetails().get(position).getName();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        mBinding.fragmentNewClaimSpnClaimModeoftravel.setAdapter(arrayAdapter);
    }


//    private void setUpModeOfTravelSpinner() {
//
//        String[] claimType = getActivity().getResources().getStringArray(R.array.claim_type);
//        arrayAdapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, claimType);
//        arrayAdapter.setFont(getString(R.string.gotham_book));
//        mBinding.fragmentNewClaimSpnClaimModeoftravel.setAdapter(arrayAdapter);
////        mBinding.fragmentNewClaimDetailsRvAddClaim.setOnRippleCompleteListener(this);
//
//    }


    private void setChoosenType() {
        claimchoosentype = getArguments().getInt("choosentype");
    }

    private void setUpView() {
        claimDetailsList = new ArrayList<>();
        addClaimDetails = new AddClaimDetails();
        calendar = Calendar.getInstance();
        mBinding.fragmentNewClaimTvMonthYear.setText(getDate());
/*
        for (int i = 1; i < 5; i++) {
            claimDetailsList.add(new ClaimDetails("Food", "Airport", "100" + i, "attachment"));
        }
*/
        claimAdapter = new ClaimDetailsAdapter(getActivity(), claimDetailsList, this);
        mBinding.fragmentNewClaimRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.fragmentNewClaimRv.setAdapter(claimAdapter);
        mBinding.fragmentNewClaimRv.setItemAnimator(new FadeInUpAnimator());
        mBinding.fragmentNewClaimTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    SetCLaimData();

                }
            }
        });

    }


    @Override
    public void onClick(View view) {
        ClaimDetails claimDetails = (ClaimDetails) view.getTag();
        addFragment(new NewClaimsDetailsFragment(claimDetails, this), getString(R.string.claim_details));

    }

    private void setTravelMode(TravelMode pos) {
        switch (pos) {
            case PLANE:
                mBinding.fragmentNewClaimIvPlane.setImageResource(R.drawable.ic_plane_selected);
                mBinding.fragmentNewClaimIvBikeCar.setImageResource(R.drawable.ic_car_bike);
                mBinding.fragmentNewClaimIvBus.setImageResource(R.drawable.ic_bus);
                mBinding.fragmentNewClaimIvTrain.setImageResource(R.drawable.ic_train);
                mBinding.fragmentNewClaimTvPlane.setTextColor(Color.BLACK);
                mBinding.fragmentNewClaimTvTrain.setTextColor(getResources().getColor(R.color.login_tv_hint));
                mBinding.fragmentNewClaimTvBus.setTextColor(getResources().getColor(R.color.login_tv_hint));
                mBinding.fragmentNewClaimTvBikeCar.setTextColor(getResources().getColor(R.color.login_tv_hint));
                break;
            case TRAIN:
                mBinding.fragmentNewClaimIvPlane.setImageResource(R.drawable.ic_plane);
                mBinding.fragmentNewClaimIvBikeCar.setImageResource(R.drawable.ic_car_bike);
                mBinding.fragmentNewClaimIvBus.setImageResource(R.drawable.ic_bus);
                mBinding.fragmentNewClaimIvTrain.setImageResource(R.drawable.ic_train_selected);
                mBinding.fragmentNewClaimTvPlane.setTextColor(getResources().getColor(R.color.login_tv_hint));
                mBinding.fragmentNewClaimTvTrain.setTextColor(Color.BLACK);
                mBinding.fragmentNewClaimTvBus.setTextColor(getResources().getColor(R.color.login_tv_hint));
                mBinding.fragmentNewClaimTvBikeCar.setTextColor(getResources().getColor(R.color.login_tv_hint));
                break;

            case BUS:
                mBinding.fragmentNewClaimIvPlane.setImageResource(R.drawable.ic_plane);
                mBinding.fragmentNewClaimIvBikeCar.setImageResource(R.drawable.ic_car_bike);
                mBinding.fragmentNewClaimIvBus.setImageResource(R.drawable.ic_bus_selected);
                mBinding.fragmentNewClaimIvTrain.setImageResource(R.drawable.ic_train);
                mBinding.fragmentNewClaimTvPlane.setTextColor(getResources().getColor(R.color.login_tv_hint));
                mBinding.fragmentNewClaimTvTrain.setTextColor(getResources().getColor(R.color.login_tv_hint));
                mBinding.fragmentNewClaimTvBus.setTextColor(Color.BLACK);
                mBinding.fragmentNewClaimTvBikeCar.setTextColor(getResources().getColor(R.color.login_tv_hint));
                break;

            case BIKECAR:
                mBinding.fragmentNewClaimIvPlane.setImageResource(R.drawable.ic_plane);
                mBinding.fragmentNewClaimIvBikeCar.setImageResource(R.drawable.ic_car_bike_selected);
                mBinding.fragmentNewClaimIvBus.setImageResource(R.drawable.ic_bus);
                mBinding.fragmentNewClaimIvTrain.setImageResource(R.drawable.ic_train);
                mBinding.fragmentNewClaimTvPlane.setTextColor(getResources().getColor(R.color.login_tv_hint));
                mBinding.fragmentNewClaimTvTrain.setTextColor(getResources().getColor(R.color.login_tv_hint));
                mBinding.fragmentNewClaimTvBus.setTextColor(getResources().getColor(R.color.login_tv_hint));
                mBinding.fragmentNewClaimTvBikeCar.setTextColor(Color.BLACK);
                break;

            default:
                mBinding.fragmentNewClaimIvPlane.setImageResource(R.drawable.ic_plane);
                mBinding.fragmentNewClaimIvBikeCar.setImageResource(R.drawable.ic_car_bike);
                mBinding.fragmentNewClaimIvBus.setImageResource(R.drawable.ic_bus);
                mBinding.fragmentNewClaimIvTrain.setImageResource(R.drawable.ic_train);
                mBinding.fragmentNewClaimTvPlane.setTextColor(getResources().getColor(R.color.login_tv_hint));
                mBinding.fragmentNewClaimTvTrain.setTextColor(getResources().getColor(R.color.login_tv_hint));
                mBinding.fragmentNewClaimTvBus.setTextColor(getResources().getColor(R.color.login_tv_hint));
                mBinding.fragmentNewClaimTvBikeCar.setTextColor(getResources().getColor(R.color.login_tv_hint));
                break;
        }
    }

    private void SetCLaimData() {
        if (claimchoosentype == ClaimRequestType.TRAVELSELECTED) {
            addClaimDetails.setClaimType("Travel");
            addClaimDetails.setTravelModeID(selectedmodeOfTravelID);
            addClaimDetails.setPurposeOfVisit(mBinding.frgmentNewClaimDetailsEdPurposeOfVisit.getText().toString());
            addClaimDetails.setFromDate(fromdate);
            addClaimDetails.setToDate(todate);
            addClaimDetails.setFromPlace(mBinding.fragmentNewClaimEdFormPlace.getText().toString());
            addClaimDetails.setToPlace(mBinding.fragmentNewClaimEdToPlace.getText().toString());
        } else {
            addClaimDetails.setClaimType("Other");
            addClaimDetails.setOtherExpenceMonth(expensemonth);
            addClaimDetails.setOtherExpenceYear(expenseyear);
        }
        if (mBinding.rbtnMetro.isChecked()) {
            addClaimDetails.setCityType("1");
        } else {
            addClaimDetails.setCityType("2");
        }
        addClaimDetails.setExpenses(claimAdapter.getClaimExpensesList());
        addClaimDetailsApi();

    }

    private int checkradiobutton() {

        if (mBinding.rbtnMetro.isChecked()) {
            return 1;
        } else {
            return 2;
        }

    }

    private void addClaimDetailsApi() {
        showDialog();
        ((HomeActivity) getActivity()).getApiTask().addClaimDetails(
                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
                addClaimDetails, this);
    }


    @Override
    public void onBackPressListerner(Object object) {
        claimAdapter.add((ClaimDetails) object);
    }

    private String getDate() {
        String date = null;
        try {

            DecimalFormat mFormat = new DecimalFormat("00");
            expensemonth = (mFormat.format(Double.valueOf(Calendar.MONTH) + 1));
            expenseyear = String.valueOf(calendar.get(Calendar.YEAR));
            date = getFormatDate("MM-yyyy", "MMMM yyyy",
                    String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" +
                            calendar.get(Calendar.YEAR));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (date == null) ? "" : date;
    }

    private boolean checkValidation() {
        if (claimchoosentype == ClaimRequestType.TRAVELSELECTED) {
            if (modeOfTravel == null) {
                showSnackBar(getResources().getString(R.string.selectmodeoftravel));
                ModeOfTravelDetailsApiCall();
                return false;
            } else if (TextUtils.isEmpty(mBinding.fragmentNewClaimEdFormPlace.getText())) {
                showSnackBar(getResources().getString(R.string.enter_fromplace));
                return false;
            } else if (TextUtils.isEmpty(mBinding.fragmentNewClaimEdToPlace.getText())) {
                showSnackBar(getResources().getString(R.string.enter_toplace));
                return false;
            } else if (TextUtils.isEmpty(mBinding.frgmentNewClaimDetailsEdPurposeOfVisit.getText())) {
                showSnackBar(getResources().getString(R.string.enter_purposeofvisit));
                return false;
            } else if (claimDetailsList.isEmpty()) {
                showSnackBar(getResources().getString(R.string.enterclaimdetails));
                return false;
            }
        } else {
            if (claimDetailsList.isEmpty()) {
                showSnackBar(getResources().getString(R.string.enterclaimdetails));
                return false;
            }

        }
        return true;
    }

    private void showSnackBar(String message) {
        ((HomeActivity) getActivity()).showSnackBar(mBinding.getRoot(), message, Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onResponseComplete(Object clsGson, int requestCode, int responseCode) {

        if (requestCode == RequestCode.MODEOFTRAVEL) {
            dismissDialog();
//            ((HomeActivity) getActivity()).dismissDialog();
            modeOfTravel = (ModeOfTravel) clsGson;
            if (modeOfTravel != null && modeOfTravel.getDetails().size() > 0) {
                setAdapter(modeOfTravel.getDetails());
            } else {
            }
        } else if (requestCode == RequestCode.ADDCLAIMDETAILS) {
            claimExpenseResponse = (ClaimExpenseResponse) clsGson;
            if (claimExpenseResponse != null) {
                for (int i = 0; i < claimExpenseResponse.getDetails().get(0).getExpenses().size(); i++) {
                    if (!TextUtils.isEmpty(addClaimDetails.getExpenses().get(i).getAttachment())) {
                        RequestBody id = RequestBody.create(MediaType.parse("text/plain"),
                                claimExpenseResponse.getDetails().get(0).getExpenses().get(i).getTravelApplicationLineID());
                        File file = new File(addClaimDetails.getExpenses().get(i).getAttachment());
//                        MultipartBody.Part AttachmentImage = MultipartBody.Part.createFormData("Image",
//                                "Attachment" + claimExpenseResponse.getDetails().get(0).getExpenses().get(i).getTravelApplicationLineID(),
//                                RequestBody.create(MediaType.parse("image/*"), file));

                        // Create RequestBody for the image file
                        RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), file);

// Create MultipartBody.Part for the image file
                        MultipartBody.Part imagePart = MultipartBody.Part.createFormData(
                                "Image",
                                file.getName(), // Use file.getName() to get the actual file name
                                imageBody
                        );
                        ((HomeActivity) getActivity()).getApiTask().uploadAttachmentList(
                                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN), id, imagePart
                                , this);
                    }
                }


            } else {

            }
            dismissDialog();
        } else if (requestCode == RequestCode.UPLOADCLAIMATTACHMENTIMAGE) {
            if (onBackPressListerner != null) {
                onBackPressListerner.onBackPressListerner(addClaimDetails);
            }
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onResponseError(String errorMessage, int requestCode) {
        Log.e("onResponseError", "" + requestCode);
        Log.e("onResponseError....", "" + errorMessage);
        dismissDialog();
//        if (requestCode == RequestCode.ADDCLAIMDETAILS) {
//            getActivity().onBackPressed();
//        }
    }
}
