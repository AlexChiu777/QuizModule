package com.intel.quiz.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intel.quiz.model.Quiz;
import com.intel.quiz.model.Quizzes;

/**
 * Created by Alex on 11/25/2016.
 */
public class JsonUtil {
    private static JsonUtil instance = null;

    public static JsonUtil getInstance() {
        if (instance == null) {
            instance = new JsonUtil();
        }
        return instance;
    }

    public Quizzes getQuizList(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Quizzes.class);
    }

    /*String prettyQuiz = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quiz);
    System.out.println(prettyQuiz);*/


}
