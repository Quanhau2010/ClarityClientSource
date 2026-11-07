#version 330 core

in  vec2 uv;
out vec4 outColor;

uniform sampler2D ColorTexture;
uniform sampler2D DepthTexture;

uniform vec3  outlineColor;
uniform float outlineThickness;

uniform bool  tintEnabled;
uniform vec3  tintColor;
uniform float tintAmount;

void main() {
    float centerDepth = texture(DepthTexture, uv).r;
    bool  isHand      = centerDepth < 0.99;

    
    if (isHand) {
        vec4 origColor = texture(ColorTexture, uv);
        if (tintEnabled) {
            outColor = vec4(mix(origColor.rgb, tintColor, tintAmount), origColor.a);
        } else {
            outColor = origColor;
        }
        return;
    }

    vec2  texel = 1.0 / vec2(textureSize(DepthTexture, 0));
    float r     = outlineThickness;

    
    
    
    const int   N      = 16;
    const float TWO_PI = 6.28318530718;

    float nearestHit = r + 1.0; 
    bool  hit        = false;

    for (int i = 0; i < N; i++) {
        float angle = (float(i) + 0.5) / float(N) * TWO_PI;
        vec2  dir   = vec2(cos(angle), sin(angle)) * texel;

        
        for (int ri = 1; ri <= 3; ri++) {
            float sample_r = r * float(ri) / 3.0;
            if (texture(DepthTexture, uv + dir * sample_r).r < 0.99) {
                nearestHit = min(nearestHit, sample_r);
                hit = true;
                break; 
            }
        }
        if (hit && nearestHit < 0.4 * r) break; 
    }

    if (!hit) discard;

    
    
    float alpha = smoothstep(r, 0.0, nearestHit);

    outColor = vec4(outlineColor, clamp(alpha, 0.0, 1.0));
}
