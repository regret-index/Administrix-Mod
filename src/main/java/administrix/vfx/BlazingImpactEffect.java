package administrix.vfx;

import com.badlogic.gdx.graphics.Color;;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

// One of many base-game effect recolouring overrides.
// This is for Heavy Blade's.

public class BlazingImpactEffect extends VerticalImpactEffect {

    public BlazingImpactEffect (float x, float y) {
        this(x, y, new Color(0.95F, 0.9F, 0.8F, 0.1F));
    }

    public BlazingImpactEffect (float x, float y, Color color) {
        super(x, y);
        this.color = color;
    }
}
