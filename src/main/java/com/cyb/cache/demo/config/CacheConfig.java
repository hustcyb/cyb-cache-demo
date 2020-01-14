package com.cyb.cache.demo.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.event.EventType;
import org.ehcache.expiry.ExpiryPolicy;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import com.cyb.cache.demo.domain.Course;
import com.cyb.cache.demo.domain.Product;
import com.cyb.cache.demo.ehcache.LoggingCacheEventListener;
import com.cyb.cache.demo.redis.KryoRedisSerializer;

/**
 * 缓存配置
 * 
 * @author 01373660
 *
 */
@Configuration
public class CacheConfig {

	/**
	 * 初始化Redis缓存管理程序
	 * 
	 * @param connectionFactory
	 *            Redis连接工厂
	 * @return Redis缓存管理程序
	 */
	@Primary
	@Bean
	public RedisCacheManager cacheManager(
			RedisConnectionFactory connectionFactory) {
		CacheKeyPrefix keyPrefix = cacheName -> cacheName + ":";
		RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration
				.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(300))
				.computePrefixWith(keyPrefix)
				.serializeValuesWith(
						SerializationPair.fromSerializer(RedisSerializer
								.string()));
		RedisCacheConfiguration charCodeCacheConfiguration = RedisCacheConfiguration
				.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(120))
				.computePrefixWith(keyPrefix)
				.serializeValuesWith(
						SerializationPair
								.fromSerializer(new GenericToStringSerializer<Integer>(
										Integer.class)));
		RedisCacheConfiguration studentCacheConfiguration = RedisCacheConfiguration
				.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(600))
				.computePrefixWith(keyPrefix)
				.serializeValuesWith(
						SerializationPair.fromSerializer(RedisSerializer.json()));
		RedisCacheConfiguration productCacheConfiguration = RedisCacheConfiguration
				.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(1200))
				.computePrefixWith(keyPrefix)
				.serializeValuesWith(
						SerializationPair
								.fromSerializer(new KryoRedisSerializer<Product>()));
		Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
		cacheConfigurations.put("CHCODE", charCodeCacheConfiguration);
		cacheConfigurations.put("STU", studentCacheConfiguration);
		cacheConfigurations.put("PROD", productCacheConfiguration);

		return RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(defaultCacheConfiguration)
				.withInitialCacheConfigurations(cacheConfigurations).build();
	}

	/**
	 * 初始化JCache缓存管理程序
	 * 
	 * @return JCache缓存管理程序
	 * @throws URISyntaxException 
	 */
	@Bean
	public JCacheCacheManager jcacheCacheManager() throws URISyntaxException {
		CacheEventListenerConfigurationBuilder listenerConfigurationBuilder = CacheEventListenerConfigurationBuilder
				.newEventListenerConfiguration(new LoggingCacheEventListener(),
						EventType.CREATED, EventType.UPDATED,
						EventType.EXPIRED, EventType.REMOVED, EventType.EVICTED)
				.unordered().asynchronous();
		ResourcePoolsBuilder resourcePoolsBuilder = ResourcePoolsBuilder.heap(
				2000L).offheap(100, MemoryUnit.MB);
		ExpiryPolicy<Object, Object> expiryPolicy = ExpiryPolicyBuilder
				.timeToLiveExpiration(Duration.ofSeconds(120));
		CacheConfiguration<Long, Course> courseCacheConfiguration = CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, Course.class,
						resourcePoolsBuilder).withExpiry(expiryPolicy)
				.withDispatcherConcurrency(4).add(listenerConfigurationBuilder)
				.build();
		Map<String, CacheConfiguration<?, ?>> cacheConfigurationMap = new HashMap<>();
		cacheConfigurationMap.put("CRS", courseCacheConfiguration);

		EhcacheCachingProvider ehcacheCachingProvider = (EhcacheCachingProvider) Caching
				.getCachingProvider(EhcacheCachingProvider.class.getName());
		DefaultConfiguration defaultConfiguration = new DefaultConfiguration(
				cacheConfigurationMap,
				ehcacheCachingProvider.getDefaultClassLoader());
		CacheManager cacheManager = ehcacheCachingProvider.getCacheManager(
				ehcacheCachingProvider.getDefaultURI(), defaultConfiguration);

		return new JCacheCacheManager(cacheManager);
		
//		Class<?> clazz = getClass();
//		CachingProvider cachingProvider = Caching.getCachingProvider();
//		ClassLoader classLoader = clazz.getClassLoader();
//		URI uri = clazz.getResource("/ehcache.xml").toURI();
//		CacheManager cacheManager = cachingProvider.getCacheManager(uri, classLoader);
//		
//		return new JCacheCacheManager(cacheManager);
	}
}
