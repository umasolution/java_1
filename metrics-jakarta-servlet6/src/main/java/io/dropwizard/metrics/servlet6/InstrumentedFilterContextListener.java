package io.dropwizard.metrics.servlet6;

import com.codahale.metrics.MetricRegistry;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * A listener implementation which injects a {@link MetricRegistry} instance into the servlet
 * context. Implement {@link #getMetricRegistry()} to return the {@link MetricRegistry} for your
 * application.
 */
public abstract class InstrumentedFilterContextListener implements ServletContextListener {
    /**
     * @return the {@link MetricRegistry} to inject into the servlet context.
     */
    protected abstract MetricRegistry getMetricRegistry();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE, getMetricRegistry());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
