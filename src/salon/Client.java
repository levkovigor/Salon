/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salon;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import static salon.Salon.N;

/**
 *
 * @author igor
 */
public class Client extends Thread {

    BlockingQueue<Thread> queue;

    public Client(BlockingQueue<Thread> queue) {
        this.queue = queue;
    }

    public void run() {
        
        System.out.print("Клиент: " + this.getId() + " ");

        if (queue.size() == N) {
            
            System.out.println("Нет Мест");
            
        } else {

            try {
                queue.put(this);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (queue.peek().getClass().equals(Master.class)) {
                
                Master master = (Master) queue.poll();
               
                synchronized (master) {
                    master.notify();
                    System.out.print("разбудил мастера ");
                }
            }

            synchronized (this) {
                try {
                    System.out.println("ожидает ");
                    this.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
   
        }

        System.out.println("Клиент: " + this.getId() + " уходит");
    }
}
