## ActualOpto Project

## author: Pdawg53

## Room Marvin sucks ass

## Simple app that takes in a player's username, a pvm boss, a gp budget and outputs the best setup for that cost


## Player's username? for stats ==> how will I pull stats? maybe some osrs api? https://secure.runescape.com/m=hiscore_oldschool/index_lite.ws?player={username}

## PVM boss? will be used to lookup boss, stats, etc. (modified greedy algo) https://runescape.wiki/w/RuneScape_Bestiary#Finding_monster_stats

## GP budget will be used with GE Price lookup. API? https://prices.runescape.wiki/api/v1/osrs

## Player Stats: Users input their username; real-time Hiscores API call fetches stats.

## GE Data: Scheduler (scheduler() and runOneIteration()) updates gear prices in the DB every 24 hours.

## Boss Selection: Users input a boss name; app looks up its stats/weaknesses (now via a scheduled DB update instead of static data). weakness not always explicit

## Boss Stats Scheduler: New component to fetch boss stats monthly and store them in the DB.

## Read-Only App: Still only reads from the DB; schedulers handle writes.






