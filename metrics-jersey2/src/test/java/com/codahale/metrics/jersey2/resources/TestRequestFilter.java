package com.codahale.metrics.jersey2.resources;

import com.codahale.metrics.jersey2.TestClock;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

public class TestRequestFilter implements ContainerRequestFilter {

    private final TestClock testClock;

    public TestRequestFilter(TestClock testClock) {
        this.testClock = testClock;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        testClock.tick += 4;
    }
}
