package io;

import java.io.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ParseFile {
    public ParseFile(File file) {
        this.file = file;
    }

    final private File file;


    public synchronized File getFile() {
        return file;
    }

    private synchronized String read(Predicate<Integer> predicate) {
        String result = "";
        try (BufferedReader read = new BufferedReader(new FileReader(file))) {
            result = read.lines().map(str -> getLine(predicate, str)).collect(Collectors.joining(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getLine(Predicate<Integer> predicate, String line) {
        String result = "";
        if (predicate != null) {
            result = line.chars().map(ch -> predicate.test(ch) ? ch : Character.MIN_VALUE).collect(StringBuilder::new,
                    StringBuilder::appendCodePoint,
                    StringBuilder::append)
                    .toString().replace("\0", "");
        } else {
            result = line;
        }
        return result;
    }

    public String getContent() throws IOException {
        return this.read(null);
    }

    public String getContentWithoutUnicode() throws IOException {
        return this.read(
                (Integer integer) -> integer < 0x80
        );
    }

    public synchronized void saveContent(String content) throws IOException {
        try (PrintStream pstream = new PrintStream(file)) {
            pstream.print(content);
            System.out.print("");
        }
    }
}