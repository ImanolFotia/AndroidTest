package com.example.imanol.pvp_battle;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by Imanol on 05/06/2017.
 */

public class Triangle {

    FloatBuffer mVertices;
    FloatBuffer mColors;

    int mVBO;

    private final int mBytesPerFloat = 4;

    /** How many elements per vertex. */
    private final int mStrideBytes = 6 * mBytesPerFloat;

    public Triangle()
    {
        mVertices = ByteBuffer.allocateDirect(12 * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        final float[] triangle2VerticesData = {

                -10.0f, -10.0f, 0.0f,

                -10.0f, 10.0f, 0.0f,

                10.0f, -10.0f, 0.0f,

                10.0f, 10.0f, 0.0f};
        mVertices.put(triangle2VerticesData).position(0);

        final float[] triangle2ColorData = {
                3.0f, 3.0f,

                3.0f, 0.0f,

                0.0f, 3.0f,

                0.0f, 0.0f};

        mColors = ByteBuffer.allocateDirect(8 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mColors.put(triangle2ColorData).position(0);
    }


    public void Render(Shader shader)
    {
        // Pass in the position information
        mVertices.position(0);
        GLES20.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false, 0, mVertices);
        GLES20.glEnableVertexAttribArray(0);

        mColors.position(0);
        GLES20.glVertexAttribPointer(1, 2, GLES20.GL_FLOAT, false, 0, mColors);
        GLES20.glEnableVertexAttribArray(1);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }

}
