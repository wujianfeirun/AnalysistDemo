package com.gaussic.util;

/**
 * Created by asus on 2017/5/6.
 */
public class Test {
    public static void main(String[] args) {
        ElasticsearchManager esManager = new ElasticsearchManager("192.168.1.101","www.elastic");

        esManager.searchDataByAllMatch("megacorp",30);

      //  esManager.closeClient();
    }
}
