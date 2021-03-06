package com.sam_chordas.android.stockhawk.rest;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sam_chordas on 10/8/15.
 */
public class Utils {

    private static String LOG_TAG = Utils.class.getSimpleName();

    public static boolean showPercent = true;

    public static ArrayList quoteJsonToContentVals(String JSON) {
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        JSONObject jsonObject = null;
        JSONArray resultsArray = null;
        try {
            jsonObject = new JSONObject(JSON);
            if (jsonObject != null && jsonObject.length() != 0) {
                jsonObject = jsonObject.getJSONObject(Keys.QUERY);
                int count = Integer.parseInt(jsonObject.getString(Keys.COUNT));
                if (count == 1) {
                    jsonObject = jsonObject.getJSONObject(Keys.RESULTS)
                            .getJSONObject(Keys.QUOTE);

                    ContentProviderOperation buildBatchOperation = buildBatchOperation(jsonObject);
                    batchOperations.add(buildBatchOperation);
                } else {
                    resultsArray = jsonObject.getJSONObject(Keys.RESULTS).getJSONArray(Keys.QUOTE);

                    if (resultsArray != null && resultsArray.length() != 0) {
                        for (int i = 0; i < resultsArray.length(); i++) {
                            jsonObject = resultsArray.getJSONObject(i);
                            ContentProviderOperation buildBatchOperation = buildBatchOperation(jsonObject);
                            batchOperations.add(buildBatchOperation);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return batchOperations;
    }

    public static String truncateBidPrice(String bidPrice) {
        if (null != bidPrice && !bidPrice.equals("null"))
            bidPrice = String.format("%.2f", Float.parseFloat(bidPrice));
        return bidPrice;
    }

    public static String truncateDate(String dateStr, String delim) {
        String[] splittedStr = null;
        String truncatedString = "";
        if (null != dateStr && !dateStr.equals("null"))
            splittedStr = dateStr.split(delim);
        for (int i = 0; i < splittedStr.length; i++) {
            truncatedString += truncateInt(splittedStr[i]);
            if (i < splittedStr.length - 1)
                truncatedString += delim;
        }

        return truncatedString;
    }

    public static String truncateInt(String intStr) {
        if (null != intStr && !intStr.equals("null"))
            intStr = String.format("%d", Integer.parseInt(intStr));
        return intStr;
    }

    public static String truncateDelimString(String delimStr, String delim) {
        String[] splittedStr = null;
        String truncatedString = "";
        if (null != delimStr && !delimStr.equals("null"))
            splittedStr = delimStr.split(delim);
        for (int i = 0; i < splittedStr.length; i++) {
            truncatedString += truncateBidPrice(splittedStr[i]);
            if (i < splittedStr.length - 1)
                truncatedString += delim;
        }

        return truncatedString;
    }


    public static String truncateChange(String change, boolean isPercentChange) {
        if (null != change && !change.equals("null")) {
            String weight = change.substring(0, 1);
            String ampersand = "";
            if (isPercentChange) {
                ampersand = change.substring(change.length() - 1, change.length());
                change = change.substring(0, change.length() - 1);
            }
            change = change.substring(1, change.length());
            double round = (double) Math.round(Double.parseDouble(change) * 100) / 100;
            change = String.format("%.2f", round);
            StringBuffer changeBuffer = new StringBuffer(change);
            changeBuffer.insert(0, weight);
            changeBuffer.append(ampersand);
            change = changeBuffer.toString();
        }
        return change;
    }

    public static ContentProviderOperation buildBatchOperation(JSONObject jsonObject) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                QuoteProvider.Quotes.CONTENT_URI);
        try {
            String change = jsonObject.getString(QuoteColumns.CHANGE);
            if ("null".equals(change))
                return null;
            builder.withValue(QuoteColumns.SYMBOL, jsonObject.getString(QuoteColumns.SYMBOL));
            builder.withValue(QuoteColumns.BIDPRICE, truncateBidPrice(jsonObject.getString(Keys.BID)));
            builder.withValue(QuoteColumns.PERCENT_CHANGE, truncateChange(
                    jsonObject.getString(Keys.CHANGING_PERCENT), true));
            builder.withValue(QuoteColumns.CHANGE, truncateChange(change, false));
            builder.withValue(QuoteColumns.ISCURRENT, 1);
            if (change.charAt(0) == '-') {
                builder.withValue(QuoteColumns.ISUP, 0);
            } else {
                builder.withValue(QuoteColumns.ISUP, 1);
            }

            builder.withValue(QuoteColumns.DAYS_RANGE, truncateDelimString(jsonObject.getString(QuoteColumns.DAYS_RANGE), "-"));
            builder.withValue(QuoteColumns.LAST_TRADE_Date, truncateDate(jsonObject.getString(QuoteColumns.LAST_TRADE_Date), "/"));
            builder.withValue(QuoteColumns.YEAR_RANGE, truncateDelimString(jsonObject.getString(QuoteColumns.YEAR_RANGE), "-"));
            builder.withValue(QuoteColumns.PREVOUS_CLOSE, truncateBidPrice(jsonObject.getString(QuoteColumns.PREVOUS_CLOSE)));
            builder.withValue(QuoteColumns.PRICE_EPS_EST_CURR_YEAR, truncateBidPrice(jsonObject.getString(QuoteColumns.PRICE_EPS_EST_CURR_YEAR)));
            builder.withValue(QuoteColumns.PRICE_EPS_EST_NXT_YEAR, truncateBidPrice(jsonObject.getString(QuoteColumns.PRICE_EPS_EST_NXT_YEAR)));
            builder.withValue(QuoteColumns.AVG_CHANGE_FROM_TWO_HUNDRED_DAYS, truncateBidPrice(jsonObject.getString(QuoteColumns.AVG_CHANGE_FROM_TWO_HUNDRED_DAYS)));
            builder.withValue(QuoteColumns.PERC_CHANGE_FROM_TWO_HUNDRED_DAYS, truncateChange(jsonObject.getString(QuoteColumns.PERC_CHANGE_FROM_TWO_HUNDRED_DAYS), true));
            builder.withValue(QuoteColumns.AVG_CHANGE_FROM_FIFTY_DAYS, truncateBidPrice(jsonObject.getString(QuoteColumns.AVG_CHANGE_FROM_FIFTY_DAYS)));
            builder.withValue(QuoteColumns.PERC_CHANGE_FROM_FIFTY_DAYS, truncateChange(jsonObject.getString(QuoteColumns.PERC_CHANGE_FROM_FIFTY_DAYS), true));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
