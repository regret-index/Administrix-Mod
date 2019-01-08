package administrix.cards.common.attack;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

// Disabled for beta release.

public class TraineeJiangshi extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:TraineeJiangshi";
    public static final String NAME = "Trainee Jiangshi";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;
    private static final AbstractCard.CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final int ATTACK_DMG = 1;

    public TraineeJiangshi() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.FIENDISH_CRIMSON, COST,
                CARD_STRINGS.DESCRIPTION,
                type, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.exhaust = true;
    }

    public static int countCards()
    {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (isTrainee(c)) {
                count++;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (isTrainee(c)) {
                count++;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (isTrainee(c)) {
                count++;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (isTrainee(c)) {
                count++;
            }
        }
        return count;
    }

    public static boolean isTrainee(AbstractCard c)
    {
        return (c.rarity == CardRarity.BASIC ||
                c.rarity == CardRarity.COMMON);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int training = countCards();

        if ((damage >= m.currentHealth + m.currentBlock && damageTypeForTurn == DamageInfo.DamageType.NORMAL) ||
                (damage >= m.currentHealth && damageTypeForTurn == DamageInfo.DamageType.HP_LOSS)) {

            if (m != null) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Settings.GOLD_COLOR.cpy()), 0.3F));
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage + training, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));

            if (((m.isDying) || (m.currentHealth <= 0)) && (!m.halfDead) &&
                    (!m.hasPower("Minion"))) {
                AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(
                        CardLibrary.getCard(ID).makeCopy()));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TraineeJiangshi();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
        }
    }

}