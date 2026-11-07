#version 330 core

in  vec2 uv;
out vec4 outColor;  

uniform sampler2D JfaTexture;
uniform float     stepSize;    
uniform vec2      texelSize;   
uniform float     glowRadius;  

void main() {
    vec2  bestSeed = vec2(0.0);
    float bestDist = 1e9;
    bool  found    = false;

    for (int dy = -1; dy <= 1; dy++) {
        for (int dx = -1; dx <= 1; dx++) {
            vec2 sampleUV = clamp(uv + vec2(float(dx), float(dy)) * stepSize * texelSize,
                                  vec2(0.0), vec2(1.0));
            vec4 s = texture(JfaTexture, sampleUV);
            if (s.a < 0.5) continue;  

            
            vec2  delta   = (uv - s.rg) / texelSize;
            float pixDist = length(delta);

            if (pixDist > glowRadius) continue;  

            if (pixDist < bestDist) {
                bestDist = pixDist;
                bestSeed = s.rg;
                found    = true;
            }
        }
    }

    outColor = found ? vec4(bestSeed, 0.0, 1.0) : vec4(0.0, 0.0, 0.0, 0.0);
}
