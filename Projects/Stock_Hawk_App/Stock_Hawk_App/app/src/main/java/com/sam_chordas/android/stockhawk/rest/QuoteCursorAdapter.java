package com.sam_chordas.android.stockhawk.rest;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.touch_helper.ItemTouchHelperAdapter;
import com.sam_chordas.android.stockhawk.touch_helper.ItemTouchHelperViewHolder;

/**
 * Created by sam_chordas on 10/6/15.
 * Credit to skyfishjy gist:
 * https://gist.github.com/skyfishjy/443b7448f59be978bc59
 * for the code structure
 */
public class QuoteCursorAdapter extends CursorRecyclerViewAdapter<QuoteCursorAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    private static Context mContext;
    private static Typeface robotoLight;
    private boolean isPercent;
    private static int expandedPosition = -1;

    public QuoteCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        robotoLight = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_quote, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        itemView.setTag(vh);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {
        viewHolder.symbol.setText(cursor.getString(cursor.getColumnIndex("symbol")));
        viewHolder.bidPrice.setText(cursor.getString(cursor.getColumnIndex("bid_price")));
        viewHolder.daysRange.setText(cursor.getString(cursor.getColumnIndex("DaysRange")));
        viewHolder.yearRange.setText(cursor.getString(cursor.getColumnIndex("YearRange")));
        viewHolder.previousClose.setText(cursor.getString(cursor.getColumnIndex("PreviousClose")));
        viewHolder.lastTradeDate.setText(cursor.getString(cursor.getColumnIndex("LastTradeDate")));
        viewHolder.priceEstimateCurrYear.setText(cursor.getString(cursor.getColumnIndex("PriceEPSEstimateCurrentYear")));
        viewHolder.priceEstimateNXTYear.setText(cursor.getString(cursor.getColumnIndex("PriceEPSEstimateNextYear")));
        //Details Views
        int position = viewHolder.getAdapterPosition();
        if (position == expandedPosition) {
            viewHolder.rlStockDetails.setVisibility(View.VISIBLE);

        } else {
            viewHolder.rlStockDetails.setVisibility(View.GONE);
        }

        int sdk = Build.VERSION.SDK_INT;
        if (cursor.getInt(cursor.getColumnIndex("is_up")) == 1) {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.change.setBackgroundDrawable(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_green));
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.change.setBackground(
                            mContext.getResources().getDrawable(R.drawable.percent_change_pill_green));
                }
            }
        } else {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.change.setBackgroundDrawable(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_red));
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.change.setBackground(
                            mContext.getResources().getDrawable(R.drawable.percent_change_pill_red));
                }
            }
        }
        if (Utils.showPercent) {
            viewHolder.change.setText(cursor.getString(cursor.getColumnIndex("percent_change")));
        } else {
            viewHolder.change.setText(cursor.getString(cursor.getColumnIndex("change")));
        }
    }

    @Override
    public void onItemDismiss(int position) {
        Cursor c = getCursor();
        c.moveToPosition(position);
        String symbol = c.getString(c.getColumnIndex(QuoteColumns.SYMBOL));
        mContext.getContentResolver().delete(QuoteProvider.Quotes.withSymbol(symbol), null, null);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder, View.OnClickListener {
        public final TextView symbol;
        public final TextView bidPrice;
        public final TextView change;
        //New added details
        public final RelativeLayout rlStockDetails;
        public final TextView daysRange;
        public final TextView yearRange;
        public final TextView previousClose;
        public final TextView lastTradeDate;
        public final TextView priceEstimateCurrYear;
        public final TextView priceEstimateNXTYear;

        public ViewHolder(View itemView) {
            super(itemView);
            symbol = (TextView) itemView.findViewById(R.id.stock_symbol);
            symbol.setTypeface(robotoLight);
            bidPrice = (TextView) itemView.findViewById(R.id.bid_price);
            change = (TextView) itemView.findViewById(R.id.change);
            daysRange = (TextView) itemView.findViewById(R.id.daysRange);
            yearRange = (TextView) itemView.findViewById(R.id.yearRange);
            previousClose = (TextView) itemView.findViewById(R.id.previousClose);
            lastTradeDate = (TextView) itemView.findViewById(R.id.lastTrade);
            priceEstimateCurrYear = (TextView) itemView.findViewById(R.id.priceEstimateCurrYear);
            priceEstimateNXTYear = (TextView) itemView.findViewById(R.id.priceEstimateNXTYear);
            rlStockDetails = (RelativeLayout) itemView.findViewById(R.id.rlStockDetails);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }


        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
            // Check for an expanded view, collapse if you find one
            if (expandedPosition >= 0) {
                int prev = expandedPosition;
                notifyItemChanged(prev);
            }
            // Set the current position to "expanded"
            expandedPosition = holder.getAdapterPosition();
            notifyItemChanged(expandedPosition);
        }
    }
}
