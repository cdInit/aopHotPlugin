package tuling.tech.spring.demo;

import com.google.common.base.*;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableMap;
import org.assertj.core.util.Preconditions;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class GuavaTest {
    @Test
    public void testJoiner(){
        // 字符串处理Joiner
        final List<String> list = Arrays.asList("a","b","c",null);
//        System.out.println(Joiner.on("#").join(list)); //NullPointerException
        System.out.println(Joiner.on("#").useForNull("default").join(list)); //a#b#c#default
        System.out.println(Joiner.on("#").skipNulls().join(list)); //a#b#c

        final Map<String,String> map = ImmutableMap.of("h1","v1","h2","v2");
        System.out.println(Joiner.on("#").withKeyValueSeparator("=").join(map)); //h1=v1#h2=v2

        String collect = list.stream()
                .map(item -> item == null || item.isEmpty() ? "default" : item)
                .filter(item -> !item.isEmpty())
                .collect(Collectors.joining("#")); //a#b#c
        System.out.println(collect); //a#b#c#default



    }

    @Test
    public void testSplit(){
        String str = "a#b#c#  #e##";
        System.out.println(Splitter.on("#").splitToList(str)); //[a, b, c,   , e, , ]
        System.out.println(Splitter.on("#").omitEmptyStrings().splitToList(str)); //[a, b, c,   , e]
        System.out.println(Splitter.on("#").trimResults().omitEmptyStrings().splitToList(str)); //[a, b, c, e]
        System.out.println(Splitter.fixedLength(4).splitToList(str)); //[a#b#, c#  , #e##]
        System.out.println(Splitter.on("#").limit(2).splitToList(str)); //[a, b#c#  #e##] 两个元素

        String str2 = "a1=a;b1=b";
        //Map
        System.out.println(Splitter.on(";").withKeyValueSeparator("=").split(str2)); //{a1=a, b1=b}
    }

    @Test
    public void testPreconditions(){
        List<String> list = null;
        assert list != null : "this is msg"; //java.lang.AssertionError: this is msg
//        Objects.requireNonNull(null); //java.lang.NullPointerException
//        List<String> list = null;
//        checkNotNullWithMsg(list); //java.lang.NullPointerException: this is null
    }

    private void checkNotNullWithMsg(List<String> list){
        Preconditions.checkNotNull(list,"this is null");

    }

    @Test
    public void testObjects(){
        Guava g1 = new Guava("1","2",new Date());
        Guava g2 = new Guava("2","2",new Date());
        Guava g3 = new Guava("3","2",new Date());
        System.out.println(g1.compareTo(g2)); // -1
        System.out.println(g3.compareTo(g2)); // 1
    }
    static class Guava implements Comparable<Guava>{
        private final String factory;
        private final String version;
        private final Date date;

        public Guava(String factory, String version, Date date) {
            this.factory = factory;
            this.version = version;
            this.date = date;
        }

        @Override
        public int compareTo(Guava o) {
            return ComparisonChain.start().compare(this.factory,o.factory)
                    .compare(this.version,o.version)
                    .compare(this.date,o.date).result();
        }
    }

    @Test
    public void testStrings(){
        System.out.println(Strings.emptyToNull("")); //null
        System.out.println(Strings.nullToEmpty(null)); //""
        System.out.println(Strings.nullToEmpty("h")); //h
        System.out.println(Strings.commonPrefix("d1","d2")); //d
        System.out.println(Strings.repeat("d1",3)); //d1d1d1
    }
    @Test
    public void testCharsets(){
        Charset charset = Charset.forName("UTF-8");
        Charset charset2 = Charsets.UTF_8;
    }
    @Test
    public void testCharMatcher(){
        System.out.println(CharMatcher.is('A').countIn("Alex A a b")); //2
        System.out.println(CharMatcher.breakingWhitespace().collapseFrom("Alex     A a b", '*')); //Alex*A*a*b
        System.out.println(CharMatcher.javaDigit().or(CharMatcher.whitespace()).removeFrom("Alex     A a b 1 231"));//AlexAab
    }



}
