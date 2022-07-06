package net.gooday2die.openbooks;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;


public class BookReader {
    Map<String, Object> bookData;
    public BookReader(String booksDirectory) throws FileNotFoundException {
        bookData = new Yaml().load(new FileReader(booksDirectory));
        this.read();
    }
    public void read() {
        for (Map.Entry<String, Object> entry : bookData.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
    }
}
