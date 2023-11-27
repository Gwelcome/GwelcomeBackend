package backend.Gwelcome.jwt;

import lombok.Getter;

@Getter
public class Subject {
    private final String memberId;

    public Subject(String memberId) {
        this.memberId = memberId;
    }

    public static Subject atk(String memberId) {
        return new Subject(memberId);
    }

    public static Subject rtk(String memberId) {
        return new Subject(memberId);
    }
}