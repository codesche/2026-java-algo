package theory.book;

public class Book {

    private final String isbn;
    private final String title;
    private BookStatus status;

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
        this.status = BookStatus.AVAILABLE;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void borrow() {
        if (status == BookStatus.BORROWED) {
            throw new IllegalStateException("이미 대출된 도서입니다.");
        }
        this.status = BookStatus.BORROWED;
    }

    public void returnBook() {
        if (status == BookStatus.AVAILABLE) {
            throw new IllegalStateException("이미 반납된 도서입니다.");
        }
        this.status = BookStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return title + " (" + status + ")";
    }

}
