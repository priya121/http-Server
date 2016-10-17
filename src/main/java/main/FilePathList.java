package main;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FilePathList {
    private List files;

    public FilePathList() {
        this.files = Arrays.asList(File.separator,
                                  "/form",
                                  "/coffee",
                                  "/tea",
                                  "/redirect",
                                  "/text-file.txt",
                                  "/file1");
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
