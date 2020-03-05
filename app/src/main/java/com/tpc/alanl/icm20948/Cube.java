package com.tpc.alanl.icm20948;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;


public class Cube {
    // Our vertices.

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mVertexBuffer2;
    private FloatBuffer mTextureBuffer;
    private FloatBuffer mColorBuffer;
    private ByteBuffer  mIndexBuffer;
    private ByteBuffer  mIndexBuffer2;

    /** The texture pointer */
    private int[] textures = new int[6];

    private int[] resources = {
            // front
            R.drawable.front,
            // back
            R.drawable.back,
            // LEFT
            R.drawable.left,
            // RIGHT
            R.drawable.right,
            // TOP
            R.drawable.top,
            // BOTTOM
            R.drawable.bottom,
    };


    /*private float vertices[] = {
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f,  1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f,  1.0f,
            1.0f, -1.0f,  1.0f,
            1.0f,  1.0f,  1.0f,
            -1.0f,  1.0f,  1.0f,
    };*/

    private float vertices[] = {
            // front
            -1.0f, -1.0f,  1.0f,  // 0. left-bottom-front
            1.0f, -1.0f,  1.0f,  // 1. right-bottom-front
            -1.0f,  1.0f,  1.0f,  // 2. left-top-front
            1.0f,  1.0f,  1.0f,  // 3. right-top-front
            // back
            1.0f, -1.0f, -1.0f,  // 4. right-bottom-back
            -1.0f, -1.0f, -1.0f,  // 5. left-bottom-back
            1.0f,  1.0f, -1.0f,  // 6. right-top-back
            -1.0f,  1.0f, -1.0f,  // 7. left-top-back
            // LEFT
            -1.0f, -1.0f, -1.0f,  // 5. left-bottom-back
            -1.0f, -1.0f,  1.0f,  // 0. left-bottom-front
            -1.0f,  1.0f, -1.0f,  // 7. left-top-back
            -1.0f,  1.0f,  1.0f,  // 2. left-top-front
            // RIGHT
            1.0f, -1.0f,  1.0f,  // 1. right-bottom-front
            1.0f, -1.0f, -1.0f,  // 4. right-bottom-back
            1.0f,  1.0f,  1.0f,  // 3. right-top-front
            1.0f,  1.0f, -1.0f,  // 6. right-top-back
            // TOP
            -1.0f,  1.0f,  1.0f,  // 2. left-top-front
            1.0f,  1.0f,  1.0f,  // 3. right-top-front
            -1.0f,  1.0f, -1.0f,  // 7. left-top-back
            1.0f,  1.0f, -1.0f,  // 6. right-top-back
            // BOTTOM
            -1.0f, -1.0f, -1.0f,  // 5 left-bottom-back
            1.0f, -1.0f, -1.0f,  // 4. right-bottom-back
            -1.0f, -1.0f,  1.0f,  // 0. left-bottom-front
            1.0f, -1.0f,  1.0f   // 1. right-bottom-front
    };

    private float colors[] = {
            0.0f,  1.0f,  0.0f,  1.0f,
            0.0f,  1.0f,  0.0f,  1.0f,
            1.0f,  0.5f,  0.0f,  1.0f,
            1.0f,  0.5f,  0.0f,  1.0f,
            1.0f,  0.0f,  0.0f,  1.0f,
            1.0f,  0.0f,  0.0f,  1.0f,
            0.0f,  0.0f,  1.0f,  1.0f,
            1.0f,  0.0f,  1.0f,  1.0f
    };

    /*private float texture[] = {
            // Mapping coordinates for the vertices
            0.0f, 1.0f, 1.0f,     // top left     (V2)
            0.0f, 0.0f, 1.0f,     // bottom left  (V1)
            1.0f, 1.0f, 1.0f,     // top right    (V4)
            1.0f, 0.0f, 1.0f,     // bottom right (V3)

    };*/

    private float texture[] = {

            0,1,  // 0 front
            1,1,  // 1
            0,0,  // 2
            1,0,  // 3

            0,1, // 5 BOTTOM
            1,1, // 4
            0,0, // 0
            1,0, // 1

            0,1, // 5 LEFT
            1,1, // 0
            0,0, // 7
            1,0, // 2

            0,1, // 4 back
            1,1, // 5
            0,0, // 6
            1,0, // 7

            0,1, // 2 TOP
            1,1, // 3
            0,0, // 7
            1,0, // 6

            0,1, // 1 RIGHT
            1,1, // 4
            0,0, // 3
            1,0,  // 6

    };

    /*private byte indices[] = {
            0, 4, 5, 0, 5, 1,
            1, 5, 6, 1, 6, 2,
            2, 6, 7, 2, 7, 3,
            3, 7, 4, 3, 4, 0,
            4, 7, 6, 4, 6, 5,
            3, 0, 1, 3, 1, 2
    };*/

    private byte[] indices = {
            /*// front
            0,1,3,
            0,3,2,

            // BOTTOM
            5,4,1,
            5,1,0,

            //LEFT
            5,0,2,
            5,2,7,

            //back
            4,5,7,
            4,7,6,

            //TOP
            2,3,6,
            2,6,7,

            //RIGHT
            1,4,6,
            1,6,3,*/
            0, 2, 1,
            2, 1, 3,

            4, 5, 6,
            5, 6, 7,

            8, 9, 10,
            9, 10, 11,

            12, 13, 14,
            13, 14, 15,

            16, 17, 18,
            17, 18, 19,

            20, 21, 22,
            21, 22, 23

    };

    public Cube() {
        /*ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mVertexBuffer = byteBuf.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mColorBuffer = byteBuf.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);*/
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = byteBuffer.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer2 = byteBuffer.asFloatBuffer();
        mVertexBuffer2.put(vertices);
        mVertexBuffer2.position(0);

        byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        mTextureBuffer = byteBuffer.asFloatBuffer();
        mTextureBuffer.put(texture);
        mTextureBuffer.position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
    }

    /**
     * This function draws our square on screen.
     * @param gl
     */
    public void draw(GL10 gl) {
        /*gl.glFrontFace(GL10.GL_CW);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);*/
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        // Point to our buffers
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        // Set the face rotation
        gl.glFrontFace(GL10.GL_CW);

        // Point to our vertex buffer
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer2);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);

        // Draw the vertices as triangle strip
//        gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);
        for (int i=0;i<6;i++){
            //Bind our only previously generated texture in this case
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[i]);
            mIndexBuffer.position(6*i);
            //Draw the vertices as triangles, based on the Index Buffer information
            gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);
        }
        //gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 36);

        //Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

    public void loadGLTexture(GL10 gl, Context context) {
        /*// loading texture
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.crate);

        // generate one texture pointer
        gl.glGenTextures(1, textures, 0);
        // ...and bind it to our array
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        // create nearest filtered texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        // Use Android GLUtils to specify a two-dimensional texture image from our bitmap
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        // Clean up
        bitmap.recycle();*/

        //Generate a 6 texture pointer...
        gl.glGenTextures(6, textures, 0);

        Bitmap bitmap = null;

        for (int i=0;i<6;i++)
        {
            // Create a bitmap
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    resources[i]);

            //...and bind it to our array
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[i]);

            //Create Nearest Filtered Texture
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);

            //Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

            //Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

            //Clean up
            bitmap = null;
        }
    }

}
