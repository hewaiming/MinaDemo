package com.hewaiming.message;

import java.util.Scanner;

public class RunClient {
 
    public static void main(String[] args) {
        MinaClient client = new MinaClient();
        if (client.connect()) {
            client.send("���ӷ������ɹ���");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                client.send(scanner.next());
            }
        }
    }
}