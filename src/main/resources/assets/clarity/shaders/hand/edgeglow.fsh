#version 330 core

in  vec2 uv;
out vec4 outColor;

uniform sampler2D ColorTexture;
uniform sampler2D DepthTexture;

uniform vec3  glowColor;
uniform float glowRadius;
uniform float glowIntensity;
uniform float glowFalloff;

uniform bool  tintEnabled;
uniform vec3  tintColor;
uniform float tintAmount;

void main() {
    float centerDepth = texture(DepthTexture, uv).r;

    
    if (centerDepth < 0.99) {
        vec4 origColor = texture(ColorTexture, uv);
        if (tintEnabled) {
            outColor = vec4(mix(origColor.rgb, tintColor, tintAmount), origColor.a);
        } else {
            outColor = origColor;
        }
        return;
    }

    vec2  texel = 1.0 / vec2(textureSize(DepthTexture, 0));
    float pixR  = glowRadius;

    
    float minPixDist = pixR;
    float halfStep   = 0.5;
    vec2 cardinals[4];
    cardinals[0] = vec2( halfStep,  0.0) * texel;
    cardinals[1] = vec2(-halfStep,  0.0) * texel;
    cardinals[2] = vec2( 0.0,  halfStep) * texel;
    cardinals[3] = vec2( 0.0, -halfStep) * texel;
    for (int d = 0; d < 4; d++) {
        if (texture(DepthTexture, uv + cardinals[d]).r < 0.99)
            minPixDist = min(minPixDist, halfStep);
    }

    
    if (minPixDist > 0.5) {
        
        
        const int   N      = 32;
        const float GOLDEN = 2.39996323;

        for (int i = 0; i < N; i++) {
            float angle = float(i) * GOLDEN;
            float r     = sqrt(float(i + 1) / float(N)) * pixR;
            vec2  off   = vec2(cos(angle), sin(angle)) * r * texel;

            if (texture(DepthTexture, uv + off).r < 0.99) {
                minPixDist = min(minPixDist, r);
                
                if (minPixDist < 1.5) break;
            }
        }
    }

    if (minPixDist >= pixR) discard;

    
    float t     = clamp(minPixDist / pixR, 0.0, 1.0);
    float soft  = smoothstep(1.0, 0.0, t);
    float alpha = glowIntensity * pow(soft, glowFalloff * 0.5 + 0.5);

    outColor = vec4(glowColor, clamp(alpha, 0.0, 1.0));
}
