package org.jeecg.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Description:
 * @Author: Kingpin
 * @Date: 2020-08-06 10:49:01
 **/
public class PythonTest {
    public static void main(String[] args) {
        try {
            String stockCode = "sh.600000";
            String[] args11 = new String[]{"python", "G:\\workgroup\\stock\\python\\stock-profit-1.py", stockCode};
            Process pr = Runtime.getRuntime().exec(args11);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            pr.waitFor();
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
