package administrix.actions;

// Adjusted version of Mana Rampage. Thanks, Flynn1014.

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class TranscensionAction extends AbstractGameAction {

    private int amount;
    private AbstractMonster target;
    private ArrayList<AbstractCard> list = new ArrayList<>();
    private boolean isMulti = false;
    AbstractPlayer p;

    public TranscensionAction(int amount, AbstractCard lastAttack) {
        this(amount, lastAttack, AbstractDungeon.getMonsters().getRandomMonster(true));
        isMulti = true;
    }

    public TranscensionAction(int amount, AbstractCard lastAttack, AbstractMonster toTarget) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        target = toTarget;

        for (int i = 0; i < this.amount; i++) {
            AbstractCard t = lastAttack.makeCopy();
            for (int j = 0; j < t.timesUpgraded; j++) {
                t.upgrade();
            }
            list.add(t);
        }
    }

    public void update() {
        if (list.isEmpty()) {
            this.isDone = true;
            return;
        }

        AbstractCard card = list.get(0);
        list.remove(0);

        if (card.target == AbstractCard.CardTarget.ALL || card.target == AbstractCard.CardTarget.ALL_ENEMY) {
            target = AbstractDungeon.getMonsters().getRandomMonster(true);
        }

        if (target == null) {
            this.isDone = true;
            return;
        }

        AbstractDungeon.player.limbo.group.add(card);
        card.current_x = ((float)Settings.WIDTH / 2.0F);
        card.current_y = ((float)Settings.HEIGHT / 2.0F - 800F * Settings.scale);
        card.target_x = ((float)Settings.WIDTH / 2.0F + (MathUtils.random(200.0F, -200.0F) * Settings.scale));
        card.target_y = ((float)Settings.HEIGHT / 2.0F + 800F * Settings.scale);
        card.drawScale = 0.12F;
        card.freeToPlayOnce = true;
        card.purgeOnUse = true;
        card.targetAngle = 0.0F;

        /*
        if (!card.canUse(AbstractDungeon.player, this.target)) {
            AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
        }
        */

        card.applyPowers();
        AbstractDungeon.actionManager.currentAction = null;
        AbstractDungeon.actionManager.addToTop(this);
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, this.target));

        if (!Settings.FAST_MODE) {
            if (this.amount > 10) {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FAST));
            } else {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            }
        } else {
            if (this.amount > 10) {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_XFAST));
            } else {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }
        }
    }
}