package backend.Gwelcome.model;

public enum Interest {
    일자리분야("Job field"),
    주거분야("residential sector"),
    복지분야("Welfare field"),
    교육분야("Education field"),
    문화분야("Culture field");

    private final String data;

    Interest(String data){
        this.data = data;
    }

    public String data() {
        return data;
    }
}
