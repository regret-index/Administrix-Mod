package administrix.vfx;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.vfx.combat.FlyingSpikeEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

// Derived from the base game's Power Buff Effect.

public class PlotDisplayEffect extends AbstractGameEffect {
    private static final float TEXT_DURATION = 2.0F;
    private static final float STARTING_OFFSET_Y;
    private static final float TARGET_OFFSET_Y;
    private float x;
    private float y;
    private float offsetY;
    private String msg;
    private Color targetColor;

    public PlotDisplayEffect(String msg) {
        this.duration = 2.0F;
        this.startingDuration = 2.0F;
        this.msg = msg;
        this.x = ((float)Settings.WIDTH / 2.0F);
        this.y = (float)Settings.HEIGHT * 2.0F / 3.0F;
        this.targetColor = Color.GOLD.cpy();
        this.color = Color.PURPLE.cpy();
        this.offsetY = STARTING_OFFSET_Y;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            int i;
            for(i = 0; i < 20; ++i) {
                AbstractDungeon.effectsQueue.add(new FlyingSpikeEffect(this.x - MathUtils.random(-160.0F, 160.0F) * Settings.scale, this.y + MathUtils.random(80.0F, 120.0F) * Settings.scale, -90.0F, 0.0F, MathUtils.random(-200.0F, -50.0F) * Settings.scale, Color.PURPLE.cpy()));
            }

            for(i = 0; i < 20; ++i) {
                AbstractDungeon.effectsQueue.add(new FlyingSpikeEffect(this.x - MathUtils.random(-160.0F, 160.0F) * Settings.scale, this.y + MathUtils.random(80.0F, 120.0F) * Settings.scale, 90.0F, 0.0F, MathUtils.random(200.0F, 50.0F) * Settings.scale, Color.PURPLE.cpy()));
            }
        }

        this.offsetY = Interpolation.exp10In.apply(TARGET_OFFSET_Y, STARTING_OFFSET_Y, this.duration / 2.0F);
        this.color.r = Interpolation.pow2In.apply(this.targetColor.r, 1.0F, this.duration / this.startingDuration);
        this.color.g = Interpolation.pow2In.apply(this.targetColor.g, 1.0F, this.duration / this.startingDuration);
        this.color.b = Interpolation.pow2In.apply(this.targetColor.b, 1.0F, this.duration / this.startingDuration);
        this.color.a = Interpolation.exp10Out.apply(0.0F, 1.0F, this.duration / 2.0F);
        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {
            this.isDone = true;
            this.duration = 0.0F;
        }
    }

    public void render(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.applyPowerFont, this.msg, this.x, this.y + this.offsetY, this.color);
    }

    public void dispose() {
    }

    static {
        STARTING_OFFSET_Y = 60.0F * Settings.scale;
        TARGET_OFFSET_Y = 100.0F * Settings.scale;
    }
}