package com.example.demo;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.SynthesizedAnnotation;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ProxyHint;
import org.springframework.nativex.hint.TypeHint;

@SpringBootApplication
@TypeHint(types = {EnableBinding.class, SynthesizedAnnotation.class})
@TypeHint(typeNames = {"org.springframework.integration.context.ConverterRegistrar",
						"org.springframework.integration.context.CustomConversionServiceFactoryBean",
						"org.apache.kafka.common.serialization.ByteArrayDeserializer",
						"org.apache.kafka.common.serialization.ByteArraySerializer"})
@ProxyHint(types = {EnableBinding.class, SynthesizedAnnotation.class})
public class SpringCloudStreamKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamKafkaApplication.class, args).close();
	}

	@Bean
	public FooFunction graalUppercaseFunction() {
		return new FooFunction();
	}

	public static class FooFunction implements Function<String, String> {

		@Override
		public String apply(String s) {
			return s.toUpperCase();
		}
	}

	@Bean
	public FooConsumer graalLoggingConsumer() {
		return new FooConsumer();
	}

	public static class FooConsumer implements Consumer<String> {

		@Override
		public void accept(String s) {
			System.out.println("++++++Received:" + s);
		}
	}

	@Bean
	public ApplicationRunner runner(KafkaTemplate<String, String> template,
				ConsumerFactory<String, String> cf) {

		cf.addListener(new ConsumerFactory.Listener() { });
		return args -> {
			template.send("graalUppercaseFunction-in-0", "foo");
			System.out.println("++++++Sent:foo");
			Thread.sleep(5000);
		};
	}

}
