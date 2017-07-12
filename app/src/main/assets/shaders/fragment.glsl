precision mediump float;

varying vec2 uv;

uniform sampler2D tex;

void main()
{
    gl_FragColor = vec4(texture2D(tex, uv).rgb, 1.0);
}