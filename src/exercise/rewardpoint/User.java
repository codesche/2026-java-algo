package exercise.rewardpoint;

public class User {

    private String id;
    private Grade grade;
    private boolean firstOrder;

    public User(String id, Grade grade, boolean firstOrder) {
        this.id = id;
        this.grade = grade;
        this.firstOrder = firstOrder;
    }

    public Grade getGrade() {
        return grade;
    }

    public boolean isFirstOrder() {
        return firstOrder;
    }

}
