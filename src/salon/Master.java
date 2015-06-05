/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salon;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author igor
 */
public class Master extends Thread {

    BlockingQueue<Thread> queue;
    int speed;

    public Master(BlockingQueue<Thread> queue, int speed) {
        this.queue = queue;
        this.speed = speed;
    }

    public void run() {

        while (true) {

            if (!queue.isEmpty()) {

                Client client = (Client) queue.poll();
                System.out.println("Клиент: " + client.getId() + " стрижется");

                try {
                    Random generator = new Random();
                    Thread.sleep((generator.nextInt(10) + 1) * 100 * speed);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
                }

                synchronized (client) {
                    client.notify();
                }

            } else {

                synchronized (this) {
                    try {
                        queue.put(this);
                        System.out.println("Мастер заснул");
                        this.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
    }

}
