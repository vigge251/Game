package renderEngine;

/**
 * Created by Victor on 2016-04-09.
 */
public class RawModel {
    private int vaoId;
    private int vertexCount;

    public RawModel(int vaoId, int vertexCount){
        this.vaoId = vaoId;
        this.vertexCount = vertexCount;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
