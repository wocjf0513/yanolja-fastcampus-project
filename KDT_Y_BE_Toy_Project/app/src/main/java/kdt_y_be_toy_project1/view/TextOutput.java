package kdt_y_be_toy_project1.view;

final class TextOutput {

    private static final String LINE_DELIMITER = System.lineSeparator();
    private final StringBuilder buffer;

    public TextOutput() {
        buffer = new StringBuilder();
    }

    public void print(String line) {
        buffer.append(line);
    }

    public void println(String line) {
        buffer.append(line).append(LINE_DELIMITER);
    }

    public String flush() {
        String output = buffer.toString();
        buffer.setLength(0);
        return output;
    }
}
