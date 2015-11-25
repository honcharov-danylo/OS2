package com.company;

import javafx.util.Pair;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
    Pocket pocket=Pocket.getInstance();
    ProcessA processA=new ProcessA();
    ProcessB processB=new ProcessB();
    pocket.AddRandomCoin(20);
    processA.start();
    processB.start();


        get("/", (req, res) ->{
        String r=GetHeader();
            r+=GetCoinsListInHtml();
            r+=GetForAddingsButtons();
            r+=GetButtons();
            r+=GetForRemovingButtons();
            r+=GetFooter();
            return r;
        });

        get("/exch/:id", (req, res) ->{
            String r=GetHeader();
            r+=GetCoinsListInHtml();
           // System.out.println(Integer.getInteger(req.params(":id")));
           // if(req.params(":id").endsWith(".ico")) {
           //     res.redirect("/");
          //      return null;
          //  }
            //System.out.println(req.params(":id"));
            r+=GetButtonsForExchange(Integer.parseInt(req.params(":id")));
            r+=GetFooter();
            return r;
        });


        get("/add/:id", (req, res) ->{
            pocket.AddCoin(new Coin(Integer.parseInt(req.params(":id"))));
            res.redirect("/");
            return null;
        });


        get("/rem/:id", (req, res) ->{
            pocket.RemoveCoin(new Coin(Integer.parseInt(req.params(":id"))));
            res.redirect("/");
            return null;
        });

        get("/res/:idf/:ids", (req, res) ->{
            processA.queue.add(Integer.parseInt(req.params(":idf")));
            Pair<Coin,Coin> exch=new Pair<>(new Coin(Integer.parseInt(req.params(":idf"))),new Coin(Integer.parseInt(req.params(":ids"))));
            processB.queue.add(exch);
            String mes=null;
            while(mes==null) {
            mes=processB.GetMessage();}
            String r=GetHeader();
            r+="<center><font size=+3 color=black><b>"+mes+"</b></font></center><br/>";
            r+="<center><font size=+3 color=black><a href=/ >Назад</a></font></center><br/>";
            r+=GetFooter();
            return r;
        });
    }
    private static String GetStyles()
    {
        LinkedList<String> styles=null;
        try {
             styles = new LinkedList<String>(Files.readAllLines(new File("style.css").toPath()));
            String res="";
            for(String s:styles)
            res+=s+"\r\n";
            return " <style>\n" +res+"</style>";
        }
        catch (IOException io)
        {
            return "";
        }
        }
    private static String  GetHeader()
    {
        return "<html><head><title>Чудесная вторая лаба</title>"+GetStyles()+"</head><body class='gradient'>";
    }
    private static String GetCoinsListInHtml()
    {
        String res="Монетки в автомате:<br/>";
        Pocket p=Pocket.getInstance();
        res+="Всего аж "+p.coins.size()+" штук<br/>";
        res+="<div style='width:20%;height:50%;'>";
        LinkedList<Coin> allcoins=new LinkedList<>(p.coins);

        Collections.sort(allcoins, new Comparator<Coin>() {
            @Override
            public int compare(Coin o1, Coin o2) {
                if(o1.GetValue()>o2.GetValue()) return 1;
                else return -1;
            }
        });
        for(Coin a:allcoins)
        {
            res+="<div class='coin silver'><p>"+a.GetValue()+"</p></div>";
        }
        res+="</div>";
        return res;
    }
    private static String GetButtons()
    {
        String res="";
        res+="<div width=20% height=30% style='border: 4px double black; position:absolute; top:180;left:20%; padding:5px '>";
        res+="Выберите монету, которую хотите кинуть в автомат:<br/>";
        res+=GetAbstractButton("exch");
        res+="</div>";
        return res;
    }
    private static String GetAbstractButton(String addr)
    {

        String res="";
        Integer[] acv=Coin.GetAcceptebleValues();
        if(addr!=null)
        for(int i=0;i<acv.length;i++)
        {
            res+="<button class='btn' onClick='location.href=\"/"+addr+"/"+acv[i]+"\"' name=but"+acv[i]+">"+acv[i]+"</button>";
        }
        else
        {
            for(int i=0;i<acv.length;i++)
            {
                res+="<button class='btn' onClick='location.href=\"/"+acv[i]+"\"' name=but"+acv[i]+">"+acv[i]+"</button>";
            }
        }
        return res;
    }
    private static String GetForAddingsButtons()
    {
        String res="";
        res+="<div width=20% height=30% style='border: 4px double black; position:absolute; top:10px; left:20%; padding:5px;'>";
        res+="Выберите монету, которую хотите добавить (просто добавить) в автомат:<br/>";
        res+=GetAbstractButton("add");
        res+="</div>";
        return res;
    }
    private static String GetForRemovingButtons()
    {

        String res="";
        res+="<div width=20% height=30% style='border: 4px double black; position:absolute; top:95px; left:20%; padding:5px; '>";
        res+="Выберите монету, которую хотите удалить из автомата:<br/>";
        res+=GetAbstractButton("rem");
        res+="</div>";
        return res;
    }
    private static String GetFooter()
    {
        return "</body></html>";
    }
    private static String GetButtonsForExchange(int w)
    {
        String res="";
        res+="<div width=20% height=30% style='border: 4px double black; position:absolute; top:180;left:20%;padding:5px;'>";
        res+="Выберите монеты, на которые хотите поменять:<br/>";
        res+=GetAbstractButton("res/"+w);
        res+="</div>";
        return res;
    }

}
