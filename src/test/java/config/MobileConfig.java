package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/MobileConfig.properties")
public interface MobileConfig extends Config {
    @Key("https.url")
    String url();

    @Key("https.curl")
    String curl();

    @Key("device.name")
    String device();

    @Key("device.user")
    String user();

    @Key("device.key")
    String key();
}
