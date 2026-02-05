package gay.mountainspring.cauldronsplus.block;

import com.mojang.serialization.MapCodec;

import gay.mountainspring.aquifer.block.cauldron.CauldronContentsType;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusBehavior;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusContentsTypes;

public class MilkCauldronBlock extends FourLeveledCauldronBlock {
	public static final MapCodec<MilkCauldronBlock> CODEC = createCodec(MilkCauldronBlock::new);
	
	@Override
	protected MapCodec<? extends MilkCauldronBlock> getCodec() {
		return CODEC;
	}
	
	public MilkCauldronBlock(CauldronGroup group, Settings settings) {
		super(group, settings, CauldronsPlusBehavior.MILK_CAULDRON_BEHAVIOR);
	}
	
	@Override
	public CauldronContentsType aquifer$getContentsType() {
		return CauldronsPlusContentsTypes.MILK;
	}
}
