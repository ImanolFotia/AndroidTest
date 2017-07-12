uniform mat4 u_MVPMatrix;

attribute vec3 a_Position;
attribute vec3 a_Normal;
attribute vec2 a_TexCoords;

varying vec2 uv;
varying vec3 normal;

void main()
{
    uv = a_TexCoords;
    normal = a_Normal;

    gl_Position = u_MVPMatrix * vec4(a_Position, 1.0);
}