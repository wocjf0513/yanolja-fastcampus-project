package kdt_y_be_toy_project1.common.util;

public enum FileFormat {
    JSON("json"),
    CSV("csv");

    private final String value;

    FileFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
