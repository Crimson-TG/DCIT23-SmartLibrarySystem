# DCIT23-SmartLibrarySystem
For the DCIT23 project


NOTE:
OOP classes na gagawin natin (5/5)

Book class should contain:
Fields: id, title, author, and isAvailable()
Methods: setAvailable(), toString()

User class should contain:
Fields: name, id, borrowedList(ArrayList, dito nakalagay mga record ng hiniram ng user na books which goes to -> LoanRecord class (ArrayList\LoanRecord\))
Methods: canBorrow(), borrowBook(), and returnBook().

LoanRecord class should contain:
Fields: bookId, borrowerName, borrowDate, dueDate, returnedDate, and penalty()
Methods: calculatePenalty()

LibraryManager class holds ArrayList\Book\ and ArrayList\LoanRecord. And should contain:
Methods: searchByTitle(), searchByAuthor(), borrow(bookId, user), returnBook(bookId, user), listAvailable(), and listBorrowed().

GUI Controller class will connect UI events sa LibraryManager methods; this class will perform the input and output (input validation at messages/outputs)

* Please keep the code minimal and clean, make it as short as you can so that integration will be easy. 
** We will only gonna use Swing elements for GUI and not JavaFX because it's much more complex. 

Major revision for the groups:
I d divide nalang natin into two groups
Group A: Model Classes (BookUser and LoanRecord classes) + penalty and due-date logic. 
Group B: LibraryManager logics (search, borrow/return rules and logic) + kayo sa integration test (testing nyo and ensure nyo na naka connect ng maayos classes natin, tho lahat magtutulungan sa part na'to but it's still your priority)
Both A & B: GUI Controller + GUI Panels (Group A builds search UI/catalogs and Group B will work on the borrow/return dialogs)

Package creation will be like this:
com.library.model & com.library.service & com.library.ui & com.library.util & src\App.java (Main Method)

MEMBERS:
Group A:
Alexa
Nimo
Troy
Kent
Group B:
Arah
Rave
Cancejo
Prince

sdsdasd