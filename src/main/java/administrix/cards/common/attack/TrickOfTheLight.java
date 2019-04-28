package administrix.cards.common.attack;

import administrix.AdministrixMod;
import administrix.cards.AbstractAdministrixCard;
import administrix.patches.AbstractCardEnum;
import administrix.powers.YangPower;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;

import static administrix.patches.CardTagsEnum.PLOT;

public class TrickOfTheLight extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:TrickOfTheLight";
    public static final String NAME = "Trick of the Light";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF_AND_ENEMY;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 8;
    private static final int YANG_AMOUNT = 4;

    public TrickOfTheLight() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.TRICK_OF_THE_LIGHT, COST,
                CARD_STRINGS.DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.LichGold,
                rarity, target);
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = YANG_AMOUNT;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
        for(int i = 0; i < 6; ++i) {
            AbstractDungeon.effectsQueue.add(new PlasmaOrbPassiveEffect(m.hb.cX + MathUtils.random(60F, 100F), m.hb.cY + MathUtils.random(-60F, 140F)));
            AbstractDungeon.effectsQueue.add(new PlasmaOrbPassiveEffect(m.hb.cX + MathUtils.random(-100F, -60F), m.hb.cY + MathUtils.random(-60F, 140F)));
        }

        if (!AbstractDungeon.player.drawPile.group.isEmpty()) {
            for (int i = p.drawPile.group.size() - 1; i >= 0; --i) {
                AbstractCard c = p.drawPile.group.get(i);

                if (c.hasTag(PLOT)) {
                    p.drawPile.moveToDiscardPile(c);
                    p.drawPile.removeCard(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                    break;
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new YangPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
    }


    @Override
    public AbstractCard makeCopy() {
        return new TrickOfTheLight();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}