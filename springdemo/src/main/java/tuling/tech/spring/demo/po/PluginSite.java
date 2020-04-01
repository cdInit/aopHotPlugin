package tuling.tech.spring.demo.po;

import lombok.Data;

import java.util.List;

@Data
public class PluginSite {
    private String name;
    private List<PluginConfig> configs;
}
