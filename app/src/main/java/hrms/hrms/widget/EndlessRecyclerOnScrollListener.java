package hrms.hrms.widget;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {


    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private onLastItem onLastItem;
    private RecyclerView fragmentHomeRvOfferDetails;
    private LinearLayoutManager mLayoutManager;
    private boolean loading = false;


    public EndlessRecyclerOnScrollListener(
            LinearLayoutManager mLayoutManager, RecyclerView recyclerView, onLastItem onLastItem) {
        this.mLayoutManager = mLayoutManager;
        this.fragmentHomeRvOfferDetails = recyclerView;
        this.onLastItem = onLastItem;
    }

    public EndlessRecyclerOnScrollListener(RecyclerView recyclerView, onLastItem onLastItem) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager)
            this.mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.fragmentHomeRvOfferDetails = recyclerView;
        this.onLastItem = onLastItem;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (mLayoutManager == null)
            return;
        visibleItemCount = fragmentHomeRvOfferDetails
                .getChildCount();

        totalItemCount = mLayoutManager
                .getItemCount();

        firstVisibleItem = mLayoutManager
                .findFirstVisibleItemPosition();

        if (!loading) {

            if (totalItemCount == (visibleItemCount + firstVisibleItem)) {
                onLastItem.onLastItem();
                loading = true;
            }
        } else {
            if (totalItemCount > (visibleItemCount + firstVisibleItem))
                loading = false;
        }
    }

    public interface onLastItem {
        void onLastItem();
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }
}
