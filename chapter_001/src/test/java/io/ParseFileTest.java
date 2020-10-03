package io;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParseFileTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testGetAndSaveContent() throws IOException, InterruptedException {
        File firstFile = temporaryFolder.newFile("first");
        File secondFile = temporaryFolder.newFile("second");

        try (PrintStream pstream = new PrintStream(firstFile)) {
            pstream.print("First file text");
        }

        try (PrintStream pstream = new PrintStream(secondFile)) {
            pstream.print("Second file text");
        }

        Map<String, ParseFile> parsers = new ConcurrentHashMap<>();
        Map<String, String> results = new ConcurrentHashMap<>();

        Thread thread1 = new Thread(
                () ->  {
                    ParseFile parser = new ParseFile(firstFile);
                    parsers.put(Thread.currentThread().getName(), parser);
                    try {
                        results.put(Thread.currentThread().getName(), parser.getContent());
                        parser.saveContent(" mark by ".concat(Thread.currentThread().getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, "First Thread"
        );

        Thread thread2 = new Thread(
                () ->  {
                    ParseFile parser = new ParseFile(secondFile);
                    parsers.put(Thread.currentThread().getName(), parser);
                    try {
                        results.put(Thread.currentThread().getName(), parser.getContent());
                        parser.saveContent(" mark by ".concat(Thread.currentThread().getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, "Second Thread"
        );

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertThat(results.get(thread1.getName()), is("First file text"));
        assertThat(results.get(thread2.getName()), is("Second file text"));

        assertEquals(parsers.get(thread1.getName()).getFile(), firstFile);
        assertEquals(parsers.get(thread2.getName()).getFile(), secondFile);


        try (BufferedReader read = new BufferedReader(new FileReader(firstFile))) {
            assertThat(read.readLine(), is(" mark by ".concat(thread1.getName())));
        }

        try (BufferedReader read = new BufferedReader(new FileReader(secondFile))) {
            assertThat(read.readLine(), is(" mark by ".concat(thread2.getName())));
        }
    }

    @Ignore
    @Test
    public void testGetContentWithoutUnicode() throws IOException {
        File firstFile = temporaryFolder.newFile("first");
        String unicodeTest = "First " + "\uFFAC" + " text";
        try (PrintStream pstream = new PrintStream(firstFile)) {
            pstream.print(unicodeTest);
        }
        ParseFile parser = new ParseFile(firstFile);

        assertThat(parser.getContent(), is(unicodeTest));
        assertThat(parser.getContentWithoutUnicode(), is("First  text"));
    }

}