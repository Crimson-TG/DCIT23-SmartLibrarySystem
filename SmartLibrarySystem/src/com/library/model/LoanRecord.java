package com.library.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanRecord {
    private String bookId;
    private String borrowerName;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public LoanRecord(String bookId, String borrowerName) {
        this.bookId = bookId;
        this.borrowerName = borrowerName;
        this.borrowDate = LocalDate.now();
        this.dueDate = this.borrowDate.plusDays(7);
        this.returnedDate = null;
    }

    public double calculatePenalty() {
        LocalDate endDate = (returnedDate != null) ? returnedDate : LocalDate.now();
        if (endDate.isAfter(dueDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, endDate);
            return daysOverdue * 10.0;
        }
        return 0.0;
    }

    public String getBookId() { return bookId; }
    public String getBorrowerName() { return borrowerName; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnedDate() { return returnedDate; }
    
    public void setReturnedDate(LocalDate returnedDate) { 
        this.returnedDate = returnedDate; 
    }
}