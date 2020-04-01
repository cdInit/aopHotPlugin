package cdinit.mybatis.v1.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CdMapperProxy implements InvocationHandler {
    private CdSqlSession sqlSession;

    public CdMapperProxy(CdSqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getDeclaringClass().getName().equals(CdConfiguration.TestMapperXml.namespace)){
            String sql = CdConfiguration.TestMapperXml.methodSqlMapping.get(method.getName());
            return sqlSession.selectOne(sql,String.valueOf(args[0]));
        }
        return method.invoke(this,args);
    }
}
