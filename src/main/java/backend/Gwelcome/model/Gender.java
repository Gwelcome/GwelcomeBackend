package backend.Gwelcome.model;

public enum Gender {
    남자("MAN"),
    여자("WOMAN");

    private final String data;
    Gender(String data){
        this.data = data;
    }
    public String data() {
        return data;
    }
}