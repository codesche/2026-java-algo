package exercise.logdata;

import java.util.*;
import java.util.stream.Collectors;

public class Log<T> {

    private T userId;
    private String message;
    private int level;

    public Log(T userId, String message, int level) {
        this.userId = userId;
        this.message = message;
        this.level = level;
    }

    public T getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Log{" +
            "userId=" + userId +
            ", message='" + message + '\'' +
            ", level=" + level +
            '}';
    }
}
