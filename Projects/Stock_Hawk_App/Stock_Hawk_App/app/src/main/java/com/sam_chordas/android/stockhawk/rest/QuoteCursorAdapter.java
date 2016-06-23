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
    private static int mExpandedPosition = 0;

    public void setExpandedPosition(int expandedPosition) {
        mExpandedPosition = expandedPosition;
    }

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
        viewHolder.priceChangeTwoHundredDays.setText(cursor.getString(cursor.getColumnIndex("ChangeFromTwoHundreddayMovingAverage")));
        viewHolder.percentChangeTwoHundredDays.setText(cursor.getString(cursor.getColumnIndex("PercentChangeFromTwoHundreddayMovingAverage")));
        viewHolder.priceChangeFiftyDays.setText(cursor.getString(cursor.getColumnIndex("ChangeFromFiftydayMovingAverage")));
        viewHolder.percentChangeFiftyDays.setText(cursor.getString(cursor.getColumnIndex("PercentChangeFromFiftydayMovingAverage")));

        //Details Views
        int position = viewHolder.getAdapterPosition();
        if (position == mExpandedPosition) {
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
        if (cursor.getString(cursor.getColumnIndex("PercentChangeFromTwoHundreddayMovingAverage")).startsWith("+")) {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.percentChangeTwoHundredDays.setBackgroundDrawable(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_green));
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.percentChangeTwoHundredDays.setBackground(
                            mContext.getResources().getDrawable(R.drawable.percent_change_pill_green));
                }
            }
        } else {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.percentChangeTwoHundredDays.setBackgroundDrawable(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_red));
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.percentChangeTwoHundredDays.setBackground(
                            mContext.getResources().getDrawable(R.drawable.percent_change_pill_red));
                }
            }
        }
        if (cursor.getString(cursor.getColumnIndex("PercentChangeFromFiftydayMovingAverage")).startsWith("+")) {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.percentChangeFiftyDays.setBackgroundDrawable(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_green));
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.percentChangeFiftyDays.setBackground(
                            mContext.getResources().getDrawable(R.drawable.percent_change_pill_green));
                }
            }
        } else {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.percentChangeFiftyDays.setBackgroundDrawable(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_red));
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.percentChangeFiftyDays.setBackground(
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
        public final TextView priceChangeTwoHundredDays;
        public final TextView percentChangeTwoHundredDays;
        public final TextView priceChangeFiftyDays;
        public final TextView percentChangeFiftyDays;

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
            priceChangeTwoHundredDays = (TextView) itemView.findViewById(R.id.changeTwoHundred);
            percentChangeTwoHundredDays = (TextView) itemView.findViewById(R.id.changeTwoHundredPerc);
            priceChangeFiftyDays = (TextView) itemView.findViewById(R.id.changeFifty);
            percentChangeFiftyDays = (TextView) itemView.findViewById(R.id.changeFiftyPerc);
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
            if (mExpandedPosition >= 0) {
                int prev = mExpandedPosition;
                notifyItemChanged(prev);
            }
            // Set the current position to "expanded"
            mExpandedPosition = holder.getAdapterPosition();
            notifyItemChanged(mExpandedPosition);
        }
    }
}
