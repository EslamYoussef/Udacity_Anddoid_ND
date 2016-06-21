package com.sam_chordas.android.stockhawk.widget;


import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;


/**
 * Created by Eslam on 6/21/2016.
 */

public class WidgetRemoteService extends RemoteViewsService {
    private static final String[] STOCK_HAWK_COLUMNS = {
            QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
            QuoteColumns.PERCENT_CHANGE,
            QuoteColumns.CHANGE, QuoteColumns.ISUP, QuoteColumns.DAYS_RANGE, QuoteColumns.YEAR_RANGE, QuoteColumns.PREVOUS_CLOSE, QuoteColumns.LAST_TRADE_Date, QuoteColumns.PRICE_EPS_EST_CURR_YEAR, QuoteColumns.PRICE_EPS_EST_NXT_YEAR};
    // these indices must match the projection
    static final int INDEX_ID = 0;
    static final int INDEX_SUMBOL = 1;
    static final int INDEX_BIDPRICE = 2;
    static final int INDEX_PERCENT_CHANGE = 3;
    static final int INDEX_CHANGE = 4;
    static final int INDEX_ISUP = 5;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

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
                Log.d("Cursor Count", String.valueOf(data.getCount()));
                data.moveToFirst();
                String symbols = "";
                do {
                    symbols += data.getString(INDEX_SUMBOL);
                }
                while (data.moveToNext());
                Log.d("Symbols =", symbols);
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
                return new RemoteViews(getPackageName(), R.id.lvStockHawks);
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
