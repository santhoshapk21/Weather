package hrms.hrms.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hris365.R;
import com.hris365.databinding.RowNavItemBinding;

import java.util.List;

import hrms.hrms.model.NavItem;


/**
 * Created by Yudiz on 20/06/16.
 */
public class NavItemAdapter extends RecyclerView.Adapter<NavItemAdapter.ViewHolder> {

    private Context context;
    private List<NavItem> navItems;
    private View.OnClickListener onClickListener;
    private int selector = -1;

    public NavItemAdapter(Context context, List<NavItem> navItems,
                          View.OnClickListener onClickListener) {
        this.context = context;
        this.navItems = navItems;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_nav_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.mBinding.llClick.setTag(position);
        holder.mBinding.llClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null)
                    onClickListener.onClick(v);
            }
        });
        if (selector == position) {
            holder.mBinding.tvTextTitle.setText(navItems.get(position).getTitle());
            holder.mBinding.tvTextTitle.setTextColor(
                    context.getResources().getColor(R.color.colorPrimary));
            holder.mBinding.icIcon.setImageResource(navItems.get(position).getSelectedImageId());

        } else {
            holder.mBinding.tvTextTitle.setText(navItems.get(position).getTitle());
            holder.mBinding.icIcon.setImageResource(navItems.get(position).getUnselectedImageId());
            holder.mBinding.tvTextTitle.setTextColor(
                    context.getResources().getColor(R.color.menu_font_color));

        }
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public void setSelector(int selector) {
        this.selector = selector;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowNavItemBinding mBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }


}
