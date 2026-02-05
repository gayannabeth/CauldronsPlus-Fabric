package gay.mountainspring.cauldronsplus.block.entity;

import org.jetbrains.annotations.Nullable;

import gay.mountainspring.aquifer.util.ColorSupplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

public class DyedWaterCauldronBlockEntity extends BlockEntity implements ColorSupplier {
	private DyedColorComponent color = new DyedColorComponent(0xffffffff, true);
	
	public DyedWaterCauldronBlockEntity(BlockPos pos, BlockState state) {
		super(CauldronBlockEntityTypes.DYED_WATER_CAULDRON, pos, state);
	}
	
	public void setColor(int newColor) {
		this.color = new DyedColorComponent(MathHelper.clamp(newColor & 0xffffff, 0x000000, 0xffffff), true);
	}
	
	public void setColor(DyedColorComponent newColor) {
		this.color = newColor;
	}
	
	@Override
	public int getColor() {
		return this.color.rgb();
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt, WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		nbt.putInt("color", this.getColor());
	}
	
	@Override
	protected void readNbt(NbtCompound nbt, WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.color = new DyedColorComponent(nbt.getInt("color"), true);
		if (this.world != null) {
			this.world.updateListeners(pos, this.getCachedState(), this.getCachedState(), Block.REDRAW_ON_MAIN_THREAD);
		}
	}
	
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	
	@Override
	public NbtCompound toInitialChunkDataNbt(WrapperLookup registryLookup) {
		return this.createNbt(registryLookup);
	}
	
	public void applyDye(DyeItem dye) {
		this.setColor(ColorHelper.Argb.averageArgb(dye.getColor().getEntityColor() & 0xffffff, this.getColor()));
	}
	
	public ItemStack applyColor(ItemStack stack) {
		if (stack.isIn(ItemTags.DYEABLE)) {
			DyedColorComponent colorComponent = stack.get(DataComponentTypes.DYED_COLOR);
			DyedColorComponent newColorComponent = colorComponent == null ? new DyedColorComponent(this.color.rgb(), this.color.showInTooltip()) : new DyedColorComponent(ColorHelper.Argb.averageArgb(colorComponent.rgb(), this.getColor()), colorComponent.showInTooltip());
			ItemStack newStack = stack.copy();
			newStack.set(DataComponentTypes.DYED_COLOR, newColorComponent);
			return newStack;
		} else {
			return stack;
		}
	}
	
	@Override
	public @Nullable Object getRenderData() {
		return this.getColor();
	}
}