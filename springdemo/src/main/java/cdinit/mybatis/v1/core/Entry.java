package cdinit.mybatis.v1.core;

public class Entry {
    public static void main(String[] args) {
        CdSqlSession sqlSession = new CdSqlSession(new CdConfiguration(),new CdSimpleExecutor());
        TestMapper mapper = sqlSession.getMapper(TestMapper.class);
        Test test = mapper.selectByPrimary("1");
        System.out.println(test);
    }
}
