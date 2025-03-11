package com.bayzdelivery;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * Test suite for the BayzDelivery application.
 * <p>
 * This class is used to run all the tests located in the package
 * "com.bayzdelivery.service". It ensures that the application context
 * loads correctly and all the defined tests are executed.
 * <p>
 * Annotations:
 * - @Suite: Marks this class as a test suite.
 * - @SelectPackages: Specifies the package "com.bayzdelivery.service"
 * to be included in the test run.
 * <p>
 * Methods:
 * - contextLoads(): Verifies that the application context loads without
 * any issues.
 */
@Suite
@SelectPackages("com.bayzdelivery.service")
public class BayzDeliveryApplicationTest {
    @Test
    void contextLoads() {
    }
}
