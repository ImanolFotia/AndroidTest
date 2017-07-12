package com.example.imanol.pvp_battle;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;
import android.content.res.AssetManager;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;


/**
 * Created by Imanol on 05/06/2017.
 */

public class Shader {

    private int mProgram = 0;

    private Context mContext;

    public int getProgramID()
    {
        return mProgram;
    }

    public String toString(InputStream is)
    {
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String content;
        try {
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            while ((content = br.readLine()) != null) {
                sb.append(content);
            }
            isr.close();
            br.close();
        } catch (IOException ioe) {
            System.out.println("IO Exception");
            ioe.printStackTrace();
        }
        String mystring = sb.toString();
        return mystring;
    }

    public Shader(Context context)
    {
        int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

        mContext = context;
        String vertexShaderCode = "";
        String fragmentShaderCode = "";
        AssetManager assets = mContext.getAssets();

        try {
            InputStream vertexContent = assets.open("shaders/vertex.glsl");
            vertexShaderCode = this.toString(vertexContent);
            vertexContent.close();

            InputStream fragmentContent = assets.open("shaders/fragment.glsl");
            fragmentShaderCode = this.toString(fragmentContent);
            fragmentContent.close();
        }
        catch(IOException e) {
            System.out.println(e);
        }
        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.

        GLES20.glShaderSource(vertexShaderHandle, vertexShaderCode);

        // Compile the shader.
        GLES20.glCompileShader(vertexShaderHandle);

        // Get the compilation status.
        int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        // If the compilation failed, delete the shader.
        if (compileStatus[0] == 0)
        {
            GLES20.glDeleteShader(vertexShaderHandle);
            vertexShaderHandle = 0;
        }


        if (vertexShaderHandle == 0)
        {
            throw new RuntimeException("Error creating vertex shader.");
        }

        // Load in the fragment shader shader.
        int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);


        // Pass in the shader source.
        GLES20.glShaderSource(fragmentShaderHandle, fragmentShaderCode);

        // Compile the shader.
        GLES20.glCompileShader(fragmentShaderHandle);

        // Get the compilation status.
        compileStatus = new int[1];
        GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        // If the compilation failed, delete the shader.
        if (compileStatus[0] == 0)
        {
            GLES20.glDeleteShader(fragmentShaderHandle);
            fragmentShaderHandle = 0;
        }


        if (fragmentShaderHandle == 0)
        {
            throw new RuntimeException("Error creating fragment shader.");
        }

        // Create a program object and store the handle to it.
        mProgram = GLES20.glCreateProgram();

        if (mProgram != 0)
        {
            // Bind the vertex shader to the program.
            GLES20.glAttachShader(mProgram, vertexShaderHandle);

            // Bind the fragment shader to the program.
            GLES20.glAttachShader(mProgram, fragmentShaderHandle);

            // Bind attributes
            GLES20.glBindAttribLocation(mProgram, 0, "a_Position");
            GLES20.glBindAttribLocation(mProgram, 1, "a_Normal");
            GLES20.glBindAttribLocation(mProgram, 2, "a_TexCoords");

            // Link the two shaders together into a program.
            GLES20.glLinkProgram(mProgram);

            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
            if (linkStatus[0] == 0)
            {
                GLES20.glDeleteProgram(mProgram);
                mProgram = 0;
            }
        }

        if (mProgram == 0)
        {
            throw new RuntimeException("Error creating program.");
        }

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVPMatrix");
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "a_Color");
    }

    public int mMVPMatrixHandle = 0, mPositionHandle = 0, mColorHandle = 0;

}
