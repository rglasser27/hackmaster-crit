# Hackmaster Crit Table

Hackmaster is a sort of "joke" role playing game that is designed to be as overly complicated as possible.
When you attack somebody, there are a crazy number of combinations of ways that you can hit.

* You can do hacking, slashing or crushing damage depending on your weapon type.
* You can hit on any part of the body, resolved by a roll of up to 10,000.
* If you roll high enough to score a critical hit, you can roll again. If you get a second critical hit, it does
much more damage. If you get a third critical hit, it's almost a guaranteed kill (depending on body part).
* This is all resolved through a manual which, all by itself, is 170 pages long, full of tables.

I've never played this game. It seems too ridiculous to play. But it's a _great_ computer science project.

## Running the application

This is a Spring Boot application. You will need to use Maven to load in the appropriate libraries, and then
run HackmasterApplication, which is the main Java class.

Once you have it up and running on a port of your choice (i.e., 8080), you should be able to open a web browser and load
http://localhost:8080/hackmaster. This is a REST endpoint. There are three parameters you can pass in:

* "type": Must be a string value containing "HACKING", "CRUSHING", or "PIERCING" (defined in AttackType.java)
* "part": A number from 1-10000, which resolves to a part of the body (see "hacking1.csv" or either of the other
2 files for resolution of body parts)
* "severity": A number from 1-24, representing the amount of damage you did.

### Randomization

You can supply any of the three parameters or leave any of them out. If you do not provide all parameters, a random
number in the appropriate range will be generated.

### Sample calls

Request:
```
http://localhost:8080/hackmaster?&type=HACKING&severity=1&part=1
``` 

Response:
```json
{
  "severity": 1,
  "result": {
    "type": "HACKING",
    "bodyPart": {
      "id": 1,
      "lowRoll": 1,
      "highRoll": 100,
      "name": "Foot, top"
    },
    "effects": [
      {
        "key": "1",
        "description": "take 1 damage"
      }
    ]
  },
  "part": 1
}
```

Request:
```
http://localhost:8080/hackmaster?&type=CRUSHING&part=3200&severity=24
``` 

Response:
```json
{
  "severity": 24,
  "result": {
    "type": "CRUSHING",
    "bodyPart": {
      "id": 22,
      "lowRoll": 3156,
      "highRoll": 3425,
      "name": "Chest"
    },
    "effects": [
      {
        "key": "body cavity crushed (dead)",
        "description": "body cavity crushed (dead)"
      }
    ]
  },
  "part": 3200
}
```


## Code reference

You can find the tables  &mdash; translated from the manual linked below &mdash; as CSV files in the folder src/resources/data. 

## References:
* My blog posts about this: [Operation HackMaster Crit Tables, episode 1](http://castlesofair.blogspot.com/2011/09/operation-hackmaster-crit-tables.html);
[Episode 2](http://castlesofair.blogspot.com/2011/09/operation-hackmaster-crit-tables_19.html)
* [Critical hit booklet](https://www.kenzerco.com/hackmaster/downloads/Crit_Hit_Booklet.pdf) on the
official Hackmaster site.
