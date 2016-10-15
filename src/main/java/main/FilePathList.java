package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilePathList {
    private final ArrayList files;

    public FilePathList() {
        this.files = new ArrayList(Arrays.asList("/form", "/coffee", "/redirect", "/text-file.txt"));
    }

    public boolean validFilePath(String path) {
        return files.stream()
                    .filter(file -> file.equals(path))
                    .findAny()
                    .isPresent();
    }

    public List getFiles() {
        return files;
    }
}
