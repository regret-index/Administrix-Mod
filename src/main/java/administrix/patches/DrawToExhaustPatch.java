package administrix.patches;

// Coded up by Robin-MK0.5. Exhausts certain cards when drawn.

import administrix.cards.uncommon.skill.BishamonBlessing;
import administrix.cards.uncommon.skill.GuanyinBlessing;
import administrix.cards.uncommon.skill.Overdrive;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "draw",
        paramtypez =
        {
            int.class
        }
)

public class DrawToExhaustPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars={"c"}
    )
    public static void Insert(AbstractPlayer _instance, int numCards, AbstractCard c) {
        if ((c instanceof GuanyinBlessing || c instanceof BishamonBlessing) && !c.upgraded ||
             c instanceof Overdrive)
        {
            _instance.hand.moveToExhaustPile(c);
            _instance.hand.removeCard(c);
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(
                    "com.megacrit.cardcrawl.cards.CardGroup", "removeTopCard");

            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}