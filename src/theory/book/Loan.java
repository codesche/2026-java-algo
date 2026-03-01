package theory.book;

import java.time.LocalDateTime;

public class Loan {

    private final String loanId;
    private final Book book;
    private final Member member;
    private final LocalDateTime borrowedAt;

    public Loan(String loanId, Book book, Member member) {
        this.loanId = loanId;
        this.book = book;
        this.member = member;
        this.borrowedAt = LocalDateTime.now();
    }

    public String getLoanId() {
        return loanId;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public String toString() {
        return "Loan{" +
            "book=" + book.getTitle() +
            ", member=" + member.getName() +
            ", borrowedAt=" + borrowedAt +
            '}';
    }
}
