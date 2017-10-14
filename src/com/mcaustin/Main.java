package com.mcaustin;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.sikuli.script.FindFailed;

import java.rmi.UnexpectedException;

public class Main {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static void printHowToUse() {
        System.out.println("***********************");
        System.out.println("** How To Use         *");
        System.out.println("***********************");
        System.out.println("java -jar MyVegas.jar username password");
    }

    public static void main(String[] args) throws FindFailed, UnexpectedException {
        System.out.println("Beginning MyVegas Run. CommandLine Arguments:");
        System.out.println("OS: " + OS);
        for (int i =0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        if (args.length != 2) {
            printHowToUse();
            System.exit(-1);
        }

        if (OS.indexOf("win") >= 0) {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
        }

        String username = args[0];
        String password = args[1];

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--mute-audio");
        ChromeDriver driver = new ChromeDriver(chromeOptions);

        FacebookController facebookController = new FacebookController(driver, username, password);
        MyVegasController myVegasController = new MyVegasController();

        CollectCoinsStrategy collectCoinsStrategy = new CollectCoinsStrategy(facebookController, myVegasController);

        collectCoinsStrategy.run();

        System.out.println("Run complete, terminating in 5 seconds");
        myVegasController.pause(5d);

        driver.close();
    }
}
