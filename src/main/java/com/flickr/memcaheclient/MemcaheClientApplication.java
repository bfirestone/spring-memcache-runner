package com.flickr.memcaheclient;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;
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

    @Value("${ttl:300}")
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
        MemcachedClient memcacheClient = new MemcachedClient(new InetSocketAddress(memcacheHost, memcachePort));

        int randNum = random.nextInt(10000);

        if (!memcacheValue.equals("none")) {
            if (useRandom) {
                log.info("using random number generator - num=[{}]", randNum);
                memcacheValue = String.format("%s::%s", memcacheValue, randNum);
            }

            log.info("setting value=[{}] for key=[{}] expiry=[{}]", memcacheValue, memcacheKey, memcacheExpiry);
            memcacheClient.set(memcacheKey, memcacheExpiry, memcacheValue);
            log.info("testing value for key=[{}]", memcacheKey);
        } else {
            log.info("pulling value for key=[{}]", memcacheKey);
        }

        log.info("value=[{}]", memcacheClient.get(memcacheKey));
    }
}
