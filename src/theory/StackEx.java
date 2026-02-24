package theory;

import java.util.EmptyStackException;

public class StackEx {

    static class MyStack {
        private int[] arr;
        private int top;
        private int capacity;

        public MyStack(int size) {
            this.capacity = size;
            this.arr = new int[size];
            this.top = -1;
        }

        // push
        public void push(int value) {
            if (top == capacity - 1) {
                throw new StackOverflowError("스택이 가득 찼습니다.");
            }
            arr[++top] = value;
        }

        // pop
        public int pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return arr[top--];
        }

        // peek
        public int peek() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return arr[top];
        }

        public boolean isEmpty() {
            return top == -1;
        }

        public int size() {
            return top + 1;
        }
    }

    // 괄호 유효성 검사
    public static boolean isValidParentheses(String str) {
        MyStack stack = new MyStack(str.length());

        for (char ch : str.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty()) return false;
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        // 1. 기본 스택 테스트
        MyStack stack = new MyStack(5);

        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Top: " + stack.peek());         // 30
        System.out.println("Pop: " + stack.pop());          // 30
        System.out.println("Size: " + stack.size());        // 2

        // 2. 괄호 검사
        String test1 = "(()())";
        String test2 = "(()";

        System.out.println(test1 + " → " + isValidParentheses(test1));
        System.out.println(test2 + " → " + isValidParentheses(test2));
    }

}
