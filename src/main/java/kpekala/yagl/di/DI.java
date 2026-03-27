package kpekala.yagl.di;

import kpekala.yagl.engine.DepthTester;
import kpekala.yagl.scene.components.Camera;
import kpekala.yagl.scene.components.DemoScene;
import kpekala.yagl.scene.utils.ColorConfig;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

import java.io.File;

@Slf4j
public class DI {

    private static DI instance;

    private DI() {}

    private static ColorConfig colorConfig;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public void initialize() {
        colorConfig = colorConfig("src/main/resources/colors.json");
    }

    private ColorConfig colorConfig(String filePath) {
        try {
            return objectMapper.readValue(new File(filePath), ColorConfig.class);
        } catch (Exception e) {
            log.error("Failed to load color config from file: " + filePath, e);
            return new ColorConfig();
        }
    }

    public static DepthTester depthTester(int width, int height) {
        return new DepthTester(width, height);
    }

    public DemoScene demoScene() {
        return new DemoScene(new Camera(), colorConfig);
    }

    public static DI getInstance() {
        if (instance == null) {
            instance = new DI();
            instance.initialize();
        }
        return instance;
    }
}
