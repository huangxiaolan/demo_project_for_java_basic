package com.hxiaol.socketio.metrics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  监控程序
 */
public class Metric {
    private static final int[] TimeRange = new int[] { 0, 10, 20, 50, 100, 200, 500, 1000, 5000, Integer.MAX_VALUE };
    private ConcurrentHashMap<String, Counter> counter = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, AtomicInteger> packetSend = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, AtomicInteger> packetRecv = new ConcurrentHashMap<>();

    private ConcurrentLinkedQueue<CountStat> countStats = new ConcurrentLinkedQueue<>();

    public void send(String event){
        String key = event;

        if(!packetSend.contains(key)){

            AtomicInteger pack = packetSend.putIfAbsent(key,new AtomicInteger(1));
            if(pack != null){
                pack.incrementAndGet();
            }

        }else{
            packetSend.get(key).incrementAndGet();
        }



    }

    public void recv(String event){
        String key = event;

        if(!packetRecv.contains(key)){

            AtomicInteger pack = packetRecv.putIfAbsent(key,new AtomicInteger(1));
            if(pack != null){
                pack.incrementAndGet();
            }
        }else{
            packetRecv.get(key).incrementAndGet();
        }
    }

    public void add(String event,long time,boolean success){
        countStats.add(new CountStat(event,time,success));

    }
    public  String output() {
        StringBuilder sb = new StringBuilder();
        sb.append("发送请求数: \n");
        packetSend.keySet().stream().forEach(k -> {
            sb.append("\t").append(k).append(":\t").append(packetSend.get(k).intValue()).append("\n");
        });
        sb.append("接收响应数: \n");
        packetRecv.keySet().stream().forEach(k -> {
            sb.append("\t").append(k).append(":\t").append(packetRecv.get(k).intValue()).append("\n");
        });
        sb.append("耗时: \n");
        countStats.forEach(countStat -> {
            String name = countStat.getName();
            long time = countStat.getTime();
            boolean success = countStat.isSuccess();
            if (!counter.containsKey(name)) {
                Counter c = counter.putIfAbsent(name, new Counter(name));
                if(c != null){
                    c.addCount(time,success);
                }
            }
            counter.get(name).addCount(time,success);
        });
        counter.values().stream().forEach(c -> {
            sb.append(c.toString()).append("\n");
        });

        return sb.toString();
    }



    @Getter
    @Setter
    public static class Counter {
        String name;
        int totalCount;
        int failedCount;
        long totalTime;
        int[] countRange = new int[TimeRange.length];
        long[] sumTimeRange = new long[TimeRange.length];

        public Counter(String name) {
            this.name = name;
        }

        public void addCount(long usedTime, boolean success) {
            if (usedTime < 0) {
                return;
            }
            synchronized (this) {
                int i = TimeRange.length - 1;
                while (TimeRange[i] > usedTime) {
                    i--;
                }
                totalCount++;
                failedCount += success ? 0 : 1;
                totalTime += usedTime;

                countRange[i]++;
                sumTimeRange[i] += usedTime;
            }
        }

        public void output() {
            System.out.println(this.toString());
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(name).append(":");
            sb.append("\t请求数: ").append(totalCount).append(",\t失败数: ").append(failedCount).append(",\t平均耗时: ")
              .append(0 == totalCount ? 0 : (totalTime / totalCount)).append("ms\n");
            for (int i = 0; i < TimeRange.length - 1; i++) {
                sb.append("\t");
                sb.append(TimeRange[i]).append("~")
                  .append(TimeRange[i + 1] == Integer.MAX_VALUE ? "" : TimeRange[i + 1]).append(" ms: ")
                  .append(countRange[i]).append(",\t占比: ").append(p(countRange[i], totalCount)).append("%\n");
            }
            return sb.toString();
        }

        private double p(int c, int t) {
            if (t == 0)
                return 0;
            return Math.round(c * 10000.0 / t) / 100.0;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class CountStat{
        String name ;
        long time ;
        boolean success;
    }
}