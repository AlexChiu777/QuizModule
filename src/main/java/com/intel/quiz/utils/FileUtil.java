package com.intel.quiz.utils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Alex on 11/25/2016.
 */
public class FileUtil {
    private static FileUtil instance = null;

    public static FileUtil getInstance() {
        if (instance == null) {
            instance = new FileUtil();
        }
        return instance;
    }

    public String readFileContent(String pathToFile) throws Exception {
        return new String(Files.readAllBytes(Paths.get(pathToFile)), StandardCharsets.UTF_8);
    }
}
