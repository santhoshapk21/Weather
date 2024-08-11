package hrms.hrms.fragment.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowNotificationBinding;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import hrms.hrms.utility.Constants;


/**
 * Created by gjz on 9/4/16.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ContactsViewHolder> {

    private List<Calendar> calendarList;
    private View.OnClickListener onClickListener;
    private Context context;
    private Fragment fragment;
    private Calendar currCalendar, prevCalendar;
    private List<String> list;

    public NotificationAdapter(Context context, Fragment fragment, List<Calendar> calendarList, View.OnClickListener onClickListener) {
        this.context = context;
        this.fragment = fragment;
        this.calendarList = calendarList;
        this.onClickListener = onClickListener;
        list = Constants.getDateText();
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notification, parent, false);
        return new ContactsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, int position) {

        Calendar calendar = calendarList.get(position);
        currCalendar = Calendar.getInstance(Locale.getDefault());
        prevCalendar = Calendar.getInstance(Locale.getDefault());
        if (position == 0 || !isEqual(calendar, calendarList.get(position - 1))) {
            holder.mBinding.rowNotificationLlHeader.setVisibility(View.VISIBLE);

            if (isEqual(currCalendar, calendarList.get(position))) {
                holder.mBinding.rowNotificationTvTxtDate.setText(list.get(0));
            } else if (isEqual(prevCalendar, calendarList.get(position))) {
                holder.mBinding.rowNotificationTvTxtDate.setText(list.get(1));
            } else {
                if (calendar.before(currCalendar))
                    holder.mBinding.rowNotificationTvTxtDate.setText(list.get(2));
                else
                    holder.mBinding.rowNotificationTvTxtDate.setText("");
            }
        } else {
            holder.mBinding.rowNotificationLlHeader.setVisibility(View.GONE);
        }
        prevCalendar = Calendar.getInstance(Locale.getDefault());
//        prevCalendar.add(Calendar.DAY_OF_MONTH, -1);


        holder.mBinding.rowNotificationTvDate.setText(calendarList.get(position).get(Calendar.DAY_OF_MONTH) + " " + calendarList.get(position).getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + calendarList.get(position).get(Calendar.YEAR));
    }

    @Override
    public int getItemCount() {
        return calendarList.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        private RowNotificationBinding mBinding;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    private String getString(int id) {
        return context.getResources().getString(id);
    }

    private boolean isEqual(Calendar first, Calendar second) {
        if ((first.get(Calendar.DAY_OF_MONTH) == second.get(Calendar.DAY_OF_MONTH)) && (first.get(Calendar.MONTH) ==
                (second.get(Calendar.MONTH))) && (first.get(Calendar.YEAR) == (second.get(Calendar.YEAR)))) {
            return true;
        }
        return false;
    }

}