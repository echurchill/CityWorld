package me.daddychurchill.CityWorld.Support;

import java.util.Stack;

import org.bukkit.Material;
import org.bukkit.block.Block;

public final class RememberedBlocks {

	private final SupportBlocks blocks;
	private final Stack<rememberedBlock> originals;

	public RememberedBlocks(SupportBlocks chunk) {
		blocks = chunk;
		originals = new Stack<>();
	}

	private static class rememberedBlock {
		private final Material origMaterial;
		private final int origX;
		private final int origY;
		private final int origZ;

		rememberedBlock(Block block, int x, int y, int z) {
			origMaterial = block.getType();
			origX = x;
			origY = y;
			origZ = z;
		}

		void restoreBlock(SupportBlocks blocks) {
			blocks.setBlock(origX, origY, origZ, origMaterial);
		}
	}

	public void forgetBlocks() {
		originals.removeAllElements();
	}

	public void restoreBlocks() {
		while (!originals.empty()) {

			// restore the block
			rememberedBlock original = originals.pop();
			original.restoreBlock(blocks);
		}
	}

	public void setBlock(int x, int y, int z, Material material) {
		originals.push(new rememberedBlock(blocks.getActualBlock(x, y, z), x, y, z));
		blocks.setBlock(x, y, z, material);
	}

	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				Block block = blocks.getActualBlock(x, y, z);
				originals.push(new rememberedBlock(block, x, y, z));
				block.setType(material);
			}
		}
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					Block block = blocks.getActualBlock(x, y, z);
					originals.push(new rememberedBlock(block, x, y, z));
					block.setType(material);
				}
			}
		}
	}

	public void clearBlocks(int x1, int x2, int y, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				Block block = blocks.getActualBlock(x, y, z);
				originals.push(new rememberedBlock(block, x, y, z));

				// now clear it
				if (!block.isEmpty()) {
					block.setType(Material.AIR);
				}
			}
		}
	}

	public void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					Block block = blocks.getActualBlock(x, y, z);
					originals.push(new rememberedBlock(block, x, y, z));

					// now clear it
					if (!block.isEmpty()) {
						block.setType(Material.AIR);
					}
				}
			}
		}
	}

}
