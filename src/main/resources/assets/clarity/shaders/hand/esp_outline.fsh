#version 330 core

in  vec2 uv;
out vec4 outColor;

uniform sampler2D PlayerMask;

uniform float outlineThickness;
uniform bool  outlineEnabled;

uniform bool  fillEnabled;
uniform vec3  fillColor;
uniform float fillOpacity;

void main() {
    vec4 mask = texture(PlayerMask, uv);
    bool isPlayer = mask.a > 0.01;

    
    
    if (isPlayer) {
        if (fillEnabled) {
            outColor = vec4(fillColor, clamp(fillOpacity * mask.a, 0.0, 1.0));
        } else {
            discard;
        }
        return;
    }

    
    
    
    if (!outlineEnabled) discard;

    vec2  texel = 1.0 / vec2(textureSize(PlayerMask, 0));
    float r     = outlineThickness;

    const int   N      = 16;
    const float TWO_PI = 6.28318530718;

    float nearestHit = r + 1.0;
    vec4  hitColor   = vec4(1.0);
    bool  hit        = false;

    for (int i = 0; i < N; i++) {
        float angle = (float(i) + 0.5) / float(N) * TWO_PI;
        vec2  dir   = vec2(cos(angle), sin(angle)) * texel;

        for (int ri = 1; ri <= 3; ri++) {
            float sample_r = r * float(ri) / 3.0;
            vec4  s        = texture(PlayerMask, uv + dir * sample_r);
            if (s.a > 0.01) {
                if (sample_r < nearestHit) {
                    nearestHit = sample_r;
                    hitColor   = s;
                }
                hit = true;
                break;
            }
        }
        if (hit && nearestHit < 0.4 * r) break;
    }

    if (!hit) discard;

    float alpha = smoothstep(r, 0.0, nearestHit) * hitColor.a;
    outColor = vec4(hitColor.rgb, clamp(alpha, 0.0, 1.0));
}
