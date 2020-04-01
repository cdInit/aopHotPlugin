package tuling.tech.spring.demo.po;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PluginConfig {
    private String id;
    private String name;
    private String className;
    private String localUrl; // 本地路径
    private String active;
    private String version;
    private String exp;
}
