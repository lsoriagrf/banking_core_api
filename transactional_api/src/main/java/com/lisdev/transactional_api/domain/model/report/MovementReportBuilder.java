package com.lisdev.transactional_api.domain.model.report;

import com.lisdev.transactional_api.domain.exception.InvalidReportPeriodException;
import com.lisdev.transactional_api.domain.model.Account;
import com.lisdev.transactional_api.domain.model.AccountType;
import com.lisdev.transactional_api.domain.model.Movement;
import com.lisdev.transactional_api.domain.model.MovementType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class MovementReportBuilder {

    private MovementReportBuilder() {}

    public static void validateDateRange(LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate, "startDate");
        Objects.requireNonNull(endDate, "endDate");
        if (startDate.isAfter(endDate)) {
            throw new InvalidReportPeriodException("startDate must be on or before endDate");
        }
    }

    public static MovementReport buildMovementReport(
            String identification,
            String customerFullName,
            LocalDate startDate,
            LocalDate endDate,
            List<Account> activeAccounts,
            List<Movement> movementsInRange) {

        MovementReport report = new MovementReport();
        report.setIdentification(identification != null ? identification.trim() : "");
        report.setName(customerFullName != null ? customerFullName.trim() : "");

        MovementReport.ReportPeriod period = new MovementReport.ReportPeriod();
        period.setFrom(startDate);
        period.setTo(endDate);
        report.setReportPeriod(period);
        Map<Integer, List<Movement>> byAccount = movementsInRange.stream()
                .collect(Collectors.groupingBy(Movement::getAccountId));

        activeAccounts.stream()
                .sorted(Comparator.comparing(Account::getCreatedAt,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .map(account -> toAccountDetail(account, byAccount))
                .forEach(detail -> report.getAccounts().add(detail));
        return report;
    }

    private static MovementReport.AccountDetail toAccountDetail(
            Account account, Map<Integer, List<Movement>> byAccount) {
        List<Movement> movements = byAccount.getOrDefault(account.getId(), List.of())
                .stream()
                .sorted(Comparator.comparing(Movement::getCreatedAt,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());

        MovementReport.AccountDetail detail = new MovementReport.AccountDetail();
        detail.setAccountNumber(account.getAccountNumber());
        detail.setAccountType(resolveAccountTypeLabel(account));
        detail.setCurrentBalance(Objects.requireNonNullElse(account.getBalance(), BigDecimal.ZERO));
        detail.setMovements(movements.stream()
                .map(MovementReportBuilder::toMovementDetail)
                .collect(Collectors.toList()));
        return detail;
    }

    private static MovementReport.MovementDetail toMovementDetail(Movement m) {
        MovementReport.MovementDetail row = new MovementReport.MovementDetail();
        row.setDate(m.getCreatedAt() != null ? m.getCreatedAt().toLocalDate() : null);
        row.setType(MovementType.descriptionById(m.getTransactionTypeId()));
        row.setAmount(signedMovementAmount(m.getAmount(), m.getTransactionTypeId()));
        row.setBalanceAfter(Objects.requireNonNullElse(m.getBalance(), BigDecimal.ZERO));
        return row;
    }

    public static BigDecimal signedMovementAmount(BigDecimal amount, Integer transactionTypeId) {
        Objects.requireNonNull(amount, "amount");
        if (MovementType.Withdrawal.getId().equals(transactionTypeId)) {
            return amount.negate();
        }
        return amount;
    }

    private static String resolveAccountTypeLabel(Account account) {
        if (account.getAccountType() != null && !account.getAccountType().isBlank()) {
            return account.getAccountType();
        }
        return AccountType.descriptionById(account.getAccountTypeId());
    }
}
