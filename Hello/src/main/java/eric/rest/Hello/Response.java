package eric.rest.Hello;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Response {

    private final String message;
    private final LocalDateTime dateTime;
    private final String privateMsg;
    private final static String STR = "My String";
    private MyClass myClass;

    public Response(String message) {
        this.message = message;
        this.dateTime = LocalDateTime.now();
        this.privateMsg = "This is my private msg";
        this.myClass = new MyClass("this is my private class");
    }

    public String getPrivateMsg() {
        return privateMsg;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getMyClass() {
        return myClass.prvClassStr;
    }

    private class MyClass {
        private String prvClassStr;

        public MyClass(String prvClassStr) {
            this.prvClassStr = prvClassStr;
        }

        public String getPrvClassStr() {
            return prvClassStr;
        }
    }
}
