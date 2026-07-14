package com.electro.store.api.domain.report.model.enums;

import com.electro.store.api.domain.report.model.ReportPeriod;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public enum ReportFrequency {

    ANNUAL {
        @Override
        public ReportPeriod resolve(LocalDate date) {
            LocalDate start = date.withDayOfYear(1);
            return new ReportPeriod(start.atStartOfDay(), start.plusYears(1).atStartOfDay(), "Año" + date.getYear());
        }
    },
    MONTHLY {
        @Override
        public ReportPeriod resolve(LocalDate date) {
            LocalDate start = date.withDayOfMonth(1);
            String label = start.format(DateTimeFormatter.ofPattern("MMMM yyyy", SPANISH));
            return new ReportPeriod(start.atStartOfDay(), start.plusMonths(1).atStartOfDay(), label.toUpperCase(SPANISH));
        }
    },
    WEEKLY {
        @Override
        public ReportPeriod resolve(LocalDate date) {
            LocalDate monday = date.with(DayOfWeek.MONDAY);
            DateTimeFormatter day = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new ReportPeriod(monday.atStartOfDay(), monday.plusWeeks(1).atStartOfDay(), "SEMANA DEL " + monday.format(day) + " AL " + monday.plusDays(6).format(day));
        }
    },
    DAILY {
        @Override
        public ReportPeriod resolve(LocalDate date) {
            return new ReportPeriod(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    };

    static final Locale SPANISH = Locale.of("es", "PE");

    public abstract ReportPeriod resolve(LocalDate date);


}
