package gay.mountainspring.cauldronsplus.block;

import com.mojang.serialization.MapCodec;

import gay.mountainspring.aquifer.block.cauldron.CauldronContentsType;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusContentsTypes;

public class SlimeCauldronBlock extends NineLeveledCauldronBlock {
	public static final MapCodec<SlimeCauldronBlock> CODEC = createCodec(SlimeCauldronBlock::new);
	
	@Override
	protected MapCodec<SlimeCauldronBlock> getCodec() {
		return CODEC;
	}
	
	public SlimeCauldronBlock(CauldronGroup group, Settings settings) {
		super(group, settings, null); // TODO: behavior map
		// TODO Auto-generated constructor stub
	}

	@Override
	public CauldronContentsType aquifer$getContentsType() {
		return CauldronsPlusContentsTypes.SLIME;
	}
}