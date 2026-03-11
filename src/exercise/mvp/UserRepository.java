package exercise.mvp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private Map<Long, User> store = new HashMap<>();

    public void save(User user) {
        store.put(user.getId(), user);
    }

    public User findById(Long id) {
        return store.get(id);
    }

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

}
