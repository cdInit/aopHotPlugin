package cdinit.mybatis.v1.core;

public class CdSimpleExecutor implements CdExecutor {
    @Override
    public <T> T query(String statement, String parameter) {
        // JDBC 链接
        return (T)new Test("test",1);
    }
}
