package com.library.service;

import com.library.model.Book;
import com.library.model.LoanRecord;

import java.util.ArrayList;
import java.util.List;

public class LibraryManager {

    private static final int MAX_BORROW_LIMIT = 3;

    private final ArrayList<Book> books;
    private final ArrayList<LoanRecord> loanRecords;

    // Constructor — pre-loaded with your 19 books
    public LibraryManager() {
        this.books = new ArrayList<>();
        this.loanRecords = new ArrayList<>();
        initializeBooks();
    }

    private void initializeBooks() {
        books.add(new Book("LIB-101", "The Social Contract",          "Jean-Jacques Rousseau"));
        books.add(new Book("LIB-102", "The Little Prince",            "Antoine de Saint-Exupéry"));
        books.add(new Book("LIB-103", "To Kill a Mockingbird",        "Harper Lee"));
        books.add(new Book("LIB-104", "Atomic Habits",                "James Clear"));
        books.add(new Book("LIB-105", "The Alchemist",                "Paulo Coelho"));
        books.add(new Book("LIB-106", "The Republic",                 "Plato"));
        books.add(new Book("LIB-107", "The Great Gatsby",             "F. Scott Fitzgerald"));
        books.add(new Book("LIB-108", "The Art of War",               "Sun Tzu"));
        books.add(new Book("LIB-109", "Thinking, Fast and Slow",      "Daniel Kahneman"));
        books.add(new Book("LIB-110", "Ikigai: The Japanese Secret",  "Hector Garcia"));
        books.add(new Book("LIB-111", "Pride and Prejudice",          "Jane Austen"));
        books.add(new Book("LIB-112", "The Diary of a Young Girl",    "Anne Frank"));
        books.add(new Book("LIB-113", "The Power of Habit",           "Charles Duhigg"));
        books.add(new Book("LIB-114", "1984",                         "George Orwell"));
        books.add(new Book("LIB-115", "Kafka on the Shore",           "Haruki Murakami"));
        books.add(new Book("LIB-116", "The Catcher in the Rye",       "J.D. Salinger"));
        books.add(new Book("LIB-117", "Meditations",                  "Marcus Aurelius"));
        books.add(new Book("LIB-118", "Sapiens: A Brief History",     "Yuval Noah Harari"));
        books.add(new Book("LIB-119", "The 5 AM Club",                "Robin Sharma"));
    }

    // ─── SEARCH ──────────────────────────────────────────────────────────────

    /**
     * Search books whose title contains the keyword (case-insensitive).
     */
    public List<Book> searchByTitle(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be null or empty.");
        }

        String lower = keyword.trim().toLowerCase();
        List<Book> results = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lower)) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Search books whose author name contains the keyword (case-insensitive).
     */
    public List<Book> searchByAuthor(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be null or empty.");
        }

        String lower = keyword.trim().toLowerCase();
        List<Book> results = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(lower)) {
                results.add(book);
            }
        }
        return results;
    }

    // ─── BORROW ──────────────────────────────────────────────────────────────

    /**
     * Borrow a book by ID for a given user.
     * Rules:
     *  - Book must exist
     *  - Book must be available
     *  - User must not have reached the borrow limit (max 3)
     */
    public void borrow(String bookId, String user) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty.");
        }
        if (user == null || user.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }

        String trimmedUser = user.trim();

        // Find the book
        Book target = findBookById(bookId.trim());
        if (target == null) {
            System.out.println("ERROR: Book with ID '" + bookId + "' not found.");
            return;
        }

        // Check availability
        if (!target.isAvailable()) {
            System.out.println("ERROR: '" + target.getTitle() + "' is currently unavailable.");
            return;
        }

        // Check borrow limit
        int currentlyBorrowed = countBorrowedByUser(trimmedUser);
        if (currentlyBorrowed >= MAX_BORROW_LIMIT) {
            System.out.println("ERROR: " + trimmedUser + " has reached the maximum borrow limit of "
                    + MAX_BORROW_LIMIT + " books.");
            return;
        }

        // All checks passed — proceed
        target.borrowed();
        loanRecords.add(new LoanRecord(bookId.trim(), trimmedUser));
        System.out.println("SUCCESS: '" + target.getTitle() + "' borrowed by " + trimmedUser + ".");
    }

    // ─── RETURN ──────────────────────────────────────────────────────────────

    /**
     * Return a book by ID for a given user.
     * Calculates and displays any late return penalty.
     */
    public void returnBook(String bookId, String user) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty.");
        }
        if (user == null || user.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }

        String trimmedUser = user.trim();
        String trimmedId   = bookId.trim();

        // Find the active loan record (not yet returned)
        LoanRecord record = findActiveLoan(trimmedId, trimmedUser);
        if (record == null) {
            System.out.println("ERROR: No active loan found for Book ID '" + trimmedId
                    + "' under user '" + trimmedUser + "'.");
            return;
        }

        // Find the book and mark as returned
        Book target = findBookById(trimmedId);
        if (target != null) {
            target.returned();
        }

        // Close the loan record and calculate penalty
        record.closeRecord();
        double penalty = record.calculatePenalty();

        System.out.println("SUCCESS: '" + (target != null ? target.getTitle() : trimmedId)
                + "' returned by " + trimmedUser + ".");

        if (penalty > 0) {
            System.out.printf("PENALTY:  Late return fee — ₱%.2f%n", penalty);
        } else {
            System.out.println("No late fee. Returned on time!");
        }
    }

    // ─── DISPLAY ─────────────────────────────────────────────────────────────

    /**
     * Returns a list of all currently available books.
     */
    public List<Book> listAvailable() {
        List<Book> available = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                available.add(book);
            }
        }
        return available;
    }

    /**
     * Returns a list of all currently borrowed books with their borrower info.
     */
    public List<LoanRecord> listBorrowed() {
        List<LoanRecord> active = new ArrayList<>();
        for (LoanRecord record : loanRecords) {
            if (!record.isReturned()) {
                active.add(record);
            }
        }
        return active;
    }

    // ─── PRIVATE HELPERS ─────────────────────────────────────────────────────

    private Book findBookById(String bookId) {
        for (Book book : books) {
            if (book.getId().equalsIgnoreCase(bookId)) {
                return book;
            }
        }
        return null;
    }

    private LoanRecord findActiveLoan(String bookId, String user) {
        for (LoanRecord record : loanRecords) {
            if (record.getBookId().equalsIgnoreCase(bookId)
                    && record.getBorrowerName().equalsIgnoreCase(user)
                    && !record.isReturned()) {
                return record;
            }
        }
        return null;
    }

    private int countBorrowedByUser(String user) {
        int count = 0;
        for (LoanRecord record : loanRecords) {
            if (record.getBorrowerName().equalsIgnoreCase(user) && !record.isReturned()) {
                count++;
            }
        }
        return count;
    }
}

