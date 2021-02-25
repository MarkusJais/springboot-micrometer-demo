package com.markusjais.examples.springbootmicrometerdemo;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableConfigurationProperties()
public class Application {

	/*@Autowired
	private MeterRegistry meterRegistry;*/


	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}


	@Bean
	MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
		return registry -> registry.config().commonTags("mytag", "hello-tag");
	}


    @Bean
    public MeterFilter renameGetAllBooksTimerMeterFilter() {
        return MeterFilter.renameTag("getAllBooksTimer", "book.request", "book.request.all");
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
