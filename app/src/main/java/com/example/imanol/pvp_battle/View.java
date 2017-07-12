package com.example.imanol.pvp_battle;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Imanol on 05/06/2017.
 */

public class View implements GLSurfaceView.Renderer {

    private Context mContext;

    final float eyeX = 0.0f;
    final float eyeY = 0.0f;
    final float eyeZ = 1.5f;

    // We are looking toward the distance
    final float lookX = 0.0f;
    final float lookY = 0.0f;
    final float lookZ = -5.0f;

    // Set our up vector. This is where our head would be pointing were we holding the camera.
    final float upX = 0.0f;
    final float upY = 1.0f;
    final float upZ = 0.0f;

    float mDeltaX, mDeltaY;

    public View(Context context)
    {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glFrontFace(GLES20.GL_CCW);
       // GLES20.glEnable(GLES20.GL_CULL_FACE);
        //GLES20.glCullFace(GLES20.GL_BACK);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        mContext = context;

    }

    @Override
    public void onDrawFrame(GL10 glUnused){
        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).


        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);

        GLES10.glClear(GLES10.GL_COLOR_BUFFER_BIT);
        GLES10.glClear(GLES10.GL_DEPTH_BUFFER_BIT);
        GLES10.glClearColor(0.0f, 1.0f, 1.0f, 0.0f);
        long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, mDeltaX*0.02f, -mDeltaY*0.02f, -2.0f);
        //Matrix.rotateM(mModelMatrix, 0, mDeltaX, 0.0f, 1.0f, 0.0f);
        //Matrix.rotateM(mModelMatrix, 0, mDeltaY, 1.0f, 0.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        GLES20.glUseProgram(mShader.getProgramID());
        GLES20.glUniformMatrix4fv(mShader.mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture.texture[0]);
        GLES20.glUniform1i(mTexture.texture[0], 0);
        //mTriangle.Render(mShader);
        mCube.Render();

        GLES20.glUseProgram(0);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {

        mTriangle = new Triangle();
        mShader = new Shader(mContext);
        mTexture = new Texture("textures/brick.png", this.mContext);
        mCube = new Cube();
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        // Set our up vector. This is where our head would be pointing were we holding the camera.
    }



    @Override
    public void onSurfaceChanged(GL10 a, int width, int height)
    {

        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 0.1f;
        final float far = 100.0f;

        //Matrix.frustumM(mProjectionMatrix, 0, left,right, bottom, top, near, far);
        Matrix.perspectiveM(mProjectionMatrix, 0, 75.0f, ratio, near, far);
        GLES10.glViewport(0, 0, width, height);
    }

    Shader mShader;
    Triangle mTriangle;
    Texture mTexture;
    Cube mCube;

    float[] mMVPMatrix = new float[16], mViewMatrix = new float[16], mModelMatrix = new float[16], mProjectionMatrix = new float[16];


}
