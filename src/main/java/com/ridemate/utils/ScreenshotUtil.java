package com.ridemate.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtil {

    private ScreenshotUtil() { }

    /** Saves a screenshot into the screenshots/ folder and returns its path (or null if it failed). */
    public static String capture(WebDriver driver, String testName) {
        if (driver == null) {
            return null;
        }
        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path folder = Paths.get("screenshots");
            Files.createDirectories(folder);
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path target = folder.resolve(testName + "_" + time + ".png");
            Files.copy(source.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
            return target.toString();
        } catch (Exception e) {
            System.out.println("Could not take screenshot: " + e.getMessage());
            return null;
        }
    }
}
