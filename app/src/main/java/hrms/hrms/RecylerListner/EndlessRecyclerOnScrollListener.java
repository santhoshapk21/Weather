package hrms.hrms.RecylerListner;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {


    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private onLastItem onLastItem;
    private int ilimit = 25;
    private int pageNumber = 2;
    private RecyclerView fragmentHomeRvOfferDetails;
    private LinearLayoutManager mLayoutManager;
    private boolean loading = false;
    private int lastTotal = -1;


    public EndlessRecyclerOnScrollListener(LinearLayoutManager mLayoutManager, RecyclerView recyclerView, onLastItem onLastItem) {
        this.mLayoutManager = mLayoutManager;
        this.fragmentHomeRvOfferDetails = recyclerView;
        this.onLastItem = onLastItem;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = fragmentHomeRvOfferDetails
                .getChildCount();

        totalItemCount = mLayoutManager
                .getItemCount();

        firstVisibleItem = mLayoutManager
                .findFirstVisibleItemPosition();

        if (!loading) {
            if (totalItemCount == (visibleItemCount + firstVisibleItem)) {
                onLastItem.onLastItem(pageNumber, ilimit);
                pageNumber++;
                loading = true;
                lastTotal = totalItemCount;
            }
        } else {
            if (totalItemCount > lastTotal)
                loading = false;
        }
    }
}
