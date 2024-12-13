package com.hhu.validation;

import org.junit.Test;

public class test {
    @Test
    public void test1()
    {
        String s = "小栗子";
        System.out.println(s.matches("^[\\\\u4e00-\\\\u9fa5A-Za-z0-9._-]{2,16}$"));
    }
}
