package theory.book;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LibraryService {

    private final Map<String, Book> books = new ConcurrentHashMap<>();
    private final Map<String, Member> members = new ConcurrentHashMap<>();
    private final Map<String, Loan> loans = new ConcurrentHashMap<>();

    // 도서 등록
    public void registerBook(String isbn, String title) {
        if (books.containsKey(isbn)) {
            throw new IllegalArgumentException("이미 존재하는 ISBN입니다.");
        }
        books.put(isbn, new Book(isbn, title));
    }

    // 회원 등록
    public void registerMember(String memberId, String name) {
        if (members.containsKey(memberId)) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
        members.put(memberId, new Member(memberId, name));
    }

    // 도서 대출 (동시성 고려)
    public synchronized void borrowBook(String isbn, String memberId) {
        Book book = Optional.ofNullable(books.get(isbn))
            .orElseThrow(() -> new NoSuchElementException("도서를 찾을 수 없습니다."));

        Member member = Optional.ofNullable(members.get(memberId))
            .orElseThrow(() -> new NoSuchElementException("회원을 찾을 수 없습니다."));

        book.borrow();

        String loanId = UUID.randomUUID().toString();
        Loan loan = new Loan(loanId, book, member);
        loans.put(loanId, loan);

        System.out.println("대출 완료: " + loan);
    }

    // 도서 반납
    public synchronized void returnBook(String isbn) {
        Book book = Optional.ofNullable(books.get(isbn))
            .orElseThrow(() -> new NoSuchElementException("도서를 찾을 수 없습니다."));

        book.returnBook();

        // 해당 도서의 대출 기록 제거
        loans.values().removeIf(loan -> loan.getBook().getIsbn().equals(isbn));

        System.out.println("반납 완료: " + book.getTitle());
    }

    // 대출 가능 도서 조회 (Stream 활용)
    public List<Book> getAvailableBooks() {
        return books.values().stream()
            .filter(book -> book.getStatus() == BookStatus.AVAILABLE)
            .collect(Collectors.toList());
    }

    // 회원별 대출 목록 조회
    public List<Loan> getLoansByMember(String memberId) {
        return loans.values().stream()
            .filter(loan -> loan.getMember().getMemberId().equals(memberId))
            .collect(Collectors.toList());
    }

}
