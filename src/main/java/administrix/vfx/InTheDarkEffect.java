package administrix.vfx;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.vfx.combat.HeartMegaDebuffEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

// Another effect override- this puts a duration to the
// dimming for Hope in the Dark, borrowed from the heart.

public class InTheDarkEffect extends HeartMegaDebuffEffect {

    public InTheDarkEffect(float timing) {
        super();
        this.duration = this.startingDuration = timing;
    }
}
