package kpekala.yagl.scene.utils;

import kpekala.yagl.scene.model.basic.Vector3f;
import lombok.Data;

import java.util.Map;

@Data
public class ColorConfig {
    public Map<String, float[]> colors;

    public ColorConfig() {
    }

    public Vector3f getColor(String name) {
        var color = colors.get(name);
        if (color == null) {
            return ColorUtils.defaultColor;
        }
        return new Vector3f(color[0], color[1], color[2]);
    }
}
