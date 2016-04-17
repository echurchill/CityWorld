package me.daddychurchill.CityWorld.Support;

import org.bukkit.Material;

public class CornerBlocks {
	
	//TODO: so now that the general mechanics works I need to convert this over to a plug in model

	private final static byte non = 0;
	private final static byte opt = 1;
	private final static byte FLR = 2;
	private final static byte WWW = 3;
	private final static byte GGG = 4;
	private final static byte WGG = 5;
	private final static byte WGW = 6;
	
	// these won't show up on the roof
	private final static byte BRR = 7; // balcony just below floor and railing topped with nothing above
	private final static byte BNN = 8; // balcony just below floor and nothing above
	private final static byte BDD = 9; // balcony just below floor and door topped with a wall block 
	private final static byte BWW = 10; // WWW 
	private final static byte BGG = 11; // WGG 
	private final static byte BGW = 12; // WGW 
	
	public final static int CornerWidth = 7;
	
	public enum CornerBlocksStyle { ROUND, LARGEDIAGONAL, SMALLDIAGONAL, INNOTCH, OUTNOTCH, DOUBLENOTCH, OUTBUBBLE, INBUBBLE, 
		COLUMN, BRACE, FINS, CASTLE, INDENT, INOUTDENT, 
		ONEBALCONY, TWOBALCONY, ROUNDEDBALCONY, CORNERBALCONY, ANOTHERCORNERBALCONY, OUTBALCONY}; 
	public enum CornerDirections { NW, NE, SW, SE };
	
	private final byte[][] LargeDiagonalNW = {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, opt},
			{FLR, FLR, FLR, FLR, WGG, opt, non},
			{FLR, FLR, FLR, WWW, opt, non, non},
			{FLR, FLR, WGG, opt, non, non, non},
			{FLR, WGG, opt, non, non, non, non},
			{WWW, opt, non, non, non, non, non},
	};
	private byte[][] LargeDiagonalNE;
	private byte[][] LargeDiagonalSW;
	private byte[][] LargeDiagonalSE;
	
	private final byte[][] SmallDiagonalNW = {
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, opt},
			{FLR, FLR, FLR, FLR, WGG, opt, non},
			{WGG, WGG, WGG, WWW, opt, non, non},
	};
	private byte[][] SmallDiagonalNE;
	private byte[][] SmallDiagonalSW;
	private byte[][] SmallDiagonalSE;
	
	private final byte[][] InNotchNW = {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, WWW, WGG, WGG, WWW},
			{FLR, FLR, FLR, WGG, non, non, non},
			{FLR, FLR, FLR, WGG, non, non, non},
			{WWW, WGG, WGG, WWW, non, non, non},
	};
	private byte[][] InNotchNE;
	private byte[][] InNotchSW;
	private byte[][] InNotchSE;
	
	private final byte[][] OutNotchNW = {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, opt},
			{FLR, FLR, FLR, FLR, WWW, opt, non},
			{FLR, FLR, FLR, FLR, FLR, WGW, non},
			{FLR, FLR, WWW, FLR, FLR, FLR, WGW},
			{FLR, WGG, opt, WGW, FLR, WGW, opt},
			{WWW, opt, non, non, WGW, opt, non},
	};
	private byte[][] OutNotchNE;
	private byte[][] OutNotchSW;
	private byte[][] OutNotchSE;
	
	private final byte[][] DoubleNotchNW = {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, GGG},
			{FLR, FLR, FLR, FLR, WWW, GGG, GGG},
			{FLR, FLR, FLR, FLR, GGG, non, non},
			{FLR, FLR, WWW, GGG, GGG, non, non},
			{FLR, FLR, GGG, non, non, non, non},
			{WWW, GGG, GGG, non, non, non, non},
	};
	private byte[][] DoubleNotchNE;
	private byte[][] DoubleNotchSW;
	private byte[][] DoubleNotchSE;
	
	private final byte[][] OutBubbleNW = {
			{FLR, FLR, FLR, FLR, FLR, WGG, opt},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, FLR, WWW, non},
			{FLR, FLR, FLR, FLR, WGG, opt, non},
			{FLR, FLR, FLR, WGG, opt, non, non},
			{WGG, WGG, WWW, opt, non, non, non},
			{opt, non, non, non, non, non, non},
	};
	private byte[][] OutBubbleNE;
	private byte[][] OutBubbleSW;
	private byte[][] OutBubbleSE;
	
	private final byte[][] InBubbleNW = {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, WGG, WGG, WGW},
			{FLR, FLR, FLR, WGG, opt, non, non},
			{FLR, FLR, WGG, opt, non, non, non},
			{FLR, FLR, WGG, non, non, non, non},
			{WWW, WWW, WGW, non, non, non, non},
	};
	private byte[][] InBubbleNE;
	private byte[][] InBubbleSW;
	private byte[][] InBubbleSE;
	
	private final byte[][] ColumnNW = {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, WGG, WGG, WGG, WGG, WGG},
			{FLR, FLR, WGG, non, non, non, non},
			{FLR, FLR, WGG, non, WWW, WWW, WWW},
			{FLR, FLR, WGG, non, WWW, WWW, WWW},
			{WWW, WWW, WGG, non, WWW, WWW, WWW},
	};
	private byte[][] ColumnNE;
	private byte[][] ColumnSW;
	private byte[][] ColumnSE;
	
	private final byte[][] BraceNW = {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, GGG, WWW},
			{FLR, FLR, FLR, FLR, GGG, opt, non},
			{FLR, FLR, FLR, WWW, WWW, non, non},
			{FLR, FLR, GGG, WWW, WWW, WWW, non},
			{FLR, GGG, opt, non, WWW, WWW, WWW},
			{WWW, WWW, non, non, non, WWW, WWW},
	};
	private byte[][] BraceNE;
	private byte[][] BraceSW;
	private byte[][] BraceSE;
	
	private final byte[][] FinsNW = {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, GGG},
			{FLR, FLR, FLR, FLR, WWW, GGG, GGG},
			{FLR, FLR, FLR, FLR, GGG, non, non},
			{FLR, FLR, WWW, GGG, GGG, non, non},
			{FLR, FLR, GGG, non, non, GGG, non},
			{WWW, GGG, GGG, non, non, non, GGG},
	};
	private byte[][] FinsNE;
	private byte[][] FinsSW;
	private byte[][] FinsSE;
	
	private final byte[][] CastleNW = {
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGW, non},
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGW},
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{WWW, WGW, WWW, FLR, WWW, non, non},
			{WWW, non, WWW, WGW, WWW, non, non},
	};
	private byte[][] CastleNE;
	private byte[][] CastleSW;
	private byte[][] CastleSE;
	
	private final byte[][] IndentNW = {
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{WWW, WGG, WGG, WGG, WGG, WGG, non},
			{WWW, non, non, non, non, non, non},
	};
	private byte[][] IndentNE;
	private byte[][] IndentSW;
	private byte[][] IndentSE;
	
	private final byte[][] InOutDentNW = {
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{WWW, WGG, WGG, WWW, FLR, FLR, WGG},
			{WWW, non, non, WWW, WGG, WGG, WGG},
	};
	private byte[][] InOutDentNE;
	private byte[][] InOutDentSW;
	private byte[][] InOutDentSE;
	
	private final byte[][] OneBalconyNW = {
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{FLR, FLR, FLR, FLR, WGW, BNN, BRR},
			{FLR, FLR, FLR, FLR, BDD, BNN, BRR},
			{FLR, FLR, FLR, FLR, WGG, BNN, BRR},
			{WWW, WGW, BDD, WGG, WGG, BNN, BRR},
			{WWW, BNN, BNN, BNN, BNN, BNN, BRR},
			{WWW, BRR, BRR, BRR, BRR, BRR, BWW},
	};
	private byte[][] OneBalconyNE;
	private byte[][] OneBalconySW;
	private byte[][] OneBalconySE;
	
	private final byte[][] TwoBalconyNW = {
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{FLR, FLR, FLR, FLR, BGW, BNN, BRR},
			{FLR, FLR, FLR, FLR, BDD, BNN, BRR},
			{FLR, FLR, FLR, FLR, BGW, BNN, BRR},
			{WWW, BGW, BDD, BGW, BWW, BNN, BRR},
			{WWW, BNN, BNN, BNN, BNN, BWW, BWW},
			{WWW, BRR, BRR, BRR, BRR, BWW, non},
	};
	private byte[][] TwoBalconyNE;
	private byte[][] TwoBalconySW;
	private byte[][] TwoBalconySE;
	
	private final byte[][] RoundedBalconyNW = {
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{FLR, FLR, FLR, FLR, BGW, BNN, BRR},
			{FLR, FLR, FLR, FLR, BDD, BNN, BRR},
			{FLR, FLR, FLR, FLR, BGG, BNN, BRR},
			{WWW, BGW, BDD, BGG, BNN, BNN, WWW}, // yep those are WWW, just to be different
			{WWW, BNN, BNN, BNN, BNN, BWW, opt},
			{WWW, BRR, BRR, BRR, WWW, opt, non},
	};
	private byte[][] RoundedBalconyNE;
	private byte[][] RoundedBalconySW;
	private byte[][] RoundedBalconySE;
	
	private final byte[][] CornerBalconyNW = {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, BWW, BWW, BDD, WWW},
			{FLR, FLR, BWW, BNN, BNN, BNN, BRR},
			{FLR, FLR, BWW, BNN, BNN, BNN, BRR},
			{FLR, FLR, BDD, BNN, BNN, BNN, BRR},
			{WWW, WGG, WWW, BRR, BRR, BRR, BRR},
	};
	private byte[][] CornerBalconyNE;
	private byte[][] CornerBalconySW;
	private byte[][] CornerBalconySE;
	
	private final byte[][] AnotherCornerBalconyNW = {
			{FLR, FLR, FLR, FLR, FLR, FLR, WWW},
			{FLR, FLR, FLR, FLR, FLR, FLR, WGG},
			{FLR, FLR, FLR, BWW, BWW, BDD, WWW},
			{FLR, FLR, BWW, BNN, BNN, BNN, BRR},
			{FLR, FLR, BWW, BNN, BNN, BNN, WWW}, // yep those are WWW, just to be different
			{FLR, FLR, BDD, BNN, BNN, BNN, BRR},
			{WWW, WGG, WWW, BRR, WWW, BRR, BWW},
	};
	private byte[][] AnotherCornerBalconyNE;
	private byte[][] AnotherCornerBalconySW;
	private byte[][] AnotherCornerBalconySE;
	
	private final byte[][] OutBalconyNW = {
			{FLR, FLR, FLR, FLR, FLR, WWW, WWW},
			{FLR, FLR, FLR, FLR, FLR, WGG, non},
			{FLR, FLR, FLR, FLR, WWW, WWW, WWW},
			{FLR, FLR, FLR, BDD, BNN, BNN, BRR},
			{FLR, FLR, WWW, BNN, BNN, BNN, BRR},
			{WWW, WGG, WWW, BNN, BNN, BNN, BRR},
			{WWW, non, WWW, BRR, BRR, BRR, BRR},
	};
	private byte[][] OutBalconyNE;
	private byte[][] OutBalconySW;
	private byte[][] OutBalconySE;
	
	public CornerBlocksStyle pickCornerStyle(Odds odds) {
//		return CornerBlocksStyle.INOUTDENT;
		CornerBlocksStyle[] values = CornerBlocksStyle.values();
		return values[odds.getRandomInt(values.length)];
	}
	
	public CornerBlocks() {
		
		// LargeDiagonalSW already done
		LargeDiagonalNE = flipWE(LargeDiagonalNW);
		LargeDiagonalSE = flipNS(LargeDiagonalNE);
		LargeDiagonalSW = flipNS(LargeDiagonalNW);
		
		// SmallDiagonalSW already done
		SmallDiagonalNE = flipWE(SmallDiagonalNW);
		SmallDiagonalSE = flipNS(SmallDiagonalNE);
		SmallDiagonalSW = flipNS(SmallDiagonalNW);
		
		// InNotchSW already done
		InNotchNE = flipWE(InNotchNW);
		InNotchSE = flipNS(InNotchNE);
		InNotchSW = flipNS(InNotchNW);
		
		// OutNotchSW already done
		OutNotchNE = flipWE(OutNotchNW);
		OutNotchSE = flipNS(OutNotchNE);
		OutNotchSW = flipNS(OutNotchNW);
		
		// DoubleNotchSW already done
		DoubleNotchNE = flipWE(DoubleNotchNW);
		DoubleNotchSE = flipNS(DoubleNotchNE);
		DoubleNotchSW = flipNS(DoubleNotchNW);
		
		// OutBubbleSW already done
		OutBubbleNE = flipWE(OutBubbleNW);
		OutBubbleSE = flipNS(OutBubbleNE);
		OutBubbleSW = flipNS(OutBubbleNW);
		
		// InBubbleSW already done
		InBubbleNE = flipWE(InBubbleNW);
		InBubbleSE = flipNS(InBubbleNE);
		InBubbleSW = flipNS(InBubbleNW);
		
		// ColumnSW already done
		ColumnNE = flipWE(ColumnNW);
		ColumnSE = flipNS(ColumnNE);
		ColumnSW = flipNS(ColumnNW);
		
		// BraceSW already done
		BraceNE = flipWE(BraceNW);
		BraceSE = flipNS(BraceNE);
		BraceSW = flipNS(BraceNW);
		
		// FinsSW already done
		FinsNE = flipWE(FinsNW);
		FinsSE = flipNS(FinsNE);
		FinsSW = flipNS(FinsNW);
		
		// CastleSW already done
		CastleNE = flipWE(CastleNW);
		CastleSE = flipNS(CastleNE);
		CastleSW = flipNS(CastleNW);
		
		// IndentSW already done
		IndentNE = flipWE(IndentNW);
		IndentSE = flipNS(IndentNE);
		IndentSW = flipNS(IndentNW);
		
		// InOutDentSW already done
		InOutDentNE = flipWE(InOutDentNW);
		InOutDentSE = flipNS(InOutDentNE);
		InOutDentSW = flipNS(InOutDentNW);
		
		// OneBalconySW already done
		OneBalconyNE = flipWE(OneBalconyNW);
		OneBalconySE = flipNS(OneBalconyNE);
		OneBalconySW = flipNS(OneBalconyNW);
		
		// TwoBalconySW already done
		TwoBalconyNE = flipWE(TwoBalconyNW);
		TwoBalconySE = flipNS(TwoBalconyNE);
		TwoBalconySW = flipNS(TwoBalconyNW);
		
		// RoundedBalconySW already done
		RoundedBalconyNE = flipWE(RoundedBalconyNW);
		RoundedBalconySE = flipNS(RoundedBalconyNE);
		RoundedBalconySW = flipNS(RoundedBalconyNW);
		
		// CornerBalconySW already done
		CornerBalconyNE = flipWE(CornerBalconyNW);
		CornerBalconySE = flipNS(CornerBalconyNE);
		CornerBalconySW = flipNS(CornerBalconyNW);
		
		// AnotherCornerBalconySW already done
		AnotherCornerBalconyNE = flipWE(AnotherCornerBalconyNW);
		AnotherCornerBalconySE = flipNS(AnotherCornerBalconyNE);
		AnotherCornerBalconySW = flipNS(AnotherCornerBalconyNW);
		
		// OutBalconySW already done
		OutBalconyNE = flipWE(OutBalconyNW);
		OutBalconySE = flipNS(OutBalconyNE);
		OutBalconySW = flipNS(OutBalconyNW);
		
	}
	
	public void drawVerticals(CornerDirections direction, CornerBlocksStyle style, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
			Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
		byte[][] data = getStyleData(direction, style);
		if (data != null)
			setVerticals(data, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}
	
	public void drawHorizontals(CornerDirections direction, CornerBlocksStyle style, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset,
			Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
		byte[][] data = getStyleData(direction, style);
		if (data != null)
			setHorizontals(data, blocks, xInset, y1, y2, zInset, primary, secondary, outsetEffect, onRoof);
	}
	
	//TODO this is really stupidly done, I need to refactor this quite a bit
	private byte[][] getStyleData(CornerDirections direction, CornerBlocksStyle style) {
		switch (style) {
		default:
		case ROUND:
			// legacy style, handled a different way
			return null;
		case LARGEDIAGONAL:
			switch (direction) {
			default:
			case NE:
				return LargeDiagonalNE;
			case NW:
				return LargeDiagonalNW;
			case SE:
				return LargeDiagonalSE;
			case SW:
				return LargeDiagonalSW;
			}
		case SMALLDIAGONAL:
			switch (direction) {
			default:
			case NE:
				return SmallDiagonalNE;
			case NW:
				return SmallDiagonalNW;
			case SE:
				return SmallDiagonalSE;
			case SW:
				return SmallDiagonalSW;
			}
		case INNOTCH:
			switch (direction) {
			default:
			case NE:
				return InNotchNE;
			case NW:
				return InNotchNW;
			case SE:
				return InNotchSE;
			case SW:
				return InNotchSW;
			}
		case OUTNOTCH:
			switch (direction) {
			default:
			case NE:
				return OutNotchNE;
			case NW:
				return OutNotchNW;
			case SE:
				return OutNotchSE;
			case SW:
				return OutNotchSW;
			}
		case DOUBLENOTCH:
			switch (direction) {
			default:
			case NE:
				return DoubleNotchNE;
			case NW:
				return DoubleNotchNW;
			case SE:
				return DoubleNotchSE;
			case SW:
				return DoubleNotchSW;
			}
		case INBUBBLE:
			switch (direction) {
			default:
			case NE:
				return InBubbleNE;
			case NW:
				return InBubbleNW;
			case SE:
				return InBubbleSE;
			case SW:
				return InBubbleSW;
			}
		case OUTBUBBLE:
			switch (direction) {
			default:
			case NE:
				return OutBubbleNE;
			case NW:
				return OutBubbleNW;
			case SE:
				return OutBubbleSE;
			case SW:
				return OutBubbleSW;
			}
		case COLUMN:
			switch (direction) {
			default:
			case NE:
				return ColumnNE;
			case NW:
				return ColumnNW;
			case SE:
				return ColumnSE;
			case SW:
				return ColumnSW;
			}
		case BRACE:
			switch (direction) {
			default:
			case NE:
				return BraceNE;
			case NW:
				return BraceNW;
			case SE:
				return BraceSE;
			case SW:
				return BraceSW;
			}
		case FINS:
			switch (direction) {
			default:
			case NE:
				return FinsNE;
			case NW:
				return FinsNW;
			case SE:
				return FinsSE;
			case SW:
				return FinsSW;
			}
		case CASTLE:
			switch (direction) {
			default:
			case NE:
				return CastleNE;
			case NW:
				return CastleNW;
			case SE:
				return CastleSE;
			case SW:
				return CastleSW;
			}
		case INDENT:
			switch (direction) {
			default:
			case NE:
				return IndentNE;
			case NW:
				return IndentNW;
			case SE:
				return IndentSE;
			case SW:
				return IndentSW;
			}
		case INOUTDENT:
			switch (direction) {
			default:
			case NE:
				return InOutDentNE;
			case NW:
				return InOutDentNW;
			case SE:
				return InOutDentSE;
			case SW:
				return InOutDentSW;
			}
		case ONEBALCONY:
			switch (direction) {
			default:
			case NE:
				return OneBalconyNE;
			case NW:
				return OneBalconyNW;
			case SE:
				return OneBalconySE;
			case SW:
				return OneBalconySW;
			}
		case TWOBALCONY:
			switch (direction) {
			default:
			case NE:
				return TwoBalconyNE;
			case NW:
				return TwoBalconyNW;
			case SE:
				return TwoBalconySE;
			case SW:
				return TwoBalconySW;
			}
		case ROUNDEDBALCONY:
			switch (direction) {
			default:
			case NE:
				return RoundedBalconyNE;
			case NW:
				return RoundedBalconyNW;
			case SE:
				return RoundedBalconySE;
			case SW:
				return RoundedBalconySW;
			}
		case CORNERBALCONY:
			switch (direction) {
			default:
			case NE:
				return CornerBalconyNE;
			case NW:
				return CornerBalconyNW;
			case SE:
				return CornerBalconySE;
			case SW:
				return CornerBalconySW;
			}
		case ANOTHERCORNERBALCONY:
			switch (direction) {
			default:
			case NE:
				return AnotherCornerBalconyNE;
			case NW:
				return AnotherCornerBalconyNW;
			case SE:
				return AnotherCornerBalconySE;
			case SW:
				return AnotherCornerBalconySW;
			}
		case OUTBALCONY:
			switch (direction) {
			default:
			case NE:
				return OutBalconyNE;
			case NW:
				return OutBalconyNW;
			case SE:
				return OutBalconySE;
			case SW:
				return OutBalconySW;
			}
		}
	}
	
	private void setVerticals(byte[][] source, AbstractBlocks blocks, int xInset, int y1, int y2, int zInset, 
			Material primary, Material secondary, boolean outsetEffect, boolean onRoof) {
		for (int x = 0; x < CornerWidth; x++) {
			for (int z = 0; z < CornerWidth; z++) {
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
				case WGW:
					blocks.setBlock(xInset + x, y1, zInset + z, primary);
					blocks.setBlocks(xInset + x, y1 + 1, y2 - 1, zInset + z, secondary);
					blocks.setBlock(xInset + x, y2 - 1, zInset + z, primary);
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
		for (int x = 0; x < CornerWidth; x++) {
			for (int z = 0; z < CornerWidth; z++) {
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
				case GGG:
				case WGG:
				case WGW:
				case BNN:
				case BDD:
				case BRR:
				case BWW:
				case BGW:
				case BGG:
				default:
					blocks.setBlocks(xInset + x, y1, y2, zInset + z, primary);
					break;
				}
			}
		}
	}
	
	private byte[][] flipWE(byte[][] source) {
		byte[][] result = new byte[CornerWidth][CornerWidth];
		for (int x = 0; x < CornerWidth; x++) {
			for (int z = 0; z < CornerWidth; z++) {
				result[CornerWidth - x - 1][z] = source[x][z];
			}
		}
		return result;
	}

	private byte[][] flipNS(byte[][] source) {
		byte[][] result = new byte[CornerWidth][CornerWidth];
		for (int x = 0; x < CornerWidth; x++) {
			for (int z = 0; z < CornerWidth; z++) {
				result[x][CornerWidth - z - 1] = source[x][z];
			}
		}
		return result;
	}
}
