#version 330 core

in vec2 uv;
out vec4 outColor;

uniform sampler2D ColorTexture;
uniform sampler2D DepthTexture;
uniform float time;
uniform float chromaSpeed;
uniform float chromaSaturation;
uniform float chromaBrightness;

vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

vec3 createChromaEffect(vec2 coord, float t) {
    
    float hue  = fract(coord.y * 0.5 + coord.x * 0.3 + t * chromaSpeed);
    float wave = sin(coord.y * 10.0 + t * 3.0) * 0.05;
    hue += wave;
    return hsv2rgb(vec3(hue, chromaSaturation, chromaBrightness));
}

void main() {
    
    vec2 texelSize = 1.0 / vec2(textureSize(DepthTexture, 0));

    
    
    
    float centerDepth = texture(DepthTexture, uv).r;
    float minDepth    = centerDepth;
    minDepth = min(minDepth, texture(DepthTexture, uv + vec2(-texelSize.x, 0.0)).r);
    minDepth = min(minDepth, texture(DepthTexture, uv + vec2(0.0, -texelSize.y)).r);

    
    float mask = smoothstep(0.99, 0.98, minDepth);
    if (mask < 0.01) discard;

    vec4 originalColor = texture(ColorTexture, uv);
    vec3 chromaColor   = createChromaEffect(uv, time);

    
    float pulse = 0.88 + sin(time * 4.0) * sin(time * 4.0) * 0.12;
    chromaColor *= pulse;

    
    
    vec4 finalHandColor = mix(originalColor, vec4(chromaColor, 1.0), 0.80);

    outColor = vec4(finalHandColor.rgb, mask);
}
