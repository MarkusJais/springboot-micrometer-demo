package com.markusjais.examples.springbootmicrometerdemo.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MetricsConfig {

	@Bean
	MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
		return registry -> registry.config().commonTags("mytag", "hello-tag");
	}

    @Bean
    public MeterFilter renameGetAllBooksTimerMeterFilter() {
        return MeterFilter.renameTag("getAllBooksTimer", "book.request", "book.request.all");
    }

	@Bean
    public MeterFilter denyJvmMetrics() {
		return MeterFilter.denyNameStartsWith("jvm");
    }


/*
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		Counter.builder("book.orders")
				.tag("book",  "order")
				.description("The number of book orders")
				.register(meterRegistry);

	}*/

}
