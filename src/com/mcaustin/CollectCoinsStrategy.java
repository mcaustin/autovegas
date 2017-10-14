package com.mcaustin;

import java.rmi.UnexpectedException;

public class CollectCoinsStrategy {

    private FacebookController facebookController;
    private MyVegasController myVegasController;

    public CollectCoinsStrategy(FacebookController facebookController, MyVegasController myVegasController) {
        this.facebookController = facebookController;
        this.myVegasController = myVegasController;
    }


    public void run() throws UnexpectedException {
        facebookController.logIn();
        facebookController.launchGame();

        while (!myVegasController.isLoadingComplete()) {
            myVegasController.pause(1d);
        }
        facebookController.centerGameOnScreen();
        myVegasController.checkAndCollectDailyBonus();

        facebookController.closePopUp();

        myVegasController.closeAllPopUps();
        myVegasController.collectChips();

        for (int i = 0; i < 2; i++) {
            myVegasController.moveScreenRight();
            myVegasController.collectChips();
        }

        for (int i = 0; i < 10; i++) {
            myVegasController.moveScreenUp();
            myVegasController.collectChips();
        }

        for (int i = 0; i < 3; i++) {
            myVegasController.moveScreenLeft();
            myVegasController.collectChips();
        }

        for (int i = 0; i < 13; i++) {
            myVegasController.moveScreenDown();
            myVegasController.collectChips();
        }

    }

}
