package com.lisdev.transactional_api.domain.model.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementReport {

    private String identification;
    private String name;
    private ReportPeriod reportPeriod;
    private List<AccountDetail> accounts = new ArrayList<>();

    @Getter
    @Setter
    public static class ReportPeriod {

        private LocalDate from;
        private LocalDate to;
    }

    @Getter
    @Setter
    public static class AccountDetail {

        private String accountNumber;
        private String accountType;
        private BigDecimal currentBalance;
        private List<MovementDetail> movements = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class MovementDetail {

        private LocalDate date;
        private String type;
        private BigDecimal amount;
        private BigDecimal balanceAfter;
    }
}
