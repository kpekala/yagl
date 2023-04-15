package kpekala.yagl.utils;


import com.mokiat.data.front.parser.*;
import kpekala.yagl.yagl.model.complex.Polygon;
import kpekala.yagl.yagl.utils.ColorUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

public class OBJLoader {

    //Example of use
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

    public static Polygon[] load(String objPath, String mtlPath) throws IOException {
        var objModel = loadObjModel(objPath);
        var objToDraw = objModel.getObjects().get(0);
        var singleMesh = objToDraw.getMeshes().get(0);
        return parseMesh(singleMesh, objModel.getVertices(), null);
    }

    private static MTLLibrary loadMtlLibrary(String mtlPath) throws IOException {
        URL url = getURL(mtlPath);
        var in = url.openStream();
        return new MTLParser().parse(in);
    }

    private static OBJModel loadObjModel(String path) throws IOException {
        URL objFileURL = getURL(path);
        assert objFileURL != null;
        InputStream in = objFileURL.openStream();
        return new OBJParser().parse(in);
    }

    private static URL getURL(String path) {
        return OBJLoader.class.getResource(path);
    }

    private static Polygon[] parseMesh(OBJMesh mesh, List<OBJVertex> vs, MTLLibrary mtlLibrary) {
        List<OBJFace> faces = mesh.getFaces();
        var polygons = new Polygon[faces.size()];
        for (int i = 0; i < faces.size(); i++) {
            var color = ColorUtils.randomGreyColor();
            float[][] data = faces.get(i).getReferences().stream().
                    map(ref -> new float[]{vs.get(ref.vertexIndex).x, vs.get(ref.vertexIndex).y, vs.get(ref.vertexIndex).z})
                    .toArray(float[][]::new);
            polygons[i] = new Polygon(data, color);
        }
        return polygons;
    }
}
