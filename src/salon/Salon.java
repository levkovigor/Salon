/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salon;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author igor
 */
public class Salon {

    public static final int N = 5;
    public static final int MASTER_SPEED = 4;
    public static final int CLIENT_SPEED = 2;
    public static final int CLIENTS = 20;

    public static BlockingQueue<Thread> queue = new ArrayBlockingQueue<>(N);

    public static void main(String[] args) {

        Master master = new Master(queue, MASTER_SPEED);
        master.start();

        int i = 1;

        while (i <= CLIENTS) {

            try {
                Random generator = new Random();
                Thread.sleep((generator.nextInt(10) + 1) * 100 * CLIENT_SPEED);
            } catch (InterruptedException ex) {
                Logger.getLogger(Salon.class.getName()).log(Level.SEVERE, null, ex);
            }

            new Client(queue).start();
            i++;
        }
    }

}
