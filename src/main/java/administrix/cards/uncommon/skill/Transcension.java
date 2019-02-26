package administrix.cards.uncommon.skill;

import administrix.AdministrixMod;
import administrix.actions.TranscensionAction;
import administrix.cards.AbstractAdministrixCard;
import administrix.patches.AbstractCardEnum;
import administrix.powers.YangPower;
import administrix.powers.YinPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

public class Transcension extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:Transcension";
    public static final String NAME = "Transcension";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int FAKE_DIVISION_POINT = 0;
    private static final int BASE_DIVISION_POINT = 9;
    private static final int UPGRADE_DIVISION_POINT = 7;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType type = CardType.SKILL;
    public static AbstractCard lastAttack = null;

    public Transcension() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.TRANSCENSION, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, TARGET);
        this.baseMagicNumber = this.magicNumber = 0;
        this.secondMagicNumber = this.baseSecondMagicNumber = FAKE_DIVISION_POINT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // Count up the lower of one's Yin and Yang,
        // grab the last Attack played this turn,
        // play it for as many times as that ends up with.
        // Constructor is for looseness of targetting.
        int yinAmount = p.hasPower(YinPower.POWER_ID) ?
                        p.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = p.hasPower(YangPower.POWER_ID) ?
                         p.getPower(YangPower.POWER_ID).amount : 0;
        int equalPoint = (yinAmount > yangAmount) ? yangAmount : yinAmount;

        int divideBy = (this.upgraded) ? UPGRADE_DIVISION_POINT : BASE_DIVISION_POINT;
        this.magicNumber = equalPoint / divideBy;

        lastAttack = null;

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == CardType.ATTACK) {
                lastAttack = c;
            }
        }

        if (this.magicNumber > 0 && lastAttack != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.WHITE, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.2F));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_FIRE"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.BLACK, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.2F));

            if (lastAttack.target == CardTarget.ALL || lastAttack.target == CardTarget.ALL_ENEMY) {
                AbstractDungeon.actionManager.addToBottom(new TranscensionAction(this.magicNumber, lastAttack));
            } else {
                AbstractDungeon.actionManager.addToBottom(new TranscensionAction(this.magicNumber, lastAttack, m));
            }
        }
    }

    public void applyPowers()
    {
        // Call up the Yin / Yang count and the last attack played this turn
        // to be able to tell the player what'll happen.
        super.applyPowers();

        int yinAmount = AbstractDungeon.player.hasPower(YinPower.POWER_ID) ?
                        AbstractDungeon.player.getPower(YinPower.POWER_ID).amount : 0;
        int yangAmount = AbstractDungeon.player.hasPower(YangPower.POWER_ID) ?
                         AbstractDungeon.player.getPower(YangPower.POWER_ID).amount : 0;
        int equalPoint = (yinAmount > yangAmount) ? yangAmount : yinAmount;

        int divideBy = (this.upgraded) ? UPGRADE_DIVISION_POINT : BASE_DIVISION_POINT;
        this.magicNumber = equalPoint / divideBy;

        lastAttack = null;

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == CardType.ATTACK) {
                lastAttack = c;
            }
        }

        // Adjust the description against play plurality, times to play,
        // and the upgrade's division point display.
        if (this.magicNumber > 0 && lastAttack != null)
        {
            if (lastAttack.target == CardTarget.ALL || lastAttack.target == CardTarget.ALL_ENEMY) {
                target = CardTarget.ALL_ENEMY;
                this.isMultiDamage = true;
            } else {
                target = CardTarget.ENEMY;
                this.isMultiDamage = false;
            }
            if (this.upgraded) {
                if (this.magicNumber == 1) {
                    this.rawDescription = (UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0] +
                                           lastAttack.name + EXTENDED_DESCRIPTION[2]);
                } else {
                    this.rawDescription = (UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0] +
                                           lastAttack.name + " " + this.magicNumber +
                                           EXTENDED_DESCRIPTION[1]);
                }
            } else {
                if (this.magicNumber == 1) {
                    this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0] +
                                           lastAttack.name + EXTENDED_DESCRIPTION[2]);
                } else {
                    this.rawDescription = (UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0] +
                                           lastAttack.name + " " + this.magicNumber +
                                           EXTENDED_DESCRIPTION[1]);
                }
            }
            initializeDescription();
        }
    }

    public void onMoveToDiscard()
    {
        if (this.upgraded) {
            this.rawDescription = UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = DESCRIPTION;
        }
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Transcension();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.upgradeSecondMagicNumber(UPGRADE_DIVISION_POINT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}