package com.site.blog.my.core.service;



public interface IdGeneratorService {

    void IdGenerator(long workerId, long datacenterId);
    long nextId();
    Integer nextIdStr();
    long tilNextMillis(long lastTimestamp);
    long timeGen();
}
