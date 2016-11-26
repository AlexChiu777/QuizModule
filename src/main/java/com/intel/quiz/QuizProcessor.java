package com.intel.quiz;

import com.intel.quiz.model.Question;
import com.intel.quiz.model.Quiz;
import com.intel.quiz.model.Quizzes;
import com.intel.quiz.utils.FileUtil;
import com.intel.quiz.utils.JsonUtil;
import com.intel.quiz.utils.PageUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Alex on 11/24/2016.
 */

@FixMethodOrder(MethodSorters.JVM)
public class QuizProcessor {
    private final String INTEL_LOGIN_ASP_URI = "_layouts/Login.aspx";
    private final String LOGIN_BUTTON_ID = "id=bodyContent_Login";
    private final String LOGOUT_BUTTON_ID = "css=a > img.padme-for-now-yay";
    private final String WAIT_PAGE_DELAY = "60000";

    private final String ACCOUNT_FILE_NAME= "IntelAccounts.txt";

    private final String INTEL_USERNAME_ID = "UserName";
    private final String INTEL_PASSWORD_ID = "Password";
    private final String INTEL_LOGOUT_ID = "id=ctl00_cHeader_aLogout";

    //home page
    private final String EDGE_PRO_HOME = "/50/asmo/edgepro";

    //Quiz stuff
    private final String TAKE_THE_QUIZ = "Take the Quiz";
    private final String QUESTION_TEXT = "question";
    private final String ANSWER_TEXT = "answer";
    private final String ACTIONS_ELEMENT = "actions";
    private final String CLOSE_ELEMENT = "Close";

    private static final String BASE_URL = "https://retailedge.intel.com";
    private final String QUIZ_FILE_PATH = System.getProperty("user.dir") + "//src//main//resources//data//EdgePro.json";

    private static Quizzes quizzes;

    String driverPath = "src\\main\\resources\\driver\\";
    static WebDriver driver;

    Quiz quiz;

    @Test
    public void launchQuizProcessor() {
        quizFileSetup();
        launchBrowser();
        openApplication();
        testLogin();
        //testEdgePro();
        testEdgeProExam();
        testLogout();
        closeDriver();

    }


    public void quizFileSetup() {
        try {
            String quizFile = FileUtil.getInstance().readFileContent(QUIZ_FILE_PATH);
            quizzes = JsonUtil.getInstance().getQuizList(quizFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(quizzes);
    }


    public void launchBrowser() {
        System.out.println("launching firefox browser");
        System.setProperty("webdriver.gecko.driver", driverPath+"geckodriver.exe");
        driver = new FirefoxDriver();
        System.out.println(driver);
    }


    public void openApplication() {
        driver.navigate().to(BASE_URL + "/50");
        PageUtil.getInstance().waitForPageLoaded(driver);
    }


    public void testLogin() {
        driver.findElement(By.id(INTEL_USERNAME_ID)).clear();
        driver.findElement(By.id(INTEL_USERNAME_ID)).sendKeys("duduepyon@hotmail.com");
        driver.findElement(By.id(INTEL_PASSWORD_ID)).clear();
        driver.findElement(By.id(INTEL_PASSWORD_ID)).sendKeys("D.c98309407");
        driver.findElement(By.id("bodyContent_Login")).click();
    }

    public void testEdgeProExam() {
        WebDriverWait wait = new WebDriverWait(driver, 40);

            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }


        driver.navigate().to(BASE_URL + EDGE_PRO_HOME);

            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        WebElement element = driver.findElement(By.xpath("//div[@data-code='201610html-EdgePro-Processors-Exam']"));
        element.click();
    }


    public void testEdgePro() {
        WebDriverWait wait = new WebDriverWait(driver, 40);
        for (Quiz quiz: quizzes.getQuizzes()) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            driver.navigate().to(BASE_URL + quiz.getLink());

            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            wait.until(ExpectedConditions.elementToBeClickable(By.linkText(TAKE_THE_QUIZ))).click();

            boolean done = false;
            while (!done) {
                for (Question question : quiz.getQuestions()) {
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (driver.findElements(By.className(QUESTION_TEXT)).size() != 0) {
                        if (driver.findElement(By.className(QUESTION_TEXT)).getText().contains(question.getQuestion())) {
                            findTextFromClassAndClick(ANSWER_TEXT, question.getAnswer());
                        }
                    } else {
                        if (driver.findElement(By.className(ACTIONS_ELEMENT)).isDisplayed()) {
                            done = true;
                        }
                    }
                }
            }

            wait.until(ExpectedConditions.elementToBeClickable(By.linkText(CLOSE_ELEMENT))).click();
        }

    }



    public void testLogout() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.findElement(By.cssSelector("a > img.padme-for-now-yay")).click();
        PageUtil.getInstance().waitForPageLoaded(driver);
    }


    public void closeDriver() {
        if(driver!=null) {
            driver.close();
        }
    }


    public void findTextFromClassAndClick(String className, String text) {
        for (WebElement element : driver.findElements(By.className(className))) {
            if (element.getText().contains(text)) {
                element.click();
                break;
            }
        }
    }
}