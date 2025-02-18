package com.hhu.utils;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class HHUPartitionedBloomFilter {

    @Autowired
    private RedissonClient redissonClient;

    // 布隆过滤器的名称前缀
    private static final String BLOOM_FILTER_PREFIX = "bloom_filter:";

    // 时间格式
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // 布隆过滤器的过期时间（7天）
    private static final long BLOOM_FILTER_EXPIRE_DAYS = 7;

    /**
     * 获得普通布隆过滤器
     */

    private RBloomFilter<String> getBloomFilter(String prefix) {
        String filterName = BLOOM_FILTER_PREFIX + prefix;
        // 获取布隆过滤器
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(filterName);
        // 如果布隆过滤器不存在，则初始化一个新的布隆过滤器
        if (!bloomFilter.isExists()) {
            bloomFilter.tryInit(1000L, 0.03); // 参数1: 预期插入的元素数量，参数2: 期望的误判率
        }

        return bloomFilter;
    }

    /**
     * 向布隆过滤器中添加元素
     */
    public void add(String element, String prefix) {
        RBloomFilter<String> bloomFilter = getBloomFilter(prefix);
        bloomFilter.add(element);
    }

    /**
     * 检查元素是否存在于布隆过滤器中
     */
    public boolean contains(String element, String prefix) {
        RBloomFilter<String> bloomFilter = getBloomFilter(prefix);
        return bloomFilter.contains(element);
    }

    /**
     * ===============================以下是时间分区布隆过滤器=============================
     */

    /**
     * 获取指定日期的布隆过滤器
     */
    private RBloomFilter<String> getBloomFilterByDate(LocalDate date, String prefix) {
        // 根据日期生成布隆过滤器的名称
        String filterName = BLOOM_FILTER_PREFIX + prefix + ":" + date.format(DATE_FORMATTER);

        // 获取布隆过滤器
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(filterName);

        // 如果布隆过滤器不存在，则初始化一个新的布隆过滤器
        if (!bloomFilter.isExists()) {
            bloomFilter.tryInit(1000L, 0.03); // 参数1: 预期插入的元素数量，参数2: 期望的误判率
        }

        return bloomFilter;
    }

    /**
     * 向当前日期的布隆过滤器中添加元素
     */
    public void partitionedAdd(String element, String prefix) {
        RBloomFilter<String> bloomFilter = getBloomFilterByDate(LocalDate.now(), prefix);
        bloomFilter.add(element);
    }

    /**
     * 检查元素是否存在于最近七天内的布隆过滤器中
     */
    public boolean partitionedContains(String element, String prefix) {
        LocalDate currentDate = LocalDate.now();

        // 遍历过去七天
        for (int i = 0; i < 7; i++) {
            LocalDate date = currentDate.minusDays(i);
            RBloomFilter<String> bloomFilter = getBloomFilterByDate(date, prefix);

            // 如果任意一个布隆过滤器包含该元素，则返回 true
            if (bloomFilter.contains(element)) {
                return true;
            }
        }

        // 如果过去七天的布隆过滤器都不包含该元素，则返回 false
        return false;
    }

}
