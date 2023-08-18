package io.github.devsong.base.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimplePoolSizeCalculator extends PoolSizeCalculator {

    @Override
    protected Runnable creatTask() {
        return new AsyncIOTask();
    }

    @Override
    protected BlockingQueue<Runnable> createWorkQueue() {
        return new LinkedBlockingQueue<>(1000);
    }

    @Override
    protected long getCurrentThreadCPUTime() {
        return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
    }

    public static void main(String[] args) {
        PoolSizeCalculator poolSizeCalculator = new SimplePoolSizeCalculator();
        poolSizeCalculator.calculateBoundaries(new BigDecimal("0.4"), new BigDecimal(100000));
    }
}

/**
 * 自定义的异步IO任务
 *
 * @author Will
 */
@Slf4j
class AsyncIOTask implements Runnable {

    @Override
    public void run() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            String getURL = "http://localhost:7080/swagger-ui.html";
            URL getUrl = new URL(getURL);

            connection = (HttpURLConnection) getUrl.openConnection();
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;
            while ((line = reader.readLine()) != null) {
                // empty loop
                System.out.println(line);
            }
        } catch (IOException e) {
            log.error("error", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    log.error("error", e);
                }
            }
            connection.disconnect();
        }
    }
}
