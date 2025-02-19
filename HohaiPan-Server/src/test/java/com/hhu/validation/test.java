package com.hhu.validation;

import com.hhu.utils.HHUPartitionedBloomFilter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class test {

    @Test
    public void test1()
    {
        HHUPartitionedBloomFilter hhuPartitionedBloomFilter = new HHUPartitionedBloomFilter();
        hhuPartitionedBloomFilter.add("WdxTb3","shareCode");
   }
}
