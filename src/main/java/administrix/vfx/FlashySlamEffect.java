package administrix.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.IronWaveParticle;

// One of many base-game effect overrides.
// This is for an Iron Wave clone.

public class FlashySlamEffect extends AbstractGameEffect {
    private float waveTimer = 0.0F;
    private float x;
    private float y;
    private float cX;
    private static final float WAVE_INTERVAL = 0.03F;
    private Color color;

    public FlashySlamEffect (float x, float y, float cX) {
        this(x, y, cX, (new Color(0.95F, 0.9F, 0.8F, 0.1F)));
    }

    public FlashySlamEffect (float x, float y, float cX, Color color) {
        this.x = x + 120.0F * Settings.scale;
        this.y = y - 20.0F * Settings.scale;
        this.cX = cX;
        this.color = color;
    }

    @Override
    public void update() {
        this.waveTimer -= Gdx.graphics.getDeltaTime();
        if (this.waveTimer < 0.0F) {
            this.waveTimer = 0.03F;
            this.x += 160.0F * Settings.scale;
            this.y -= 15.0F * Settings.scale;
            AbstractDungeon.effectsQueue.add(new FlashySlamParticle(this.x, this.y, this.color));
            if (this.x > this.cX) {
                this.isDone = true;
                CardCrawlGame.sound.playA("ATTACK_MAGIC_FAST_3", -0.4F);
            }
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}