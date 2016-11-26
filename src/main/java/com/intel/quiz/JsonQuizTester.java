package com.intel.quiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intel.quiz.model.Quiz;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Alex on 11/25/2016.
 */
public class JsonQuizTester {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String fileString = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "//src//main//resources//data//EdgePro.json")), StandardCharsets.UTF_8);

            Quiz quiz = mapper.readValue(fileString, Quiz.class);

            String prettyQuiz = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quiz);
            System.out.println(prettyQuiz);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
