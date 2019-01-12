The _Administrix_, the lich princess of the rising sun,
is inspired by Toyosatomimi no Miko (豊聡耳 神子),
a character from the _Touhou Project_ series.

Latest releases can be found [here](https://github.com/regret-index/Administrix-Mod/releases). The versions changelog can be found [here](https://github.com/regret-index/Administrix-Mod/blob/master/changelog.txt).

--== Credits ==--
======

 * index-j: Content code, endless playtesting and debugging
 * Robin-MK0.5: Card edit art, playtesting, advanced feature debugging,
   constant support

 * Twilight Frontier: Official art assets for character art.
 * ZUN: For creating the Touhou Project.
 * Megacrit: For creating Slay the Spire.

--== Instructions ==--
====== 

As with the other mods on the Steam Workshop, this mod requires subscribing to
both ModTheSpire and BaseMod first. 

For those using the older methods, the standard instructions still go as
follows:

 * Install ModTheSpire, if you haven't already.
   Latest release was available [here](https://github.com/kiooeht/ModTheSpire/releases).
   Drop off the .jar in one's local directory for Slay The Spire,
   then create a mods folder in that same directory.
 * Install BaseMod, if you haven't already.
   Latest release was available [here](https://github.com/daviscook477/BaseMod/releases).
   Drop off the .jar in the /mods/ folder.
 * Install this mod, by dropping off the .jar in the same folder.
 * Activate ModTheSpire (or MTS.CMD), then activate both the BaseMod
   and The Administrix in the menu before playing.

--== Summation ==--
======

This tricky card set focuses on themes of duality, synergy, and deck 
manipulation:

 * cards with effects on both drawing and discarding them,
 * many sources of Artifact to grasp onto transient power
   or brush off toxic side-effects,
 * and a manipulable duo of card fuel in Yin and Yang.
 
Side-themes include:

 * caring about card types played,
 * flexible card creation,
 * and swapping cards between one's hand, the Draw Pile, and the Discard Pile.

This v0.8 release contains 70 character-unique cards of a total planned 80,
plus four relics out of a total planned ten and one potion of two.

Design goals have been to make an internally consistent character, with decently
reliable representation and support for multiple interwoven deck archetypes
(Yin-focus vs Yang-focus vs balance; Artifact abuse; Plot abuse).
The Administrix readily uses multi-function cards plus flexible and enduring
defenses to constantly scale up from weaker base cards; playing her can start
out slow in understanding rather full cards, but she has many ways to gain many
buffs as she edges ever closer to becoming a legend.

--== New keywords ==--
======

For those who care about such, card spoilers follow here. This character's list
of keywords is definitely long and complicated enough to deserve this full
write-up, though, as a reference point. This is on the Github page too, after
all. (Note that spreadsheet links and copies of the StS extractor are attached
to each release, for those who wish to spoil themselves completely.)

==== __Plot__ ====

 * Cards with this have a __Plot__ effect on drawing them, the same effect on
   manually discarding them, and an entirely different effect on playing them.
   These are primarily defensive, in contrast to the Silent's utility focus
   and the Servant's offensive focus, though strong counterexamples still exist.
   Two 0-cost cards in the starting deck draw and discard on play, plus apply
   debuff effects through Plot.

== __Transpose__ ==

 * To __Transpose__ is to discard a card from the Draw Pile, then move a
   different card from the Discard Pile to the bottom of the Draw Pile. The
   starting relic provides a constant source of this, and so do a slim number
   of other cards. This triggers Plot effects too, though it's a trade of
   actively choosing such versus being able to trigger such later or twice whenever
   drawn.

==== __Duality__ ====

 * __Duality__ provides temporary Strength and Dexterity after each turn
   one plays an equal number of Attacks and Skills (eventually with a unique UI
   to help keep track). Do remember, temporary stats are executed as a stacking
   debuff that applies another debuff to return to normal at the end of the
   turn: try to gather much of it before gaining Artifact to keep it all.

== __Wilting__ ==

 * __Wilting__ is a self-inflicted debuff that (blockably) damages the player
   whenever they play a card. It is joined by Frail and two variations of Draw
   Reduction as the self-debuffs in this card set, and also vies for the use of
   one's own Artifact- transition rituals take a toll on the body, after all.

==== __Yin, Yang__ ====

 * These two buffs do nothing by themselves. __Yin and Yang__ are incidentally
   provided by many cards, provide strong scaling for yet more cards, and
   slowly equalize each turn. (This'll also kept on the card-count UI readied
   for Duality- the Administrix has absurd numbers of buffs to keep track of.)

== __Affinity__ ==

 * One reocurring use of Yin and Yang provides a benefit for focusing on one or
   the other. With __Affinity__, if you have more Yang than Yin, then whenever
   you gain Yang you also gain Block. Alternatively, if you have more Yin than
   Yang, then whenever you gain Yin, you also deal damage to all enemies twice.
   A card in the starting deck provides little beyond a source of this;
   it balances out the Plot cards in the starting deck.

== __Daybreaks, Nightfalls__ ==

 * These are 0-cost weak and Ethereal cards that Exhaust when played.
   __Daybreaks__ deal 3 (5) damage and also provide Yang, while __Nightfalls__ gain
   3 (5) block and also provide Yin. These scale strongly to Strength and 
   Dexterity gain, can be played or ignored to balance for Duality and Affinity,
   plus readily help trigger the starting relic's conditions- these are well 
   beyond Shivs. Be careful with playing these against Time Eater and the Heart!

...

--== Known Issues ==--
======

 * Playing cards too quickly can sometimes have tooltips or targetting arrows
   mess up the Transpose interface.
 * Both Blessings lack part of the Plot card indicator.
 * Schism+ and Newborn Divinity+ squish their descriptions against a fraction
   of the card text box.
 * The in-game character statistics page prints a blank listing for the 
   character name.

--== Full Release Plans ==--
======

 * Quick mechanics reference + tutorial guide.
 * Balance adjustments as according to feedback.
 * The remaining 10/80 cards (more pure-value commons and supports for 
   muddled Y / Y decks, alongside lingering concepts) 7/11 relics for a full 
   character set (boss ones...), and another potion (a Plot focus?)
 * A custom UI panel, constantly depicting card types played for Duality
   (instead of the current placeholder relic-based UI) plus tracking Yin & Yang
   in a fashion seperated from Miko's endless power stacks.
 * Further effects improvements. Character animation, the character select 
   screen, Wuji, some self-debuff cards, and the plainer Rare cards are all 
   intended targets. Some secret effects against bosses, too, perhaps.
 * A Simplified Chinese Translation has been offered up, and is being readied
   for.

...

Thanks for reading all of this, if you did!