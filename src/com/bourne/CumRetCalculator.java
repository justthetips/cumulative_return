package com.bourne;

import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

class CumRetCalculator {

    private TreeMap<Date, Double> returnMap;


    public CumRetCalculator(Map<Date, Double> dailyReturns) {
        //since it did not say the map would be in date order, use a tree map
        //if we can be sure the map passed in is in date order, faster implementations
        //can be done
        returnMap = new TreeMap<Date, Double>(dailyReturns);
    }

    double findCumReturn(Date asof, Date base) {
        if (base.before(returnMap.firstKey())) {
            throw new IllegalArgumentException("Base date is before the first date we have");
        }
        if (asof.before(base)) {
            throw new IllegalArgumentException("As Of Date is before the base date");
        }
        //get the submap
        NavigableMap<Date, Double> periodMap = this.returnMap.subMap(base, true, asof, true);

        //iterate thru and calculate the cumulative product
        double cumprod = 1;
        for (Double value : periodMap.values()) {
            cumprod *= (1 + value);
        }

        //here is the answer
        return (cumprod / 1) - 1;
    }


}
