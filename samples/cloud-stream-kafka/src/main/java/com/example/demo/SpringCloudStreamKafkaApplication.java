package com.example.demo;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.binder.kafka.config.KafkaBinderConfiguration;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ResourceHint;
import org.springframework.nativex.hint.TypeHint;
import org.springframework.util.StringUtils;

@SpringBootApplication
@NativeHint(
		types = {
				@TypeHint(types = {
						GenericSelector.class,
						GenericTransformer.class,
						GenericHandler.class,
						ByteArrayDeserializer.class,
						ByteArraySerializer.class,
						KafkaBinderConfiguration.class
				})
		})
@TypeHint(typeNames = {
		"org.springframework.cloud.stream.function.BindableFunctionProxyFactory",
})
@ResourceHint(patterns = "META-INF/spring.binders")
public class SpringCloudStreamKafkaApplication {

	@Autowired
	StreamBridge streamBridge;

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamKafkaApplication.class, args);
	}

	@Bean
	public Function<String, String> graalUppercaseFunction() {
		return String::toUpperCase;
	}

	@Bean
	public Consumer<String> graalLoggingConsumer() {
		return s -> {
			System.out.println("++++++Received:" + s);
			// Verifying that StreamBridge API works in native applications.
			streamBridge.send("sb-out", s);
		};
	}

	@Bean
	public Supplier<String> graalSupplier() {
		return () -> {
			String woodchuck = "How much wood a woodchuck chuck if a woodchuck could chuck wood?";
			final String[] splitWoodchuck = woodchuck.split(" ");
			Random random = new Random();
			return splitWoodchuck[random.nextInt(splitWoodchuck.length)];
		};
	}

}
