package com.site.blog.my.core.service.impl;

import com.site.blog.my.core.service.IdGeneratorService;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

@Service
public class IdGeneratorImpl implements IdGeneratorService {
    private final long twepoch = 1503849600000L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = 31L;
    private final long maxDatacenterId = 31L;
    private final long sequenceBits = 12L;
    private final long workerIdShift = 12L;
    private final long datacenterIdShift = 17L;
    private final long timestampLeftShift = 22L;
    private final long sequenceMask = 4095L;
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    private static final String SEQ_FORMATTER = "%019d";
    private static final String INDEX_CUST_B = "0";
    private static final String INDEX_CUST_C = "1";
    private static final String INDEX_BOTH = "2";

    @Override
    public void IdGenerator(long workerId, long datacenterId) {
        System.out.println(String.format("当前工作组编号[%d], 数据中心编号[%d]", workerId, datacenterId));
        if (workerId <= 31L && workerId >= 0L) {
            if (datacenterId <= 31L && datacenterId >= 0L) {
                this.workerId = workerId;
                this.datacenterId = datacenterId;
            } else {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", 31L));
            }
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 31L));
        }
    }

    @Override
    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
        } else {
            if (this.lastTimestamp == timestamp) {
                this.sequence = this.sequence + 1L & 4095L;
                if (this.sequence == 0L) {
                    timestamp = this.tilNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }

            this.lastTimestamp = timestamp;
            return timestamp - 1503849600000L << 22 | this.datacenterId << 17 | this.workerId << 12 | this.sequence;
        }
    }
    @Override
    public  Integer nextIdStr() {
        return Integer.valueOf((int) this.nextId());
    }

    public String nextSeq(CustType custType) {
        String id = String.format("%019d", this.nextId());
        String seq;
        switch(custType) {
            case CUST_B:
                seq = "0".concat(id);
                break;
            case CUST_C:
                seq = "1".concat(id);
                break;
            default:
                seq = "2".concat(id);
        }

        return seq;
    }
    @Override
    public long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
        }

        return timestamp;
    }
    @Override
    public long timeGen() {
        return System.currentTimeMillis();
    }

    public static enum CustType {
        CUST_B,
        CUST_C,
        BOTH;

        private CustType() {
        }
    }
}
