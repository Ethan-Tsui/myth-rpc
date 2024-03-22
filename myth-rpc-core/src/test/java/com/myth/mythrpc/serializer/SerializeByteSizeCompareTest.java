package com.myth.mythrpc.serializer;

import com.myth.mythrpc.model.User;

import java.io.IOException;

/**
 * @author Ethan
 * @version 1.0
 */
public class SerializeByteSizeCompareTest {

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

    public void jdkSerializeSizeTest() throws IOException {
        Serializer serializeFactory = new JdkSerializer();
        User user = buildUserDefault();
        byte[] result = serializeFactory.serialize(user);
        System.out.println("jdk's size is " + result.length);
    }

    public void hessianSerializeSizeTest() throws IOException {
        Serializer serializeFactory = new HessianSerializer();
        User user = buildUserDefault();
        byte[] result = serializeFactory.serialize(user);
        User deserializeUser = serializeFactory.deserialize(result, User.class);
        System.out.println("hessian's size is " + result.length);
    }

    public void fastJsonSerializeSizeTest() throws IOException {
        Serializer serializeFactory = new FastJsonSerializer();
        User user = buildUserDefault();
        byte[] result = serializeFactory.serialize(user);
        User deserializeUser = serializeFactory.deserialize(result, User.class);
        System.out.println("fastJson's size is " + result.length);
    }

    public void kryoSerializeSizeTest() throws IOException {
        Serializer serializeFactory = new KryoSerializer();
        User user = buildUserDefault();
        byte[] result = serializeFactory.serialize(user);
        User deserializeUser = serializeFactory.deserialize(result, User.class);
        System.out.println("kryo's size is " + result.length);
    }

    public static void main(String[] args) throws IOException {
        SerializeByteSizeCompareTest serializeByteSizeCompareTest = new SerializeByteSizeCompareTest();
        serializeByteSizeCompareTest.fastJsonSerializeSizeTest();
        serializeByteSizeCompareTest.jdkSerializeSizeTest();
        serializeByteSizeCompareTest.kryoSerializeSizeTest();
        serializeByteSizeCompareTest.hessianSerializeSizeTest();
    }
}
