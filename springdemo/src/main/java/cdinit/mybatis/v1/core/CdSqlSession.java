package cdinit.mybatis.v1.core;

public class CdSqlSession {
    // CdConfiguration
    private CdConfiguration cdConfiguration;
    // CdExecutor
    private CdExecutor cdExecutor;

    public CdSqlSession(CdConfiguration cdConfiguration, CdExecutor cdExecutor) {
        this.cdConfiguration = cdConfiguration;
        this.cdExecutor = cdExecutor;
    }

    /**
     * getMapper
     * @param clazz
     */
    public <T> T getMapper(Class<T> clazz){
        return cdConfiguration.getMapper(clazz,this);
    }

    /**
     *
     * @param statement sql语句
     * @param parameter 参数
     * @param <T>
     * @return
     */
    public <T> T selectOne(String statement,String parameter){
        return cdExecutor.query(statement,parameter);
    }

}
