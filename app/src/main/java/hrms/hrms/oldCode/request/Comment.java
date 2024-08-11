package hrms.hrms.oldCode.request;

/**
 * Created by yudiz on 16/03/17.
 */

public class Comment {

    //Dialog Date Time Picker

//        Utility.showDateDialog(getFragmentManager(), 01, 01, calendar.get(Calendar.YEAR) - 50, 01, 01, calendar.get(Calendar.YEAR) + 100, new OnDateDialogListener() {
//            @Override
//            public void onPositiveActionClicked(DatePickerDialog dialog) {
//                calendar.setTimeInMillis(dialog.getDate());
//                isDateChange = true;
//                View view = new View(getContext());
//                view.setTag(headerAdapter.getSelectedPosition());
//                onClick(view);
//            }
//        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));



    // Api Response


//        } else if (requestCode == RequestCode.REQUESTLEAVE) {
//            responseRequestLeavesList = (List<ResponseRequestLeave>) clsGson;
//            if (responseRequestLeavesList != null && responseRequestLeavesList.size() > 0)
//                setLeaveAdapter();
//            else
//                setNoDataView();
//        } else if (requestCode == RequestCode.REQUESTREGULARISATION) {
//            responseRequestRegularisations = (List<ResponseRequestRegularisation>) clsGson;
//            if (responseRequestRegularisations != null && responseRequestRegularisations.size() > 0)
//                setRequestReqularizeAttendanceAdapter();
//            else
//                setNoDataView();
//        } else if (requestCode == RequestCode.REQUESTCORRECTION) {
//            responseRequestCorrections = (List<ResponseRequestCorrection>) clsGson;
//            if (responseRequestCorrections != null && responseRequestCorrections.size() > 0)
//                setRequestAttendanceCorrectionRequestAdapter();
//            else
//                setNoDataView();
//        } else if (requestCode == RequestCode.REQUESTSHIFTCHANGE) {
//            responseRequestShiftChanges = (List<ResponseRequestedShiftChange>) clsGson;
//            if (responseRequestShiftChanges != null && responseRequestShiftChanges.size() > 0)
//                setShiftChangeAdapter();
//            else
//                setNoDataView();
//        } else if (requestCode == RequestCode.REQUESTATTENDANCE) {
//            responseRequestAttendances = (List<ResponseRequestAttendance>) clsGson;
//            if (responseRequestAttendances != null && responseRequestAttendances.size() > 0)
//                setMarkAttendanceAdapter();
//            else
//                setNoDataView();
//        }


    // Adapters


//    private void onLeaveRequestApiCall(int month, int year) {
//        call = ((HomeActivity) getActivity()).getApiTask().doGetRequestedLeave(
//                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
//                (month + 1),
//                year,
//                this
//        );
//    }

//    private void onCorrectionRequestApiCall(int month, int year) {
//        call = ((HomeActivity) getActivity()).getApiTask().doGetRequestedCorrection(
//                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
//                (month + 1),
//                year,
//                this
//        );
//    }

//    private void onRequestedShiftChange(int month, int year) {
//        call = ((HomeActivity) getActivity()).getApiTask().doGetRequestedShiftChange(
//                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
//                (month + 1),
//                year,
//                this
//        );
//    }

//    private void onReqularisationRequestApiCall(int month, int year) {
//        call = ((HomeActivity) getActivity()).getApiTask().doGetRequestedRegularisation(
//                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
//                (month + 1),
//                year,
//                this
//        );
//    }

//    private void onMarkAttendanceRequestApiCall(int month, int year) {
//        call = ((HomeActivity) getActivity()).getApiTask().doGetRequestedAttendance(
//                ((HomeActivity) getActivity()).getString(BaseAppCompactActivity.TYPE.ACCESSTOKEN),
//                (month + 1),
//                year,
//                this
//        );
//    }

}
