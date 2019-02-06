package me.daddychurchill.CityWorld.Support;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import static me.daddychurchill.CityWorld.Support.CornerBlocks.UnitType.*;

public final class CornerBlocks {

	public final static int CornerWidth = 7;

	// TODO I have made this generate glass blocks instead of glass panes due to
	// oddities with direction and 1.13.2. I will need to think hard on how we can
	// do this the right way
	enum UnitType {
		non, // nothing here
		opt,
		FLR,
		WWW, // solid vertical wall
		GGG, // vertical glass wall
		WGG, // wall - glass - glass : solid bottom block topped with two blocks of glass
		GWG, // glass - wall - glass
		GGW, // glass - glass - wall
		GWW, // glass - wall - wall
		WGW, // wall - glass - wall
		WWG, // wall - wall - glass
		Wgg, // wall - pane - pane
		ggg, // pane - pane - pane
		ggW, // pane - pane - wall

		// these won't show up on the roof
		BRN, // balcony floor block, single iron railing, and nothing above it
		BrN, // balcony floor block, single wood fence, and nothing above it
		BgN, // balcony floor block, single thin glass pane, and nothing above it
		BDD, // balcony floor block, door (auto detect direction in convex angle) , and wall
		Bdd, // balcony floor block, door (auto detect direction in concave angle) , and wall

		// blocks above it
		BNN, // balcony floor block, and nothing but air above it
		BWW, // balcony floor block, and wall blocks above it (WWW)
		BGG, // balcony floor block, and glass above it (WGG)
		BGW, // balcony floor block, glass and wall blocks above it (WGW)

		Wnn, // single wall block, and nothing but air above it
		nWn, // nothing but air, glass block, topped with nothing but air
		nnW, // nothing but air, topped with a single wall block
	}

	public boolean isOldRoundedCorner(int i) {
		return i == 0; // should always be the first one
	}

	public int pickCornerStyle(Odds odds) {
		return odds.getRandomInt(corners.size());
	}

	private Corner getCorner(int i) {
		return corners.get(i % corners.size()); // insure that the index is within the range of defined corners
	}

	public CornerBlocks() {
		corners = new ArrayList<>();

		corners.add(new RoundedCorner()); // always put this in first

//		corners.add(new CustomCorner(new byte[][] {
//		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, WGG, opt },
				{ FLR, FLR, FLR, FLR, WGG, opt, non },
				{ FLR, FLR, FLR, WWW, opt, non, non },
				{ FLR, FLR, WGG, opt, non, non, non },
				{ FLR, WGG, opt, non, non, non, non },
				{ WWW, opt, non, non, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WGG },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WGG },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WGG },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, WGG, opt },
				{ FLR, FLR, FLR, FLR, WGG, opt, non },
				{ WGG, WGG, WGG, WWW, opt, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WGG },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WGG },
				{ FLR, FLR, FLR, WWW, WGG, WGG, WWW },
				{ FLR, FLR, FLR, WGG, non, non, non },
				{ FLR, FLR, FLR, WGG, non, non, non },
				{ WWW, WGG, WGG, WWW, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, Wgg },
				{ FLR, FLR, FLR, FLR, FLR, FLR, Wgg },
				{ FLR, FLR, FLR, WWW, Wgg, Wgg, WWW },
				{ FLR, FLR, FLR, Wgg, non, non, non },
				{ FLR, FLR, FLR, Wgg, non, non, non },
				{ WWW, Wgg, Wgg, WWW, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, WGG, opt },
				{ FLR, FLR, FLR, FLR, WWW, opt, non },
				{ FLR, FLR, FLR, FLR, FLR, WGW, non },
				{ FLR, FLR, WWW, FLR, FLR, FLR, WGW },
				{ FLR, WGG, opt, WGW, FLR, WGW, opt },
				{ WWW, opt, non, non, WGW, opt, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, GGG },
				{ FLR, FLR, FLR, FLR, WWW, GGG, GGG },
				{ FLR, FLR, FLR, FLR, GGG, non, non },
				{ FLR, FLR, WWW, GGG, GGG, non, non },
				{ FLR, FLR, GGG, non, non, non, non },
				{ WWW, GGG, GGG, non, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, ggg },
				{ FLR, FLR, FLR, FLR, WWW, ggg, ggg },
				{ FLR, FLR, FLR, FLR, ggg, non, non },
				{ FLR, FLR, WWW, ggg, ggg, non, non },
				{ FLR, FLR, ggg, non, non, non, non },
				{ WWW, ggg, ggg, non, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WGG, opt },
				{ FLR, FLR, FLR, FLR, FLR, WGG, non },
				{ FLR, FLR, FLR, FLR, FLR, WWW, non },
				{ FLR, FLR, FLR, FLR, WGG, opt, non },
				{ FLR, FLR, FLR, WGG, opt, non, non },
				{ WGG, WGG, WWW, opt, non, non, non },
				{ opt, non, non, non, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, WGG, WGG, WGW },
				{ FLR, FLR, FLR, WGG, opt, non, non },
				{ FLR, FLR, WGG, opt, non, non, non },
				{ FLR, FLR, WGG, non, non, non, non },
				{ WWW, WWW, WGW, non, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, WGG, WGG, WGG, WGG, WGG },
				{ FLR, FLR, WGG, non, non, non, non },
				{ FLR, FLR, WGG, non, WWW, WWW, WWW },
				{ FLR, FLR, WGG, non, WWW, WWW, WWW },
				{ WWW, WWW, WGG, non, WWW, WWW, WWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, WGG, WGG, WGG, WGG, WGG },
				{ FLR, FLR, WGG, non, non, non, non },
				{ FLR, FLR, WGG, non, WWW, nWn, WWW },
				{ FLR, FLR, WGG, non, nWn, WWW, nWn },
				{ WWW, WWW, WGG, non, WWW, nWn, WWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, Wgg, Wgg, Wgg, Wgg, Wgg },
				{ FLR, FLR, Wgg, non, non, non, non },
				{ FLR, FLR, Wgg, non, WWW, WWW, WWW },
				{ FLR, FLR, Wgg, non, WWW, WWW, WWW },
				{ WWW, WWW, Wgg, non, WWW, WWW, WWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, Wgg, Wgg, Wgg, Wgg, Wgg },
				{ FLR, FLR, Wgg, non, non, non, non },
				{ FLR, FLR, Wgg, non, WWW, nWn, WWW },
				{ FLR, FLR, Wgg, non, nWn, WWW, nWn },
				{ WWW, WWW, Wgg, non, WWW, nWn, WWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, GWG, GWG, GWG, GWG, GWG },
				{ FLR, FLR, GWG, nWn, nWn, nWn, nWn },
				{ FLR, FLR, GWG, nWn, non, non, non },
				{ FLR, FLR, GWG, nWn, non, non, non },
				{ WWW, WWW, GWG, nWn, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, GWG, GWG, GWG, GWG, GWG },
				{ FLR, FLR, GWG, nWn, nWn, nWn, WWW },
				{ FLR, FLR, GWG, nWn, non, non, non },
				{ FLR, FLR, GWG, nWn, non, non, non },
				{ WWW, WWW, GWG, WWW, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, GGW },
				{ FLR, FLR, GGW, GGW, GGW, GGW, GGW },
				{ FLR, FLR, GGW, non, non, non, non },
				{ FLR, FLR, GGW, non, non, non, non },
				{ FLR, FLR, GGW, non, non, non, non },
				{ WWW, GGW, GGW, non, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, GGW, GGW, GGW, GGW, nnW },
				{ FLR, FLR, GGW, nnW, nnW, nnW, nnW },
				{ FLR, FLR, GGW, nnW, non, non, non },
				{ FLR, FLR, GGW, nnW, non, non, non },
				{ WWW, WWW, nnW, nnW, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, ggW },
				{ FLR, FLR, ggW, ggW, ggW, ggW, ggW },
				{ FLR, FLR, ggW, non, non, non, non },
				{ FLR, FLR, ggW, non, non, non, non },
				{ FLR, FLR, ggW, non, non, non, non },
				{ WWW, ggW, ggW, non, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, ggW, WWW },
				{ FLR, FLR, ggW, ggW, ggW, ggW, nnW },
				{ FLR, FLR, ggW, nnW, nnW, nnW, nnW },
				{ FLR, FLR, ggW, nnW, non, non, non },
				{ FLR, ggW, ggW, nnW, non, non, non },
				{ WWW, WWW, nnW, nnW, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WGG, Wnn },
				{ FLR, FLR, FLR, FLR, FLR, WGG, Wnn },
				{ FLR, FLR, FLR, FLR, WGG, Wnn, non },
				{ FLR, FLR, FLR, WGG, Wnn, non, non },
				{ FLR, FLR, WGG, Wnn, non, non, non },
				{ WGG, WGG, Wnn, non, non, non, non },
				{ Wnn, Wnn, non, non, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, GGG, GGG, GGG, GGG, WGW },
				{ FLR, FLR, GGG, non, non, non, WWW },
				{ FLR, FLR, GGG, non, non, non, non },
				{ FLR, FLR, GGG, non, non, non, non },
				{ WWW, WWW, WGW, WWW, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, ggg, ggg, ggg, ggg, WGW },
				{ FLR, FLR, ggg, non, non, non, WWW },
				{ FLR, FLR, ggg, non, non, non, non },
				{ FLR, FLR, ggg, non, non, non, non },
				{ WWW, WWW, WGW, WWW, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWG },
				{ FLR, FLR, FLR, FLR, FLR, WGW, non },
				{ FLR, FLR, FLR, FLR, GWW, non, non },
				{ FLR, FLR, FLR, GWW, non, non, non },
				{ FLR, FLR, WGW, non, non, non, non },
				{ WWW, WWG, non, non, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, WGG, WGG, WGG, WGG, WGG },
				{ FLR, FLR, WGG, non, non, non, non },
				{ FLR, FLR, WGG, non, nWn, nWn, nWn },
				{ FLR, FLR, WGG, non, nWn, WWW, nWn },
				{ WWW, WWW, WGG, non, nWn, nWn, nWn },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, WGG, WGG, WGG, WGG, WGG },
				{ FLR, FLR, WGG, non, non, non, non },
				{ FLR, FLR, WGG, non, WWW, non, WWW },
				{ FLR, FLR, WGG, non, non, non, WWW },
				{ WWW, WWW, WGG, non, WWW, WWW, WWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, WGG, WGG, WGG, WGG, WGG },
				{ FLR, FLR, WGG, non, non, non, non },
				{ FLR, FLR, WGG, non, WWW, WWW, WWW },
				{ FLR, FLR, WGG, non, WWW, non, non },
				{ WWW, WWW, WGG, non, WWW, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, WGG, WGG, WGG, WGG, WGG },
				{ FLR, FLR, WGG, opt, opt, non, non },
				{ FLR, FLR, WGG, opt, opt, opt, WWW },
				{ FLR, FLR, WGG, non, opt, WWW, non },
				{ WWW, WWW, WGG, non, WWW, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, WGG, WGG, WGG, WGG, WGG },
				{ FLR, FLR, WGG, non, non, non, opt },
				{ FLR, FLR, WGG, non, non, non, WWW },
				{ FLR, FLR, WGG, non, non, non, opt },
				{ WWW, WWW, WGG, opt, WWW, opt, WWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, Wgg, Wgg, Wgg, Wgg, Wgg },
				{ FLR, FLR, Wgg, non, non, non, non },
				{ FLR, FLR, Wgg, non, nWn, nWn, nWn },
				{ FLR, FLR, Wgg, non, nWn, WWW, nWn },
				{ WWW, WWW, Wgg, non, nWn, nWn, nWn },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, Wgg, Wgg, Wgg, Wgg, Wgg },
				{ FLR, FLR, Wgg, non, non, non, non },
				{ FLR, FLR, Wgg, non, WWW, non, WWW },
				{ FLR, FLR, Wgg, non, non, non, WWW },
				{ WWW, WWW, Wgg, non, WWW, WWW, WWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, Wgg, Wgg, Wgg, Wgg, Wgg },
				{ FLR, FLR, Wgg, non, non, non, non },
				{ FLR, FLR, Wgg, non, WWW, WWW, WWW },
				{ FLR, FLR, Wgg, non, WWW, non, non },
				{ WWW, WWW, Wgg, non, WWW, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, Wgg, Wgg, Wgg, Wgg, Wgg },
				{ FLR, FLR, Wgg, opt, opt, non, non },
				{ FLR, FLR, Wgg, opt, opt, opt, WWW },
				{ FLR, FLR, Wgg, non, opt, WWW, non },
				{ WWW, WWW, Wgg, non, WWW, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, Wgg, Wgg, Wgg, Wgg, Wgg },
				{ FLR, FLR, Wgg, non, non, non, opt },
				{ FLR, FLR, Wgg, non, non, non, WWW },
				{ FLR, FLR, Wgg, non, non, non, opt },
				{ WWW, WWW, Wgg, opt, WWW, opt, WWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, GGG, WWW },
				{ FLR, FLR, FLR, FLR, GGG, opt, non },
				{ FLR, FLR, FLR, WWW, WWW, non, non },
				{ FLR, FLR, GGG, WWW, WWW, WWW, non },
				{ FLR, GGG, opt, non, WWW, WWW, WWW },
				{ WWW, WWW, non, non, non, WWW, WWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, GGG },
				{ FLR, FLR, FLR, FLR, WWW, GGG, GGG },
				{ FLR, FLR, FLR, FLR, GGG, non, non },
				{ FLR, FLR, WWW, GGG, GGG, non, non },
				{ FLR, FLR, GGG, non, non, GGG, non },
				{ WWW, GGG, GGG, non, non, non, GGG },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, WGW, non },
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WGW },
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ WWW, WGW, WWW, FLR, WWW, non, non },
				{ WWW, non, WWW, WGW, WWW, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, WGG, non },
				{ FLR, FLR, FLR, FLR, FLR, WGG, non },
				{ FLR, FLR, FLR, FLR, FLR, WGG, non },
				{ FLR, FLR, FLR, FLR, FLR, WGG, non },
				{ WWW, WGG, WGG, WGG, WGG, WGG, non },
				{ WWW, non, non, non, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, WGG, non },
				{ FLR, FLR, FLR, FLR, FLR, WGG, non },
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WGG },
				{ WWW, WGG, WGG, WWW, FLR, FLR, WGG },
				{ WWW, non, non, WWW, WGG, WGG, WGG },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, Wgg, non },
				{ FLR, FLR, FLR, FLR, FLR, Wgg, non },
				{ FLR, FLR, FLR, FLR, FLR, Wgg, non },
				{ FLR, FLR, FLR, FLR, FLR, Wgg, non },
				{ WWW, Wgg, Wgg, Wgg, Wgg, Wgg, non },
				{ WWW, non, non, non, non, non, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, Wgg, non },
				{ FLR, FLR, FLR, FLR, FLR, Wgg, non },
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, Wgg },
				{ WWW, Wgg, Wgg, WWW, FLR, FLR, Wgg },
				{ WWW, non, non, WWW, Wgg, Wgg, Wgg },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, FLR, WGW, BNN, BRN },
				{ FLR, FLR, FLR, FLR, BDD, BNN, BRN },
				{ FLR, FLR, FLR, FLR, WGG, BNN, BRN },
				{ WWW, WGW, BDD, WGG, WGG, BNN, BRN },
				{ WWW, BNN, BNN, BNN, BNN, BNN, BRN },
				{ WWW, BRN, BRN, BRN, BRN, BRN, BWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, FLR, BGW, BNN, BRN },
				{ FLR, FLR, FLR, FLR, BDD, BNN, BRN },
				{ FLR, FLR, FLR, FLR, BGW, BNN, BRN },
				{ WWW, BGW, BDD, BGW, BWW, BNN, BRN },
				{ WWW, BNN, BNN, BNN, BNN, BWW, BWW },
				{ WWW, BRN, BRN, BRN, BRN, BWW, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, FLR, BGW, BNN, BRN },
				{ FLR, FLR, FLR, FLR, BDD, BNN, BRN },
				{ FLR, FLR, FLR, FLR, BGG, BNN, BRN },
				{ WWW, BGW, BDD, BGG, BNN, BNN, WWW },
				{ WWW, BNN, BNN, BNN, BNN, BWW, opt },
				{ WWW, BRN, BRN, BRN, WWW, opt, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WGG },
				{ FLR, FLR, FLR, BWW, BWW, Bdd, WWW },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BRN },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BRN },
				{ FLR, FLR, Bdd, BNN, BNN, BNN, BRN },
				{ WWW, WGG, WWW, BRN, BRN, BRN, BRN },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, WGG, non },
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, BDD, BNN, BNN, BRN },
				{ FLR, FLR, WWW, BNN, BNN, BNN, BRN },
				{ WWW, WGG, WWW, BNN, BNN, BNN, BRN },
				{ WWW, non, WWW, BRN, BRN, BRN, BRN },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, Wgg },
				{ FLR, FLR, FLR, BWW, BWW, Bdd, WWW },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BRN },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BRN },
				{ FLR, FLR, Bdd, BNN, BNN, BNN, BRN },
				{ WWW, Wgg, WWW, BRN, BRN, BRN, BRN },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, Wgg, non },
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, BDD, BNN, BNN, BRN },
				{ FLR, FLR, WWW, BNN, BNN, BNN, BRN },
				{ WWW, Wgg, WWW, BNN, BNN, BNN, BRN },
				{ WWW, non, WWW, BRN, BRN, BRN, BRN },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, FLR, WGW, BNN, BrN },
				{ FLR, FLR, FLR, FLR, BDD, BNN, BrN },
				{ FLR, FLR, FLR, FLR, WGG, BNN, BrN },
				{ WWW, WGW, BDD, WGG, WGG, BNN, BrN },
				{ WWW, BNN, BNN, BNN, BNN, BNN, BrN },
				{ WWW, BrN, BrN, BrN, BrN, BrN, BWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, FLR, BGW, BNN, BrN },
				{ FLR, FLR, FLR, FLR, BDD, BNN, BrN },
				{ FLR, FLR, FLR, FLR, BGW, BNN, BrN },
				{ WWW, BGW, BDD, BGW, BWW, BNN, BrN },
				{ WWW, BNN, BNN, BNN, BNN, BWW, BWW },
				{ WWW, BrN, BrN, BrN, BrN, BWW, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, FLR, BGW, BNN, BrN },
				{ FLR, FLR, FLR, FLR, BDD, BNN, BrN },
				{ FLR, FLR, FLR, FLR, BGG, BNN, BrN },
				{ WWW, BGW, BDD, BGG, BNN, BNN, WWW },
				{ WWW, BNN, BNN, BNN, BNN, BWW, opt },
				{ WWW, BrN, BrN, BrN, WWW, opt, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WGG },
				{ FLR, FLR, FLR, BWW, BWW, Bdd, WWW },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BrN },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BrN },
				{ FLR, FLR, Bdd, BNN, BNN, BNN, BrN },
				{ WWW, WGG, WWW, BrN, BrN, BrN, BrN },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, WGG, non },
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, BDD, BNN, BNN, BrN },
				{ FLR, FLR, WWW, BNN, BNN, BNN, BrN },
				{ WWW, WGG, WWW, BNN, BNN, BNN, BrN },
				{ WWW, non, WWW, BrN, BrN, BrN, BrN },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WGG },
				{ FLR, FLR, FLR, BWW, BWW, Bdd, WWW },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BrN },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BrN },
				{ FLR, FLR, Bdd, BNN, BNN, BNN, BrN },
				{ WWW, WGG, WWW, BrN, BrN, BrN, WWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, Wgg },
				{ FLR, FLR, FLR, BWW, BWW, Bdd, WWW },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BrN },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BrN },
				{ FLR, FLR, Bdd, BNN, BNN, BNN, BrN },
				{ WWW, Wgg, WWW, BrN, BrN, BrN, BrN },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, Wgg, non },
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, BDD, BNN, BNN, BrN },
				{ FLR, FLR, WWW, BNN, BNN, BNN, BrN },
				{ WWW, Wgg, WWW, BNN, BNN, BNN, BrN },
				{ WWW, non, WWW, BrN, BrN, BrN, BrN },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, Wgg },
				{ FLR, FLR, FLR, BWW, BWW, Bdd, WWW },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BrN },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BrN },
				{ FLR, FLR, Bdd, BNN, BNN, BNN, BrN },
				{ WWW, Wgg, WWW, BrN, BrN, BrN, WWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, FLR, WGW, BNN, BgN },
				{ FLR, FLR, FLR, FLR, BDD, BNN, BgN },
				{ FLR, FLR, FLR, FLR, WGG, BNN, BgN },
				{ WWW, WGW, BDD, WGG, WGG, BNN, BgN },
				{ WWW, BNN, BNN, BNN, BNN, BNN, BgN },
				{ WWW, BgN, BgN, BgN, BgN, BgN, BWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, FLR, BGW, BNN, BgN },
				{ FLR, FLR, FLR, FLR, BDD, BNN, BgN },
				{ FLR, FLR, FLR, FLR, BGW, BNN, BgN },
				{ WWW, BGW, BDD, BGW, BWW, BNN, BgN },
				{ WWW, BNN, BNN, BNN, BNN, BWW, BWW },
				{ WWW, BgN, BgN, BgN, BgN, BWW, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, FLR, BGW, BNN, BgN },
				{ FLR, FLR, FLR, FLR, BDD, BNN, BgN },
				{ FLR, FLR, FLR, FLR, BGG, BNN, BgN },
				{ WWW, BGW, BDD, BGG, BNN, BNN, WWW },
				{ WWW, BNN, BNN, BNN, BNN, BWW, opt },
				{ WWW, BgN, BgN, BgN, WWW, opt, non },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WGG },
				{ FLR, FLR, FLR, BWW, BWW, Bdd, WWW },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BgN },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BgN },
				{ FLR, FLR, Bdd, BNN, BNN, BNN, BgN },
				{ WWW, WGG, WWW, BgN, BgN, BgN, BgN },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, WGG, non },
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, BDD, BNN, BNN, BgN },
				{ FLR, FLR, WWW, BNN, BNN, BNN, BgN },
				{ WWW, WGG, WWW, BNN, BNN, BNN, BgN },
				{ WWW, non, WWW, BgN, BgN, BgN, BgN },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, WGG },
				{ FLR, FLR, FLR, BWW, BWW, Bdd, WWW },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BgN },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BgN },
				{ FLR, FLR, Bdd, BNN, BNN, BNN, BgN },
				{ WWW, WGG, WWW, BgN, BgN, BgN, WWW },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, Wgg },
				{ FLR, FLR, FLR, BWW, BWW, Bdd, WWW },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BgN },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BgN },
				{ FLR, FLR, Bdd, BNN, BNN, BNN, BgN },
				{ WWW, Wgg, WWW, BgN, BgN, BgN, BgN },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, WWW, WWW },
				{ FLR, FLR, FLR, FLR, FLR, Wgg, non },
				{ FLR, FLR, FLR, FLR, WWW, WWW, WWW },
				{ FLR, FLR, FLR, BDD, BNN, BNN, BgN },
				{ FLR, FLR, WWW, BNN, BNN, BNN, BgN },
				{ WWW, Wgg, WWW, BNN, BNN, BNN, BgN },
				{ WWW, non, WWW, BgN, BgN, BgN, BgN },
		}));

		corners.add(new CustomCorner(new UnitType[][] {
				{ FLR, FLR, FLR, FLR, FLR, FLR, WWW },
				{ FLR, FLR, FLR, FLR, FLR, FLR, Wgg },
				{ FLR, FLR, FLR, BWW, BWW, Bdd, WWW },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BgN },
				{ FLR, FLR, BWW, BNN, BNN, BNN, BgN },
				{ FLR, FLR, Bdd, BNN, BNN, BNN, BgN },
				{ WWW, Wgg, WWW, BgN, BgN, BgN, WWW },
		}));

//		corners.add(new CustomCorner(new byte[][] {
//		}));
//
	}

	public void drawNWVerticals(int i, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
			Material secondary, boolean outsetEffect, boolean onRoof) {
		getCorner(i).drawNWVerticals(blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}

	public void drawNEVerticals(int i, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
			Material secondary, boolean outsetEffect, boolean onRoof) {
		getCorner(i).drawNEVerticals(blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}

	public void drawSWVerticals(int i, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
			Material secondary, boolean outsetEffect, boolean onRoof) {
		getCorner(i).drawSWVerticals(blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}

	public void drawSEVerticals(int i, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
			Material secondary, boolean outsetEffect, boolean onRoof) {
		getCorner(i).drawSEVerticals(blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}

	public void drawNWHorizontals(int i, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
			Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
		getCorner(i).drawNWHorizontals(blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}

	public void drawNEHorizontals(int i, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
			Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
		getCorner(i).drawNEHorizontals(blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}

	public void drawSWHorizontals(int i, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
			Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
		getCorner(i).drawSWHorizontals(blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}

	public void drawSEHorizontals(int i, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
			Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
		getCorner(i).drawSEHorizontals(blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}

	private final ArrayList<Corner> corners;

	private abstract class Corner {
		protected abstract void drawNWVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);

		protected abstract void drawNEVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);

		protected abstract void drawSWVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);

		protected abstract void drawSEVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);

		protected abstract void drawNWHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);

		protected abstract void drawNEHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);

		protected abstract void drawSWHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);

		protected abstract void drawSEHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);
	}

	private class RoundedCorner extends Corner {
		private RoundedCorner() {

		}

		@Override
		public void drawNWVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
		}

		@Override
		public void drawNEVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
		}

		@Override
		public void drawSWVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
		}

		@Override
		public void drawSEVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
		}

		@Override
		public void drawNWHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
		}

		@Override
		public void drawNEHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
		}

		@Override
		public void drawSWHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
		}

		@Override
		public void drawSEHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
		}
	}

	protected class CustomCorner extends Corner {
		private CustomCorner(UnitType[][] source) {
			NW = source;
			NE = flipWE(NW);
			SE = flipNS(NE);
			SW = flipNS(NW);
		}

		private final UnitType[][] NW;
		private final UnitType[][] NE;
		private final UnitType[][] SW;
		private final UnitType[][] SE;

		private UnitType[][] flipWE(UnitType[][] source) {
			UnitType[][] result = new UnitType[CornerBlocks.CornerWidth][CornerBlocks.CornerWidth];
			for (int x = 0; x < CornerBlocks.CornerWidth; x++) {
				System.arraycopy(source[x], 0, result[CornerBlocks.CornerWidth - x - 1], 0, CornerBlocks.CornerWidth);
			}
			return result;
		}

		private UnitType[][] flipNS(UnitType[][] source) {
			UnitType[][] result = new UnitType[CornerBlocks.CornerWidth][CornerBlocks.CornerWidth];
			for (int x = 0; x < CornerBlocks.CornerWidth; x++) {
				for (int z = 0; z < CornerBlocks.CornerWidth; z++) {
					result[x][CornerBlocks.CornerWidth - z - 1] = source[x][z];
				}
			}
			return result;
		}

		@Override
		public void drawNWVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
			setVerticals(NW, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof,
					BlockFace.NORTH_WEST);
		}

		@Override
		public void drawNEVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
			setVerticals(NE, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof,
					BlockFace.NORTH_EAST);
		}

		@Override
		public void drawSWVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
			setVerticals(SW, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof,
					BlockFace.SOUTH_WEST);
		}

		@Override
		public void drawSEVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
			setVerticals(SE, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof,
					BlockFace.SOUTH_EAST);
		}

		@Override
		public void drawNWHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
			setHorizontals(NW, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
		}

		@Override
		public void drawNEHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
			setHorizontals(NE, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
		}

		@Override
		public void drawSWHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
			setHorizontals(SW, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
		}

		@Override
		public void drawSEHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, Material primary,
				Material secondary, boolean outsetEffect, boolean onRoof) {
			setHorizontals(SE, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
		}

		private void setVerticals(UnitType[][] source, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof, BlockFace connectedTo) {
			for (int x = 0; x < CornerBlocks.CornerWidth; x++) {
				for (int z = 0; z < CornerBlocks.CornerWidth; z++) {
					switch (source[x][z]) {
					case WWW:
						blocks.setBlocks(xInset + x, y1, y2, zInset + z, primary);
						break;
					case GGG:
					case ggg:
						blocks.setBlocks(xInset + x, y1, y2, zInset + z, secondary);
						break;
//					case ggg:
//						// TODO: Direction
//						blocks.setBlocks(xInset + x, y1, y2, zInset + z, Material.GLASS_PANE,
//								getDirections(source, x, z, connectedTo));
//						break;
					case WGG:
					case Wgg:
						blocks.setBlock(xInset + x, y1, zInset + z, primary);
						blocks.setBlocks(xInset + x, y1 + 1, y2, zInset + z, secondary);
						break;
//					case Wgg:
//						blocks.setBlock(xInset + x, y1, zInset + z, primary);
//						// TODO: Direction
//						blocks.setBlocks(xInset + x, y1 + 1, y2, zInset + z, Material.GLASS_PANE,
//								getDirections(source, x, z, connectedTo));
//						break;
					case GWW:
						blocks.setBlock(xInset + x, y1, zInset + z, secondary);
						blocks.setBlocks(xInset + x, y1 + 1, y2, zInset + z, primary);
						break;
					case WGW:
						blocks.setBlock(xInset + x, y1, zInset + z, primary);
						blocks.setBlocks(xInset + x, y1 + 1, y2 - 1, zInset + z, secondary);
						blocks.setBlock(xInset + x, y2 - 1, zInset + z, primary);
						break;
					case WWG:
						blocks.setBlocks(xInset + x, y1, y2 - 1, zInset + z, primary);
						blocks.setBlock(xInset + x, y2 - 1, zInset + z, secondary);
						break;
					case GGW:
					case ggW:
						blocks.setBlocks(xInset + x, y1, y2 - 1, zInset + z, secondary);
						blocks.setBlock(xInset + x, y2 - 1, zInset + z, primary);
						break;
//					case ggW:
//						// TODO: Direction
//						blocks.setBlocks(xInset + x, y1, y2 - 1, zInset + z, Material.GLASS_PANE,
//								getDirections(source, x, z, connectedTo));
//						blocks.setBlock(xInset + x, y2 - 1, zInset + z, primary);
//						break;
					case GWG:
						blocks.setBlock(xInset + x, y1, zInset + z, secondary);
						blocks.setBlocks(xInset + x, y1 + 1, y2 - 1, zInset + z, primary);
						blocks.setBlock(xInset + x, y2 - 1, zInset + z, secondary);
						break;

					case BWW:
						if (!onRoof) {
							blocks.setBlocks(xInset + x, y1, y2, zInset + z, primary);
						}
						break;
					case BGG:
						if (!onRoof) {
							blocks.setBlock(xInset + x, y1, zInset + z, primary);
							blocks.setBlocks(xInset + x, y1 + 1, y2, zInset + z, secondary);
						}
						break;
					case BGW:
						if (!onRoof) {
							blocks.setBlock(xInset + x, y1, zInset + z, primary);
							blocks.setBlocks(xInset + x, y1 + 1, y2 - 1, zInset + z, secondary);
							blocks.setBlock(xInset + x, y2 - 1, zInset + z, primary);
						}
						break;
					case BDD:
						if (!onRoof) {
							// Put a door here
							blocks.setBlocks(xInset + x, y1 + 2, y2, zInset + z, primary);
							blocks.setDoor(xInset + x, y1, zInset + z, Material.SPRUCE_DOOR,
									getDoorDirectionOfConvexAngle(source, x, z, connectedTo));
						}
						break;
					case Bdd:
						if (!onRoof) {
							// Put a door here
							blocks.setBlocks(xInset + x, y1 + 2, y2, zInset + z, primary);
							blocks.setDoor(xInset + x, y1, zInset + z, Material.SPRUCE_DOOR,
									getDoorDirectionOfConcaveAngle(source, x, z, connectedTo));
						}
						break;
					case BRN:
						if (onRoof) {
							blocks.setBlocks(xInset + x, y1, y2, zInset + z, primary);
						} else {
							blocks.setBlock(xInset + x, y1, zInset + z, Material.IRON_BARS,
									getDirections(source, x, z, connectedTo));
						}
						break;
					case BrN:
						if (onRoof) {
							blocks.setBlocks(xInset + x, y1, y2, zInset + z, primary);
						} else {
							blocks.setBlock(xInset + x, y1, zInset + z, Material.SPRUCE_FENCE,
									getDirections(source, x, z, connectedTo));
						}
						break;
					case BgN:
						if (onRoof) {
							blocks.setBlocks(xInset + x, y1, y2, zInset + z, primary);
						} else {
							blocks.setBlock(xInset + x, y1, zInset + z, Material.GLASS);
//							blocks.setBlock(xInset + x, y1, zInset + z, Material.GLASS_PANE,
//									getDirections(source, x, z, connectedTo));
						}
						break;

					case Wnn:
						blocks.setBlock(xInset + x, y1, zInset + z, primary);
						break;
					case nWn:
						blocks.setBlocks(xInset + x, y1 + 1, y2 - 1, zInset + z, primary);
						break;
					case nnW:
						blocks.setBlock(xInset + x, y2 - 1, zInset + z, primary);
						break;

					case opt:
						if (outsetEffect)
							blocks.setBlocks(xInset + x, y1, y2, zInset + z, primary);
						break;
					case non:
					case BNN:
					case FLR:
					default:
						break;
					}
				}
			}
		}

		private void setHorizontals(UnitType[][] source, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
			for (int x = 0; x < CornerBlocks.CornerWidth; x++) {
				for (int z = 0; z < CornerBlocks.CornerWidth; z++) {
					switch (source[x][z]) {
					case non:
						blocks.setBlocks(xInset + x, y1, y2, zInset + z, secondary);
						break;
					case opt:
						if (outsetEffect)
							blocks.setBlocks(xInset + x, y1, y2, zInset + z, primary);
						else
							blocks.setBlocks(xInset + x, y1, y2, zInset + z, secondary);
						break;
					case FLR:
					case WWW:
					case GWG:
					case GGG:
					case WGG:
					case ggg:
					case Wgg:
					case GGW:
					case ggW:
					case GWW:
					case WGW:
					case WWG:
					case BNN:
					case BDD:
					case BRN:
					case BrN:
					case BgN:
					case BWW:
					case BGW:
					case BGG:
					case Wnn:
					case nWn:
					case nnW:
					default:
						blocks.setBlocks(xInset + x, y1, y2, zInset + z, primary);
						break;
					}
				}
			}
		}
	}

	// Detect direction (Maybe we need some better ways?)
	private BlockFace[] getDirections(UnitType[][] source, int x, int z, BlockFace connectedTo) {
		if (connectedTo == BlockFace.SOUTH_WEST) {
			if (x + z + 1 == CornerBlocks.CornerWidth) {
				return new BlockFace[] { BlockFace.SOUTH, BlockFace.WEST };
			} else if (x + z + 1 < CornerBlocks.CornerWidth) {
				return new BlockFace[] { BlockFace.EAST, BlockFace.WEST };
			} else { // x + z + 1 > CornerBlocks.CornerWidth
				return new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH };
			}
		}
		if (connectedTo == BlockFace.NORTH_EAST) {
			if (x + z + 1 == CornerBlocks.CornerWidth) {
				return new BlockFace[] { BlockFace.NORTH, BlockFace.EAST };
			} else if (x + z + 1 < CornerBlocks.CornerWidth) {
				return new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH };
			} else { // x + z + 1 > CornerBlocks.CornerWidth
				return new BlockFace[] { BlockFace.EAST, BlockFace.WEST };
			}
		}
		if (connectedTo == BlockFace.SOUTH_EAST) {
			if (x == z) {
				return new BlockFace[] { BlockFace.SOUTH, BlockFace.EAST };
			} else if (x < z) {
				return new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH };
			} else { // x > z
				return new BlockFace[] { BlockFace.EAST, BlockFace.WEST };
			}
		}
		if (connectedTo == BlockFace.NORTH_WEST) {
			if (x == z) {
				return new BlockFace[] { BlockFace.NORTH, BlockFace.WEST };
			} else if (x < z) {
				return new BlockFace[] { BlockFace.EAST, BlockFace.WEST };
			} else { // x > z
				return new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH };
			}
		}

		return new BlockFace[] {};
	}

	// Detect door direction in convex angle (Maybe we need some better ways?)
	// Maybe hardcode in/with corners list?
	private BlockFace getDoorDirectionOfConvexAngle(UnitType[][] source, int x, int z, BlockFace connectedTo) {
		if (connectedTo == BlockFace.SOUTH_WEST) {
			if (x + z + 1 < CornerBlocks.CornerWidth) {
				return BlockFace.NORTH;
			} else if (x + z + 1 > CornerBlocks.CornerWidth) {
				return BlockFace.EAST;
			}
		}
		if (connectedTo == BlockFace.NORTH_EAST) {
			if (x + z + 1 < CornerBlocks.CornerWidth) {
				return BlockFace.WEST;
			} else if (x + z + 1 > CornerBlocks.CornerWidth) {
				return BlockFace.SOUTH;
			}
		}
		if (connectedTo == BlockFace.SOUTH_EAST) {
			if (x < z) {
				return BlockFace.WEST;
			} else if (x > z) {
				return BlockFace.NORTH;
			}
		}
		if (connectedTo == BlockFace.NORTH_WEST) {
			if (x < z) {
				return BlockFace.SOUTH;
			} else if (x > z) {
				return BlockFace.EAST;
			}
		}

		return BlockFace.EAST;
	}

	// Detect door direction in concave angle (Maybe we need some better ways?)
	// Maybe hardcode in/with corners list?
	private BlockFace getDoorDirectionOfConcaveAngle(UnitType[][] source, int x, int z, BlockFace connectedTo) {
		if (connectedTo == BlockFace.SOUTH_WEST) {
			if (x + z + 1 < CornerBlocks.CornerWidth) {
				return BlockFace.EAST;
			} else if (x + z + 1 > CornerBlocks.CornerWidth) {
				return BlockFace.NORTH;
			}
		}
		if (connectedTo == BlockFace.NORTH_EAST) {
			if (x + z + 1 < CornerBlocks.CornerWidth) {
				return BlockFace.SOUTH;
			} else if (x + z + 1 > CornerBlocks.CornerWidth) {
				return BlockFace.WEST;
			}
		}
		if (connectedTo == BlockFace.SOUTH_EAST) {
			if (x < z) {
				return BlockFace.NORTH;
			} else if (x > z) {
				return BlockFace.WEST;
			}
		}
		if (connectedTo == BlockFace.NORTH_WEST) {
			if (x < z) {
				return BlockFace.EAST;
			} else if (x > z) {
				return BlockFace.SOUTH;
			}
		}

		return BlockFace.EAST;
	}
}
