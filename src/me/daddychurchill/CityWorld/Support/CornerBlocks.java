package me.daddychurchill.CityWorld.Support;

import java.util.ArrayList;

import org.bukkit.Material;

public class CornerBlocks {
	
	public final static int CornerWidth = 7;
	
	private final static byte non = 0;
	private final static byte opt = 1;
	private final static byte FLR = 2;
	private final static byte WWW = 3;
	private final static byte GGG = 4;
	private final static byte WGG = 5;
	private final static byte GGW = 6;
	private final static byte WWG = 7;
	private final static byte WGW = 8;
	private final static byte GWW = 9;
	private final static byte GWG = 10;
	
	// these won't show up on the roof
	private final static byte BRR = 20; // balcony just below floor and iron railing topped with nothing above
	private final static byte Brr = 21; // balcony just below floor and wood fence topped with nothing above
	private final static byte BNN = 22; // balcony just below floor and nothing above
	private final static byte BDD = 23; // balcony just below floor and door topped with a wall block 
	private final static byte BWW = 24; // WWW 
	private final static byte BGG = 25; // WGG 
	private final static byte BGW = 26; // WGW 

	private final static byte Wnn = 30;
	private final static byte nWn = 31;
	private final static byte nnW = 32;
	
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
		corners = new ArrayList<Corner>();
		
		corners.add(new RoundedCorner()); // always put this in first
		
//		corners.add(new CustomCorner(new byte[][] {
//		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, opt},
			{FLR, FLR, FLR, FLR, WGG, opt, non},
			{FLR, FLR, FLR, WWW, opt, non, non},
			{FLR, FLR, WGG, opt, non, non, non},
			{FLR, WGG, opt, non, non, non, non},
			{WWW, opt, non, non, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, opt},
			{FLR, FLR, FLR, FLR, WGG, opt, non},
			{WGG, WGG, WGG, WWW, opt, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, WWW, WGG, WGG, WWW},
			{FLR, FLR, FLR, WGG, non, non, non},
			{FLR, FLR, FLR, WGG, non, non, non},
			{WWW, WGG, WGG, WWW, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, opt},
			{FLR, FLR, FLR, FLR, WWW, opt, non},
			{FLR, FLR, FLR, FLR, FLR, WGW, non},
			{FLR, FLR, WWW, FLR, FLR, FLR, WGW},
			{FLR, WGG, opt, WGW, FLR, WGW, opt},
			{WWW, opt, non, non, WGW, opt, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, GGG},
			{FLR, FLR, FLR, FLR, WWW, GGG, GGG},
			{FLR, FLR, FLR, FLR, GGG, non, non},
			{FLR, FLR, WWW, GGG, GGG, non, non},
			{FLR, FLR, GGG, non, non, non, non},
			{WWW, GGG, GGG, non, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, WGG, opt},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, FLR, WWW, non},
			{FLR, FLR, FLR, FLR, WGG, opt, non},
			{FLR, FLR, FLR, WGG, opt, non, non},
			{WGG, WGG, WWW, opt, non, non, non},
			{opt, non, non, non, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, WGG, WGG, WGW},
			{FLR, FLR, FLR, WGG, opt, non, non},
			{FLR, FLR, WGG, opt, non, non, non},
			{FLR, FLR, WGG, non, non, non, non},
			{WWW, WWW, WGW, non, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, WGG, WGG, WGG, WGG, WGG},
			{FLR, FLR, WGG, non, non, non, non},
			{FLR, FLR, WGG, non, WWW, WWW, WWW},
			{FLR, FLR, WGG, non, WWW, WWW, WWW},
			{WWW, WWW, WGG, non, WWW, WWW, WWW},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, WGG, WGG, WGG, WGG, WGG},
			{FLR, FLR, WGG, non, non, non, non},
			{FLR, FLR, WGG, non, WWW, nWn, WWW},
			{FLR, FLR, WGG, non, nWn, WWW, nWn},
			{WWW, WWW, WGG, non, WWW, nWn, WWW},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, GWG, GWG, GWG, GWG, GWG},
			{FLR, FLR, GWG, nWn, nWn, nWn, nWn},
			{FLR, FLR, GWG, nWn, non, non, non},
			{FLR, FLR, GWG, nWn, non, non, non},
			{WWW, WWW, GWG, nWn, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, GWG, GWG, GWG, GWG, GWG},
			{FLR, FLR, GWG, nWn, nWn, nWn, WWW},
			{FLR, FLR, GWG, nWn, non, non, non},
			{FLR, FLR, GWG, nWn, non, non, non},
			{WWW, WWW, GWG, WWW, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, GGW},
			{FLR, FLR, GGW, GGW, GGW, GGW, GGW},
			{FLR, FLR, GGW, non, non, non, non},
			{FLR, FLR, GGW, non, non, non, non},
			{FLR, FLR, GGW, non, non, non, non},
			{WWW, GGW, GGW, non, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, GGW, GGW, GGW, GGW, nnW},
			{FLR, FLR, GGW, nnW, nnW, nnW, nnW},
			{FLR, FLR, GGW, nnW, non, non, non},
			{FLR, FLR, GGW, nnW, non, non, non},
			{WWW, WWW, nnW, nnW, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, WGG, Wnn},
			{FLR, FLR, FLR, FLR, FLR, WGG, Wnn},
			{FLR, FLR, FLR, FLR, WGG, Wnn, non},
			{FLR, FLR, FLR, WGG, Wnn, non, non},
			{FLR, FLR, WGG, Wnn, non, non, non},
			{WGG, WGG, Wnn, non, non, non, non},
			{Wnn, Wnn, non, non, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, GGG, GGG, GGG, GGG, WGW},
			{FLR, FLR, GGG, non, non, non, WWW},
			{FLR, FLR, GGG, non, non, non, non},
			{FLR, FLR, GGG, non, non, non, non},
			{WWW, WWW, WGW, WWW, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWG},
			{FLR, FLR, FLR, FLR, FLR, WGW, non},
			{FLR, FLR, FLR, FLR, GWW, non, non},
			{FLR, FLR, FLR, GWW, non, non, non},
			{FLR, FLR, WGW, non, non, non, non},
			{WWW, WWG, non, non, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, WGG, WGG, WGG, WGG, WGG},
			{FLR, FLR, WGG, non, non, non, non},
			{FLR, FLR, WGG, non, nWn, nWn, nWn},
			{FLR, FLR, WGG, non, nWn, WWW, nWn},
			{WWW, WWW, WGG, non, nWn, nWn, nWn},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, WGG, WGG, WGG, WGG, WGG},
			{FLR, FLR, WGG, non, non, non, non},
			{FLR, FLR, WGG, non, WWW, non, WWW},
			{FLR, FLR, WGG, non, non, non, WWW},
			{WWW, WWW, WGG, non, WWW, WWW, WWW},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, WGG, WGG, WGG, WGG, WGG},
			{FLR, FLR, WGG, non, non, non, non},
			{FLR, FLR, WGG, non, WWW, WWW, WWW},
			{FLR, FLR, WGG, non, WWW, non, non},
			{WWW, WWW, WGG, non, WWW, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, WGG, WGG, WGG, WGG, WGG},
			{FLR, FLR, WGG, opt, opt, non, non},
			{FLR, FLR, WGG, opt, opt, opt, WWW},
			{FLR, FLR, WGG, non, opt, WWW, non},
			{WWW, WWW, WGG, non, WWW, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, WGG, WGG, WGG, WGG, WGG},
			{FLR, FLR, WGG, non, non, non, opt},
			{FLR, FLR, WGG, non, non, non, WWW},
			{FLR, FLR, WGG, non, non, non, opt},
			{WWW, WWW, WGG, opt, WWW, opt, WWW},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, GGG, WWW},
			{FLR, FLR, FLR, FLR, GGG, opt, non},
			{FLR, FLR, FLR, WWW, WWW, non, non},
			{FLR, FLR, GGG, WWW, WWW, WWW, non},
			{FLR, GGG, opt, non, WWW, WWW, WWW},
			{WWW, WWW, non, non, non, WWW, WWW},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, GGG},
			{FLR, FLR, FLR, FLR, WWW, GGG, GGG},
			{FLR, FLR, FLR, FLR, GGG, non, non},
			{FLR, FLR, WWW, GGG, GGG, non, non},
			{FLR, FLR, GGG, non, non, GGG, non},
			{WWW, GGG, GGG, non, non, non, GGG},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGW, non},
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGW},
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{WWW, WGW, WWW, FLR, WWW, non, non},
			{WWW, non, WWW, WGW, WWW, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{WWW, WGG, WGG, WGG, WGG, WGG, non},
			{WWW, non, non, non, non, non, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{WWW, WGG, WGG, WWW, FLR, FLR, WGG},
			{WWW, non, non, WWW, WGG, WGG, WGG},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{FLR, FLR, FLR, FLR, WGW, BNN, BRR},
			{FLR, FLR, FLR, FLR, BDD, BNN, BRR},
			{FLR, FLR, FLR, FLR, WGG, BNN, BRR},
			{WWW, WGW, BDD, WGG, WGG, BNN, BRR},
			{WWW, BNN, BNN, BNN, BNN, BNN, BRR},
			{WWW, BRR, BRR, BRR, BRR, BRR, BWW},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{FLR, FLR, FLR, FLR, BGW, BNN, BRR},
			{FLR, FLR, FLR, FLR, BDD, BNN, BRR},
			{FLR, FLR, FLR, FLR, BGW, BNN, BRR},
			{WWW, BGW, BDD, BGW, BWW, BNN, BRR},
			{WWW, BNN, BNN, BNN, BNN, BWW, BWW},
			{WWW, BRR, BRR, BRR, BRR, BWW, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{FLR, FLR, FLR, FLR, BGW, BNN, BRR},
			{FLR, FLR, FLR, FLR, BDD, BNN, BRR},
			{FLR, FLR, FLR, FLR, BGG, BNN, BRR},
			{WWW, BGW, BDD, BGG, BNN, BNN, WWW}, // yep those are WWW, just to be different
			{WWW, BNN, BNN, BNN, BNN, BWW, opt},
			{WWW, BRR, BRR, BRR, WWW, opt, non},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, BWW, BWW, BDD, WWW},
			{FLR, FLR, BWW, BNN, BNN, BNN, BRR},
			{FLR, FLR, BWW, BNN, BNN, BNN, BRR},
			{FLR, FLR, BDD, BNN, BNN, BNN, BRR},
			{WWW, WGG, WWW, BRR, BRR, BRR, BRR},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{FLR, FLR, FLR, BDD, BNN, BNN, BRR},
			{FLR, FLR, WWW, BNN, BNN, BNN, BRR},
			{WWW, WGG, WWW, BNN, BNN, BNN, BRR},
			{WWW, non, WWW, BRR, BRR, BRR, BRR},
		}));

		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{FLR, FLR, FLR, FLR, WGW, BNN, Brr},
			{FLR, FLR, FLR, FLR, BDD, BNN, Brr},
			{FLR, FLR, FLR, FLR, WGG, BNN, Brr},
			{WWW, WGW, BDD, WGG, WGG, BNN, Brr},
			{WWW, BNN, BNN, BNN, BNN, BNN, Brr},
			{WWW, Brr, Brr, Brr, Brr, Brr, BWW},
		}));
		
		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{FLR, FLR, FLR, FLR, BGW, BNN, Brr},
			{FLR, FLR, FLR, FLR, BDD, BNN, Brr},
			{FLR, FLR, FLR, FLR, BGW, BNN, Brr},
			{WWW, BGW, BDD, BGW, BWW, BNN, Brr},
			{WWW, BNN, BNN, BNN, BNN, BWW, BWW},
			{WWW, Brr, Brr, Brr, Brr, BWW, non},
		}));
		
		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{FLR, FLR, FLR, FLR, BGW, BNN, Brr},
			{FLR, FLR, FLR, FLR, BDD, BNN, Brr},
			{FLR, FLR, FLR, FLR, BGG, BNN, Brr},
			{WWW, BGW, BDD, BGG, BNN, BNN, WWW}, // yep those are WWW, just to be different
			{WWW, BNN, BNN, BNN, BNN, BWW, opt},
			{WWW, Brr, Brr, Brr, WWW, opt, non},
		}));
		
		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, BWW, BWW, BDD, WWW},
			{FLR, FLR, BWW, BNN, BNN, BNN, Brr},
			{FLR, FLR, BWW, BNN, BNN, BNN, Brr},
			{FLR, FLR, BDD, BNN, BNN, BNN, Brr},
			{WWW, WGG, WWW, Brr, Brr, Brr, Brr},
		}));
		
		corners.add(new CustomCorner(new byte[][] {
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{FLR, FLR, FLR, BDD, BNN, BNN, Brr},
			{FLR, FLR, WWW, BNN, BNN, BNN, Brr},
			{WWW, WGG, WWW, BNN, BNN, BNN, Brr},
			{WWW, non, WWW, Brr, Brr, Brr, Brr},
		}));
		
//		corners.add(new CustomCorner(new byte[][] {
//		}));
//
	}
	
	public void drawNWVerticals(int i, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
			Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
		getCorner(i).drawNWVerticals(blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}
	
	public void drawNEVerticals(int i, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
			Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
		getCorner(i).drawNEVerticals(blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}
	
	public void drawSWVerticals(int i, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
			Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
		getCorner(i).drawSWVerticals(blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}
	
	public void drawSEVerticals(int i, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
			Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
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
	
	private ArrayList<Corner> corners;
	
	private abstract class Corner {
		public abstract void drawNWVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);
		public abstract void drawNEVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);
		public abstract void drawSWVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);
		public abstract void drawSEVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);
		
		public abstract void drawNWHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);
		public abstract void drawNEHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);
		public abstract void drawSWHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof);
		public abstract void drawSEHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
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
	
	private class CustomCorner extends Corner {
		private CustomCorner(byte[][] source) {
			NW = source;
			NE = flipWE(NW);
			SE = flipNS(NE);
			SW = flipNS(NW);
		}
		
		private byte[][] NW;
		private byte[][] NE;
		private byte[][] SW;
		private byte[][] SE;

		private byte[][] flipWE(byte[][] source) {
			byte[][] result = new byte[CornerBlocks.CornerWidth][CornerBlocks.CornerWidth];
			for (int x = 0; x < CornerBlocks.CornerWidth; x++) {
				for (int z = 0; z < CornerBlocks.CornerWidth; z++) {
					result[CornerBlocks.CornerWidth - x - 1][z] = source[x][z];
				}
			}
			return result;
		}

		private byte[][] flipNS(byte[][] source) {
			byte[][] result = new byte[CornerBlocks.CornerWidth][CornerBlocks.CornerWidth];
			for (int x = 0; x < CornerBlocks.CornerWidth; x++) {
				for (int z = 0; z < CornerBlocks.CornerWidth; z++) {
					result[x][CornerBlocks.CornerWidth - z - 1] = source[x][z];
				}
			}
			return result;
		}

		@Override
		public void drawNWVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
			setVerticals(NW, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
		}
		
		@Override
		public void drawNEVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
			setVerticals(NE, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
		}
		
		@Override
		public void drawSWVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
			setVerticals(SW, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
		}
		
		@Override
		public void drawSEVerticals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
			setVerticals(SE, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
		}
		
		@Override
		public void drawNWHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
			setHorizontals(NW, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
		}
		
		@Override
		public void drawNEHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
			setHorizontals(NE, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
		}
		
		@Override
		public void drawSWHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
			setHorizontals(SW, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
		}
		
		@Override
		public void drawSEHorizontals(AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
			setHorizontals(SE, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
		}
		
		private void setVerticals(byte[][] source, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, 
				Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
			for (int x = 0; x < CornerBlocks.CornerWidth; x++) {
				for (int z = 0; z < CornerBlocks.CornerWidth; z++) {
					switch (source[x][z]) {
					case WWW:
						blocks.setBlocks(xInset + x, y1, y2, zInset + z, primary);
						break;
					case GGG:
						blocks.setBlocks(xInset + x, y1, y2, zInset + z, secondary);
						break;
					case WGG:
						blocks.setBlock(xInset + x, y1, zInset + z, primary);
						blocks.setBlocks(xInset + x, y1 + 1, y2, zInset + z, secondary);
						break;
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
						blocks.setBlocks(xInset + x, y1, y2 - 1, zInset + z, secondary);
						blocks.setBlock(xInset + x, y2 - 1, zInset + z, primary);
						break;
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
							//TODO: need to put a door here
							blocks.setBlocks(xInset + x, y1 + 2, y2, zInset + z, primary);
						}
						break;
					case BRR:
						if (onRoof) {
							blocks.setBlocks(xInset + x, y1, y2, zInset + z, primary);
						} else {
							blocks.setBlock(xInset + x, y1, zInset + z, Material.IRON_FENCE);
						}
						break;
					case Brr:
						if (onRoof) {
							blocks.setBlocks(xInset + x, y1, y2, zInset + z, primary);
						} else {
							blocks.setBlock(xInset + x, y1, zInset + z, Material.FENCE);
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
		
		private void setHorizontals(byte[][] source, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, 
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
					case GGW:
					case GWW:
					case WGW:
					case WWG:
					case BNN:
					case BDD:
					case BRR:
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
	
}
