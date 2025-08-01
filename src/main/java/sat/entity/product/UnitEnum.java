package sat.entity.product;

public enum UnitEnum {
    МГ("мг"),
    Г("г"),
    КГ("кг"),
    Т("т"),
    МЛ("мл"),
    Л("л"),
    СМ3("см3"),
    М3("м3"),
    ММ("мм"),
    СМ("см"),
    М("м"),
    КМ("км"),
    ДЮЙМ("дюйм"),
    ШТ("шт"),
    УПАК("упак");

    private final String value;

    UnitEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UnitEnum fromValue(String value) {
        for (UnitEnum unit : values()) {
            if (unit.value.equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Неизвестная единица измерения: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
