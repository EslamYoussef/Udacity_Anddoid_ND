package com.sam_chordas.android.stockhawk.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by sam_chordas on 10/5/15.
 */
public class QuoteColumns {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String SYMBOL = "symbol";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String PERCENT_CHANGE = "percent_change";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String CHANGE = "Change";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String BIDPRICE = "Bid_price";
    @DataType(DataType.Type.TEXT)
    public static final String CREATED = "created";
    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String ISUP = "is_up";
    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String ISCURRENT = "is_current";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String DAYS_RANGE = "DaysRange";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String LAST_TRADE_Date = "LastTradeDate";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String YEAR_RANGE = "YearRange";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String PREVOUS_CLOSE = "PreviousClose";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String PRICE_EPS_EST_CURR_YEAR = "PriceEPSEstimateCurrentYear";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String PRICE_EPS_EST_NXT_YEAR = "PriceEPSEstimateNextYear";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String AVG_CHANGE_FROM_TWO_HUNDRED_DAYS = "ChangeFromTwoHundreddayMovingAverage";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String PERC_CHANGE_FROM_TWO_HUNDRED_DAYS = "PercentChangeFromTwoHundreddayMovingAverage";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String AVG_CHANGE_FROM_FIFTY_DAYS = "ChangeFromFiftydayMovingAverage";
    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String PERC_CHANGE_FROM_FIFTY_DAYS = "PercentChangeFromFiftydayMovingAverage";

}
