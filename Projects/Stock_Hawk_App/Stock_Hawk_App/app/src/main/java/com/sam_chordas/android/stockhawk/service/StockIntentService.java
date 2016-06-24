package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.TaskParams;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.rest.Keys;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {

    public StockIntentService() {
        super(StockIntentService.class.getName());
    }

    public StockIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(StockIntentService.class.getSimpleName(), "Stock Intent Service");
        StockTaskService stockTaskService = new StockTaskService(this);
        Bundle args = new Bundle();
        if (intent.getStringExtra(Keys.TAG).equals(Keys.ADD)) {
            if (intent.hasExtra(QuoteColumns.SYMBOL))
                if (null != intent.getStringExtra(QuoteColumns.SYMBOL) && !intent.getStringExtra(QuoteColumns.SYMBOL).equals("null")) {
                    args.putString(QuoteColumns.SYMBOL, intent.getStringExtra(QuoteColumns.SYMBOL));
                }
        }
        // We can call OnRunTask from the intent service to force it to run immediately instead of
        // scheduling a task.
        stockTaskService.onRunTask(new TaskParams(intent.getStringExtra(Keys.TAG), args));
    }
}
