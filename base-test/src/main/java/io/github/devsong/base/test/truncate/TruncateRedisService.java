package io.github.devsong.base.test.truncate;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;

@RequiredArgsConstructor
public class TruncateRedisService implements TruncateService {
    private final RedissonClient redissonClient;

    @Override
    public void truncate() {
        redissonClient.getKeys().flushdb();
    }
}
