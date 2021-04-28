package com.example.demo;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.function.Function;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.TransformerSupplier;
import org.apache.kafka.streams.kstream.internals.AbstractStream;
import org.apache.kafka.streams.kstream.internals.KStreamImpl;

import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.binder.kafka.streams.function.KafkaStreamsBindableProxyFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.core.DecoratingProxy;
import org.springframework.nativex.hint.AccessBits;
import org.springframework.nativex.hint.InitializationHint;
import org.springframework.nativex.hint.InitializationTime;
import org.springframework.nativex.hint.MethodHint;
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
						KafkaStreamsBindableProxyFactory.class,
//						Method.class, Type.class, Type[].class,
//						AbstractStream.class,
//						KeyValueMapper.class,
//						TransformerSupplier.class,
//						Named.class,
//						String[].class
//						KTableBinderConfiguration.class,
//						KStreamBinderConfiguration.class
				}),
//				@TypeHint(typeNames = {"org.springframework.cloud.stream.binder.kafka.streams.KStreamBoundElementFactory$KStreamWrapper"}, access = AccessBits.FULL_REFLECTION,
//						methods = {@MethodHint(name = "transform", parameterTypes = {TransformerSupplier.class, String[].class}),
//								@MethodHint(name = "transform", parameterTypes = {TransformerSupplier.class, Named.class, String[].class}),
//								@MethodHint(name = "flatTransform", parameterTypes = {TransformerSupplier.class, Named.class, String[].class}),
//								@MethodHint(name = "flatMap", parameterTypes = {KeyValueMapper.class, Named.class}),
//								@MethodHint(name = "flatMap", parameterTypes = {KeyValueMapper.class})
//						})
		})
//@TypeHint(typeNames = {
//		"org.springframework.cloud.stream.binder.kafka.streams.KStreamBoundElementFactory",
//		"org.springframework.cloud.stream.binder.kafka.streams.KStreamBoundElementFactory$KStreamWrapper",
//		"org.springframework.cloud.stream.binder.kafka.streams.KStreamBoundElementFactory$KStreamWrapperHandler",
//})
//@ProxyHint(typeNames = {
//		"java.lang.reflect.ParameterizedType",
//		"org.springframework.core.SerializableTypeWrapper$SerializableTypeProxy",
//		"java.io.Serializable"
//})
//@ProxyHint(typeNames = {
//		"java.lang.reflect.TypeVariable",
//		"org.springframework.core.SerializableTypeWrapper$SerializableTypeProxy",
//		"java.io.Serializable"
//})
//@ProxyHint(typeNames = {
//		"java.lang.reflect.WildcardType",
//		"org.springframework.core.SerializableTypeWrapper$SerializableTypeProxy",
//		"java.io.Serializable"
//})
//@ProxyHint(typeNames = {
//		"org.springframework.cloud.stream.annotation.EnableBinding",
//		"org.springframework.core.annotation.SynthesizedAnnotation"
//})
@ProxyHint(typeNames = {
		"org.springframework.cloud.stream.binder.kafka.streams.KStreamBoundElementFactory$KStreamWrapper",
		"org.apache.kafka.streams.kstream.KStream",
		"org.springframework.aop.SpringProxy",
		"org.springframework.aop.framework.Advised",
		"org.springframework.core.DecoratingProxy"
})

//@ProxyHint(types = {
//		KStream.class,
//		SpringProxy.class,
//		Advised.class,
//		DecoratingProxy.class
//})
public class SpringCloudStreamKafkaStreamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamKafkaStreamsApplication.class, args);
	}

	@Bean
	public Function<KStream<String, String>, KStream<String, String>> graalKStreamFunction() {
		return ks -> ks;
	}

}
