package com.example.demo;

import java.util.function.Function;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.streams.kstream.KStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.binder.kafka.streams.function.KafkaStreamsBindableProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.nativex.hint.InitializationHint;
import org.springframework.nativex.hint.InitializationTime;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ProxyHint;
import org.springframework.nativex.hint.TypeHint;

@SpringBootApplication
@InitializationHint(packageNames = "org.apache.kafka.streams.kstream", initTime = InitializationTime.BUILD)
@NativeHint(
		types = {
				@TypeHint(types = {
						ByteArrayDeserializer.class,
						ByteArraySerializer.class,
						KafkaStreamsBindableProxyFactory.class
				})
		})
@ProxyHint(typeNames = {
		"org.springframework.cloud.stream.binder.kafka.streams.KStreamBoundElementFactory$KStreamWrapper",
		"org.apache.kafka.streams.kstream.KStream",
		"org.springframework.aop.SpringProxy",
		"org.springframework.aop.framework.Advised",
		"org.springframework.core.DecoratingProxy"
})
public class SpringCloudStreamKafkaStreamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamKafkaStreamsApplication.class, args);
	}

	@Bean
	public Function<KStream<String, String>, KStream<String, String>> graalKStreamFunction() {
		return ks -> ks;
	}

}
