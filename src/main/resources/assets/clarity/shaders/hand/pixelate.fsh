#version 330 core


in vec2 uv;
out vec4 outColor;

uniform sampler2D ColorTexture;
uniform sampler2D DepthTexture;
uniform vec2  texel;
uniform float pxScale;
uniform float pixelSize;

void main() {
    vec2 cell = texel * max(2.0, pixelSize * max(pxScale, 0.5));
    vec2 q = (floor(uv / cell) + 0.5) * cell;                
    bool inBlock = texture(DepthTexture, q).r < 0.99;
    bool inPx = texture(DepthTexture, uv).r < 0.99;
    if (!inBlock && !inPx) discard;
    if (!inBlock) { outColor = vec4(texture(ColorTexture, uv).rgb, 1.0); return; }
    vec3 c = texture(ColorTexture, q).rgb;
    c = floor(c * 6.0 + 0.5) / 6.0;                          
    
    bool l = texture(DepthTexture, q - vec2(cell.x, 0.0)).r >= 0.99;
    bool r = texture(DepthTexture, q + vec2(cell.x, 0.0)).r >= 0.99;
    bool d = texture(DepthTexture, q - vec2(0.0, cell.y)).r >= 0.99;
    bool u = texture(DepthTexture, q + vec2(0.0, cell.y)).r >= 0.99;
    vec2 f = fract(uv / cell);
    float edge = 0.0;
    if (l && f.x < 0.2) edge = 1.0;
    if (r && f.x > 0.8) edge = 1.0;
    if (d && f.y < 0.2) edge = 1.0;
    if (u && f.y > 0.8) edge = 1.0;
    outColor = vec4(mix(c, c * 0.25, edge), 1.0);
}
