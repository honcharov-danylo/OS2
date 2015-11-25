package com.company;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by leon on 31.10.15.
 */
public class ProcessA extends Thread {
    ArrayBlockingQueue<Integer> queue=new ArrayBlockingQueue<Integer>(10);

    public void run()
    {
        Pocket p = Pocket.getInstance();
        Flags f=Flags.getInstance();
        try {
         while (!Thread.currentThread().isInterrupted()) {
             Coin newCoin = new Coin(queue.take());
            //Dekker's start
             f.flagA=true;
        //    System.out.print(f.flagB);
             while(f.flagB==true)
            {
                if(f.turn==1)
                {
                    f.flagA=false;
                    while(f.turn==1){}
                    f.flagA=true;
                }
            }


         //critical section starts
    //         System.out.println("\r\n"+newCoin.GetValue()+" was created - (process A report)");

             p.AddCoin(newCoin);
             f.turn=1;
             f.flagA=false;
         //critical section ends

         }
     }
     catch(InterruptedException ie)
     {
         System.out.println("Process A finished the work. Good luck!");
     }
    }

}
