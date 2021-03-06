package com.flickr.memcaheclient;

import lombok.extern.slf4j.Slf4j;
import net.rubyeye.xmemcached.XMemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@Slf4j
@SpringBootApplication
public class MemcaheClientApplication implements CommandLineRunner {
    @Value("${host:localhost}")
    private String memcacheHost;

    @Value("${port:11211}")
    private int memcachePort;

    @Value("${key:HelloWorld}")
    private String memcacheKey;

    @Value("${value:none}")
    private String memcacheValue;

    @Value("${expiry:300}")
    private int memcacheExpiry;

    @Value("${use.random:false}")
    private boolean useRandom;

    private final Random random = new Random();

    public static void main(String[] args) {
        log.info("starting runner");
        SpringApplication.run(MemcaheClientApplication.class, args).close();
        System.exit(0);
    }

    @Override
    public void run(String... args) throws Exception {
        XMemcachedClient xMemcachedClient = new XMemcachedClient(memcacheHost, memcachePort);

        int randNum = random.nextInt(10000);

        if (!memcacheValue.equals("none")) {
            if (useRandom) {
                log.info("using random number generator - num=[{}]", randNum);
                memcacheValue = String.format("%s::%s", memcacheValue, randNum);
            }

            log.info("setting value=[{}] for key=[{}] expiry=[{}]", memcacheValue, memcacheKey, memcacheExpiry);
            xMemcachedClient.set(memcacheKey, memcacheExpiry, memcacheValue);
            log.info("testing value for key=[{}]", memcacheKey);
        } else {
            log.info("pulling value for key=[{}]", memcacheKey);
        }

        String value = xMemcachedClient.get(memcacheKey);

        log.info("value=[{}]", value);
    }
}
