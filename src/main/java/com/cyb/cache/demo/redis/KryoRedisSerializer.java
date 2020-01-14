package com.cyb.cache.demo.redis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * kryo实现的redis序列化程序
 * 
 * @author 01373660
 *
 */
public class KryoRedisSerializer<T> implements RedisSerializer<T> {

	/**
	 * kryo线程变量
	 */
	private static final ThreadLocal<Kryo> kryos = ThreadLocal
			.withInitial(KryoRedisSerializer::buildKryo);

	/**
	 * 初始化kryo实例
	 * 
	 * @return kryo实例
	 */
	private static Kryo buildKryo() {
		Kryo kryo = new Kryo();
		kryo.setRegistrationRequired(false);

		return kryo;
	}

	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}

		Kryo kryo = kryos.get();
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				Output output = new Output(outputStream)) {
			kryo.writeClassAndObject(output, t);
			output.flush();

			return outputStream.toByteArray();
		} catch (IOException exception) {
			throw new SerializationException("kryo序列化报错", exception);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null) {
			return null;
		}

		Kryo kryo = kryos.get();
		try (Input input = new Input(bytes)) {
			return (T) kryo.readClassAndObject(input);
		} catch (Exception exception) {
			throw new SerializationException("kryo反序列化报错", exception);
		}
	}

}
