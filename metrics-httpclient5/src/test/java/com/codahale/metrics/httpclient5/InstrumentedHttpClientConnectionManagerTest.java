package com.codahale.metrics.httpclient5;

import com.codahale.metrics.MetricRegistry;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.ArgumentMatchers.any;

public class InstrumentedHttpClientConnectionManagerTest {
    private final MetricRegistry metricRegistry = new MetricRegistry();

    @Test
    public void shouldRemoveGauges() {
        final InstrumentedHttpClientConnectionManager instrumentedHttpClientConnectionManager = InstrumentedHttpClientConnectionManager.builder(metricRegistry).build();
        assertThat(metricRegistry.getGauges().entrySet().stream()
                .map(e -> entry(e.getKey(), e.getValue().getValue())))
                .containsOnly(entry("org.apache.hc.client5.http.io.HttpClientConnectionManager.available-connections", 0),
                        entry("org.apache.hc.client5.http.io.HttpClientConnectionManager.leased-connections", 0),
                        entry("org.apache.hc.client5.http.io.HttpClientConnectionManager.max-connections", 25),
                        entry("org.apache.hc.client5.http.io.HttpClientConnectionManager.pending-connections", 0));

        instrumentedHttpClientConnectionManager.close();
        Assert.assertEquals(0, metricRegistry.getGauges().size());

        // should be able to create another one with the same name ("")
        InstrumentedHttpClientConnectionManager.builder(metricRegistry).build().close();
    }

    @Test
    public void configurableViaBuilder() {
        final MetricRegistry registry = Mockito.mock(MetricRegistry.class);

        InstrumentedHttpClientConnectionManager.builder(registry)
                .name("some-name")
                .name("some-other-name")
                .build()
                .close();

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(registry, Mockito.atLeast(1)).registerGauge(argumentCaptor.capture(), any());
        assertTrue(argumentCaptor.getValue().contains("some-other-name"));
    }
}
