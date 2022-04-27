package com.example.paint.utils;


import com.example.paint.yagl.model.complex.Polygon;
import com.mokiat.data.front.parser.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class OBJLoader {
    public static Polygon[] load(String path) throws IOException {
        URL objFileURL = OBJLoader.class.getResource(path);
        if(objFileURL == null)
            throw new IOException();
        InputStream in = objFileURL.openStream();
        final OBJModel model = new OBJParser().parse(in);
        if(model.getObjects().size()==0)
            return new Polygon[]{};
        return parseObject(model.getObjects().get(0), model.getVertices());
    }

    private static Polygon[] parseObject(OBJObject objObject, List<OBJVertex> vs) {
        List<OBJFace> faces = objObject.getMeshes().get(0).getFaces();
        var polygons = new Polygon[faces.size()];
        for (int i=0; i<faces.size(); i++){
            float[][] data = (float[][]) faces.get(i).getReferences().stream().
                    map(ref -> new float[]{vs.get(ref.vertexIndex).x,vs.get(ref.vertexIndex).y,vs.get(ref.vertexIndex).z})
                    .toArray(float[][]::new);
            //System.out.println(((float[])data[0])[0]);
            polygons[i] = new Polygon(data);
        }
        return polygons;
    }

    public static void main(String[] args) {
        URL objFileURL = OBJLoader.class.getResource("/sample.obj");
        if (objFileURL != null) {
            try (InputStream in = objFileURL.openStream()) {
                // Create an OBJParser and parse the resource
                final IOBJParser parser = new OBJParser();
                final OBJModel model = parser.parse(in);

                // Use the model representation to get some basic info
                System.out.println(MessageFormat.format(
                        "OBJ model has {0} vertices, {1} normals, {2} texture coordinates, and {3} objects.",
                        model.getVertices().size(),
                        model.getNormals().size(),
                        model.getTexCoords().size(),
                        model.getObjects().size()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
