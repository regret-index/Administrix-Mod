package administrix.patches;

// This here basically replicates the Snecko Skull case to
// make gaining Yin or Yang add a bit more with the Dragon Charm.

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = ApplyPowerAction.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez =
                {
                        AbstractCreature.class,
                        AbstractCreature.class,
                        AbstractPower.class,
                        int.class,
                        boolean.class,
                        AbstractGameAction.AttackEffect.class
                }
)

public class DragonsEyePatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars={"powerToApply"}
    )
    public static void Insert(ApplyPowerAction _instance, AbstractCreature target, AbstractCreature source, AbstractPower _powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect, AbstractPower powerToApply)
    {
        if (AbstractDungeon.player.hasRelic("AdministrixMod:DragonsEyeCharm") && source != null && source.isPlayer && (powerToApply.ID.equals("AdministrixMod:Yin") || powerToApply.ID.equals("AdministrixMod:Yang")))
        {
            AbstractDungeon.player.getRelic("AdministrixMod:DragonsEyeCharm").flash();
            powerToApply.amount += 3;
            _instance.amount += 3;
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(
                    "com.megacrit.cardcrawl.monsters.MonsterGroup", "areMonstersBasicallyDead");

            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}