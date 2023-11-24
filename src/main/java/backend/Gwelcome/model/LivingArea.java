package backend.Gwelcome.model;

public enum LivingArea {

    고양시("Goyang"),
    과천시("Gwacheon"),
    광명시("Gwangmyeong"),
    광주시("Gwangju"),
    구리시("Guri"),
    군포시("Gunpo"),
    김포시("Gimpo"),
    남양주시("Namyangju"),
    동두천시("Dongducheon"),
    부천시("Bucheon"),
    성남시("Seongnam"),
    수원시("Suwon"),
    시흥시("Siheung"),
    안산시("Ansan"),
    안성시("Anseong"),
    안양시("Anyang"),
    양주시("Yangju"),
    여주시("Yeoju"),
    오산시("Osan"),
    용인시("Yongin"),
    의왕시("Uiwang"),
    의정부시("Uijeongbu"),
    이천시("Icheon"),
    파주시("Paju"),
    평택시("Pyeongtaek"),
    포천시("Pocheon"),
    하남시("Hanam"),
    화성시("Hwaseong");

    private final String data;

    LivingArea(String data){
        this.data = data;
    }

    public String data() {
        return data;
    }
}