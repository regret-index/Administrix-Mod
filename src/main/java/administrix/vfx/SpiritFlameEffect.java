package administrix.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;

// One of many base-game effects with a color override.
// This is Hexaghost's Inferno hits.

public class SpiritFlameEffect extends AbstractGameEffect {
    private static final int COUNT = 25;
    private float x;
    private float y;

    public SpiritFlameEffect(float x, float y) {
        this(x, y, new Color(0.9F, 0.8F, 0.1F, 0.0F));
    }

    public SpiritFlameEffect(float x, float y, Color newColor) {
        this.x = x;
        this.y = y;
        this.color = newColor;
    }

    public void update() {
        for(int i = 0; i < COUNT; ++i) {
            AbstractDungeon.effectsQueue.add(new SpiritBurstParticleEffect(this.x, this.y, this.color));
            AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(this.x, this.y, this.color));
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
