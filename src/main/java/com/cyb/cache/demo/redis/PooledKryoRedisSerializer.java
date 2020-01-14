package com.cyb.cache.demo.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;

/**
 * kryo资源池实现的redis序列化程序
 * 
 * @author 01373660
 *
 * @param <T>
 */
public class PooledKryoRedisSerializer<T> implements RedisSerializer<T> {

	/**
	 * kryo资源池
	 */
	private static final Pool<Kryo> kryoPool = new Pool<Kryo>(true, false, 8) {

		@Override
		protected Kryo create() {
			return new Kryo();
		}

	};

	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}

		Kryo kryo = kryoPool.obtain();
		try (Output output = new Output(1024, -1)) {
			kryo.writeClassAndObject(output, t);
			output.flush();

			return output.getBuffer();
		} finally {
			kryoPool.free(kryo);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null) {
			return null;
		}

		Kryo kryo = kryoPool.obtain();
		try (Input input = new Input(bytes)) {
			return (T) kryo.readClassAndObject(input);
		} finally {
			kryoPool.free(kryo);
		}
	}

}
