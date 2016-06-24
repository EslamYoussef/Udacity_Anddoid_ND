package com.sam_chordas.android.stockhawk.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.rest.Keys;


/**
 * Created by Eslam on 6/21/2016.
 */

public class WidgetRemoteService extends RemoteViewsService {
    private static final String[] STOCK_HAWK_COLUMNS = {
            QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
            QuoteColumns.PERCENT_CHANGE,
            QuoteColumns.CHANGE, QuoteColumns.ISUP};
    // these indices must match the projection
    static final int INDEX_ID = 0;
    static final int INDEX_SUMBOL = 1;
    static final int INDEX_BIDPRICE = 2;
    static final int INDEX_CHANGE = 4;
    static final int INDEX_ISUP = 5;

    @Override
    public RemoteViewsFactory onGetViewFactory(final Intent intent) {

        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();


                data = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                        STOCK_HAWK_COLUMNS,
                        QuoteColumns.ISCURRENT + " = ?",
                        new String[]{"1"},
                        null);

                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_list_item_quote);
                //Extract quote data frm the cursor
                String symbol = data.getString(INDEX_SUMBOL);
                String bidPrice = data.getString(INDEX_BIDPRICE);
                Integer isUp = data.getInt(INDEX_ISUP);
                String change = data.getString(INDEX_CHANGE);
                //Assign data to the views
                views.setTextViewText(R.id.stock_symbol, symbol);
                views.setTextViewText(R.id.bid_price, bidPrice);
                views.setTextViewText(R.id.change, change);
                if (isUp == 1)
                    views.setInt(R.id.change, "setBackgroundColor", getResources().getColor(android.R.color.holo_green_dark));
                else
                    views.setInt(R.id.change, "setBackgroundColor", getResources().getColor(android.R.color.holo_red_dark));
//                final Intent fillInIntent = new Intent(WidgetRemoteService.this, MyStocksActivity.class);
                final Intent fillInIntent = new Intent();
                fillInIntent.putExtra(Keys.SELECTED_POSITION, position);
                views.setOnClickFillInIntent(R.id.list_item_quote_container, fillInIntent);
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_layout);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(INDEX_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {

                return true;
            }
        };
    }

}
