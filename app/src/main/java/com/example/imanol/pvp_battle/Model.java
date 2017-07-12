package com.example.imanol.pvp_battle;

import android.opengl.GLES20;

import android.opengl.GLES20;

/**
 * Created by Imanol on 07/06/2017.
 */

public class Model {

    private vec3[] mVertices;
    private vec2[] mTexcoords;
    private vec3[] mNormals;
    private int[] mIndices;
    private Texture diffuseTex;

    public Model(String path)
    {

    }

    public void Render(int shaderID)
    {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, diffuseTex.texture[0]);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mIndices.length, GLES20.GL_INT, 0);
    }


}
