package com.changyue;

import java.util.LinkedList;

/**
 * @program: ProAndCon
 * @description: java 多线程解决生产者和消费者问题
 * @author: ChangYue
 * @create: 2019-10-26 10:42
 */
public class ProAndCon {

    private static final int MAX_SIZE = 5;
    private static LinkedList<Integer> list = new LinkedList<>();

    class Producer implements Runnable {

        @Override
        public void run() {
            synchronized (list) {
                while (list.size() == MAX_SIZE) {
                    System.out.println("仓库已满,生产者" + Thread.currentThread().getName() + "不可生产");
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.add(1);
                System.out.println("生产者" + Thread.currentThread().getName() + "生产,仓库的容量为：" + list.size());
                list.notify();
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            synchronized (list) {
                while (list.size() == 0) {
                    System.out.println("仓库已空,消费者" + Thread.currentThread().getName() + "不可消费");
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.removeFirst();
                System.out.println("消费者" + Thread.currentThread().getName() + "消费,仓库的容量为：" + list.size());
                list.notify();
            }
        }
    }

    public static void main(String[] args) {
        ProAndCon proAndCon = new ProAndCon();
        Consumer consumer = proAndCon.new Consumer();
        Producer producer = proAndCon.new Producer();

        for (int i = 0; i < 10; i++) {
            Thread pro = new Thread(producer);
            pro.start();
            Thread com = new Thread(consumer);
            com.start();
        }
    }

}
