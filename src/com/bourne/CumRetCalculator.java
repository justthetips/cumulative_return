package com.bourne;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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




    public static void main(String args[]) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        HashMap<Date, Double> unsortedMap = new HashMap<>();
        try {
            unsortedMap.put(sdf.parse("2015-01-10"), 0.1);
            unsortedMap.put(sdf.parse("2015-02-10"), 0.05);
            unsortedMap.put(sdf.parse("2015-04-10"), 0.15);
            unsortedMap.put(sdf.parse("2015-04-15"), -0.10);
            unsortedMap.put(sdf.parse("2015-06-10"), -0.12);
        } catch (ParseException e) {
            System.err.print(e);
        }

        CumRetCalculator crc = new CumRetCalculator(unsortedMap);
        try {
            Date baseDate = sdf.parse("2015-02-01");
            Date toDate = sdf.parse("2015-06-30");
            System.out.println(crc.findCumReturn(toDate, baseDate));
        } catch (ParseException e) {
            System.err.println(e);
        } catch (IllegalArgumentException e) {
            System.err.println(e);


        }


    }



}
