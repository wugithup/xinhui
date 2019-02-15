/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.cron.ws;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class NumberFormatUtil {

    public static String format(BigDecimal big, int p) {

        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(p);
        return nf.format(big.doubleValue());
    }

    public static double round(Double d, int p) {

        BigDecimal bd = BigDecimal.valueOf(d.doubleValue());
        return bd.setScale(p, 4).doubleValue();
    }

    public static BigDecimal changeBig(Double d) {

        if ((d != null) && (d != 0)) {
            return BigDecimal.valueOf(d.doubleValue());
        }
        return new BigDecimal("0");
    }
}