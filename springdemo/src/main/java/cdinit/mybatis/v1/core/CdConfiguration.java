package cdinit.mybatis.v1.core;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class CdConfiguration {

    public <T> T getMapper(Class<T> clazz, CdSqlSession session) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{clazz}, new CdMapperProxy(session));
    }

    static class TestMapperXml{
        public static final String namespace = "cdinit.mybatis.v1.core.TestMapper";
        public static final Map<String,String> methodSqlMapping = new HashMap<>();
        static{
            methodSqlMapping.put("selectByPrimary","SELECT * FROM TEST WHERE ID = %d");
        }
    }
}
