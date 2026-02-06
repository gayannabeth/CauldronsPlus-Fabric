package gay.mountainspring.cauldronsplus.block;

import com.mojang.serialization.MapCodec;

import gay.mountainspring.aquifer.block.cauldron.CauldronContentsType;
import gay.mountainspring.aquifer.block.cauldron.CauldronGroup;
import gay.mountainspring.cauldronsplus.Cauldrons;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusBehavior;
import gay.mountainspring.cauldronsplus.block.cauldron.CauldronsPlusContentsTypes;
import net.minecraft.util.Identifier;

public class MilkCauldronBlock extends FourLeveledCauldronBlock {
	public static final MapCodec<MilkCauldronBlock> CODEC = createCodec(MilkCauldronBlock::new);
	
	public static final Identifier MILK_TEXTURE_ID = Identifier.of(Cauldrons.MOD_ID, "block/milk_still");
	
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
