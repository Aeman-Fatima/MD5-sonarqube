package com.tom.service;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class md5Test {
    private MD5 md5;

    @Before
    public void setUp() {
        md5 = new MD5();
    }

    @Test
    public void toBinary() {
        String result = md5.toBinary("h");

        assertThat(result, equalTo("1101000"));
    }

    @Test
    public void blocks() {
        int result = md5.blocks("Hello");

        assertThat(result, equalTo(1));
    }
}
