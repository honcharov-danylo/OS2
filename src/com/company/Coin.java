package com.company;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by leon on 31.10.15.
 */
public class Coin {
private int value;
private static Integer[] t=new Integer[] {1, 2, 5, 10, 25, 50 , 100};
private static LinkedList<Integer> AcceptebleValues=new LinkedList<>(Arrays.asList(t));
    public Coin(int v)
{
    value=v;
}
    public Coin()
    {
        int r=(int)(Math.random()*7);
        value=t[r];
    }
    public int GetValue()
    {
        return value;
    }
    public static Integer[] GetAcceptebleValues()
    {
        return t;
    }
    //    public boolean SetValue(int v)
//    {
//        if(AcceptebleValues.contains(v))
//        {
//        value=v;
//        return true;
//        }
//            return false;
//    }
    public static Coin GetLowerValue(Coin coin)
    {
//        System.out.println(coin.GetValue()+" ccc");

      //  System.out.println(AcceptebleValues.indexOf(coin.GetValue())+" ccc");
        int w=0;
        for(int i=0; i<t.length;i++)
        {
            if(coin.GetValue()==t[i]) w=i;
        }

        if(w!=0)
        {
            return new Coin(AcceptebleValues.get(w-1));
        }
        return null;
    }
//    public static Coin GetHighValue(Coin coin)
//    {
//        if(AcceptebleValues.indexOf(coin.GetValue())==AcceptebleValues.size()-1)
//        {
//            return new Coin(AcceptebleValues.get(AcceptebleValues.indexOf(coin)+1));
//        }
//        return null;
//    }
}
