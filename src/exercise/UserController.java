package exercise;

import java.util.List;

public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void createUser(Long id, String name, int age) {
        userService.registerUser(id, name, age);
    }

    public void getUser(Long id) {
        User user = userService.getUser(id);

        if (user == null) {
            System.out.println("회원이 존재하지 않습니다.");
        } else {
            System.out.println(user);
        }
    }

    public void getAllUsers() {
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);
    }


}
