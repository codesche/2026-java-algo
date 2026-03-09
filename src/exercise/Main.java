package exercise;

/**
 * 회원 등록
 * ----------
 * Main
 *  ↓
 * Controller.createUser()
 *  ↓
 * Service.registerUser()
 *  ↓
 * Repository.save()
 *  ↓
 * Map 저장
 * ===================================
 * 회원 조회
 * -----------
 * Main
 *  ↓
 * Controller.getUser()
 *  ↓
 * Service.getUser()
 *  ↓
 * Repository.findById()
 *  ↓
 * Map 조회
 * ===================================
 */

public class Main {

    public static void main(String[] args) {

        // 의존성 생성
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);

        // 회원 등록
        userController.createUser(1L, "Alice", 25);
        userController.createUser(2L, "Bob", 30);

        System.out.println();

        // 회원 조회
        userController.getUser(1L);
        System.out.println();

        // 전체 조회
        userController.getAllUsers();
    }

}
