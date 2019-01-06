package administrix.cards.rare.skill;

import administrix.cards.AbstractAdministrixCard;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import administrix.AdministrixMod;
import administrix.patches.AbstractCardEnum;

public class HopeInTheDark extends AbstractAdministrixCard
{
    public static final String ID = "AdministrixMod:HopeInTheDark";
    public static final String NAME = "Hope in the Dark";
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = CARD_STRINGS.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int FAKE_YIN_SCALE = 0;
    private static final int YANG_SCALE = 1;
    private static final int UPGRADE_YANG_SCALE = 5;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;

    public HopeInTheDark() {
        super(ID, CARD_STRINGS.NAME, AdministrixMod.HOPE_IN_THE_DARK, COST,
                CARD_STRINGS.DESCRIPTION, type,
                AbstractCardEnum.LichGold,
                rarity, target);
        this.baseBlock = 0;
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        // Give block scaled to how much Yang you have more of than Yin.
        int yinAmount = p.hasPower("Yin") ?
                p.getPower("Yin").amount : 0;
        int yangAmount = p.hasPower("Yang") ?
                p.getPower("Yang").amount : 0;
        int difference = yinAmount - yangAmount;
        double multiplier = (this.upgraded) ? 1.5 : 1.0;

        this.baseBlock = (int) Math.floor((difference > 0) ? difference * multiplier : 0);

        if (difference > 0) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.BLACK, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.2F));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_FIRE"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.PURPLE, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.33F));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }

        AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(AbstractDungeon.player));
    }

    public void applyPowers()
    {
        int yinAmount = AbstractDungeon.player.hasPower("Yin") ?
                        AbstractDungeon.player.getPower("Yin").amount : 0;
        int yangAmount = AbstractDungeon.player.hasPower("Yang") ?
                         AbstractDungeon.player.getPower("Yang").amount : 0;
        int difference = yinAmount - yangAmount;
        double multiplier = (this.upgraded) ? 1.0 : 1.5;

        this.baseBlock = (int) Math.floor((difference > 0) ? difference * multiplier : 0);
        super.applyPowers();

        if (this.baseBlock > 0)
        {
            if (this.upgraded) {
                this.rawDescription = (UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0]);
            } else {
                this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
            }
            initializeDescription();
        }
    }

    public void onMoveToDiscard()
    {
        if (this.upgraded) {
            this.rawDescription = (UPGRADE_DESCRIPTION);
        } else {
            this.rawDescription = (DESCRIPTION);
        }
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new HopeInTheDark();
    }

    @Override
    public void upgrade() {
        if (!upgraded)
        {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}