package administrix.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisParticle;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

// One of many base-game effect recolouring overrides.
// This is for Heavy Blade's.

public class KinesisEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float tX;
    private float tY;
    private Color edge;
    private Color inside;

    public KinesisEffect(float sX, float sY, float tX, float tY,
                         float duration, Color edge, Color inside) {
        this.x = sX;
        this.y = sY;
        this.tX = tX;
        this.tY = tY;
        this.scale = 0.12F;
        this.duration = duration; // default's 0.5F
        this.edge = edge;
        this.inside = inside;
    }

    public void update() {
        this.scale -= Gdx.graphics.getDeltaTime();
        if (this.scale < 0.0F) {
            AbstractDungeon.effectsQueue.add(new KinesisParticle(this.x + MathUtils.random(60.0F, -60.0F) * Settings.scale, this.y + MathUtils.random(60.0F, -60.0F) * Settings.scale, this.tX, this.tY, AbstractDungeon.player.flipHorizontal, this.edge, this.inside));
            this.scale = 0.04F;
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}