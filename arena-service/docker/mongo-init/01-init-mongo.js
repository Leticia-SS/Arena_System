db = db.getSiblingDB('arena_db');

db.characters.insertMany([
    {
        "name": "Yukari & Ryuto",
        "types": ["ELECTRIC"],
        "health": 11,
        "mana": 0,
        "sanity": 22,
        "speed": 21,
        "powerType": "Tamashi",
        "characterClass": "Powerhouse",
        "media": null,
        "ability": {
            "name": "Techno Suit",
            "description": "The suit has 5 Armor Tokens. Yukari does not lose Health unless all these points are depleted. Losing all tokens immediately makes Yukari Vulnerable. At the beginning of her turn, Yukari always regains 1 Armor Token. After using Charge, Yukari gains 1 Electric Token, up to 3. If she has all 3, she becomes Overcharged (OD). If Yukari is downed or depletes her Armor Tokens, she also loses all Electric Tokens."
        },
        "moveset": [
            { "name": "Charge", "activationValue": 6, "damageDice": "0", "range": "SELF", "type": "ELECTRIC", "physOrSpec": "STAT", "moveClass": "USER", "description": "Quick Action. Gather energy. Gain 1 Electric Token. Crit: Power next move. Fail: Turn Vulnerable." },
            { "name": "Overcharge", "activationValue": 15, "damageDice": "1d6", "range": "SELF", "type": "ELECTRIC", "physOrSpec": "STAT", "moveClass": "USER", "description": "Amass energy. Gain 1d4 Electric Token. OD: Counter. Deal 1d6 Shock damage in a mid radius." },
            { "name": "Unleash", "activationValue": 12, "damageDice": "0", "range": "SELF", "type": "ELECTRIC", "physOrSpec": "STAT", "moveClass": "USER", "description": "Liberate yourself for greater potential. Change to Unleashed form. Crit: Quick Action." },
            { "name": "Wrist Cannon", "activationValue": 6, "damageDice": "1d2", "range": "MID", "type": "ELECTRIC", "physOrSpec": "SPEC", "moveClass": "GADGET", "description": "Shock. Blast an electric pellet (1d2). Alt: Costs 12, blast a sphere in mid radius (1d6)." },
            { "name": "Unibeam", "activationValue": 15, "damageDice": "1d10", "range": "BREAM", "type": "ELECTRIC", "physOrSpec": "SPEC", "moveClass": "GADGET", "description": "Shock. Converge your plasma shots into a Piercing beam (1d10). Cooldown: 1d4 turns." },
            { "name": "Scan", "activationValue": 6, "damageDice": "0", "range": "SIGHT", "type": "ELECTRIC", "physOrSpec": "STAT", "moveClass": "GADGET", "description": "Detection. Run to find secrets. User turns Focused. Target is Marked. Crit: Quick Action." },
            { "name": "Pro-Flight", "activationValue": 6, "damageDice": "0", "range": "SELF", "type": "ELECTRIC", "physOrSpec": "STAT", "moveClass": "GADGET", "description": "Become Airborne until a flinch. Spend Electric Token to remain Airborne. OD: Quick Action." },
            { "name": "Surgeon Protocol", "activationValue": 9, "damageDice": "0", "range": "SELF", "type": "ELECTRIC", "physOrSpec": "STAT", "moveClass": "GADGET", "description": "Spend 1 Electric Token to gain 1 Armor Token per token spent. OD: Free Action." },
            { "name": "Tether Claw", "activationValue": 9, "damageDice": "1d2", "range": "LONG", "type": "ELECTRIC", "physOrSpec": "PHYS", "moveClass": "GADGET", "description": "Sharp. Payoff. A claw set to grapple (1d2). Spend Electric Token for an Electric-type Shock (1d4)." },
            { "name": "Capture Cubes", "activationValue": 12, "damageDice": "1d6", "range": "MID", "type": "ELECTRIC", "physOrSpec": "SPEC", "moveClass": "GADGET", "description": "Send cubes to immobilize a target. Detonate them remotely for Scorch damage (1d6)." },
            { "name": "Multimissile Blast", "activationValue": 12, "damageDice": "3d2", "range": "LONG", "type": "ELECTRIC", "physOrSpec": "SPEC", "moveClass": "GADGET", "description": "Scorch. Payoff. Deploy three homing missiles (3d2), may have different targets. Crit: 5d2." },
            { "name": "Heatlaser", "activationValue": 15, "damageDice": "1d10", "range": "MID", "type": "ELECTRIC", "physOrSpec": "SPEC", "moveClass": "GADGET", "description": "Scorch. Fire a thin laser to deal massive Piercing damage (1d10). Low priority. Erratic." },
            { "name": "Stealth Mode", "activationValue": 15, "damageDice": "0", "range": "SELF", "type": "ELECTRIC", "physOrSpec": "STAT", "moveClass": "GADGET", "description": "Turn invisible until you attack. OD: Quick Action." }
        ],
        "traits": [
            { "name": "Brainy", "description": "Whenever fighter turns Focused, they also restore 1d2 Sanity." },
            { "name": "Flying", "description": "Fighter is immune to Grounded moves, unless downed. Their turn doesn't end on moving to high ground." },
            { "name": "Mechanist", "description": "Fighter has certain interactions with props, and can repair Broken objects." }
        ],
        "finisher": "(18) All-Voltage Hyperdrive",
        "spectacle": "Sky Beam — Yukari selects an area of 5x5 radius. A massive beam of energy descends from the sky, and every target on that area takes 1d10 Shock damage."
    },
    {
        "name": "Kurusu & Tengu",
        "types": ["MYSTIC"],
        "health": 15,
        "mana": 0,
        "sanity": 14,
        "speed": 18,
        "powerType": "Tamashi",
        "characterClass": "Brawler",
        "media": null,
        "ability": {
            "name": "Sign of the Omen",
            "description": "On a Pinch, you may discard 3 Sanity to perform a Mystic Foresee as a Free Quick Action."
        },
        "moveset": [
            { "name": "Saber Slash", "activationValue": 6, "damageDice": "1d2", "range": "CLOSE", "type": "MYSTIC", "physOrSpec": "PHYS", "moveClass": "WEAPON", "description": "Sharp. Slash with your saber (1d2). Crit: Stab with the saber (1d8), Pierce." },
            { "name": "Parry", "activationValue": 12, "damageDice": "0", "range": "CLOSE", "type": "MYSTIC", "physOrSpec": "PHYS", "moveClass": "WEAPON", "description": "Counter. Fend away an attack and Riposte. Wins clash unless against Fencers." },
            { "name": "Evoke", "activationValue": 6, "damageDice": "0", "range": "CLOSE", "type": "MYSTIC", "physOrSpec": "SPEC", "moveClass": "USER", "description": "Summon your Tamashi's physical form, unlocking Tamashi moves. Crit: Quick Action." },
            { "name": "Trickster's Gaze", "activationValue": 6, "damageDice": "1d2", "range": "MID", "type": "MYSTIC", "physOrSpec": "SPEC", "moveClass": "USER", "description": "Intimidate (1d2). Glare to dissuade the weak-willed. Hit: Target Vulnerable. Crit: 1d4." },
            { "name": "Mystic Foresee", "activationValue": 9, "damageDice": "0", "range": "SELF", "type": "MYSTIC", "physOrSpec": "SPEC", "moveClass": "USER", "description": "Catch a glimpse of the future, becoming Focused. May Detect fighters. Crit: Quick Action." },
            { "name": "Cloak of Disguise", "activationValue": 12, "damageDice": "1d2", "range": "CLOSE", "type": "MYSTIC", "physOrSpec": "SPEC", "moveClass": "USER", "description": "Counter. Flap your cape. If successful, attackers turn Vulnerable. Crit: Deal 1d2 damage." },
            { "name": "Energy Blast", "activationValue": 12, "damageDice": "1d4", "range": "CONTACT", "type": "MYSTIC", "physOrSpec": "SPEC", "moveClass": "USER", "description": "Blunt. Liberate conductible mystic energy (1d4). If Health <= 8, d6. If Health <= 5, d8." },
            { "name": "Med Spray", "activationValue": 9, "damageDice": "1d2", "range": "CONTACT", "type": "MYSTIC", "physOrSpec": "STAT", "moveClass": "GADGET", "description": "A remedy to mend skin (1d2). Cures Bleeding. Crit: 1d4, Quick Action." },
            { "name": "Grappling Hook", "activationValue": 9, "damageDice": "1d2", "range": "LONG", "type": "MYSTIC", "physOrSpec": "PHYS", "moveClass": "GADGET", "description": "Move with ease through the arena, or Hook a target (1d2). Hit: Combo Energy Blast." },
            { "name": "Fan Strike", "activationValue": 6, "damageDice": "1d2", "range": "CLOSE", "type": "MYSTIC", "physOrSpec": "PHYS", "moveClass": "TAMASHI", "description": "Blunt. Tengu strikes with its fan (1d2). Hit: Combo Fan Blow. Crit: Target is Vulnerable." },
            { "name": "Fan Pull", "activationValue": 9, "damageDice": "0", "range": "MID", "type": "MYSTIC", "physOrSpec": "STAT", "moveClass": "TAMASHI", "description": "Quick Action. Tengu's fan draws nearer all targets in a cone. Crit: Targets are Vulnerable." },
            { "name": "Fan Blow", "activationValue": 12, "damageDice": "1d4", "range": "MID", "type": "MYSTIC", "physOrSpec": "PHYS", "moveClass": "TAMASHI", "description": "Tengu's fan pushes targets in a cone. Blunt against Vulnerable (1d4). Crit: Push further." },
            { "name": "Flight Trick", "activationValue": 15, "damageDice": "1d10", "range": "DASH", "type": "MYSTIC", "physOrSpec": "PHYS", "moveClass": "TAMASHI", "description": "Blunt. Tengu rushes unexpectedly (1d10). High priority. Sudden. Predictable after hit." }
        ],
        "traits": [
            { "name": "Acrobat", "description": "Fighter can Dreamscape jump as a Quick Action, and their turn doesn't end on moving to high ground." },
            { "name": "Agile", "description": "Fighter may choose to Sprint as a Quick Action. If done so, the move becomes Erratic." },
            { "name": "Meditator", "description": "As a Free Action, fighter can Concentrate to restore 1d2 Sanity per turn. While so, fighter is Vulnerable." }
        ],
        "finisher": "(18) Energy Overload",
        "spectacle": "High-Heavy Crash — In a Mid range, Kurusu summons Tengu to fan a target high in the air. Then, he fires his grappling hook at them (1d2) and forces them back to the ground (1d6), downing them."
    }
]);