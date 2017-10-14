package com.mcaustin;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import java.rmi.UnexpectedException;
import java.util.Iterator;

public class MyVegasController {

    private Screen screen;

    private static final Double QUICK_TIMEOUT = 0.2d;
    private static final int PIXEL_MOVE_X = 240;
    private static final int PIXEL_MOVE_Y = 120;

    private static final String BASE_IMAGE_PATH = "images/";

    private static final Pattern COLLECT_BONUS = new Pattern(BASE_IMAGE_PATH + "collectbonus.png");
    private static final Pattern RED_CLOSE_BUTTON = new Pattern(BASE_IMAGE_PATH + "red_close_button.png");
    private static final Pattern COLLECT_CHIPS_HOVER = new Pattern(BASE_IMAGE_PATH + "collect_chips_hover.png");
    private static final Pattern COLLECT_CHIPS_CLICK = new Pattern(BASE_IMAGE_PATH + "click_to_collect.png");
    private static final Pattern LOADING_IMAGE = new Pattern(BASE_IMAGE_PATH + "loading_image.png");
    private static final Pattern LOADING_FLASH = new Pattern(BASE_IMAGE_PATH + "loading_flash.png");
    private static final Pattern MONTE_CENTER = new Pattern(BASE_IMAGE_PATH + "monte_center.png");

    private static Region regionTopRight;
    private static Region regionCenter;
    private static Region regionBottomRight;
    private static Region regionTopLeft;
    private static Region regionBottomLeft;

    public MyVegasController() {
        screen = new Screen();
    }

    public boolean isLoadingComplete() {
        if (screen.exists(LOADING_IMAGE, QUICK_TIMEOUT) != null) {
            return false;
        }
        if (screen.exists(LOADING_FLASH, QUICK_TIMEOUT) != null) {
            return false;
        }
        if (screen.exists(RED_CLOSE_BUTTON, QUICK_TIMEOUT) != null) {
            return true;
        }
        if (screen.exists(MONTE_CENTER, QUICK_TIMEOUT) != null) {
            return true;
        }
        return false;
    }

    public void checkAndCollectDailyBonus() {
        if (screen.exists(COLLECT_BONUS, QUICK_TIMEOUT) != null) {
            doubleClickIgnoreFail(COLLECT_BONUS);
            pause(1d);
        }
    }

    public void closeAllPopUps() throws UnexpectedException {
        int breakout = 0;
        while (screen.exists(RED_CLOSE_BUTTON, QUICK_TIMEOUT) != null) {
            breakout++;
            try {
                Iterator<Match> allMatches = screen.findAll(RED_CLOSE_BUTTON);
                while (allMatches.hasNext()) {
                    screen.doubleClick(allMatches.next());
                }
            } catch (FindFailed findFailed) {
                break;
            }
            if (breakout > 15) throw new UnexpectedException("Unable to click the red close button");
        }
        initScrollRegions();
    }

    public void collectChips() {
        if (screen.exists(COLLECT_CHIPS_HOVER, QUICK_TIMEOUT) != null) {
            moveIgnoreFail(COLLECT_CHIPS_HOVER);
            doubleClickIgnoreFail(COLLECT_CHIPS_CLICK);
        }
    }

    public void moveScreenRight() {
        dragDropIgnoreFail(regionCenter, regionTopLeft);
    }

    public void moveScreenLeft() {
        dragDropIgnoreFail(regionCenter, regionBottomRight);
    }

    public void moveScreenUp() {
        dragDropIgnoreFail(regionCenter, regionBottomLeft);
    }

    public void moveScreenDown() {
        dragDropIgnoreFail(regionCenter, regionTopRight);
    }

    public void pause(Double timeout) {
        try {
            screen.wait(timeout);
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
        }
    }

    private void initScrollRegions() {
        try {
            regionCenter = screen.find(MONTE_CENTER);

            regionTopRight = new Region(regionCenter.getX() + PIXEL_MOVE_X,
                    regionCenter.getY() - PIXEL_MOVE_Y);
            regionBottomRight = new Region(regionCenter.getX() + PIXEL_MOVE_X,
                    regionCenter.getY() + PIXEL_MOVE_Y);

            regionTopLeft = new Region(regionCenter.getX() - PIXEL_MOVE_X,
                    regionCenter.getY() - PIXEL_MOVE_Y);
            regionBottomLeft = new Region(regionCenter.getX() - PIXEL_MOVE_X,
                    regionCenter.getY() + PIXEL_MOVE_Y);

        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
        }
    }

    private void doubleClickIgnoreFail(Pattern pattern) {
        try {
            screen.doubleClick(pattern);
        } catch (FindFailed findFailed) {
            System.out.print("Pattern" + pattern.getFilename() + " not found.");
        }
    }

    private void moveIgnoreFail(Pattern pattern) {
        try {
            screen.mouseMove(pattern);
        } catch (FindFailed findFailed) {
            System.out.print("Pattern" + pattern.getFilename() + " not found.");
        }
    }

    private void dragDropIgnoreFail(Region one, Region two) {
        try {
            screen.dragDrop(one, two);
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
        }
    }
}
