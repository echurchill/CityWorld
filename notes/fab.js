// $Id$
/***** FAB.JS v1.0
* City block generator CraftScript for WorldEdit
* (parts of this is based on WorldEdit's sample Maze generator)
* Copyright (C) 2011 echurch <http://www.virtualchurchill.com>
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
*/

importPackage(Packages.java.io);
importPackage(Packages.java.awt);
importPackage(Packages.com.sk89q.worldedit);
importPackage(Packages.com.sk89q.worldedit.blocks);
importPackage(Packages.com.sk89q.worldedit.regions);
importPackage(Packages.com.sk89q.worldedit.util);

// for future reference
var editsess = context.remember();
var world = editsess.getWorld();
var session = context.getSession();
var origin = player.getBlockIn();
var rand = new java.util.Random();
var originOffsetX = 0;
var originOffsetZ = 0;

// stats
var blocksTotal = 0;
var blocksSet = 0;

// what do you want to create
var helpString = "[HIGHRISE | MIDRISE | LOWRISE | TOWN | PARK | DIRTLOT | PARKINGLOT | JUSTSTREETS | FARM | FARMHOUSE | HOUSES] [HELP] [RANDOM] [FIRSTTIME]"
context.checkArgs(0, -1, helpString);
var createMode = { "HIGHRISE": 0, "MIDRISE": 1, "LOWRISE": 2, "TOWN": 3,
    "PARK": 4, "DIRTLOT": 5, "PARKINGLOT": 6, "JUSTSTREETS": 7,
    "FARM": 8, "FARMHOUSE": 9, "HOUSES": 10, "HELP": -1
};

// got to default with something
var modeCreate = createMode.MIDRISE;
var floorRatio = 0.66;
var offsetX = 0;
var offsetZ = 0;
var randSeed = false;
var firstTime = false;

// parse those args! last create mode wins!
for (var i = 1; i < argv.length; i++) {
    var arg = argv[i];

    // look for longest params first, that way FARMHOUSE can be found instead of FARM
    if (/JUSTSTREET/i.test(arg))        // also JUSTSTREETS
        modeCreate = createMode.JUSTSTREETS;
    else if (/PARKING/i.test(arg))      // also PARKINGLOT
        modeCreate = createMode.PARKINGLOT
    else if (/FARMHOUSE/i.test(arg))
        modeCreate = createMode.FARMHOUSE
    else if (/HIGHRISE/i.test(arg))
        modeCreate = createMode.HIGHRISE
    else if (/MIDRISE/i.test(arg))
        modeCreate = createMode.MIDRISE
    else if (/LOWRISE/i.test(arg))
        modeCreate = createMode.LOWRISE
    else if (/HOUSE/i.test(arg))        // also HOUSES
        modeCreate = createMode.HOUSES
    else if (/DIRT/i.test(arg))         // also DIRTLOT
        modeCreate = createMode.DIRTLOT
    else if (/FARM/i.test(arg))
        modeCreate = createMode.FARM
    else if (/TOWN/i.test(arg))
        modeCreate = createMode.TOWN
    else if (/PARK/i.test(arg))
        modeCreate = createMode.PARK

    else if (/RANDOM/i.test(arg))
        randSeed = true

    else if (/FIRSTTIME/i.test(arg))      
        firstTime = true

    else if (/HELP/i.test(arg))
        modeCreate = createMode.HELP;
}

//var config = context.getConfiguration;
//context.print(config);
//context.print(config.scriptTimeout);

// some constants
var squareBlocks = 15;
var cornerWidth = squareBlocks / 3;
var lotBlocks = ((squareBlocks * 3) - 1) / 2;
var linesOffset = Math.floor(squareBlocks / 2);
var squaresWidth = 5;
var squaresLength = 5;
var plumbingHeight = 5;
var sewerHeight = 4;
var streetHeight = 2;
var floorHeight = 4;
var roofHeight = 1; // extra bit for roof fluff like railings

// say hello!
context.print(argv[0] + " v1.0a");

// see if we can find the offset
if (!FindOriginOffset()) {
    
    // we can't, go anyway?
    if (firstTime) {
        originOffsetX = -linesOffset;
        originOffsetZ = -linesOffset;

    // if not tell the user what happened
    } else {
        context.print("ERROR: Cannot find the crossroads.");
        context.print(" Either walk to a crossroad or...")
        context.print(" use FIRSTTIME option to force creation from this spot.");
        modeCreate = createMode.HELP;
    }
} else
    firstTime = true;

// show help
if (modeCreate == createMode.HELP)
    context.print("USAGE: " + argv[0] + " " + helpString);

// check the timeout
//else if (context.getConfiguration.scriptTimeout < 10000)
//    context.print("ERROR: You need to increase your script timeout, see readme")

// otherwise let's do something!
else {

    // stair direction
    var Ascending_South = 0;
    var Ascending_North = 1;
    var Ascending_West = 2;
    var Ascending_East = 3;

    // extended BlockID types
    var ExtendedID = {
        "NORTHWARD_LADDER": EncodeBlock(BlockID.LADDER, 4),
        "EASTWARD_LADDER": EncodeBlock(BlockID.LADDER, 2),
        "SOUTHWARD_LADDER": EncodeBlock(BlockID.LADDER, 5),
        "WESTWARD_LADDER": EncodeBlock(BlockID.LADDER, 3),

        "NORTHFACING_WOODEN_DOOR": EncodeBlock(BlockID.WOODEN_DOOR, 0),
        "EASTFACING_WOODEN_DOOR": EncodeBlock(BlockID.WOODEN_DOOR, 1),
        "SOUTHFACING_WOODEN_DOOR": EncodeBlock(BlockID.WOODEN_DOOR, 2),
        "WESTFACING_WOODEN_DOOR": EncodeBlock(BlockID.WOODEN_DOOR, 3),

        "NORTHFACING_REVERSED_WOODEN_DOOR": EncodeBlock(BlockID.WOODEN_DOOR, 3 + 4),
        "EASTFACING_REVERSED_WOODEN_DOOR": EncodeBlock(BlockID.WOODEN_DOOR, 0 + 4),
        "SOUTHFACING_REVERSED_WOODEN_DOOR": EncodeBlock(BlockID.WOODEN_DOOR, 1 + 4),
        "WESTFACING_REVERSED_WOODEN_DOOR": EncodeBlock(BlockID.WOODEN_DOOR, 2 + 4),

        "NORTHFACING_TRAP_DOOR": EncodeBlock(BlockID.TRAP_DOOR, 2),
        "EASTFACING_TRAP_DOOR": EncodeBlock(BlockID.TRAP_DOOR, 0),
        "SOUTHFACING_TRAP_DOOR": EncodeBlock(BlockID.TRAP_DOOR, 3),
        "WESTFACING_TRAP_DOOR": EncodeBlock(BlockID.TRAP_DOOR, 1),

        "WHITE_CLOTH": EncodeBlock(BlockID.CLOTH, 0),
        "ORANGE_CLOTH": EncodeBlock(BlockID.CLOTH, 1),
        "MAGENTA_CLOTH": EncodeBlock(BlockID.CLOTH, 2),
        "LIGHT_BLUE_CLOTH": EncodeBlock(BlockID.CLOTH, 3),
        "YELLOW_CLOTH": EncodeBlock(BlockID.CLOTH, 4),
        "LIGHT_GREEN_CLOTH": EncodeBlock(BlockID.CLOTH, 5),
        "PINK_CLOTH": EncodeBlock(BlockID.CLOTH, 6),
        "GRAY_CLOTH": EncodeBlock(BlockID.CLOTH, 7),
        "LIGHT_GRAY_CLOTH": EncodeBlock(BlockID.CLOTH, 8),
        "CYAN_CLOTH": EncodeBlock(BlockID.CLOTH, 9),
        "PURPLE_CLOTH": EncodeBlock(BlockID.CLOTH, 10),
        "BLUE_CLOTH": EncodeBlock(BlockID.CLOTH, 11),
        "BROWN_CLOTH": EncodeBlock(BlockID.CLOTH, 12),
        "DARK_GREEN_CLOTH": EncodeBlock(BlockID.CLOTH, 13),
        "RED_CLOTH": EncodeBlock(BlockID.CLOTH, 14),
        "BLACK_CLOTH": EncodeBlock(BlockID.CLOTH, 15),

        "STONE_STEP": EncodeBlock(BlockID.STEP, 0),
        "SANDSTONE_STEP": EncodeBlock(BlockID.STEP, 1),
        "WOOD_STEP": EncodeBlock(BlockID.STEP, 2),
        "COBBLESTONE_STEP": EncodeBlock(BlockID.STEP, 3),

        "STONE_DOUBLESTEP": EncodeBlock(BlockID.DOUBLE_STEP, 0),
        "SANDSTONE_DOUBLESTEP": EncodeBlock(BlockID.DOUBLE_STEP, 1),
        "WOOD_DOUBLESTEP": EncodeBlock(BlockID.DOUBLE_STEP, 2),
        "COBBLESTONE_DOUBLESTEP": EncodeBlock(BlockID.DOUBLE_STEP, 3),

        "SOUTHASCENDING_WOODEN_STAIRS": EncodeBlock(BlockID.WOODEN_STAIRS, Ascending_South),
        "NORTHASCENDING_WOODEN_STAIRS": EncodeBlock(BlockID.WOODEN_STAIRS, Ascending_North),
        "WESTASCENDING_WOODEN_STAIRS": EncodeBlock(BlockID.WOODEN_STAIRS, Ascending_West),
        "EASTASCENDING_WOODEN_STAIRS": EncodeBlock(BlockID.WOODEN_STAIRS, Ascending_East),

        "SOUTHASCENDING_COBBLESTONE_STAIRS": EncodeBlock(BlockID.COBBLESTONE_STAIRS, Ascending_South),
        "NORTHASCENDING_COBBLESTONE_STAIRS": EncodeBlock(BlockID.COBBLESTONE_STAIRS, Ascending_North),
        "WESTASCENDING_COBBLESTONE_STAIRS": EncodeBlock(BlockID.COBBLESTONE_STAIRS, Ascending_West),
        "EASTASCENDING_COBBLESTONE_STAIRS": EncodeBlock(BlockID.COBBLESTONE_STAIRS, Ascending_East)

    };

    // calculate the floor height ratio
    var floorRatio = 0.66;
    switch (modeCreate) {
        case createMode.HIGHRISE:
            floorRatio = 1.00;
            break;
        case createMode.MIDRISE:
            floorRatio = 0.66;
            break;
        case createMode.LOWRISE:
            floorRatio = 0.33;
            break;
        case createMode.TOWN:
            floorRatio = 0.25;
            break;
        default:
            break;
    }

    // derived pseudo-constants
    var skyHigh = Math.floor(127 - origin.y);
    var belowGround = plumbingHeight + sewerHeight + streetHeight - 1;
    var cornerBlocks = squareBlocks / 3;
    var sewerFloor = plumbingHeight;
    var sewerCeiling = sewerFloor + sewerHeight - 1;
    var streetLevel = sewerCeiling + streetHeight - 1;
    var floorOverhead = belowGround + streetHeight + roofHeight;
    var floorCount = Math.floor(((127 - origin.y) * floorRatio - roofHeight) / floorHeight);
    if (floorCount < 1)
        floorCount = 1;

    // how large of an area are we building?
    var arrayWidth = squaresWidth * squareBlocks;
    var arrayDepth = squaresLength * squareBlocks;
    var arrayHeight = floorOverhead + skyHigh;

    // what direction are we facing?
    var yaw = FindYaw();
    if (yaw >= 0 && yaw < 90) {
        offsetX = -1;
        offsetZ = -1;
    } else if (yaw >= 90 && yaw < 180) {
        offsetX = 0;
        offsetZ = -1;
    } else if (yaw >= 180 && yaw < 270) {
        offsetX = 0;
        offsetZ = 0;
    } else {
        offsetX = -1;
        offsetZ = 0;
    }

    // move the offset if needed
    origin = origin.add(offsetX * (squaresWidth - 1) * squareBlocks,
                        0,
                        offsetZ * (squaresLength - 1) * squareBlocks);

    // offset the start so we are standing in the middle of the road near the manhole
    origin = origin.add(originOffsetX, -belowGround, originOffsetZ);

    // is random based on where we are?
    if (!randSeed) {
        var blockSeed = Math.floor(origin.x);
        blockSeed = blockSeed * 65536 + Math.floor(origin.y);
        blockSeed = blockSeed * 65536 + Math.floor(origin.z);
        rand.setSeed(blockSeed);
    }

    // making room to create
    var blocks = new Array(arrayWidth);
    InitializeBlocks();
    InitializeFixups();
    InitializeTrees();

    // add plumbing level (based on Maze.js from WorldEdit)
    AddPlumbingLevel();

    // add streets
    AddStreets();

    // add the inside bits
    switch (modeCreate) {
        case createMode.HIGHRISE:
        case createMode.MIDRISE:
        case createMode.LOWRISE:
        case createMode.TOWN:
            AddCitySquares();
            break;
        case createMode.PARK:
            AddParkLot();
            break;
        case createMode.FARMHOUSE:
        case createMode.FARM:
        case createMode.HOUSES:
            AddFarmAndHousesLot();
            break;
        case createMode.DIRTLOT:
            AddDirtLot();
            break;
        case createMode.PARKINGLOT:
            AddParkingLot();
            break;
        default: // createMode.JUSTSTEETS
            AddJustStreets();
            break;
    }

    // add access points (will modify the player "origin" correctly as well)
    AddManholes();

    // and we are nearly done
    TranscribeBlocks();

    // finally fix the things that need to be fixed up
    FinalizeColumns();
    FinalizeTrees();
    FinalizeFixups();

    // clean up any left over detritus
    world.removeEntities(EntityType.ITEMS,
        origin.add(Math.floor(2.5 * squareBlocks), 0, Math.floor(2.5 * squareBlocks)),
        4 * squareBlocks);

    // poof, we are done!
    //context.print("fini " + blocksSet + " of " + blocksTotal + " blocks placed");
    context.print("fini");
}

////////////////////////////////////////////////////
// all the supporting bits
////////////////////////////////////////////////////

function EncodeBlock(type, data) {
    //context.print(type + " " + data);
    return (data << 8) | type;
}

function DecodeID(block) {
    return block & 0xFF;
}

function DecodeData(block) {
    return block >> 8;
}

function InitializeBlocks() {
    //context.print(arrayWidth + " " + arrayHeight + " " + arrayDepth);
    context.print("Initializing");
    for (var x = 0; x < arrayWidth; x++) {
        blocks[x] = new Array(arrayHeight);
        for (var y = 0; y < arrayHeight; y++) {
            blocks[x][y] = new Array(arrayDepth);
            for (var z = 0; z < arrayDepth; z++)
                blocks[x][y][z] = BlockID.AIR;
        }
    }
}

// etch our array of ints into the "real" world
function TranscribeBlocks() {
    context.print("Transcribing");
    var newblock;
    for (x = 0; x < arrayWidth; x++) {
        for (var y = 0; y < arrayHeight; y++) {
            for (var z = 0; z < arrayDepth; z++) {

                // decode the new block
                newblock = blocks[x][y][z];
                SetBlockIfNeeded(origin.add(x, y, z),
                                 DecodeID(newblock), DecodeData(newblock), true);
            }
        }
    }
}

function SetBlockIfNeeded(at, blockID, blockData, force) {
    var oldBlock = editsess.rawGetBlock(at);
    var oldID = oldBlock.getType();
    blocksTotal++;

    // if it isn't the same then set it!
    if (oldID != blockID || oldBlock.getData() != blockData) {

        // do force our way or only do so if there is air there?
        if (force || oldID == BlockID.AIR) {
            editsess.rawSetBlock(at, new BaseBlock(blockID, blockData));
            blocksSet++;
        }
    }
}

function FindYaw() {
    var yaw = (player.getYaw() - 90) % 360;
    if (yaw < 0)
        yaw += 360;
    return yaw;
}

function FindOriginOffset() {
    var deltaBad = 999;
    var deltaKeepLooking = 0;
    var deltaNorth = deltaKeepLooking;
    var deltaSouth = deltaKeepLooking;
    var deltaEast = deltaKeepLooking;
    var deltaWest = deltaKeepLooking;

    // look around
    for (var a = 0; a < 12; a++) {
        deltaNorth = BlockTest(deltaNorth, a, 0, a);
        deltaSouth = BlockTest(deltaSouth, -a, 0, a);
        deltaEast = BlockTest(deltaEast, 0, a, a);
        deltaWest = BlockTest(deltaWest, 0, -a, a);
    }

    // patch the offsets
    if (deltaNorth + deltaSouth == 10 && deltaEast + deltaWest == 10) {
        originOffsetX = -(deltaSouth + 2);
        originOffsetZ = -(deltaWest + 2);
        return true;
    } else {
        return false;
    }

    function BlockTest(deltaValue, atX, atZ, value) {
        if (deltaValue == deltaKeepLooking) {
            switch (editsess.rawGetBlock(origin.add(atX, -1, atZ)).getType()) {
                case BlockID.CLOTH:
                    return value;
                case BlockID.STONE:
                case BlockID.LADDER: 
                case BlockID.AIR: // just in case a creeper exploded nearby
                    return deltaKeepLooking;
                default:
                    return deltaBad;
            }
        } else
            return deltaValue;
    }
}

// need to standarize on one param style, these five are not consistent!
function AddWalls(blockID, minX, minY, minZ, maxX, maxY, maxZ) {
    for (var x = minX; x <= maxX; x++)
        for (var y = minY; y <= maxY; y++) {
            blocks[x][y][minZ] = blockID;
            blocks[x][y][maxZ] = blockID;
        }

    for (var y = minY; y <= maxY; y++)
        for (var z = minZ; z <= maxZ; z++) {
            blocks[minX][y][z] = blockID;
            blocks[maxX][y][z] = blockID;
        }
}

function FillCube(blockID, minX, minY, minZ, maxX, maxY, maxZ) {
    for (var x = minX; x <= maxX; x++)
        for (var y = minY; y <= maxY; y++)
            for (var z = minZ; z <= maxZ; z++)
                blocks[x][y][z] = blockID;
}

function FillAtLayer(blockID, blockX, blockZ, atY, layerW, layerL) {
    FillCube(blockID, blockX, atY, blockZ,
        blockX + layerW - 1, atY, blockZ + layerL - 1);
}

function FillCellLayer(blockID, blockX, blockZ, atY, cellW, cellL) {
    FillCube(blockID, blockX, atY, blockZ,
        blockX + cellW * squareBlocks - 1, atY, blockZ + cellL * squareBlocks - 1);
}

function FillStrataLayer(blockID, at) {
    FillAtLayer(blockID, 0, 0, at, arrayWidth, arrayDepth);
}

////////////////////////////////////////////////////
// random instances of random
////////////////////////////////////////////////////

function OneInTwoChance() { return rand.nextInt(2) == 0 }
function OneInThreeChance() { return rand.nextInt(3) == 0 }
function OneInFourChance() { return rand.nextInt(4) == 0 }
function OneInNChance(N) { return rand.nextInt(N) == 0 }

////////////////////////////////////////////////////
// fix up logic for blocks
////////////////////////////////////////////////////

var fixups;

function InitializeFixups() {
    fixups = new Array();
}

function FinalizeFixups() {
    if (fixups.length > 0) {
        context.print("Placing special items");
        for (i = 0; i < fixups.length; i++)
            fixups[i].setBlock(origin);
    }
}

function SetLateBlock(atX, atY, atZ, id) {
    SetLateBlockEx(atX, atY, atZ, id, false);
}

function SetLateGroundBlock(atX, atY, atZ, id) {
    SetLateBlockEx(atX, atY, atZ, id, true);
}

function SetLateBlockEx(atX, atY, atZ, id, groundCheck) {
    // make sure there is room
    blocks[atX][atY][atZ] = BlockID.AIR;

    // handle any "more than one cube" items
    if (DecodeID(id) == BlockID.WOODEN_DOOR)
        blocks[atX][atY + 1][atZ] = BlockID.AIR;

    // keep a note of things to go
    fixups.push(new LateItem(atX, atY, atZ, id, groundCheck));
}

function LateItem(atX, atY, atZ, id, groundCheck) {
    this.blockId = id;
    this.blockX = atX
    this.blockY = atY
    this.blockZ = atZ;
    this.groundCheck = groundCheck;

    this.setBlock = function (origin) {
        if (!this.groundCheck || blocks[this.blockX][this.blockY - 1][this.blockZ] != BlockID.AIR) {
            var id = DecodeID(this.blockId);
            var data = DecodeData(this.blockId);
            var at = origin.add(this.blockX, this.blockY, this.blockZ);

            SetBlockIfNeeded(at, id, data, false);
            if (id == BlockID.WOODEN_DOOR)
                SetBlockIfNeeded(at.add(0, 1, 0), id, data + 8, false);
        }
    }
}

////////////////////////////////////////////////////
// fix up logic for trees
////////////////////////////////////////////////////

var trees;
var archTypeTree;
var archTypeBigTree;

function InitializeTrees() {
    trees = new Array();
    archTypeTree = new TreeGenerator(TreeGenerator.TreeType.TREE);
    archTypeBigTree = new TreeGenerator(TreeGenerator.TreeType.BIG_TREE);
}

function FinalizeTrees() {
    if (trees.length > 0) {
        context.print("Growing some trees");
        for (i = 0; i < trees.length; i++)
            trees[i].generate(origin);
    }
}

function SetLateForest(blockX, blockZ, sizeW, sizeL) {
    var border = 3;
    var spacing = 3;
    var odds = 10;

    for (var x = blockX + border; x <= blockX + sizeW - border; x = x + spacing)
        for (var z = blockZ + border; z <= blockZ + sizeL - border; z = z + spacing) {

            // odds% of the time plant a tree
            if (rand.nextInt(100) < odds)
                SetLateTree(x, streetLevel + 1, z, false);
        }
}

function SetLateTree(blockX, blockY, blockZ, bigTree) {
    trees.push(new Tree(blockX, blockY, blockZ, bigTree));
}

function Tree(blockX, blockY, blockZ, bigTree) {
    this.atX = blockX;
    this.atY = blockY;
    this.atZ = blockZ;
    this.big = bigTree;

    this.generate = function (origin) {
        if (editsess.rawGetBlock(origin.add(this.atX, this.atY, this.atZ)).getType() == BlockID.GRASS)
            if (this.big)
                archTypeBigTree.generate(editsess, origin.add(this.atX, this.atY + 1, this.atZ))
            else
                archTypeTree.generate(editsess, origin.add(this.atX, this.atY + 1, this.atZ));
    }
}

////////////////////////////////////////////////////
// fix up logic for support columns
////////////////////////////////////////////////////

function FinalizeColumns() {
    context.print("Driving pilings");
    var columnOffset = squareBlocks / 3;
    var y;
    for (var x = columnOffset; x < squareBlocks * 5; x += columnOffset)
        for (var z = columnOffset; z < squareBlocks * 5; z += columnOffset) {

            // find the lowest airy point
            for (y = 0; y < skyHigh; y++)
                if (blocks[x][y][z] != BlockID.AIR) {
                    y--;
                    break;
                }

            // grow the column at...
            var columnAt = origin.add(x, y, z);
            for (y = columnAt.getY(); y >= 0; y--) {
                columnAt = columnAt.setY(y);
                
                // time to quit?
                type = editsess.rawGetBlock(columnAt).getType();
                if (type == BlockID.STONE || type == BlockID.BEDROCK)
                    break;

                // big columns!
                SetBlockIfNeeded(columnAt, BlockID.STONE, 0, true);
                SetBlockIfNeeded(columnAt.add(1, 0, 0), BlockID.STONE, 0, true);
                SetBlockIfNeeded(columnAt.add(-1, 0, 0), BlockID.STONE, 0, true);
                SetBlockIfNeeded(columnAt.add(0, 0, 1), BlockID.STONE, 0, true);
                SetBlockIfNeeded(columnAt.add(0, 0, -1), BlockID.STONE, 0, true);
            }
        }
}
2
////////////////////////////////////////////////////
// specific city block construction
////////////////////////////////////////////////////

// borrowed from WorldEdit's Maze script
function AddPlumbingLevel() {
    context.print("Plumbing");

    // add some strata
    FillStrataLayer(BlockID.OBSIDIAN, 0);

    // figure out the size
    w = Math.floor(arrayWidth / 2);
    d = Math.floor(arrayDepth / 2);

    var stack = [];
    var visited = {};
    var noWallLeft = new Array(w * d);
    var noWallAbove = new Array(w * d);
    var current = 0;

    stack.push(id(0, 0))

    while (stack.length > 0) {
        var cell = stack.pop();
        var x = $x(cell), z = $z(cell);
        visited[cell] = true;

        var neighbors = []

        if (x > 0) neighbors.push(id(x - 1, z));
        if (x < w - 1) neighbors.push(id(x + 1, z));
        if (z > 0) neighbors.push(id(x, z - 1));
        if (z < d - 1) neighbors.push(id(x, z + 1));

        shuffle(neighbors);

        while (neighbors.length > 0) {
            var neighbor = neighbors.pop();
            var nx = $x(neighbor), nz = $z(neighbor);

            if (visited[neighbor] != true) {
                stack.push(cell);

                if (z == nz) {
                    if (nx < x) {
                        noWallLeft[cell] = true;
                    } else {
                        noWallLeft[neighbor] = true;
                    }
                } else {
                    if (nz < z) {
                        noWallAbove[cell] = true;
                    } else {
                        noWallAbove[neighbor] = true;
                    }
                }

                stack.push(neighbor);
                break;
            }
        }
    }

    for (var z = 0; z < d; z++) {
        for (var x = 0; x < w; x++) {
            var cell = id(x, z);

            if (!noWallLeft[cell] && z < d) {
                blocks[x * 2 + 1][1][z * 2] = BlockID.OBSIDIAN;
                blocks[x * 2 + 1][2][z * 2] = BlockID.OBSIDIAN;
                blocks[x * 2 + 1][3][z * 2] = BlockID.OBSIDIAN;
            }
            if (!noWallAbove[cell] && x < w) {
                blocks[x * 2][1][z * 2 + 1] = BlockID.OBSIDIAN;
                blocks[x * 2][2][z * 2 + 1] = BlockID.OBSIDIAN;
                blocks[x * 2][3][z * 2 + 1] = BlockID.OBSIDIAN;
            }
            blocks[x * 2 + 1][1][z * 2 + 1] = BlockID.OBSIDIAN;
            blocks[x * 2 + 1][2][z * 2 + 1] = BlockID.OBSIDIAN;
            blocks[x * 2 + 1][3][z * 2 + 1] = BlockID.OBSIDIAN;

            switch (rand.nextInt(20)) {
                case 0:
                case 1:
                    if (OneInThreeChance())
                        blocks[x * 2][1][z * 2] = BlockID.OBSIDIAN
                    else
                        blocks[x * 2][1][z * 2] = BlockID.DIRT;
                    SetLateGroundBlock(x * 2, 2, z * 2, BlockID.BROWN_MUSHROOM);
                    break;
                case 2:
                case 3:
                    if (OneInThreeChance())
                        blocks[x * 2][1][z * 2] = BlockID.OBSIDIAN
                    else
                        blocks[x * 2][1][z * 2] = BlockID.DIRT;
                    SetLateGroundBlock(x * 2, 1, z * 2, BlockID.RED_MUSHROOM);
                    break;
                case 4:
                    PlaceItem(x, z, BlockID.GOLD_BLOCK);
                    break;
                case 5:
                    PlaceItem(x, z, BlockID.DIAMOND_BLOCK);
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    PlaceItem(x, z, BlockID.WATER);
                    break;
                default:
                    if (OneInFourChance())
                        blocks[x * 2][3][z * 2] = BlockID.OBSIDIAN
                    else
                        blocks[x * 2][1][z * 2] = BlockID.OBSIDIAN
                    break;
            }
        }
    }

    // top off the plumbing
    FillStrataLayer(BlockID.CLAY, 4);

    //======================================================
    function PlaceItem(x, z, id) {
        if (OneInFourChance()) {
            if (OneInTwoChance()) {
                blocks[x * 2][3][z * 2] = BlockID.OBSIDIAN
                blocks[x * 2][1][z * 2] = id;
            } else {
                blocks[x * 2][1][z * 2] = BlockID.OBSIDIAN;
                blocks[x * 2][2][z * 2] = id;
            }
        } else
            blocks[x * 2][1][z * 2] = id;
    }

    function id(x, z) {
        return z * (w + 1) + x;
    }

    function $x(i) {
        return i % (w + 1);
    }

    function $z(i) {
        return Math.floor(i / (w + 1));
    }

    function shuffle(arr) {
        var i = arr.length;
        if (i == 0) return false;
        while (--i) {
            var j = rand.nextInt(i + 1);
            var tempi = arr[i];
            var tempj = arr[j];
            arr[i] = tempj;
            arr[j] = tempi;
        }
    }
}

function DrawParkCell(blockX, blockZ, cellX, cellZ, cellW, cellL) {
    var cellWidth = cellW * squareBlocks;
    var cellLength = cellL * squareBlocks;

    // reservoir's walls
    FillCellLayer(BlockID.SANDSTONE, blockX, blockZ, 0, cellW, cellL);
    AddWalls(BlockID.SANDSTONE, blockX, 1, blockZ,
                                blockX + cellWidth - 1, streetLevel - 1, blockZ + cellLength - 1);

    // fill up reservoir with water
    FillCube(BlockID.AIR, blockX + 1, 1, blockZ + 1,
                          blockX + cellWidth - 2, 3, blockZ + cellLength - 2);
    FillCube(BlockID.WATER, blockX + 1, 1, blockZ + 1,
                            blockX + cellWidth - 2, rand.nextInt(3) + 2, blockZ + cellLength - 2);

    // pillars to hold things up
    for (var x = cornerWidth; x < cellWidth; x = x + cornerWidth)
        for (var z = cornerWidth; z < cellLength; z = z + cornerWidth)
            for (var y = 1; y < streetLevel; y++) {

                // every other column has a lit base
                if (y == 1 && ((x % 2 == 0 && z % 2 == 1) ||
                               (x % 2 == 1 && z % 2 == 0)))
                    blocks[blockX + x][y][blockZ + z] = BlockID.LIGHTSTONE;
                else
                    blocks[blockX + x][y][blockZ + z] = BlockID.SANDSTONE;
            }

    // cap it off
    FillCellLayer(BlockID.SANDSTONE, blockX, blockZ, streetLevel, cellW, cellL);

    // add some grass
    FillCellLayer(BlockID.GRASS, blockX, blockZ, streetLevel + 1, cellW, cellL);

    // steps up to access point
    blocks[blockX + 7][streetLevel - 6][blockZ + 1] = BlockID.SANDSTONE;
    blocks[blockX + 6][streetLevel - 5][blockZ + 1] = BlockID.SANDSTONE;
    blocks[blockX + 5][streetLevel - 4][blockZ + 1] = BlockID.SANDSTONE;

    // platform for access point
    blocks[blockX + 4][streetLevel - 3][blockZ + 1] = BlockID.SANDSTONE;
    blocks[blockX + 3][streetLevel - 3][blockZ + 1] = BlockID.SANDSTONE;
    blocks[blockX + 2][streetLevel - 3][blockZ + 1] = BlockID.SANDSTONE;
    blocks[blockX + 1][streetLevel - 3][blockZ + 1] = BlockID.SANDSTONE;

    // backfill the wall behind the ladders
    blocks[blockX + 2][streetLevel - 2][blockZ + 1] = BlockID.SANDSTONE;
    blocks[blockX + 1][streetLevel - 2][blockZ + 1] = BlockID.SANDSTONE;
    blocks[blockX + 2][streetLevel - 1][blockZ + 1] = BlockID.SANDSTONE;
    blocks[blockX + 1][streetLevel - 1][blockZ + 1] = BlockID.SANDSTONE;

    // and now the ladders and trapdoor
    SetLateBlock(blockX + 3, streetLevel + 1, blockZ + 1, ExtendedID.SOUTHWARD_LADDER);
    SetLateBlock(blockX + 3, streetLevel, blockZ + 1, ExtendedID.SOUTHWARD_LADDER);
    SetLateBlock(blockX + 3, streetLevel - 1, blockZ + 1, ExtendedID.SOUTHWARD_LADDER);
    SetLateBlock(blockX + 3, streetLevel - 2, blockZ + 1, ExtendedID.SOUTHWARD_LADDER);
    SetLateBlock(blockX + 3, streetLevel + 2, blockZ + 1, ExtendedID.WESTFACING_TRAP_DOOR);

    // add some fencing
    var fenceHole = 3;
    for (var x = 0; x < cellWidth; x++) {

        // simple columns
        if (x == fenceHole || x == cellWidth - fenceHole - 1) {
            blocks[blockX + x][streetLevel + 2][blockZ] = BlockID.SAND;
            blocks[blockX + x][streetLevel + 2][blockZ + cellLength - 1] = BlockID.SAND;
            blocks[blockX + x][streetLevel + 3][blockZ] = BlockID.SANDSTONE;
            blocks[blockX + x][streetLevel + 3][blockZ + cellLength - 1] = BlockID.SANDSTONE;
            blocks[blockX + x][streetLevel + 4][blockZ] = ExtendedID.SANDSTONE_STEP;
            blocks[blockX + x][streetLevel + 4][blockZ + cellLength - 1] = ExtendedID.SANDSTONE_STEP;

            // fence itself
        } else if (x > fenceHole && x < cellWidth - fenceHole) {
            blocks[blockX + x][streetLevel + 2][blockZ] = BlockID.FENCE;
            blocks[blockX + x][streetLevel + 2][blockZ + cellLength - 1] = BlockID.FENCE;
        }
    }

    // another fence along the other axis
    for (var z = 0; z < cellLength; z++) {
        if (z == fenceHole || z == cellLength - fenceHole - 1) {
            blocks[blockX][streetLevel + 2][blockZ + z] = BlockID.SAND;
            blocks[blockX + cellWidth - 1][streetLevel + 2][blockZ + z] = BlockID.SAND;
            blocks[blockX][streetLevel + 3][blockZ + z] = BlockID.SANDSTONE;
            blocks[blockX + cellWidth - 1][streetLevel + 3][blockZ + z] = BlockID.SANDSTONE;
            blocks[blockX][streetLevel + 4][blockZ + z] = ExtendedID.SANDSTONE_STEP;
            blocks[blockX + cellWidth - 1][streetLevel + 4][blockZ + z] = ExtendedID.SANDSTONE_STEP;
        } else if (z > fenceHole && z < cellLength - fenceHole) {
            blocks[blockX][streetLevel + 2][blockZ + z] = BlockID.FENCE;
            blocks[blockX + cellWidth - 1][streetLevel + 2][blockZ + z] = BlockID.FENCE;
        }
    }

    // little park, give it a big tree
    if (cellW == 1 && cellL == 1)
        SetLateTree(blockX + Math.floor(cellWidth / 2),
                    streetLevel + 1,
                    blockZ + Math.floor(cellLength / 2), true);
    else {

        // where is the center?
        var fountainDiameter = 5;
        var fountainX = blockX + Math.floor((cellWidth - fountainDiameter) / 2);
        var fountainZ = blockZ + Math.floor((cellLength - fountainDiameter) / 2);

        // add a fountain
        FillAtLayer(BlockID.GLASS, fountainX + 1, fountainZ + 1, streetLevel, fountainDiameter - 2, fountainDiameter - 2);
        AddWalls(BlockID.STONE, fountainX, streetLevel + 1, fountainZ,
                            fountainX + fountainDiameter - 1, streetLevel + 2, fountainZ + fountainDiameter - 1);
        FillAtLayer(BlockID.WATER, fountainX + 1, fountainZ + 1, streetLevel + 1, fountainDiameter - 2, fountainDiameter - 2);

        blocks[fountainX + 2][streetLevel + 1][fountainZ + 2] = BlockID.LIGHTSTONE;
        blocks[fountainX + 2][streetLevel + 2][fountainZ + 2] = BlockID.GLASS;
        blocks[fountainX + 2][streetLevel + 3][fountainZ + 2] = BlockID.GLASS;
        blocks[fountainX + 2][streetLevel + 4][fountainZ + 2] = BlockID.GLASS;
        blocks[fountainX + 2][streetLevel + 5][fountainZ + 2] = BlockID.WATER;

        // add some trees
        SetLateForest(blockX, blockZ, cellWidth, cellLength);
    }
}

function AddCitySquares() {

    // upper limits
    var maxSize = 3;
    var maxParkSize = 1;
    var sizeX = 1;
    var sizeZ = 1;

    // if we are in a town then change them a bit
    if (modeCreate == createMode.TOWN) {
        maxSize = 2;
        maxParkSize = 2;
    } else if (modeCreate == createMode.HIGHRISE) {
        maxSize = 2;
    }

    // initialize the building cells
    var cells = new Array(3);
    for (var x = 0; x < 3; x++) {
        cells[x] = new Array(3);
        for (var z = 0; z < 3; z++)
            cells[x][z] = false;
    }

    // does this block have a park?
    if (modeCreate == createMode.TOWN ||
        modeCreate != createMode.HIGHRISE && OneInTwoChance()) {
        sizeX = RandomLotSize(maxParkSize);
        sizeZ = RandomLotSize(maxParkSize);
        var atX = rand.nextInt(maxSize - sizeX + 1);
        var atZ = rand.nextInt(maxSize - sizeZ + 1);

        // build the park then!
        MarkLotUsed(atX, atZ, sizeX, sizeZ);
        DrawParkCell((atX + 1) * squareBlocks, (atZ + 1) * squareBlocks,
                     atX, atZ, sizeX, sizeZ);
    }

    // work our way through the cells
    for (var atX = 0; atX < 3; atX++) {
        for (var atZ = 0; atZ < 3; atZ++) {
            if (!cells[atX][atZ]) { // nothing here yet.. build!
                sizeX = RandomLotSize(maxSize - atX);
                sizeZ = RandomLotSize(maxSize - atZ);

                // is there really width for it?
                for (var x = atX; x < atX + sizeX; x++) {
                    if (cells[x][atZ]) {
                        sizeX = x - atX;
                        break;
                    }
                }

                // is there really depth for it?
                for (var x = atX; x < atX + sizeX; x++) {
                    for (var z = atZ; z < atZ + sizeZ; z++) {
                        if (cells[x][z]) {
                            sizeZ = z - atZ;
                            break;
                        }
                    }
                }

                // mark the cells
                MarkLotUsed(atX, atZ, sizeX, sizeZ);

                // make it so!
                DrawBuildingCell((atX + 1) * squareBlocks, (atZ + 1) * squareBlocks,
                                 atX, atZ, sizeX, sizeZ);
            }
        }
    }

    function MarkLotUsed(atX, atZ, sizeX, sizeZ) {
        for (var x = atX; x < atX + sizeX; x++)
            for (var z = atZ; z < atZ + sizeZ; z++)
                cells[x][z] = true;
    }

    //======================================================
    function DrawBuildingCell(blockX, blockZ, cellX, cellZ, cellW, cellL) {
        var cellWidth = cellW * squareBlocks;
        var cellLength = cellL * squareBlocks;

        // floor color?
        var floorID = BlockID.WOOD;
        var stairID = BlockID.WOODEN_STAIRS;
        if (OneInFourChance()) {
            floorID = BlockID.COBBLESTONE;
            stairID = BlockID.COBBLESTONE_STAIRS;
        }

        // basement floor?
        var basementFloorID = BlockID.STONE;
        var basementWallID = BlockID.STONE;
        var basementStairID = BlockID.WOODEN_STAIRS;
        var basementLandingID = BlockID.WOOD;
        if (OneInThreeChance()) {
            basementFloorID = BlockID.COBBLESTONE;
            basementStairID = BlockID.COBBLESTONE_STAIRS;
            basementLandingID = BlockID.COBBLESTONE;
        }
        if (OneInTwoChance())
            basementWallID = BlockID.COBBLESTONE;

        // pick primary/secondary colors
        var primaryID = rand.nextInt(8);
        var secondaryID = primaryID + 8;

        // dark hue?
        if (OneInTwoChance()) {
            primaryID += 8;
            secondaryID = primaryID - 8;
        }

        // windows and wall color?
        var windowStyle = rand.nextInt(4);
        var primaryWallID = EncodeBlock(BlockID.CLOTH, primaryID);
        var secondaryWallID = EncodeBlock(BlockID.CLOTH, secondaryID);
        if (OneInTwoChance())
            primaryWallID = RandomMaterial();
        if (OneInTwoChance())
            secondaryWallID = RandomMaterial();
        if (windowStyle != 0 && OneInFourChance())
            secondaryWallID = primaryWallID;

        // how many stories and is there a way down to the basement
        var firstFloor = streetLevel + 1;
        var stories = rand.nextInt(floorCount) + 1;
        var basements = rand.nextInt(3);

        // what type of roof and what is its color? (make sure it is darkened)
        var roofStyle = rand.nextInt(4);
        var roofID = RandomRoofMaterial(secondaryWallID, secondaryID);
        if (roofID == BlockID.GLASS && roofStyle == 3) // if the roof is made of glass, can't be flat
            roofStyle = rand.nextInt(3);

        // is there an angled roof? if so then the building should be one story shorter
        var roofTop = roofStyle < 3 ? 1 : 0;
        if (stories == 1 && roofTop)
            stories++;

        // where are the stairs?
        var stairsX = blockX + Math.floor(cellWidth / 2);
        var stairsZ = blockZ + Math.floor(cellLength / 2);

        // random elements
        var insetStyle = OneInThreeChance();
        var cornerStyle = OneInThreeChance() && !insetStyle;
        var outsetStyle = OneInThreeChance() && !insetStyle;
        var shortWindows = OneInThreeChance() || outsetStyle;
        var skylightID = OneInThreeChance() ? roofID : BlockID.GLASS;
        var penthouseStyle = skylightID == BlockID.GLASS || roofID == BlockID.GLASS;
        var columnStyle = (cellW > 1 || cellL > 1) && stories > 2 && OneInTwoChance();
        var columnStories = rand.nextInt(stories / 2 + 1);
        var columnFloorInsetAmount = rand.nextInt(2);
        var gridStyle = columnStyle && OneInThreeChance();

        // building the building's bottoms from the top down
        switch (basements) {
            case 0:
                {
                    BuildFoundation();
                    HollowOut(2);
                    break;
                }
            case 1:
                {
                    BuildFoundation();
                    Build1stBasement();
                    HollowOut(1);
                    break;
                }
            default: // case 2: 
                {
                    BuildFoundation();
                    Build1stBasement();
                    Build2ndBasement();
                    HollowOut(0);
                    break;
                }
        }

        // stairs
        PunchBasementStairs(stairsX, stairsZ, basements,
            (stories - roofTop - 1 > 1) || !penthouseStyle);

        // build the building
        for (var story = 0; story < (stories - roofTop); story++) {
            var floorAt = story * floorHeight + firstFloor + 1;

            // what color is the wall? first floor is unique.
            var floorWallID = primaryWallID;
            if (story == 0 && secondaryWallID != BlockID.GLASS && OneInTwoChance())
                floorWallID = secondaryWallID;

            // where are the walls?
            var minX = blockX + 1;
            var minY = floorAt;
            var minZ = blockZ + 1;
            var maxX = blockX + cellWidth - 2;
            var maxY = floorAt + floorHeight - 2;
            var maxZ = blockZ + cellLength - 2;
            var insetAmount = (columnStyle && story < columnStories) ? 2 : (insetStyle ? 1 : 0);

            // breath in?
            if (insetAmount > 0) {
                minX += insetAmount;
                maxX -= insetAmount;
                minZ += insetAmount;
                maxZ -= insetAmount;
            }

            // NS walls
            var window = 0;
            var sectionID = floorWallID;
            for (var x = minX; x <= maxX; x++) {
                if (!(cornerStyle && (x == minX || x == maxX))) {
                    sectionID = ComputeWindowID(windowStyle, floorWallID, x, minX, minY, window);

                    for (var y = minY; y <= maxY; y++) {
                        if (shortWindows && y == minY) {
                            blocks[x][y][minZ] = floorWallID;
                            blocks[x][y][maxZ] = floorWallID;
                        } else {
                            blocks[x][y][minZ] = sectionID;
                            blocks[x][y][maxZ] = sectionID;
                        }
                    }
                }
                window++;
            }

            // EW walls
            window = 0;
            for (var z = minZ; z <= maxZ; z++) {
                if (!(cornerStyle && (z == minZ || z == maxZ))) {
                    sectionID = ComputeWindowID(windowStyle, floorWallID, z, minZ, minZ, window);

                    for (var y = minY; y <= maxY; y++) {
                        if (shortWindows && y == minY) {
                            blocks[minX][y][z] = floorWallID;
                            blocks[maxX][y][z] = floorWallID;
                        } else {
                            blocks[minX][y][z] = sectionID;
                            blocks[maxX][y][z] = sectionID;
                        }
                    }
                }
                window++;
            }

            // are we on the ground floor? if so let's add some doors
            if (story == 0) {
                var punchedDoor = false;

                // fifty/fifty chance of a door on a side, but there must be one somewhere
                if (OneInTwoChance()) {
                    SetLateBlock(minX + 3, minY, minZ, ExtendedID.WESTFACING_WOODEN_DOOR);
                    punchedDoor = true;
                }
                if (OneInTwoChance()) {
                    SetLateBlock(maxX, minY, minZ + 3, ExtendedID.NORTHFACING_WOODEN_DOOR);
                    punchedDoor = true;
                }
                if (OneInTwoChance()) {
                    SetLateBlock(maxX - 3, minY, maxZ, ExtendedID.EASTFACING_WOODEN_DOOR);
                    punchedDoor = true;
                }
                if (!punchedDoor || OneInTwoChance())
                    SetLateBlock(minX, minY, maxZ - 3, ExtendedID.SOUTHFACING_WOODEN_DOOR);
            }

            // breath in?
            if (insetAmount > 0) {
                minX -= insetAmount;
                maxX += insetAmount;
                minZ -= insetAmount;
                maxZ += insetAmount;
            }

            // draw the ceiling edge, the topmost one is special
            if (story < stories - roofTop - 1) {
                if (!outsetStyle && (gridStyle || !columnStyle || story > columnStories - 2))
                    AddWalls(secondaryWallID, minX, maxY + 1, minZ, maxX, maxY + 1, maxZ);

                // now do the next floor
                var floorInset = (columnStyle && story < columnStories - 1) ? columnFloorInsetAmount : 0;
                FillAtLayer(floorID, minX + 1 + floorInset, minZ + 1 + floorInset, maxY + 1,
                            maxX - minX - 1 - floorInset * 2, maxZ - minZ - 1 - floorInset * 2);
            }
            else
                FillAtLayer(floorID, minX, minZ, maxY + 1, maxX - minX + 1, maxZ - minZ + 1);

            // add the columns if needed
            if (columnStyle && story < columnStories) {
                var columnID = secondaryWallID;

                // NS columns
                for (var x = minX; x <= maxX; x++) {
                    if (x % 5 == 0) {
                        for (var y = minY; y <= maxY + 1; y++) {
                            blocks[x][y][minZ] = columnID;
                            blocks[x][y][maxZ] = columnID;
                        }
                    }
                }

                // EW columns
                for (var z = minZ; z <= maxZ; z++) {
                    if (z % 5 == 0) {
                        for (var y = minY; y <= maxY + 1; y++) {
                            blocks[minX][y][z] = columnID;
                            blocks[maxX][y][z] = columnID;
                        }
                    }
                }
            }

            // add some stairs, but not if there is a penthouse
            if (story < (stories - roofTop - 1) || !penthouseStyle)
                PunchStairs(stairID, stairsX, stairsZ, 
                            floorAt + floorHeight - 1, false, true, true);
        }

        // where is the roof?
        var roofAt = (stories - 1) * floorHeight + firstFloor + 1;

        // flat roofs are special
        if (roofStyle == 3) {

            // flat roofs can have one more stairs
            PunchStairs(stairID, stairsX, stairsZ, roofAt + floorHeight - 1, false, true, true);

            // now add some more fences
            AddWalls(BlockID.FENCE, blockX + 1, roofAt + floorHeight, blockZ + 1,
                                            blockX + cellWidth - 2, roofAt + floorHeight, blockZ + cellLength - 2);

            // add final stair railing
            FinalizeStairs(stairsX, stairsZ, roofAt + floorHeight, true);
        }
        else {

            // hollow out the ceiling
            if (penthouseStyle) {
                FillAtLayer(BlockID.AIR, blockX + 2, blockZ + 2, roofAt - 1, cellWidth - 4, cellLength - 4);

                // add final stair railing
                FinalizeStairs(stairsX, stairsZ, roofAt - floorHeight, (stories - roofTop > 1)); 
            } else
                FinalizeStairs(stairsX, stairsZ, roofAt, (stories - roofTop > 1));

            // cap the building
            switch (roofStyle) {
                case 0:
                    // NS ridge
                    for (r = 0; r < floorHeight; r++)
                        AddWalls(roofID, blockX + 2 + r, roofAt + r, blockZ + 1,
                                                 blockX + cellWidth - 3 - r, roofAt + r, blockZ + cellLength - 2);

                    // fill in top with skylight
                    FillCube(skylightID, blockX + 2 + floorHeight, roofAt + floorHeight - 1, blockZ + 2,
                                                 blockX + cellWidth - 3 - floorHeight, roofAt + floorHeight - 1, blockZ + cellLength - 3);
                    break;
                case 1:
                    // EW ridge
                    for (r = 0; r < floorHeight; r++)
                        AddWalls(roofID, blockX + 1, roofAt + r, blockZ + 2 + r,
                                             blockX + cellWidth - 2, roofAt + r, blockZ + cellLength - 3 - r);

                    // fill in top with skylight
                    FillCube(skylightID, blockX + 2, roofAt + floorHeight - 1, blockZ + 2 + floorHeight,
                                                 blockX + cellWidth - 3, roofAt + floorHeight - 1, blockZ + cellLength - 3 - floorHeight);
                    break;

                default:
                    // pointy
                    for (r = 0; r < floorHeight; r++)
                        AddWalls(roofID, blockX + 2 + r, roofAt + r, blockZ + 2 + r,
                                                 blockX + cellWidth - 3 - r, roofAt + r, blockZ + cellLength - 3 - r);

                    // fill in top with skylight
                    FillCube(skylightID, blockX + 2 + floorHeight, roofAt + floorHeight - 1, blockZ + 2 + floorHeight,
                                                 blockX + cellWidth - 3 - floorHeight, roofAt + floorHeight - 1, blockZ + cellLength - 3 - floorHeight);
                    break;
            }
        }

        function BuildFoundation() {
            FillCube(basementFloorID, blockX, floorHeight * 2, blockZ,
                                        blockX + cellWidth - 1, floorHeight * 2, blockZ + cellLength - 1);
            FillCube(BlockID.REDSTONE_WIRE, blockX, floorHeight * 2 + 1, blockZ,
                                        blockX + cellWidth - 1, floorHeight * 2 + 1, blockZ + cellLength - 1);
            FillCube(basementFloorID, blockX, floorHeight * 2 + 2, blockZ,
                                        blockX + cellWidth - 1, floorHeight * 2 + 2, blockZ + cellLength - 1);
        }

        function Build1stBasement() {
            // my floor is their ceiling
            FillCube(basementFloorID, blockX, floorHeight, blockZ,
                                    blockX + cellWidth - 1, floorHeight, blockZ + cellLength - 1);

            // some walls
            AddWalls(basementWallID, blockX, floorHeight + 1, blockZ,
                                    blockX + cellWidth - 1, floorHeight * 2 - 1, blockZ + cellLength - 1);
        }

        function Build2ndBasement() {
            // basement floor
            FillCube(basementFloorID, blockX, 0, blockZ,
                                    blockX + cellWidth - 1, 0, blockZ + cellLength - 1);

            // walls
            AddWalls(basementWallID, blockX, 1, blockZ,
                                    blockX + cellWidth - 1, floorHeight - 1, blockZ + cellLength - 1);

            // empty it out
            FillCube(BlockID.AIR, blockX + 1, 1, blockZ + 1,
                                  blockX + cellWidth - 2, floorHeight - 1, blockZ + cellLength - 2);
        }

        function HollowOut(floors) {
            if (floors > 0)
                FillCube(BlockID.AIR, blockX, 0, blockZ,
                                      blockX + cellWidth - 1, floorHeight * floors - 1, blockZ + cellLength - 1);
        }

        function RandomMaterial() {
            switch (rand.nextInt(13)) {
                case 1: return BlockID.COBBLESTONE;
                case 2: return BlockID.WOOD;
                case 3: if (OneInNChance(10)) return BlockID.LAPIS_LAZULI_BLOCK;
                case 4: return BlockID.SANDSTONE;
                case 5: if (OneInNChance(10)) return BlockID.GOLD_BLOCK;
                case 6: return BlockID.IRON_BLOCK;
                case 7: return BlockID.DOUBLE_STEP;
                case 8: return BlockID.BRICK;
                case 9: return BlockID.MOSSY_COBBLESTONE;
                case 10: if (OneInNChance(10)) return BlockID.DIAMOND_BLOCK;
                case 11: return BlockID.CLAY;
                case 12: if (OneInNChance(10)) return BlockID.GLASS;
                default: return BlockID.STONE;
            }
        }

        function RandomRoofMaterial(secondaryWallID, secondaryID) {
            if (DecodeID(secondaryWallID) == BlockID.CLOTH)
                if (secondaryID < 8)
                    return EncodeBlock(BlockID.CLOTH, secondaryID + 8)
                else
                    return EncodeBlock(BlockID.CLOTH, secondaryID)
            else
                return secondaryWallID;
        }

        function ComputeWindowID(style, defaultID, curAt, minAt, maxAt, state) {
            var windowID = BlockID.GLASS;
            switch (style) {
                case 1:
                    if (state % 3 == 0)
                        windowID = defaultID;
                    break;
                case 2:
                    if (state % 4 == 0)
                        windowID = defaultID;
                    break;
                case 3:
                    if (state % 5 == 0)
                        windowID = defaultID;
                    break;
                default:
                    if (curAt == minAt || curAt == maxAt || OneInTwoChance())
                        windowID = defaultID;
                    break;
            }
            return windowID;
        }

        function PunchBasementStairs(atX, atZ, basements, morestairs) {
            if (basements > 0) {

                // where to create the stairs?
                var floorNth = (2 - basements) * floorHeight + 1;
                var floor1st = floorHeight + 1;
                var atY = streetLevel + 1;
                var stepNdx = 0;
                var stepY = atY - stepNdx;

                // wall and center post
                FillCube(basementWallID, atX - 2, floorNth, atZ - 2,
                                        atX + 2, atY, atZ + 2);

                // room to put the stairs
                AddWalls(BlockID.AIR, atX - 1, floorNth, atZ - 1,
                                      atX + 1, atY, atZ + 1);

                // cap off the top for safety
                AddWalls(BlockID.FENCE, atX - 1, streetLevel + 2, atZ - 2,
                                        atX + 2, atY + 1, atZ + 2);

                while (atY - stepNdx - 1 >= floorNth) {
                    stepY = atY - stepNdx - 1;
                    switch (stepNdx % 4) {
                        case 0:
                            {
                                blocks[atX][stepY][atZ + 1] = EncodeBlock(basementStairID, Ascending_North);
                                blocks[atX - 1][stepY][atZ + 1] = basementLandingID;
                                if (stepNdx == 0) {
                                    blocks[atX - 1][stepY + 2][atZ + 1] = BlockID.AIR;
                                    blocks[atX - 1][stepY + 1][atZ + 1] = EncodeBlock(basementStairID, Ascending_North);
                                    blocks[atX - 2][stepY + 2][atZ + 1] = BlockID.AIR;

                                    blocks[atX][stepY + 1][atZ - 1] = basementWallID;
                                    blocks[atX - 1][stepY + 1][atZ] = basementWallID;
                                    blocks[atX - 1][stepY + 1][atZ - 1] = basementWallID;

                                    blocks[atX + 1][stepY + 2][atZ] = basementWallID;
                                    blocks[atX + 1][stepY + 2][atZ - 1] = basementWallID;
                                    blocks[atX][stepY + 2][atZ] = basementWallID;
                                    blocks[atX][stepY + 2][atZ - 1] = basementWallID;
                                    blocks[atX - 1][stepY + 2][atZ] = basementWallID;
                                    blocks[atX - 1][stepY + 2][atZ - 1] = basementWallID;

                                    if (morestairs) {
                                        blocks[atX + 1][stepY + 3][atZ] = basementWallID;
                                        blocks[atX + 1][stepY + 3][atZ - 1] = basementWallID;
                                        blocks[atX][stepY + 3][atZ] = basementWallID;
                                        blocks[atX][stepY + 3][atZ - 1] = basementWallID;

                                        blocks[atX + 1][stepY + 4][atZ] = basementWallID;
                                        blocks[atX + 1][stepY + 4][atZ - 1] = basementWallID;
                                    }
                                }
                                break;
                            }
                        case 1:
                            {
                                blocks[atX + 1][stepY][atZ] = EncodeBlock(basementStairID, Ascending_West);
                                blocks[atX + 1][stepY][atZ + 1] = basementLandingID;
                                break;
                            }
                        case 2:
                            {
                                blocks[atX][stepY][atZ - 1] = EncodeBlock(basementStairID, Ascending_South);
                                blocks[atX + 1][stepY][atZ - 1] = basementLandingID;
                                break;
                            }
                        default: // case 3:
                            {
                                blocks[atX - 1][stepY][atZ] = EncodeBlock(basementStairID, Ascending_East);
                                blocks[atX - 1][stepY][atZ - 1] = basementLandingID;
                                break;
                            }
                    }

                    // one more level please
                    stepNdx++;
                }
            }

            // place the doors
            if (basements >= 1) {
                AddBasementDoor(floorHeight + 1);
                if (basements >= 2) {
                    AddBasementDoor(1);
                    AddBasementTrap(0, true);
                } else
                    AddBasementTrap(floorHeight, true);
            } //else
                //AddBasementTrap(streetLevel + 1, false);

            function AddBasementDoor(blockY) {
                SetLateBlock(atX + 2, blockY, atZ + 1, ExtendedID.SOUTHFACING_WOODEN_DOOR);
            }

            function AddBasementTrap(blockY, backfillStairs) {
                blocks[atX][blockY][atZ - 1] = BlockID.AIR;
                SetLateBlock(atX, blockY + 1, atZ - 1, ExtendedID.SOUTHFACING_TRAP_DOOR);

                if (backfillStairs) {
                    blocks[atX - 1][blockY + 1][atZ - 1] = basementLandingID;
                    blocks[atX - 1][blockY + 1][atZ] = basementLandingID;
                    blocks[atX - 1][blockY + 1][atZ + 1] = basementLandingID;
                }
            }
        }

        function PunchStairs(stairID, atX, atZ, floorY, addStairwell, addGuardrail, drawStairs) {

            // how big are the stairs?
            var stairWidth = 2;
            var stairHeight = floorHeight;

            // offset just a little bit
            atX--;
            atZ--;

            // increment the height if we are putting stairs in the basement
            if (addStairwell)
                stairHeight = sewerHeight + streetHeight;

            // place stairs
            for (airX = atX; airX < atX + stairHeight; airX++) {
                if (drawStairs)
                    for (airZ = atZ; airZ < atZ + 2; airZ++) {
                        blocks[airX][floorY][airZ] = BlockID.AIR;
                        blocks[airX][floorY - (stairHeight - (airX - atX)) + 1][airZ] = stairID;
                    };

                // make sure we don't fall down the stairs
                if (addGuardrail) {
                    blocks[airX][floorY + 1][atZ + 2] = BlockID.FENCE;
                    blocks[airX][floorY + 1][atZ - 1] = BlockID.FENCE;
                }
            }

            // create a basement enclosure to protect against the nasties
            if (addStairwell) {
                AddWalls(BlockID.STONE, atX - 4, sewerFloor, atZ - 1,
                                        atX + stairHeight, streetLevel, atZ + 2);

                // and a door 
                SetLateBlock(atX - sewerHeight + 1, sewerFloor, atZ + 2, ExtendedID.WESTFACING_WOODEN_DOOR);
            }
        }

        // add final stair railing
        function FinalizeStairs(atX, atZ, floorAt, addGuardrail) {
            if (addGuardrail) {

                // how big are the stairs?
                var stairWidth = 2;
                var stairHeight = floorHeight;

                // three more to finish things up
                for (var z = 0; z < 4; z++)
                    blocks[atX - 2][floorAt][atZ + z - 2] = BlockID.FENCE;
            }
        }
    }
}

function RandomLotSize(maxSize) {
    switch (rand.nextInt(10)) {
        case 9:
        case 8:
            if (maxSize == 3)
                return 3;
        case 7:
        case 6:
        case 5:
            if (maxSize >= 2)
                return 2;
        case 4:
        case 3:
        case 2:
        case 1:
        default:
            return 1;
    }
}

// streets or bridges over canals (one of these days)
function AddStreets() {
    for (var x = 0; x < squaresWidth; x++)
        for (var z = 0; z < squaresLength; z++)
            if (x % 4 == 0 || z % 4 == 0)
                DrawStreetCell(x * squareBlocks, z * squareBlocks, x, z);

    //======================================================
    function DrawStreetCell(blockX, blockZ, cellX, cellZ) {

        // add the sewer corner bits, first one is special
        DrawSewerPart(blockX, blockZ, 0, 0);
        DrawSewerPart(blockX, blockZ, 2, 0);
        DrawSewerPart(blockX, blockZ, 0, 2);
        DrawSewerPart(blockX, blockZ, 2, 2);

        // bevel the ceilings
        DrawBevelPart(blockX, blockZ, 0, 1, true);
        DrawBevelPart(blockX, blockZ, 1, 0, false);
        DrawBevelPart(blockX, blockZ, 2, 1, true);
        DrawBevelPart(blockX, blockZ, 1, 2, false);

        // add the sewer straight bits
        if (cellX % 4 == 0 && cellZ != 0 && cellZ != squaresLength - 1 && cellZ % 4 != 0) {
            DrawSewerPart(blockX, blockZ, 0, 1);
            DrawSewerPart(blockX, blockZ, 2, 1);
            DrawBevelPart(blockX, blockZ, 1, 1, false);
        } else if (cellZ % 4 == 0 && cellX != 0 && cellX != squaresWidth - 1 && cellX % 4 != 0) {
            DrawSewerPart(blockX, blockZ, 1, 0);
            DrawSewerPart(blockX, blockZ, 1, 2);
            DrawBevelPart(blockX, blockZ, 1, 1, true);
        }

        // add the street
        FillCellLayer(BlockID.STONE, blockX, blockZ, streetLevel - 1, 1, 1);
        FillCellLayer(BlockID.STONE, blockX, blockZ, streetLevel, 1, 1);

        // add the sidewalk and streetlights
        var sidewalkY = streetLevel + 1;
        if (cellX % 4 == 0 && cellZ % 4 != 0) {
            DrawSidewalk(blockX + 0 * cornerBlocks, blockZ + 0 * cornerBlocks,
                         blockX + 1 * cornerBlocks - 2, blockZ + 3 * cornerBlocks);
            DrawSidewalk(blockX + 2 * cornerBlocks + 2, blockZ + 0 * cornerBlocks,
                         blockX + 3 * cornerBlocks, blockZ + 3 * cornerBlocks);

            DrawStreetlight(blockX + 0 * cornerBlocks + 2, sidewalkY, blockZ + 1 * cornerBlocks + 2, true, false, false, false);
            DrawStreetlight(blockX + 2 * cornerBlocks + 2, sidewalkY, blockZ + 1 * cornerBlocks + 2, false, false, true, false);

            // paint road lines
            for (var z = 0; z < squareBlocks; z++)
                if (z % 5 != 0 && z % 5 != 4)
                    blocks[blockX + linesOffset][streetLevel][blockZ + z] = ExtendedID.YELLOW_CLOTH;

        } else if (cellX % 4 != 0 && cellZ % 4 == 0) {
            DrawSidewalk(blockX + 0 * cornerBlocks, blockZ + 0 * cornerBlocks,
                         blockX + 3 * cornerBlocks, blockZ + 1 * cornerBlocks - 2);
            DrawSidewalk(blockX + 0 * cornerBlocks, blockZ + 2 * cornerBlocks + 2,
                         blockX + 3 * cornerBlocks, blockZ + 3 * cornerBlocks);

            DrawStreetlight(blockX + 1 * cornerBlocks + 2, sidewalkY, blockZ + 0 * cornerBlocks + 2, false, true, false, false);
            DrawStreetlight(blockX + 1 * cornerBlocks + 2, sidewalkY, blockZ + 2 * cornerBlocks + 2, false, false, false, true);

            // paint road lines
            for (var x = 0; x < squareBlocks; x++)
                if (x % 5 != 0 && x % 5 != 4)
                    blocks[blockX + x][streetLevel][blockZ + linesOffset] = ExtendedID.YELLOW_CLOTH;

        } else if (cellX % 4 == 0 && cellZ % 4 == 0) {
            DrawSidewalkCorner(blockX + 0 * cornerBlocks, blockZ + 0 * cornerBlocks, 0, 0);
            DrawSidewalkCorner(blockX + 2 * cornerBlocks, blockZ + 0 * cornerBlocks, 2, 0);
            DrawSidewalkCorner(blockX + 0 * cornerBlocks, blockZ + 2 * cornerBlocks, 0, 2);
            DrawSidewalkCorner(blockX + 2 * cornerBlocks, blockZ + 2 * cornerBlocks, 2, 2);

            DrawStreetlight(blockX + 0 * cornerBlocks + 2, sidewalkY, blockZ + 0 * cornerBlocks + 2, true, true, false, false);
            DrawStreetlight(blockX + 2 * cornerBlocks + 2, sidewalkY, blockZ + 0 * cornerBlocks + 2, false, true, true, false);
            DrawStreetlight(blockX + 2 * cornerBlocks + 2, sidewalkY, blockZ + 2 * cornerBlocks + 2, false, false, true, true);
            DrawStreetlight(blockX + 0 * cornerBlocks + 2, sidewalkY, blockZ + 2 * cornerBlocks + 2, true, false, false, true);

            // paint crosswalk
            var line1Color = ExtendedID.WHITE_CLOTH;
            var line2Color = ExtendedID.LIGHT_GRAY_CLOTH;
            for (var i = 0; i < 9; i++) {
                blocks[blockX + 2][streetLevel][blockZ + 3 + i] = line1Color;
                blocks[blockX][streetLevel][blockZ + 3 + i] = line2Color;

                blocks[blockX + squareBlocks - 3][streetLevel][blockZ + 3 + i] = line1Color;
                blocks[blockX + squareBlocks - 1][streetLevel][blockZ + 3 + i] = line2Color;

                blocks[blockX + 3 + i][streetLevel][blockZ] = line2Color;
                blocks[blockX + 3 + i][streetLevel][blockZ + 2] = line1Color;

                blocks[blockX + 3 + i][streetLevel][blockZ + squareBlocks - 3] = line1Color;
                blocks[blockX + 3 + i][streetLevel][blockZ + squareBlocks - 1] = line2Color;
            }
        }

        function DrawBevelPart(atX, atZ, boxX, boxZ, northSouth) {
            atX = atX + boxX * cornerBlocks;
            atZ = atZ + boxZ * cornerBlocks;
            var blockID = OneInTwoChance() ? BlockID.COBBLESTONE : BlockID.MOSSY_COBBLESTONE;
            if (northSouth)
                for (var x = 0; x < cornerBlocks; x++) {
                    blocks[atX + x][streetLevel - 2][atZ] = blockID;
                    blocks[atX + x][streetLevel - 2][atZ + cornerBlocks - 1] = blockID;
                }
            else
                for (var z = 0; z < cornerBlocks; z++) {
                    blocks[atX][streetLevel - 2][atZ + z] = blockID;
                    blocks[atX + cornerBlocks - 1][streetLevel - 2][atZ + z] = blockID;
                }
        }

        function DrawSewerPart(atX, atZ, boxX, boxZ) {
            var blockX = atX + boxX * cornerBlocks;
            var blockZ = atZ + boxZ * cornerBlocks;

            // first the walls
            var wallID = OneInTwoChance() ? BlockID.COBBLESTONE : BlockID.MOSSY_COBBLESTONE;
            AddWalls(wallID, blockX, sewerFloor, blockZ,
                             blockX + cornerBlocks - 1, sewerCeiling - 1, blockZ + cornerBlocks - 1);

            // then the goodies
            var fillID = RandomGoodie();

            // always fill the access room with air
            var accessRoom = !(boxX != 0 || boxZ != 0 || cellX % 4 != 0 || cellZ % 4 != 0);
            if (accessRoom)
                fillID = BlockID.AIR;

            // fill will goodies
            FillCube(fillID, blockX + 1, sewerFloor, blockZ + 1,
                             blockX + cornerBlocks - 2, sewerCeiling - 1 - rand.nextInt(3), blockZ + cornerBlocks - 2);

            // what type of door are we going to use?
            var doorID = BlockID.BRICK;
            if (!accessRoom && fillID == BlockID.AIR)
                doorID = BlockID.AIR;

            // place all the doors in sewer boxes that aren't the access one
            PunchDoor(2, 0, false);
            PunchDoor(0, 2, false);
            PunchDoor(2, 4, false);
            PunchDoor(4, 2, accessRoom);

            // place a single door
            function PunchDoor(offsetX, offsetZ, realDoor) {
                if (realDoor)
                    SetLateBlock(blockX + offsetX, sewerFloor, blockZ + offsetZ, ExtendedID.SOUTHFACING_WOODEN_DOOR)
                else {
                    blocks[blockX + offsetX][sewerFloor][blockZ + offsetZ] = doorID;
                    blocks[blockX + offsetX][sewerFloor + 1][blockZ + offsetZ] = doorID;
                }
            }

            function RandomGoodie() {
                switch (rand.nextInt(100)) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        return (BlockID.DIRT);
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                        return BlockID.SAND;
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                        return BlockID.GRAVEL;
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                        return BlockID.CLAY;
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                        return BlockID.IRON_ORE;
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                        return BlockID.COAL_ORE;
                    case 32:
                    case 33:
                    case 34:
                        return BlockID.GOLD_ORE;
                    case 35:
                    case 36:
                        return BlockID.LAPIS_LAZULI_ORE;
                    case 37:
                    case 38:
                        return BlockID.DIAMOND_ORE;
                    case 39:
                        return BlockID.REDSTONE_ORE;
                    case 40:
                        return BlockID.GLOWING_REDSTONE_ORE;
                    case 41:
                        return BlockID.SNOW_BLOCK;
                    case 42:
                        return BlockID.WATER;
                    case 43:
                        return BlockID.ICE;
                    case 44:
                        return BlockID.LAVA;
                    case 45:
                        return BlockID.NETHERSTONE;
                    case 46:
                        return BlockID.NETHERRACK;
                    case 47:
                        return BlockID.SLOW_SAND;
                    case 48:
                        return BlockID.GOLD_BLOCK;
                    case 49:
                        return BlockID.IRON_BLOCK;
                    case 50:
                        return BlockID.DIAMOND_BLOCK;
                    case 51:
                        return BlockID.LAPIS_LAZULI_BLOCK;
                    case 52:
                        return BlockID.TNT;
                    case 53:
                        return BlockID.CAKE_BLOCK;
                    default:
                        return BlockID.AIR;
                }
            }
        }

        function DrawSidewalkCorner(blockX, blockZ, offsetX, offsetZ) {
            for (var x = 0; x < 3; x++)
                for (var z = 0; z < 3; z++)
                    blocks[blockX + offsetX + x][sidewalkY][blockZ + offsetZ + z] = BlockID.STEP;
        }

        function DrawSidewalk(minX, minZ, maxX, maxZ) {
            for (var x = minX; x < maxX; x++)
                for (var z = minZ; z < maxZ; z++)
                    blocks[x][sidewalkY][z] = BlockID.STEP;
        }
    }
}

function DrawStreetlight(blockX, blockY, blockZ, lightN, lightE, lightS, lightW) {
    blocks[blockX][blockY][blockZ] = BlockID.IRON_BLOCK;
    blocks[blockX][blockY + 1][blockZ] = BlockID.FENCE;
    blocks[blockX][blockY + 2][blockZ] = BlockID.FENCE;
    blocks[blockX][blockY + 3][blockZ] = BlockID.FENCE;
    blocks[blockX][blockY + 4][blockZ] = BlockID.FENCE;
    blocks[blockX][blockY + 5][blockZ] = BlockID.STEP;

    if (lightN)
        blocks[blockX + 1][blockY + 5][blockZ] = BlockID.LIGHTSTONE;
    if (lightE)
        blocks[blockX][blockY + 5][blockZ + 1] = BlockID.LIGHTSTONE;
    if (lightS)
        blocks[blockX - 1][blockY + 5][blockZ] = BlockID.LIGHTSTONE;
    if (lightW)
        blocks[blockX][blockY + 5][blockZ - 1] = BlockID.LIGHTSTONE;
}

function AddParkLot() {
    DrawParkCell(squareBlocks, squareBlocks, 1, 1, 3, 3);
}

function AddDirtLot() {
    FillCube(BlockID.DIRT, 1 * squareBlocks, sewerFloor, 1 * squareBlocks,
                           4 * squareBlocks - 1, streetLevel, 4 * squareBlocks - 1);
}

function AddFarmAndHousesLot() {
    FillCube(BlockID.DIRT, 1 * squareBlocks, sewerFloor, 1 * squareBlocks,
                           4 * squareBlocks - 1, streetLevel, 4 * squareBlocks - 1);

    for (var at = 0; at < squareBlocks * 3; at++) {
        blocks[squareBlocks + at][streetLevel + 1][squareBlocks + lotBlocks] = BlockID.GRASS;
        blocks[squareBlocks + lotBlocks][streetLevel + 1][squareBlocks + at] = BlockID.GRASS;
        if (at < lotBlocks - 2 || at > lotBlocks + 2) {
            blocks[squareBlocks + at][streetLevel + 2][squareBlocks + lotBlocks] = BlockID.FENCE;
            blocks[squareBlocks + lotBlocks][streetLevel + 2][squareBlocks + at] = BlockID.FENCE;
        }
    }

    // house or not?
    var houseCreated = modeCreate != createMode.FARMHOUSE;
    var atX = 0;
    var atZ = 0;

    // do it!
    for (var a = 0; a <= 1; a++) {
        for (var b = 0; b <= 1; b++) {
            atX = squareBlocks + (lotBlocks + 1) * a;
            atZ = squareBlocks + (lotBlocks + 1) * b;

            // just HOUSES
            if (modeCreate == createMode.HOUSES) {
                DrawHouseCell(atX, atZ, a, b, lotBlocks, lotBlocks);

                // in FARM or FARMHOUSE mode
            } else if (!houseCreated && ((a == 1 && b == 1) ||
                                         (OneInFourChance()))) {
                houseCreated = true;
                DrawHouseCell(atX, atZ, a, b, lotBlocks, lotBlocks);
            } else {
                DrawFarmCell(atX, atZ, a, b, lotBlocks, lotBlocks);
            }
        }
    }

    //======================================================
    function DrawHouseCell(blockX, blockZ, cellX, cellZ, blockW, blockL) {
        var blockY = streetLevel + 1;

        // ground please
        FillCube(BlockID.GRASS, blockX, blockY, blockZ, blockX + blockW - 1, blockY, blockZ + blockL - 1);

        // pick a color
        var wallID = EncodeBlock(BlockID.CLOTH, rand.nextInt(16));
        var ceilingID = BlockID.WOOD;
        var floorID = BlockID.CLOTH;

        // place the rooms
        PlaceRoom(ceilingID, wallID, floorID, blockX, blockY, blockZ, 0, 0, 1, 1, cellX, cellZ);
        PlaceRoom(ceilingID, wallID, floorID, blockX, blockY, blockZ, 0, 1, 1, 1, cellX, cellZ);
        PlaceRoom(ceilingID, wallID, floorID, blockX, blockY, blockZ, 1, 0, 1, 1, cellX, cellZ);
        PlaceRoom(ceilingID, wallID, floorID, blockX, blockY, blockZ, 1, 1, 1, 1, cellX, cellZ);

        // interior walkways
        PlaceWalkways(blockX, blockY, blockZ, 3, 0);
        PlaceWalkways(blockX, blockY, blockZ, 0, 3);

        // extrude roof
        for (var y = 1; y < floorHeight; y++)
            for (var x = 0; x < blockW; x++)
                for (var z = 0; z < blockL; z++)
                    if (blocks[blockX + x + 1][blockY + floorHeight + y - 1][blockZ + z] != BlockID.AIR &&
                        blocks[blockX + x - 1][blockY + floorHeight + y - 1][blockZ + z] != BlockID.AIR &&
                        blocks[blockX + x][blockY + floorHeight + y - 1][blockZ + z + 1] != BlockID.AIR &&
                        blocks[blockX + x][blockY + floorHeight + y - 1][blockZ + z - 1] != BlockID.AIR)
                        blocks[blockX + x][blockY + floorHeight + y][blockZ + z] = ceilingID;

        // carve out the attic
        for (var y = 1; y < floorHeight; y++)
            for (var x = 0; x < blockW; x++)
                for (var z = 0; z < blockL; z++)
                    if (blocks[blockX + x][blockY + floorHeight + y + 1][blockZ + z] != BlockID.AIR)
                        blocks[blockX + x][blockY + floorHeight + y][blockZ + z] = BlockID.AIR;
    }

    function PlaceWalkways(blockX, blockY, blockZ, offsetX, offsetZ) {
        var atX = blockX + 10;
        var atZ = blockZ + 10;

        blocks[atX + offsetX][blockY + 1][atZ + offsetZ] = BlockID.AIR;
        blocks[atX + offsetX][blockY + 2][atZ + offsetZ] = BlockID.AIR;

        blocks[atX - offsetX][blockY + 1][atZ - offsetZ] = BlockID.AIR;
        blocks[atX - offsetX][blockY + 2][atZ - offsetZ] = BlockID.AIR;
    }

    function PlaceRoom(ceilingID, wallID, floorID, blockX, blockY, blockZ, roomX, roomZ, roomW, roomL, cellX, cellZ) {
        var blockW = roomW * (6 + rand.nextInt(3));
        var blockL = roomL * (6 + rand.nextInt(3));
        var atX = blockX + 11 + (roomX == 0 ? -blockW : -1);
        var atZ = blockZ + 11 + (roomZ == 0 ? -blockL : -1);
        var windowOffset = 1;

        // room itself
        AddBox(ceilingID, wallID, floorID, atX, blockY, atZ, atX + blockW - 1, blockY + floorHeight, atZ + blockL - 1);

        // exterior windows
        RandomizeOffset();
        if (roomX == 0) {
            if (roomZ == 0) {
                blocks[atX][blockY + 1][atZ + 1] = BlockID.AIR;
                blocks[atX][blockY + 2][atZ + 1] = BlockID.AIR;
                SetLateBlock(atX, blockY + 1, atZ + 1, ExtendedID.NORTHFACING_WOODEN_DOOR);

                windowOffset = 3;
            }

            blocks[atX][blockY + 2][atZ + windowOffset] = BlockID.GLASS;
            blocks[atX][blockY + 3][atZ + windowOffset] = BlockID.GLASS;
            blocks[atX][blockY + 2][atZ + windowOffset + 1] = BlockID.GLASS;
            blocks[atX][blockY + 3][atZ + windowOffset + 1] = BlockID.GLASS;
        } else {
            if (roomZ == 1) {
                blocks[atX + blockW - 1][blockY + 1][atZ + 1] = BlockID.AIR;
                blocks[atX + blockW - 1][blockY + 2][atZ + 1] = BlockID.AIR;
                SetLateBlock(atX + blockW - 1, blockY + 1, atZ + 1, ExtendedID.SOUTHFACING_WOODEN_DOOR);

                windowOffset = 3;
            }

            blocks[atX + blockW - 1][blockY + 2][atZ + windowOffset] = BlockID.GLASS;
            blocks[atX + blockW - 1][blockY + 3][atZ + windowOffset] = BlockID.GLASS;
            blocks[atX + blockW - 1][blockY + 3][atZ + windowOffset + 1] = BlockID.GLASS;
            blocks[atX + blockW - 1][blockY + 2][atZ + windowOffset + 1] = BlockID.GLASS;
        }

        RandomizeOffset();
        if (roomZ == 0) {
            blocks[atX + windowOffset][blockY + 2][atZ] = BlockID.GLASS;
            blocks[atX + windowOffset][blockY + 3][atZ] = BlockID.GLASS;
            blocks[atX + windowOffset + 1][blockY + 2][atZ] = BlockID.GLASS;
            blocks[atX + windowOffset + 1][blockY + 3][atZ] = BlockID.GLASS;
        } else {
            blocks[atX + windowOffset][blockY + 2][atZ + blockL - 1] = BlockID.GLASS;
            blocks[atX + windowOffset][blockY + 3][atZ + blockL - 1] = BlockID.GLASS;
            blocks[atX + windowOffset + 1][blockY + 2][atZ + blockL - 1] = BlockID.GLASS;
            blocks[atX + windowOffset + 1][blockY + 3][atZ + blockL - 1] = BlockID.GLASS;
        }

        function RandomizeOffset() {
            windowOffset = 1 + rand.nextInt(3);
        }
    }

    function AddBox(ceilingID, wallID, floorID, minX, minY, minZ, maxX, maxY, maxZ) {
        for (var x = minX; x <= maxX; x++)
            for (var y = minY; y <= maxY; y++)
                for (var z = minZ; z <= maxZ; z++)
                    if (y == maxY)
                        blocks[x][y][z] = ceilingID
                    else if (x == minX || x == maxX || z == minZ || z == maxZ)
                        blocks[x][y][z] = wallID
                    else if (y == minY)
                        blocks[x][y][z] = floorID;
    }

    function DrawFarmCell(blockX, blockZ, cellX, cellZ, blockW, blockL) {
        var blockY = streetLevel + 1;
        var bedType = rand.nextInt(18);
        var nsOrient = OneInTwoChance();

        for (var x = 0; x < blockW; x++)
            for (var z = 0; z < blockL; z++)
                if (x == 0 || z == 0 || x == blockW - 1 || z == blockL - 1)
                    blocks[x + blockX][blockY][z + blockZ] = BlockID.SOIL
                else
                    switch (bedType) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8: // wheat
                        if (nsOrient) {
                            if (z % 2 == 0) {
                                blocks[x + blockX][blockY][z + blockZ] = EncodeBlock(BlockID.SOIL, 8);
                                SetLateBlock(x + blockX, blockY + 1, z + blockZ, EncodeBlock(BlockID.CROPS, rand.nextInt(5) + 3));
                            } else
                                blocks[x + blockX][blockY][z + blockZ] = BlockID.WATER;
                        }
                        else {
                            if (x % 2 == 0) {
                                blocks[x + blockX][blockY][z + blockZ] = EncodeBlock(BlockID.SOIL, 8);
                                SetLateBlock(x + blockX, blockY + 1, z + blockZ, EncodeBlock(BlockID.CROPS, rand.nextInt(5) + 3));
                            } else
                                blocks[x + blockX][blockY][z + blockZ] = BlockID.WATER;
                        }
                        break;
                    case 9:
                    case 10: // reeds
                        if (nsOrient) {
                            if (z % 2 == 0) {
                                blocks[x + blockX][blockY][z + blockZ] = BlockID.GRASS;
                                SetLateBlock(x + blockX, blockY + 1, z + blockZ, EncodeBlock(BlockID.REED, 15));
                                SetLateBlock(x + blockX, blockY + 2, z + blockZ, EncodeBlock(BlockID.REED, 15));
                                if (OneInTwoChance()) {
                                    SetLateBlock(x + blockX, blockY + 3, z + blockZ, EncodeBlock(BlockID.REED, 15));
                                    if (OneInTwoChance()) {
                                        SetLateBlock(x + blockX, blockY + 4, z + blockZ, EncodeBlock(BlockID.REED, 15));
                                    }
                                }
                            } else
                                blocks[x + blockX][blockY][z + blockZ] = BlockID.WATER;
                        }
                        else {
                            if (x % 2 == 0) {
                                blocks[x + blockX][blockY][z + blockZ] = BlockID.GRASS;
                                SetLateBlock(x + blockX, blockY + 1, z + blockZ, EncodeBlock(BlockID.REED, 15));
                                SetLateBlock(x + blockX, blockY + 2, z + blockZ, EncodeBlock(BlockID.REED, 15));
                                if (OneInTwoChance()) {
                                    SetLateBlock(x + blockX, blockY + 3, z + blockZ, EncodeBlock(BlockID.REED, 15));
                                    if (OneInTwoChance()) {
                                        SetLateBlock(x + blockX, blockY + 4, z + blockZ, EncodeBlock(BlockID.REED, 15));
                                    }
                                }
                            } else
                                blocks[x + blockX][blockY][z + blockZ] = BlockID.WATER;
                        }
                        break;
                    case 11: // cactus
                        blocks[x + blockX][blockY][z + blockZ] = BlockID.SAND;
                        if (x % 2 == 0 && z % 2 == 1) {
                            SetLateBlock(x + blockX, blockY + 1, z + blockZ, EncodeBlock(BlockID.CACTUS, 15));
                            if (OneInTwoChance()) {
                                SetLateBlock(x + blockX, blockY + 2, z + blockZ, EncodeBlock(BlockID.CACTUS, 15));
                                if (OneInTwoChance()) {
                                    SetLateBlock(x + blockX, blockY + 3, z + blockZ, EncodeBlock(BlockID.CACTUS, 15));
                                }
                            }
                        }
                        break;
                    case 12: // roses
                        blocks[x + blockX][blockY][z + blockZ] = BlockID.GRASS;
                        SetLateBlock(x + blockX, blockY + 1, z + blockZ, BlockID.RED_FLOWER);
                        break;
                    case 13: // dandelion 
                        blocks[x + blockX][blockY][z + blockZ] = BlockID.GRASS;
                        SetLateBlock(x + blockX, blockY + 1, z + blockZ, BlockID.YELLOW_FLOWER);
                        break;
                    case 14: // random roses and dandelions
                        if (nsOrient) {
                            if (z % 2 == 0) {
                                blocks[x + blockX][blockY][z + blockZ] = BlockID.GRASS;
                                if (OneInTwoChance())
                                    SetLateBlock(x + blockX, blockY + 1, z + blockZ, BlockID.RED_FLOWER)
                                else
                                    SetLateBlock(x + blockX, blockY + 1, z + blockZ, BlockID.YELLOW_FLOWER);
                            } else
                                blocks[x + blockX][blockY][z + blockZ] = BlockID.WATER;
                        }
                        else {
                            if (x % 2 == 0) {
                                blocks[x + blockX][blockY][z + blockZ] = BlockID.GRASS;
                                if (OneInTwoChance())
                                    SetLateBlock(x + blockX, blockY + 1, z + blockZ, BlockID.RED_FLOWER)
                                else
                                    SetLateBlock(x + blockX, blockY + 1, z + blockZ, BlockID.YELLOW_FLOWER);
                            } else
                                blocks[x + blockX][blockY][z + blockZ] = BlockID.WATER;
                        }
                        break;
                    case 15: // trees
                        blocks[x + blockX][blockY][z + blockZ] = BlockID.GRASS;
                        if (x % 5 == 2 && z % 5 == 2)
                            SetLateTree(x + blockX, blockY, z + blockZ, false);
                        break;
                    case 16: // pumpkins
                        blocks[x + blockX][blockY][z + blockZ] = BlockID.DIRT;
                        if (x % 5 == 2 && z % 5 == 2) {
                            blocks[x + blockX][blockY][z + blockZ] = BlockID.LOG;
                            blocks[x + blockX][blockY + 1][z + blockZ] = BlockID.LEAVES;
                        }
                        if (nsOrient) {
                            if (z % 5 == 2) {
                                blocks[x + blockX][blockY + 1][z + blockZ] = BlockID.LEAVES;

                                if (OneInTwoChance())
                                    if (OneInFourChance())
                                        blocks[x + blockX][blockY + 1][z + blockZ - 1] = BlockID.PUMPKIN
                                    else
                                        blocks[x + blockX][blockY + 1][z + blockZ - 1] = BlockID.LEAVES;

                                if (OneInTwoChance())
                                    if (OneInFourChance())
                                        blocks[x + blockX][blockY + 1][z + blockZ + 1] = BlockID.PUMPKIN
                                    else
                                        blocks[x + blockX][blockY + 1][z + blockZ + 1] = BlockID.LEAVES;

                                if (OneInThreeChance())
                                    blocks[x + blockX][blockY + 2][z + blockZ] = BlockID.LEAVES;
                            }
                        }
                        else {
                            if (x % 5 == 2) {
                                blocks[x + blockX][blockY + 1][z + blockZ] = BlockID.LEAVES;

                                if (OneInTwoChance())
                                    if (OneInFourChance())
                                        blocks[x + blockX - 1][blockY + 1][z + blockZ] = BlockID.PUMPKIN
                                    else
                                        blocks[x + blockX - 1][blockY + 1][z + blockZ] = BlockID.LEAVES;

                                if (OneInTwoChance())
                                    if (OneInFourChance())
                                        blocks[x + blockX + 1][blockY + 1][z + blockZ] = BlockID.PUMPKIN
                                    else
                                        blocks[x + blockX + 1][blockY + 1][z + blockZ] = BlockID.LEAVES;

                                if (OneInThreeChance())
                                    blocks[x + blockX][blockY + 2][z + blockZ] = BlockID.LEAVES;
                            }
                        }
                        break;
                    default: // unplanted rows
                        if (nsOrient) {
                            if (z % 2 == 0)
                                blocks[x + blockX][blockY][z + blockZ] = EncodeBlock(BlockID.SOIL, 8);
                            else
                                blocks[x + blockX][blockY][z + blockZ] = BlockID.WATER;
                        }
                        else {
                            if (x % 2 == 0)
                                blocks[x + blockX][blockY][z + blockZ] = EncodeBlock(BlockID.SOIL, 8);
                            else
                                blocks[x + blockX][blockY][z + blockZ] = BlockID.WATER;
                        }
                        break;
                }
    }
}

function AddParkingLot() {

    // set up the default set of materials
    var lightID = BlockID.LIGHTSTONE;
    var floorID = BlockID.STONE;
    var ceilingID = BlockID.IRON_BLOCK;
    var paintID = ExtendedID.WHITE_CLOTH;
    var ramp1ID = ExtendedID.STONE_STEP;
    var ramp2ID = ExtendedID.STONE_DOUBLESTEP;

    // randomize the materials
    switch (rand.nextInt(3)) {
        case 0:
            {
                floorID = BlockID.SANDSTONE;
                ceilingID = ExtendedID.YELLOW_CLOTH;
                paintID = ExtendedID.GRAY_CLOTH;
                ramp1ID = ExtendedID.SANDSTONE_STEP;
                ramp2ID = ExtendedID.SANDSTONE_DOUBLESTEP;
                break;
            }
        case 1:
            {
                floorID = BlockID.COBBLESTONE;
                ceilingID = ExtendedID.WHITE_CLOTH;
                paintID = ExtendedID.LIGHT_GRAY_CLOTH;
                ramp1ID = ExtendedID.COBBLESTONE_STEP;
                ramp2ID = ExtendedID.COBBLESTONE_DOUBLESTEP;
                break;
            }
        default:
            {
                // stick with the defaults
            }
    }

    // set up a different set of default materials
    var fenceID = BlockID.IRON_BLOCK;
    var verticalID = BlockID.STONE;
    var upperWallID = BlockID.AIR;
    var lowerWallID = BlockID.STONE;

    // randomize the materials
    switch (rand.nextInt(3)) {
        case 0:
            {
                fenceID = BlockID.FENCE;
                verticalID = BlockID.SANDSTONE;
                lowerWallID = BlockID.SANDSTONE;
                break;
            }
        case 1:
            {
                fenceID = BlockID.IRON_BLOCK;
                verticalID = BlockID.COBBLESTONE;
                lowerWallID = BlockID.COBBLESTONE;
                break;
            }
        default:
            {
                // stick with the defaults
            }
    }

    // swizzle the material a bit more
    if (OneInThreeChance())
        ceilingID = floorID;
    if (OneInThreeChance())
        fenceID = ceilingID
    else if (OneInFourChance())
        fenceID = BlockID.GLASS;
    if (OneInTwoChance() && fenceID != BlockID.FENCE)
        upperWallID = BlockID.GLASS;

    // how tall and is there a basement?
    var floors = rand.nextInt(6) + 1;
    var basement = (floors > 1 || OneInTwoChance()) || (floors > 4);

    // make the floors
    var floor = 1;
    var floorY = streetLevel;
    for (var floor = 1; floor <= floors; floor++) {

        // special case for basements
        if (floor == 1 && basement) {

            // make room            
            FillCube(BlockID.AIR, 1 * squareBlocks, plumbingHeight - 1, 1 * squareBlocks,
                                  4 * squareBlocks - 1, streetLevel, 4 * squareBlocks - 1);

            // now draw the basement
            floorY = floorY - 5;
            DrawParkingLevel(floorY, true, false, false);

            // special case for the first floor if there is no basement
        } else if (floor == 1 && !basement) {

            // backfill
            FillCube(BlockID.DIRT, 1 * squareBlocks, plumbingHeight, 1 * squareBlocks,
                                   4 * squareBlocks - 1, streetLevel, 4 * squareBlocks - 1);

            // now draw the only floor
            DrawParkingLevel(floorY, false, true, floor == floors);
        }

        // now for the rest of the floors
        else
            DrawParkingLevel(floorY, false, basement && floor == 2, floor == floors);

        // move up a bit
        floorY = floorY + 5;
    }

    // now go back and punch the ramps
    floorY = streetLevel;
    for (var floor = 1; floor <= floors; floor++) {

        // something special for basements
        if (floor == 1 && basement)
            floorY = floorY - 5;

        // now how about the ramp
        PunchRamp(floorY, floor == 1, floor == floors);

        // move up a bit
        floorY = floorY + 5;
    }

    function DrawParkingLevel(blockY, basementFloor, groundFloor, topFloor) {

        // draw the floor
        FillCube(floorID, 1 * squareBlocks, blockY, 1 * squareBlocks,
                          4 * squareBlocks - 1, blockY, 4 * squareBlocks - 1);

        // fill in the walls if we are underground
        if (basementFloor && !topFloor)
            AddWalls(lowerWallID, 1 * squareBlocks, blockY + 1, 1 * squareBlocks,
                                  4 * squareBlocks - 1, blockY + 3, 4 * squareBlocks - 1)
        else {

            // now the railings
            AddWalls(fenceID, 1 * squareBlocks, blockY + 1, 1 * squareBlocks,
                             4 * squareBlocks - 1, blockY + 1, 4 * squareBlocks - 1);

            // and the rest of the wall
            if (!topFloor) {
                AddWalls(upperWallID, 1 * squareBlocks, blockY + 2, 1 * squareBlocks,
                                      4 * squareBlocks - 1, blockY + 3, 4 * squareBlocks - 1);

                // now the columns
                DrawColumn(1 * squareBlocks, blockY, 1 * squareBlocks, false, true, true, false);
                DrawColumn(2 * squareBlocks, blockY, 1 * squareBlocks, true, false, false, false);
                DrawColumn(3 * squareBlocks - 1, blockY, 1 * squareBlocks, false, false, true, false);
                DrawColumn(4 * squareBlocks - 1, blockY, 1 * squareBlocks, true, true, false, false);
                DrawColumn(1 * squareBlocks, blockY, 4 * squareBlocks - 1, false, false, true, true);
                DrawColumn(2 * squareBlocks, blockY, 4 * squareBlocks - 1, true, false, false, false);
                DrawColumn(3 * squareBlocks - 1, blockY, 4 * squareBlocks - 1, false, false, true, false);
                DrawColumn(4 * squareBlocks - 1, blockY, 4 * squareBlocks - 1, true, false, false, true);
                DrawColumn(4 * squareBlocks - 1, blockY, 2 * squareBlocks, false, false, false, true);
                DrawColumn(4 * squareBlocks - 1, blockY, 3 * squareBlocks - 1, false, true, false, false);
            }
        }

        // draw the outer lines
        for (var a = 4; a < 3 * squareBlocks - 4; a = a + 4) {
            for (var b = 1; b <= 4; b++) {
                blocks[a + squareBlocks][blockY][1 * squareBlocks + b] = paintID;
                blocks[a + squareBlocks][blockY][4 * squareBlocks - b - 1] = paintID;
                blocks[4 * squareBlocks - b - 1][blockY][a + squareBlocks] = paintID;
                // except for the north side where the ramp is
                //blocks[1 * squareBlocks + b][blockY][a + squareBlocks] = paintID;
            }
        }

        // draw the inner lines
        if (floors == 1) {
            DrawParkingCenter(2 * squareBlocks - 8, blockY, 2 * squareBlocks, topFloor, 7);
            DrawParkingCenter(2 * squareBlocks - 8, blockY, 3 * squareBlocks - 1, topFloor, 7);
        } else {
            DrawParkingCenter(2 * squareBlocks - 1, blockY, 2 * squareBlocks, topFloor, 5);
            DrawParkingCenter(2 * squareBlocks - 1, blockY, 3 * squareBlocks - 1, topFloor, 5);
        }

        // draw the ceiling
        if (!topFloor) {
            FillCube(ceilingID, 1 * squareBlocks, blockY + 4, 1 * squareBlocks,
                                4 * squareBlocks - 1, blockY + 4, 4 * squareBlocks - 1);

            // add some ceiling lights
            for (var x = squareBlocks + 5; x < 4 * squareBlocks; x = x + 5)
                for (var z = squareBlocks + 5; z < 4 * squareBlocks; z = z + 5) {
                    blocks[x - 1][blockY + 4][z] = lightID;
                    blocks[x][blockY + 4][z] = lightID;
                    blocks[x + 1][blockY + 4][z] = lightID;
                }
        }

        // empty the driveways
        if (groundFloor) {
            for (var z = 2 * squareBlocks - 7; z < 2 * squareBlocks - 2; z++) {
                for (var y = blockY + 1; y < blockY + 4; y++) {
                    blocks[squareBlocks][y][z] = BlockID.AIR;
                    blocks[squareBlocks][y][z + 2 * squareBlocks - 6] = BlockID.AIR;

                    for (var x = 1; x < 4; x++) {
                        blocks[squareBlocks - x][blockY + 1][z] = BlockID.AIR;
                        blocks[squareBlocks - x][blockY + 1][z + 2 * squareBlocks - 6] = BlockID.AIR;
                    }
                }
            }
        }

        // Manhole down to the plumbing... WATCH OUT IT IS DANGEROUS DOWN THERE!
        if (basementFloor) {
            blocks[1 * squareBlocks + 1][blockY][1 * squareBlocks + 1] = BlockID.AIR;
            blocks[1 * squareBlocks + 1][blockY - 1][1 * squareBlocks + 1] = BlockID.AIR;
            blocks[1 * squareBlocks + 1][blockY - 2][1 * squareBlocks + 1] = BlockID.AIR;
            blocks[1 * squareBlocks + 1][blockY - 3][1 * squareBlocks + 1] = BlockID.AIR;
            SetLateBlock(1 * squareBlocks + 1, blockY + 1, 1 * squareBlocks + 1, ExtendedID.WESTFACING_TRAP_DOOR);
        }

        function DrawColumn(blockX, blockY, blockZ, north, west, south, east) {
            for (var y = blockY; y < blockY + 4; y++) {
                blocks[blockX][y][blockZ] = verticalID;
                if (north)
                    blocks[blockX - 1][y][blockZ] = verticalID;
                if (west)
                    blocks[blockX][y][blockZ + 1] = verticalID;
                if (south)
                    blocks[blockX + 1][y][blockZ] = verticalID;
                if (east)
                    blocks[blockX][y][blockZ - 1] = verticalID;
            }
        }

        // inner lines support
        function DrawParkingCenter(blockX, blockY, blockZ, topFloor, spots) {
            for (var x = 0; x <= 4 * spots; x++) {
                if (x % 4 == 0) {
                    for (var z = blockZ - 4; z <= blockZ + 4; z++) {
                        if (x == 0 || x == 4 * spots) {
                            if (topFloor && (z == blockZ - 4 || z == blockZ + 4))
                                DrawStreetlight(blockX + x, blockY + 1, z, true, false, true, false)
                            else
                                blocks[blockX + x][blockY + 1][z] = fenceID;
                        } else
                            blocks[blockX + x][blockY][z] = paintID;
                    }
                } else
                    blocks[blockX + x][blockY][blockZ] = paintID;

                // center columns?
                if (!topFloor && (x == 0 || x == 4 * spots)) {
                    blocks[blockX + x][blockY + 1][blockZ] = verticalID;
                    blocks[blockX + x][blockY + 2][blockZ] = verticalID;
                    blocks[blockX + x][blockY + 3][blockZ] = verticalID;
                }
            }
        }
    }

    // a way down, a way up
    function PunchRamp(blockY, lowestFloor, topFloor) {
        if (!(lowestFloor && topFloor)) {
            if (!topFloor)
                FillCube(BlockID.AIR, squareBlocks + 1, blockY + 1, 2 * squareBlocks - 2,
                                      squareBlocks + 6, blockY + 5, 2 * squareBlocks - 2 + 18);

            // walls for the ramps
            for (var z = 0; z < 19; z++) {

                // only if we are not on top
                if (!topFloor) {
                    for (var y = blockY + 1; y < blockY + 4; y++) {
                        blocks[squareBlocks + 7][y][2 * squareBlocks + z - 2] = verticalID;
                        blocks[squareBlocks][y][2 * squareBlocks + z - 2] = verticalID;
                    }
                } else {
                    blocks[squareBlocks + 7][blockY + 1][2 * squareBlocks + z - 2] = fenceID;
                    blocks[squareBlocks][blockY + 1][2 * squareBlocks + z - 2] = fenceID;
                    for (var x = 1; x < 8; x++)
                        blocks[squareBlocks + x][blockY + 1][2 * squareBlocks - 2] = fenceID;
                }
            }

            // ramps for the walls
            if (!topFloor) {
                DrawRampSegment(squareBlocks + 1, blockY + 1, 2 * squareBlocks - 2, false);
                DrawRampSegment(squareBlocks + 1, blockY + 2, 2 * squareBlocks + 2, false);
                DrawRampSegment(squareBlocks + 1, blockY + 3, 2 * squareBlocks + 6, false);
                DrawRampSegment(squareBlocks + 1, blockY + 4, 2 * squareBlocks + 10, false);
                DrawRampSegment(squareBlocks + 1, blockY + 5, 2 * squareBlocks + 14, true);
            }
        }

        function DrawRampSegment(blockX, blockY, blockZ, lastRamp) {
            for (var x = 0; x < 6; x++) {
                blocks[blockX + x][blockY][blockZ] = ramp1ID;
                blocks[blockX + x][blockY][blockZ + 1] = ramp1ID;
                blocks[blockX + x][blockY][blockZ + 2] = ramp2ID;
                if (!lastRamp)
                    blocks[blockX + x][blockY][blockZ + 3] = ramp2ID;
                else {
                    blocks[blockX + x][blockY - 1][blockZ] = ceilingID;
                    blocks[blockX + x][blockY - 1][blockZ + 1] = ceilingID;
                    blocks[blockX + x][blockY - 1][blockZ + 2] = ceilingID;
                }
            }
        }
    }
}

function AddJustStreets() {
    // let see if we can literally do nothing... 
    // ...hey it worked!
}

function AddManholes() {
    var manX = 1; // - 1
    var manZ = 3; // + 1

    // add the manholes
    AddSewerManhole(manX + 0 * squareBlocks, belowGround, manZ + 0 * squareBlocks);
    AddSewerManhole(manX + 0 * squareBlocks, belowGround, manZ + 4 * squareBlocks);
    AddSewerManhole(manX + 4 * squareBlocks, belowGround, manZ + 0 * squareBlocks);
    AddSewerManhole(manX + 4 * squareBlocks, belowGround, manZ + 4 * squareBlocks);

    //======================================================
    function AddSewerManhole(locX, locY, locZ) {

        // DrawSewerPart took care of empting the room and placing the door

        // Record the need for the ladders
        for (var y = 1; y < sewerHeight + streetHeight; y++)
            SetLateBlock(locX, locY - y, locZ, ExtendedID.EASTWARD_LADDER);
        SetLateBlock(locX, locY, locZ, ExtendedID.WESTFACING_TRAP_DOOR);

        // Manhole down to the plumbing... WATCH OUT IT IS DANGEROUS DOWN THERE!
        SetLateBlock(locX + 3, locY - sewerHeight - streetHeight + 1, locZ + 2, ExtendedID.WESTFACING_TRAP_DOOR);
        SetLateBlock(locX + 3, locY - sewerHeight - streetHeight, locZ + 2, ExtendedID.SOUTHWARD_LADDER);
        SetLateBlock(locX + 3, locY - sewerHeight - streetHeight - 1, locZ + 2, ExtendedID.SOUTHWARD_LADDER);
        SetLateBlock(locX + 3, locY - sewerHeight - streetHeight - 2, locZ + 2, ExtendedID.SOUTHWARD_LADDER);
        blocks[locX + 3][locY - sewerHeight - streetHeight - 3][locZ + 2] = BlockID.AIR;
    }
}