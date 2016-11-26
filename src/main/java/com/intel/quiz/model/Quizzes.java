package com.intel.quiz.model;


import java.util.List;

/**
 * Created by Alex on 11/25/2016.
 */
public class Quizzes {

    private List<Quiz> quizzes;
    private List<Exam> exams;

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }
}
