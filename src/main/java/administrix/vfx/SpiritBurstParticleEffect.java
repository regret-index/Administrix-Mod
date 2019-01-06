package administrix.vfx;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;

// One of many base-game effect overrides.
// This is for part of Hexaghost's Inferno.

public class SpiritBurstParticleEffect extends FireBurstParticleEffect {

    public SpiritBurstParticleEffect (float x, float y, Color color) {
        super(x, y);
        this.color = color;
    }
}