/***** CITYWORLD v1.01
* City world generator for Minecraft/Bukkit
* Copyright (C) 2011-2012 daddychurchill <http://www.virtualchurchill.com>
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.

Todo
Add command to level a city block
Subway/elevated rail roads
Terrain under the city
Park benches and other furniture

v1.01
* Fixed a default settings issue where Mountains were not being included
* Reworked the ore distribution to speed things up a bit
* Added an option for inclusion of the bedrock level lava fields

v1.00
* Treats and Tricks in the Bunkers and Mineshafts
* Reworked the sewers... you can now get seriously lost down there 
* Added IncludeSeas and IncludeMountains

v0.90
* Bunkers and Mineshafts
* Reworked the settings, you will need to wipe out your old CityWorld settings

Permissions for CityWorld command
* cityworld.command (defaults to op)

Config option file is now generated when CityWorld runs. 
These options only affect "new generation" and are not world specific (yet). 
Changing them after a world has been generated will produce some odd results. 
These all default to true and simply control the inclusion of a certain feature or not. 
* IncludeSewers
* IncludeCisterns
* IncludeBasements
* IncludeMines
* IncludeBunkers
* IncludeOres
* IncludeCaves
* IncludeBuildings
* TreasuresInSewers
* SpawnersInSewers
* TreasuresInMines
* SpawnersInMines
* TreasuresInBunkers
* SpawnersInBunkers
* WorkingLights

v0.80
* Include working terrain generation
* Bridges and Tunnels now generate
* Radio Towers
* Oil drilling platforms out where the sea is deepest
* Rural blocks are back
* Interiors are still missing 
* ISSUE: lights sometimes don't light (or do) when we want them to... seems to be a Minecraft issue

v0.7x
* Never released due to "issues"

v0.63
* 1.2.3-R0.2 Happy
* Fixed a few string and permission issues

v0.62
* 1.2.3 R0 Happy, since Bukkit doesn't support taller generated heights yet we are still stuck at 127, sorry

v0.61
* 1.1 R6 Happy
* Clay is used instead of Iron where possible
* Rooftop air conditioners now don't use End Portal Frames anymore

Config option file additions
* Global.OresInSewers (true) //put the ores into the sewer vaults
* Global.OresInUnderworld (true) //put the ores into the underworld

v0.60 
* Initial Permissions and Config file

v0.57
* Quickie patch to permit support for 1.1

v0.56
* Added occasional crane on unfinished buildings
* Hacked my way around a number of roof issues, still more that can be done
* Added nav lights onto the tallest antenna on a building, if it has any

v0.55
* Theming of city blocks (highrise, midrise, lowrise, big parks, etc.)
* First pass of roofs (peaks, edges, antennas, air conditioners, etc.)

v0.54
* West is North... I really hope I have this issue nailed this time
* Rounded buildings and doors seem to be happy at last!
* Added unfinished buildings, just to add variety

v0.52
* Rounded buildings are back but I still need to get doors and stairs working with them better
* Doors and stairs now position themselves better
* Started support for unfinished buildings

v0.51
* Turned off rounded buildings... for now

v0.50
* Added rounded buildings.. but there are loads of issues remaining to be dealt with

v0.40
* Added cisterns and most of the tops of parks
* Added the command "CityWorld", which will teleport you to (and create) "CityWorld"
* Added stairs back in (they might be missing in some buildings but that is pretty rare)
* Added doors (like stairs they might be missing in some buildings)
* Manholes are more functional (ladders and doors are down there now)
* Roundabouts now have fountains, "ART!" or a bit of both
* Got BlockPopulators working
* Cleaned up the code considerably

v0.30
* Added sewers and plumbing
* Added vaults
* Added basements
* Removed stairs (coming back in next release)
* Better road layouts
* Better building layouts

v0.20
* There is still much to do, but it is good beginning. 
* We now have a little bit of color. 
* There are basements and stairs but no doors... doh! 

v0.10
wow that is stark
*/