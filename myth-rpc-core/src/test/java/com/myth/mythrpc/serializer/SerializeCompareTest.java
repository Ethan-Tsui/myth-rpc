package com.myth.mythrpc.serializer;

import com.myth.mythrpc.model.User;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;

/**
 * @author Ethan
 * @version 1.0
 */
public class SerializeCompareTest {
    private static User buildUserDefault() {
        User user = new User();
        user.setAge(11);
        user.setAddress("深圳市南山区");
        user.setBankNo(12897873624813L);
        user.setSex(1);
        user.setId(10001);
        user.setIdCardNo("440308781129381222");
        user.setRemark("备注信息字段");
        user.setUsername("ddd-user-name");
        return user;
    }

    @Benchmark
    public void jdkSerializeTest() throws IOException {
        Serializer serializeFactory = new JdkSerializer();
        User user = buildUserDefault();
        byte[] result = serializeFactory.serialize(user);
        User deserializeUser = serializeFactory.deserialize(result, User.class);
    }

    @Benchmark
    public void hessianSerializeTest() throws IOException {
        Serializer serializeFactory = new HessianSerializer();
        User user = buildUserDefault();
        byte[] result = serializeFactory.serialize(user);
        User deserializeUser = serializeFactory.deserialize(result, User.class);
    }

    @Benchmark
    public void fastJsonSerializeTest() throws IOException {
        Serializer serializeFactory = new FastJsonSerializer();
        User user = buildUserDefault();
        byte[] result = serializeFactory.serialize(user);
        User deserializeUser = serializeFactory.deserialize(result, User.class);
    }

    @Benchmark
    public void kryoSerializeTest() throws IOException {
        Serializer serializeFactory = new KryoSerializer();
        User user = buildUserDefault();
        byte[] result = serializeFactory.serialize(user);
        User deserializeUser = serializeFactory.deserialize(result, User.class);
    }

    public static void main(String[] args) throws RunnerException {
        //配置进行2轮热数 测试2轮 1个线程
        //预热的原因 是JVM在代码执行多次会有优化
        Options options = new OptionsBuilder().warmupIterations(2).measurementBatchSize(2)
                .forks(1).build();
        new Runner(options).run();
    }
}
