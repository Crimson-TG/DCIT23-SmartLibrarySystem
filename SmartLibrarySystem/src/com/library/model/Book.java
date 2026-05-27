package com.library.model;

import java.io.Serializable; // Might be needed
import java.util.Objects;

public class Book {
    private static final long serialVersionUID = 11;
    
    private final String id;    // For the unique identifiers
    private String title;
    private String author;
    private boolean available;
    
    // Constructors
    public Book(String id, String title, String author) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Book id cannot be null or empty");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty");
        }
        
        this.id = id.trim();
        this.title = title.trim();
        this.author = author.trim();
        this.available = true;  // This is to set the book availabilty to True by default
    }
    
    // Getters
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public boolean isAvailable() {
        return available;
    }
    
    // Setters and the validation
    // ID should always be finalized and shouldn't be change
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        this.title = title.trim();
    }
    
    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty");
        }
        this.author = author.trim();
    }
    
    public void borrowed() {
        this.available = false;
    }
    
    public void returned() {
        this.available = true;
    }
    
    public String toString() {
        return String.format("Book[id=%s, title=%s, author=%s, available=%s]", id, title, author, available);
    }
    
    public boolean equals(Object a) {
        if (this == a) return true;
        if (a == null || getClass() != a.getClass()) return false;
        
        Book book = (Book) a;
        return Objects.equals(id, book.id);
    }
    
    public int hashCode() {
        return Objects.hash(id);
    }
}

/*
Book class design was inspired by the open-source example of:
Abdullah Al Noman, G. M. (2019). LibrarySystem [Java source code]. GitHub. 
https://github.com/gmabdullahalnoman/LibrarySystem
*/