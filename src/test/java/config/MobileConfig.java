package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/MobileConfig.properties")
public interface MobileConfig extends Config {
    @Key("https.url")
    @DefaultValue("http://localhost:4723/wd/hub")
    String url();

    @Key("https.curl")
    String curl();

    @Key("device.name=")
    @DefaultValue("Pixel_4_API_30")
    String device();

    @Key("device.user")
    String user();

    @Key("device.key")
    String key();
}
