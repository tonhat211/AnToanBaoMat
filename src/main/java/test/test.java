package test;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class test {
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("C:\\Users\\TO NHAT\\Desktop\\product");
        File[] files = f.listFiles();
        String name="";
//        PrintWriter printWriter = new PrintWriter("C:\\Users\\TO NHAT\\Desktop\\thietbididong.txt");
        ArrayList<String> names = new ArrayList<>();
        for(File file : files) {
            name = file.getName();
            names.add(name);

        }
        String sql= " WHEN id = 1 THEN 10.99\n" +
                "    WHEN id = 2 THEN 15.49\n" +
                "    WHEN id = 3 THEN 8.99 ";
        String temp="";
        Random rd = new Random();
        int n;
        for(int i=52;i<122;i++) {
            n= rd.nextInt(200) +1;
            temp = "when id =" + i + " then " + n;
            System.out.println(temp);
        }
//        sql += temp;
//        System.out.println(sql);

        String filePath = "C:\\Users\\TO NHAT\\Desktop\\thietbididong.txt";
        String filePath2 = "C:\\Users\\TO NHAT\\Desktop\\detail_temp.txt";
        String filePath3 = "C:\\Users\\TO NHAT\\Desktop\\temp.txt";
        String re="";
        String[] tokens;
        String[] tokens2;
        ArrayList<String> ps = new ArrayList<>();
        ArrayList<String> de = new ArrayList<>();
        ArrayList<String> temps = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                ps.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath2))) {
            String line;
            while ((line = br.readLine()) != null) {
                de.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath3))) {
            String line;
            while ((line = br.readLine()) != null) {
                temps.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for(String s :temps) {
//            tokens = s.split("==");
//            re+="("+tokens[0]+",\""+tokens[5]+"\","+tokens[3]+","+tokens[4]+","+tokens[8]+"),\n";
//        }
//        System.out.println(re);




    }

    public static String upperTheFirst(String t) {
        System.out.println("hi");
        String temp = t;
        t=temp.substring(0, 1).toUpperCase() + temp.substring(1);
        return t;
    }
}
