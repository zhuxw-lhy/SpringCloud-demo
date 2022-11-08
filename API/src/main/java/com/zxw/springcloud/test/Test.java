package com.zxw.springcloud.test;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Copyright: Copyright (c) 2022 Asiainfo-Linkage
 *
 * @ClassName: Test
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: zhuxw
 * @date: 2022/8/27 14:08
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2022/8/27     zhuxw           v1.0.0               修改原因
 */
public class Test {
    public static void main(String[] args) {

        new Thread(()->{

            try {
                Socket socket = new Socket("127.0.0.1", 8082);

                while(true){
                    Scanner scanner = new Scanner(System.in);
                    String next = scanner.next();
                    socket.getOutputStream().write(next.getBytes(StandardCharsets.UTF_8));
                    Thread.sleep(2000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }).start();
    }
}
