package com.bayzdelivery;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.bayzdelivery.service")
public class BayzDeliveryApplicationTest {
    @Test
    void contextLoads() {
    }
}
