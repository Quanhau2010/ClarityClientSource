#version 330 core

in  vec2 uv;
out vec4 outColor;  

uniform sampler2D DepthTexture;

void main() {
    float d = texture(DepthTexture, uv).r;
    if (d < 0.99) {
        outColor = vec4(uv, 0.0, 1.0);   
    } else {
        outColor = vec4(0.0, 0.0, 0.0, 0.0);  
    }
}
