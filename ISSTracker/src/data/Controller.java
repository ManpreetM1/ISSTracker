
package data;

import app.AppHandler;
import app.Graphics;

public class Controller{
    static Graphics gapp;
    static AppHandler ah;
    public static void main(String[] args) {
        ah = new AppHandler();
        gapp = new Graphics(ah);
        
        Thread t = new Thread() {
            public void run() {
                while(true) {
                    ah.update();
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        };
        t.start();
        try {
            t.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
}