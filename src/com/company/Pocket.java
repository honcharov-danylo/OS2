package com.company;

import java.util.ArrayList;

/**
 * Created by leon on 31.10.15.
 */
public class Pocket {
    private static Pocket ourInstance = new Pocket();

    public static Pocket getInstance() {
        return ourInstance;
    }
    ArrayList<Coin> coins=new ArrayList<Coin>();;
    private Pocket() {
    }
    public ArrayList<Coin> GetCoins()
    {
        return coins;
    }
    public void AddCoin(Coin a)
    {
     coins.add(a);
    }
    public void AddRandomCoin(int count)
    {
        for(int i=0;i<count;i++)
        {
            coins.add(new Coin());
        }
    }
    public void RemoveCoin(Coin a)
    {
        for(Coin t:coins)
            if(t.GetValue()==a.GetValue())
            {
                coins.remove(t);
                break;
            }
    }

}
