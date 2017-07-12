package com.example.imanol.pvp_battle;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.IntBuffer;

/**
 * Created by Imanol on 30/06/2017.
 */

public class Cube {

    private int POSITION_DATA_SIZE = 3;
    private int NORMAL_DATA_SIZE = 3;
    private int TEXTURE_COORDINATE_DATA_SIZE = 2;
    private int BYTES_PER_FLOAT = 4;
    private int BYTES_PER_INT = 4;

    private int mPositionAttribute = 0;
    private int mNormalAttribute = 1;
    private int mTextureCoordinateAttribute = 2;

    private FloatBuffer mVertices;
    private ShortBuffer mIndices;
    private int[] mIBO = new int[1];
    private int[] mVBO = new int[1];

    public Cube()
    {
        this.Prepare();
    }

    public void Render()
    {
        final int stride = (POSITION_DATA_SIZE + NORMAL_DATA_SIZE + TEXTURE_COORDINATE_DATA_SIZE) * BYTES_PER_FLOAT;

        // Pass in the position information
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVBO[0]);
        GLES20.glEnableVertexAttribArray(mPositionAttribute);
        GLES20.glVertexAttribPointer(mPositionAttribute, POSITION_DATA_SIZE, GLES20.GL_FLOAT, false, stride, 0);

        // Pass in the normal information
        GLES20.glEnableVertexAttribArray(mNormalAttribute);
        GLES20.glVertexAttribPointer(mNormalAttribute, NORMAL_DATA_SIZE, GLES20.GL_FLOAT, false, stride, POSITION_DATA_SIZE * BYTES_PER_FLOAT);

        // Pass in the texture information
        GLES20.glEnableVertexAttribArray(mTextureCoordinateAttribute);
        GLES20.glVertexAttribPointer(mTextureCoordinateAttribute, TEXTURE_COORDINATE_DATA_SIZE, GLES20.GL_FLOAT, false,
                stride, (POSITION_DATA_SIZE + NORMAL_DATA_SIZE) * BYTES_PER_FLOAT);

        // Draw the cubes.
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIBO[0]);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 30, GLES20.GL_UNSIGNED_SHORT, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private void Prepare()
    {
        final float[] vertices =
                {
                         1.0f,  1.0f,  3.0f, 0.0f, 1.0f, 0.0f, 2.0f, 0.0f,
                        -1.0f,  1.0f,  3.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f,
                        -1.0f, -1.0f,  3.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
                         1.0f, -1.0f,  3.0f, 0.0f, 1.0f, 0.0f, 3.0f, 0.0f,
                         1.0f,  1.0f, 2.0f, 0.0f, 1.0f, 0.0f, 2.0f, 1.0f,
                         1.0f, -1.0f, 2.0f, 0.0f, 1.0f, 0.0f, 3.0f, 1.0f,
                        -1.0f,  1.0f, 2.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
                        -1.0f, -1.0f, 2.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f
                };
        mVertices = ByteBuffer.allocateDirect(64 * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertices.put(vertices).position(0);

        final short[] indices = { 0,1,2, 0,2,3, // Front Face
                                4,0,3, 4,3,5, // Right Face
                                1,6,7, 1,7,2, // Left Face
                                4,6,1, 4,1,0, // Top Face
                                3,2,7, 3,7,5};// Bottom Face
        mIndices = ByteBuffer.allocateDirect(30 * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        mIndices.put(indices).position(0);

        GLES20.glGenBuffers(1, mVBO, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVBO[0]);

        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, mVertices.capacity() * BYTES_PER_FLOAT,
                mVertices, GLES20.GL_STATIC_DRAW);

        GLES20.glGenBuffers(1, mIBO, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIBO[0]);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndices.capacity() * 2, mIndices, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private void Release()
    {
        if (mVBO[0] > 0) {
            GLES20.glDeleteBuffers(1, mVBO, 0);
            mVBO[0] = 0;
        }

        if (mIBO[0] > 0) {
            GLES20.glDeleteBuffers(1, mIBO, 0);
            mIBO[0] = 0;
        }
    }
}
