package com.bourne;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CumRetCalculatorTest {

    HashMap<Date,Double> returnMap = new HashMap<>();

    private static final double DELTA = 1e-15;

    @BeforeEach
    void setUp() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String datestr[] = {"2015-01-10", "2015-02-10", "2015-04-10", "2015-04-15", "2015-06-10"};
            Double values[] = {0.1, 0.05, 0.15, -.10, -.12};
            for (int i = 0; i < datestr.length; i++) {
                returnMap.put(sdf.parse(datestr[i]), values[i]);
            }

        } catch (ParseException e) {
            System.err.println(e);
        }

    }

    @Test
    void findCumReturn() {
        String[] test_dates_str = {"2015-01-31","2015-02-28","2015-03-13","2015-04-30","2015-05-08","2015-06-30"};
        Date[] test_dates = getDateArray(test_dates_str);
        Date base_date = new GregorianCalendar(2015,GregorianCalendar.FEBRUARY,1).getTime();
        CumRetCalculator crc = new CumRetCalculator(returnMap);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            crc.findCumReturn(test_dates[0],base_date);
        });

        assertEquals(0.05,crc.findCumReturn(test_dates[1],base_date),DELTA);
        assertEquals(0.05,crc.findCumReturn(test_dates[2],base_date),DELTA);
        assertEquals(0.08675,crc.findCumReturn(test_dates[3],base_date),DELTA);
        assertEquals(0.08675,crc.findCumReturn(test_dates[4],base_date),DELTA);
        assertEquals(-0.04366,crc.findCumReturn(test_dates[5],base_date),DELTA);



    }

    public static Date[] getDateArray(String dates[]){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date[] date_array = new Date[dates.length];
        try{
            for (int i=0; i<dates.length; i++){
                date_array[i] = sdf.parse(dates[i]);
            }
        } catch(ParseException e) {
            return null;
        }
        return date_array;
    }

}