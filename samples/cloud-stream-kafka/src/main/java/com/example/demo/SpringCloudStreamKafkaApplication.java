package com.example.demo;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.aopalliance.intercept.Interceptor;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.catalog.SimpleFunctionRegistry;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binder.BinderTypeRegistry;
import org.springframework.cloud.stream.binder.kafka.config.KafkaBinderConfiguration;
import org.springframework.cloud.stream.binding.AbstractBindableProxyFactory;
import org.springframework.cloud.stream.binding.Bindable;
import org.springframework.cloud.stream.binding.BindableProxyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.SynthesizedAnnotation;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.handler.MessageHandlerSupport;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.router.MessageRouter;
import org.springframework.integration.support.management.MappingMessageRouterManagement;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHandler;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ProxyHint;
import org.springframework.nativex.hint.ResourceHint;
import org.springframework.nativex.hint.TypeHint;

@SpringBootApplication
@TypeHint(types = {EnableBinding.class, SynthesizedAnnotation.class, AbstractBindableProxyFactory[].class, AbstractBindableProxyFactory.class,
		BindableProxyFactory[].class, FactoryBean[].class, InitializingBean[].class, Bindable[].class,
		Interceptor[].class, KafkaBinderConfiguration.class, BinderTypeRegistry.class, Field.class,
		SimpleFunctionRegistry.FunctionInvocationWrapper.class,
		// For supplier
		GenericSelector.class, GenericTransformer.class, GenericHandler.class, AbstractMessageRouter[].class,
		MappingMessageRouterManagement[].class, AbstractMessageHandler[].class, MessageRouter[].class, MessageHandler[].class,
		MessageHandlerSupport[].class, IntegrationObjectSupport[].class})
@TypeHint(typeNames = {"org.springframework.integration.context.ConverterRegistrar",
						"org.springframework.integration.context.CustomConversionServiceFactoryBean",
						"org.apache.kafka.common.serialization.ByteArrayDeserializer",
						"org.apache.kafka.common.serialization.ByteArraySerializer",
		"org.springframework.cloud.stream.binding.MessageChannelConfigurer",
		"org.springframework.cloud.stream.binding.MessageConverterConfigurer",
		"org.springframework.cloud.stream.binding.CompositeMessageChannelConfigurer",
		"org.springframework.cloud.stream.binding.MessageChannelAndSourceConfigurer",
						"org.springframework.cloud.stream.function.BindableFunctionProxyFactory",
						"org.springframework.cloud.stream.binding.BindableProxyFactory",
						"org.springframework.cloud.stream.binding.BindingTargetFactory",
		"org.springframework.cloud.stream.binding.AbstractBindingTargetFactory",
		"org.springframework.messaging.SubscribableChannel",
						"org.springframework.cloud.stream.binding.SubscribableChannelBindingTargetFactory",
        "org.springframework.cloud.stream.function.PartitionAwareFunctionWrapper"})
@ProxyHint(types = {EnableBinding.class, SynthesizedAnnotation.class})
@ResourceHint(patterns="META-INF/spring.binders")
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
	public Supplier<String> graalSupplier() {
		return () ->{
			byte[] array = new byte[8];
			new Random().nextBytes(array);
			return new String(array, StandardCharsets.UTF_8);
		};
	}

//	@Bean
//	public ApplicationRunner runner(KafkaTemplate<String, String> template,
//				ConsumerFactory<String, String> cf) {
//
//		cf.addListener(new ConsumerFactory.Listener() { });
//		return args -> {
//			template.send("graalUppercaseFunction-in-0", "foo");
//			System.out.println("++++++Sent:foo");
//			Thread.sleep(5000);
//		};
//	}

}
