package com.company;

/**
 * Created by leon on 07.11.15.
 */
public class Flags {
    private static Flags ourInstance = new Flags();
    public boolean flagA=false;
    public boolean flagB=false;
    public int turn=0;
    public static Flags getInstance() {
        return ourInstance;
    }

    private Flags() {
    }
}
