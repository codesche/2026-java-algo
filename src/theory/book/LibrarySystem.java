package theory.book;

/**
 * 실습 목표
 * - 도서 등록
 * - 회원 등록
 * - 도서 대출
 * - 도서 반납
 * - 대출 가능 도서 조회
 * - 회원별 대출 목록 조회
 */

public class LibrarySystem {

    public static void main(String[] args) {

        LibraryService libraryService = new LibraryService();

        // 도서 등록
        libraryService.registerBook("111", "Effective Java");
        libraryService.registerBook("222", "Clean Code");
        libraryService.registerBook("333", "Spring in Action");

        // 회원 등록
        libraryService.registerMember("M1", "홍길동");
        libraryService.registerMember("M2", "김개발");

        // 대출
        libraryService.borrowBook("111", "M1");
        libraryService.borrowBook("222", "M2");

        // 대출 가능 도서 조회
        System.out.println("=== 대출 가능 도서 ===");
        libraryService.getAvailableBooks()
            .forEach(System.out::println);

        // 회원별 대출 조회
        System.out.println("=== M1 대출 목록 ===");
        libraryService.getLoansByMember("M1")
            .forEach(System.out::println);

        // 반납
        libraryService.returnBook("111");

        System.out.println("=== 반납 후 대출 가능 도서 ===");
        libraryService.getAvailableBooks()
            .forEach(System.out::println);

    }

}
