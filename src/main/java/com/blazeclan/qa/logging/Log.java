package com.blazeclan.qa.logging;

import com.aventstack.extentreports.Status;
import com.blazeclan.qa.bdd.hooks.Hooks;
import com.blazeclan.qa.reports.ExtentTestManager;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.PickleStepTestStep;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static com.blazeclan.qa.bdd.hooks.Hooks.scenario;

public class Log {
    //Initialize Log4j instance
    private static final Logger log = LogManager.getLogger(Log.class.getName());
    //Info Level Logs
    public static void info (String message) {
       log.info(message);
       extentLog(message);
        cucumberLog(message);
    }
    //Warn Level Logs
    public static void warn (String message) {
        log.warn(message);
        extentLog(message);
        cucumberLog(message);
    }
    //Error Level Logs
    public static void error (String message) {
        log.error(message);
        extentLog(message);
        cucumberLog(message);
    }
    //Fatal Level Logs
    public static void fatal (String message) {
        log.fatal(message);
        extentLog(message);
        cucumberLog(message);
    }
    //Debug Level Logs
    public static void debug (String message) {
        log.debug(message);
        extentLog(message);
        cucumberLog(message);
    }

    private static void extentLog(String message) {
        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager.getTest().log(Status.INFO, message);
        }
    }

    private static void cucumberLog(String message) {
        if (scenario != null) {
            scenario.log(message);
        }
    }
}
