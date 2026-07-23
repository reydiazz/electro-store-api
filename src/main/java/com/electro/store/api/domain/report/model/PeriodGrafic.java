package com.electro.store.api.domain.report.model;

import com.electro.store.api.domain.report.model.enums.ReportFrequency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.LinkedHashMap;
import java.util.Locale;

public class PeriodGrafic {
    private static final Locale SPANISH = Locale.of("es", "PE");
    private static final DateTimeFormatter SHORT_DATE = DateTimeFormatter.ofPattern("dd/MM");

    private static final String[] SHORT_MONTHS = {
            "Ene", "Feb", "Mar", "Abr", "May", "Jun",
            "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
    };

    private PeriodGrafic() {
    }

    public static LinkedHashMap<String, BigDecimal> zeroSkeleton(ReportFrequency frequency, ReportPeriod period) {

        LinkedHashMap<String, BigDecimal> buckets = new LinkedHashMap<>();
        LocalDate startDay = period.start().toLocalDate();

        switch (frequency) {
            case ANNUAL -> {
                for (String month : SHORT_MONTHS) {
                    buckets.put(month, BigDecimal.ZERO);
                }
            }
            case MONTHLY -> {
                int days = startDay.lengthOfMonth();
                for (int day = 1; day <= days; day++) {
                    buckets.put(String.valueOf(day), BigDecimal.ZERO);
                }
            }
            case WEEKLY -> {
                for (int i = 0; i < 7; i++) {
                    buckets.put(weekLabel(startDay.plusDays(i)), BigDecimal.ZERO);
                }
            }
            case DAILY -> {
                for (int hour = 0; hour < 24; hour++) {
                    buckets.put(String.format("%02d:00", hour), BigDecimal.ZERO);
                }
            }
        }
        return buckets;
    }

    public static String keyOf(ReportFrequency frequency, LocalDateTime date) {
        return switch (frequency) {
            case ANNUAL -> SHORT_MONTHS[date.getMonthValue() - 1];
            case MONTHLY -> String.valueOf(date.getDayOfMonth());
            case WEEKLY -> weekLabel(date.toLocalDate());
            case DAILY -> String.format("%02d:00", date.getHour());
        };
    }

    private static String weekLabel(LocalDate day) {
        return day.getDayOfWeek().getDisplayName(TextStyle.SHORT, SPANISH) + " " + day.format(SHORT_DATE);
    }
}
