package tuling.tech.spring.demo.service;

import tuling.tech.spring.demo.po.PluginConfig;

public interface SpringPluginFactory {
    /**
     * install
     * uninstall
     * active
     * disable
     * havePluginList
     * update
     */

    void installPlugin(PluginConfig pluginConfig, Boolean active);
}
