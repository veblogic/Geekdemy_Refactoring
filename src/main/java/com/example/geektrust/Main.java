package com.example.geektrust;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<String> pgms = new ArrayList<String>();
    static ArrayList<Integer> pgmCounts = new ArrayList<Integer>();
    static ArrayList<Float> pgmAmts = new ArrayList<Float>();
    static ArrayList<String> cpns = new ArrayList<String>();

    static Float subTot = 0f;
    static Float tot = 0f;
    static Float disc= 0f;
    static Float proDisc= 0f;
    static Float enFee = 0f;
    static Float pmFee = 0f;
    static String ca = "NONE";
    static boolean proMembershipAdded = false;

    public static void main(String[] args) {

        try {
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); 
            while (sc.hasNextLine()) {
                String input = sc.nextLine();
                String[] cmd = input.split(" ");
                switch (cmd[0]) {
                    case "ADD_PROGRAMME":
                        String pgm = cmd[1];
                        String qty = cmd[2];
                        Float cost;
                        switch (pgm) {
                            case "CERTIFICATION":
                                cost = 3000f;
                                pgms.add(pgm);
                                pgmCounts.add(Integer.parseInt(qty));
                                pgmAmts.add(cost);
                                break;
                            case "DEGREE":
                                cost = 5000f;
                                pgms.add(pgm);
                                pgmCounts.add(Integer.parseInt(qty));
                                pgmAmts.add(cost);
                                break;
                            case "DIPLOMA":
                                cost = 2500f;
                                pgms.add(pgm);
                                pgmCounts.add(Integer.parseInt(qty));
                                pgmAmts.add(cost);
                                break;
                        }
                        break;
                    case "ADD_PRO_MEMBERSHIP":
                        proMembershipAdded = true;
                        break;
                    case "APPLY_COUPON":
                        cpns.add(cmd[1]);
                        break;
                    case "PRINT_BILL":
                        bill();
                        System.out.printf("SUB_TOTAL %f\n", subTot);
                        System.out.printf("COUPON_DISCOUNT %s %f\n", ca, disc);
                        System.out.printf("TOTAL_PRO_DISCOUNT %f\n", proDisc);
                        System.out.printf("PRO_MEMBERSHIP_FEE %f\n", pmFee);
                        System.out.printf("ENROLLMENT_FEE %f\n", enFee);
                        System.out.printf("TOTAL %f\n", tot);
                        break;                    
                }
                
            }
            sc.close(); 
        } catch (IOException e) {
        }
    }

    public static void bill() {
        if(proMembershipAdded) {
            Float v;
            Float pd = 0f;
            for (int i = 0; i < pgms.size(); i++) {
                switch(pgms.get(i)){
                    case "CERTIFICATION":
                        pd = pgmAmts.get(i) * 2 / 100;
                        break;
                    case "DEGREE":
                        pd = pgmAmts.get(i) * 3 / 100;
                        break;
                    case "DIPLOMA":
                        pd = pgmAmts.get(i) * 1 / 100;
                        break;
                }
                proDisc =  (pd * pgmCounts.get(i));
            }
        }

        Integer pgmCnt = 0;
        for (int i=0; i < pgms.size(); i++) {
            subTot +=  (pgmAmts.get(i) * pgmCounts.get(i));    
            pgmCnt += pgmCounts.get(i);

        }

        if (pgmCnt > 4){
            ca = "B4G1";            
        }if (subTot > 10000 && cpns.contains("DEAL_G20")){
            ca= "DEAL_G20";
        }else if(pgmCnt > 2 && cpns.contains("DEAL_G5")){
            ca = "DEAL_G5";
        }


        Float lowVal = pgmAmts.get(0);
        for (int i=0; i < pgms.size(); i++) {
            if (pgmAmts.get(i) > lowVal){
                lowVal = pgmAmts.get(i);
            }
        }

       switch (ca){
            case "B4G1":    
                tot = subTot - lowVal;
                disc = lowVal;
                break;
            case "DEAL_G20":
                disc = (tot * 20 / 100);
                tot = subTot - disc;
                break;
            case "DEAL_G5":
                disc = (tot * 5 / 100);
                tot = subTot - disc;
                break;
            default:
                tot = subTot;
                break;
       }

       if (tot < 6666f){
           enFee = 500f;
            tot = tot + enFee;
       }
    }
}
