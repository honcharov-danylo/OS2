package com.company;

import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by leon on 07.11.15.
 */
public class ProcessB extends Thread {
    public ArrayBlockingQueue<Pair<Coin,Coin>> queue = new ArrayBlockingQueue<Pair<Coin,Coin>>(10);
    private ArrayList<String> messages=new ArrayList<>();

   private Pocket p = Pocket.getInstance();
    private Flags f = Flags.getInstance();
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {

                Pair<Coin,Coin> pr=queue.take();
                Coin ForExchange = pr.getKey();
                Coin ExchangeIn = pr.getValue();

                ArrayList<Coin> coins=p.GetCoins();
                int count=ForExchange.GetValue()/ExchangeIn.GetValue();
                int counter=0;
                for(Coin c:coins)
                {
                    if(c.GetValue()==ExchangeIn.GetValue()) counter++;
                }

                //Dekker's start
                f.flagB=true;
                while(f.flagA==true)
                {
                    if(f.turn==0)
                    {
                        f.flagB=false;
                        while(f.turn==0){}
                        f.flagB=true;
                    }
                }



                // crit section starts


                if(count>counter) putMessage("We can not return exchange. Sorry.");
                else {
                    if(ForExchange.GetValue()==(count*(ExchangeIn.GetValue())))
                    {
                        putMessage("We return  "+ count +" coins , value "+ExchangeIn.GetValue());
                        //p.AddCoin(ForExchange);
                        for(int i=0;i<count;i++)
                            p.RemoveCoin(new Coin(ExchangeIn.GetValue()));
                    }
                    else
                    {
                        System.out.println(Coin.GetLowerValue(ExchangeIn).GetValue());

                        if(Coin.GetLowerValue(ExchangeIn)!=null)
                        {

                            Coin smaller=Coin.GetLowerValue(ExchangeIn);
                            if(ForExchange.GetValue()==(count*(ExchangeIn.GetValue()))+smaller.GetValue())
                            {
                                boolean ex=false;
                                for(Coin t:coins)
                                    if(t.GetValue()==smaller.GetValue()){
                                      ex=true;
                                        break;
                                    }
                                if(ex) {
                                    putMessage("We return  " + count + " coins, value " + ExchangeIn.GetValue() + " & 1 coin value " + smaller.GetValue());
                                   // p.AddCoin(ForExchange);
                                    for (int i = 0; i < count; i++)
                                        p.RemoveCoin(new Coin(ExchangeIn.GetValue()));
                                    p.RemoveCoin(new Coin(smaller.GetValue()));
                                }
                                else
                                {
                                    putMessage("We can not return exchange. Sorry.");
                                }
                            }
                        }
                    }
                }
                f.turn=0;
                f.flagB=false;
                //crit section ends
            }
        } catch (InterruptedException ie) {
            System.out.println("Process B finished the work. Good luck!");
        }


    }
    private synchronized void putMessage(String mes)
    {
        messages.add(mes);
    }
    public synchronized String GetMessage()
    {
        if(messages.size()==0) return null;
        String res=messages.get(messages.size()-1);
        messages.remove(messages.size()-1);
        return res;
    }
}
