package cdinit.mybatis.v1.core;

public interface CdExecutor {
    <T> T query(String statement, String parameter);
}
