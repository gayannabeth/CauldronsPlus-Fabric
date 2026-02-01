package gay.mountainspring.cauldronsplus.util;

import org.jetbrains.annotations.Nullable;

import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.DyeItem;
import net.minecraft.util.math.ColorHelper;

public class ColorMixingUtil {
	private ColorMixingUtil() {}
	
	public static DyedColorComponent combine(DyedColorComponent first, DyedColorComponent second) {
		int r1 = ColorHelper.Argb.getRed(first.rgb());
		int g1 = ColorHelper.Argb.getGreen(first.rgb());
		int b1 = ColorHelper.Argb.getBlue(first.rgb());
		int r2 = ColorHelper.Argb.getRed(second.rgb());
		int g2 = ColorHelper.Argb.getGreen(second.rgb());
		int b2 = ColorHelper.Argb.getBlue(second.rgb());
		int r = r1 + r2;
		int g = g1 + g2;
		int b = b1 + b2;
		r /= 2;
		g /= 2;
		b /= 2;
		int max = Math.max(r1, Math.max(g1, b1)) + Math.max(r2, Math.max(g2, b2));
		float f0 = (float) max/2;
		float f1 = Math.max(r, Math.max(g, b));
		r = (int) (r * f0 / f1);
		g = (int) (g * f0 / f1);
		b = (int) (b * f0 / f1);
		int rgb = ColorHelper.Argb.getArgb(0, r, g, b);
		return new DyedColorComponent(rgb, first.showInTooltip() || second.showInTooltip());
	}
	
	public static DyedColorComponent combine(DyedColorComponent... colors) {
		if (colors.length == 0) {
			return new DyedColorComponent(0x000000, true);
		} else if (colors.length == 1) {
			return colors[0];
		} else if (colors.length == 2) {
			return combine(colors[0], colors[1]);
		} else {
			int r = 0;
			int g = 0;
			int b = 0;
			int max = 0;
			int count = colors.length;
			boolean showInTooltip = false;
			
			for (DyedColorComponent color : colors) {
				int rgb = color.rgb();
				int r1 = ColorHelper.Argb.getRed(rgb);
				int g1 = ColorHelper.Argb.getGreen(rgb);
				int b1 = ColorHelper.Argb.getBlue(rgb);
				max += Math.max(r1, Math.max(g1, b1));
				r += r1;
				g += g1;
				b += b1;
				if (color.showInTooltip()) showInTooltip = true;
			}
			
			r /= count;
			g /= count;
			b /= count;
			float f0 = (float) max / count;
			float f1 = Math.max(r, Math.max(g, b));
			r = (int) (r * f0 / f1);
			g = (int) (g * f0 / f1);
			b = (int) (b * f0 / f1);
			int rgb = ColorHelper.Argb.getArgb(0, r, g, b);
			return new DyedColorComponent(rgb, showInTooltip);
		}
	}
	
	public static DyedColorComponent combine(@Nullable DyedColorComponent colorComponent, DyeItem dye) {
		if (colorComponent == null) {
			return new DyedColorComponent(dye.getColor().getEntityColor(), true);
		}
		
		int dyeRgb = dye.getColor().getEntityColor();
		int ccr = ColorHelper.Argb.getRed(colorComponent.rgb());
		int ccg = ColorHelper.Argb.getGreen(colorComponent.rgb());
		int ccb = ColorHelper.Argb.getBlue(colorComponent.rgb());
		int dyer = ColorHelper.Argb.getRed(dyeRgb);
		int dyeg = ColorHelper.Argb.getGreen(dyeRgb);
		int dyeb = ColorHelper.Argb.getBlue(dyeRgb);
		int r = ccr + dyer;
		int g = ccg + dyeg;
		int b = ccb + dyeb;
		r /= 2;
		g /= 2;
		b /= 2;
		int max = Math.max(ccr, Math.max(ccg, ccb)) + Math.max(dyer, Math.max(dyeg, dyeb));
		float f0 = (float) max/2;
		float f1 = Math.max(r, Math.max(g, b));
		r = (int) (r * f0 / f1);
		g = (int) (g * f0 / f1);
		b = (int) (b * f0 / f1);
		int rgb = ColorHelper.Argb.getArgb(0, r, g, b);
		return new DyedColorComponent(rgb, colorComponent.showInTooltip());
	}
	
	public static DyedColorComponent combine(@Nullable DyedColorComponent colorComponent, DyeItem... dyes) {
		if (dyes.length == 0) {
			return colorComponent == null ? new DyedColorComponent(0x000000, true) : colorComponent;
		}
		
		if (dyes.length == 1) {
			return combine(colorComponent, dyes[0]);
		}
		
		int r = 0;
		int g = 0;
		int b = 0;
		int max = 0;
		int count = dyes.length;
		
		if (colorComponent != null) {
			int r1 = ColorHelper.Argb.getRed(colorComponent.rgb());
			int g1 = ColorHelper.Argb.getGreen(colorComponent.rgb());
			int b1 = ColorHelper.Argb.getBlue(colorComponent.rgb());
			max += Math.max(r1, Math.max(g1, b1));
			r += r1;
			g += g1;
			b += b1;
			count++;
		}
		
		for (DyeItem dye : dyes) {
			int rgb = dye.getColor().getEntityColor();
			int r1 = ColorHelper.Argb.getRed(rgb);
			int g1 = ColorHelper.Argb.getGreen(rgb);
			int b1 = ColorHelper.Argb.getBlue(rgb);
			max += Math.max(r1, Math.max(g1, b1));
			r += r1;
			g += g1;
			b += b1;
		}
		
		r /= count;
		g /= count;
		b /= count;
		float f0 = (float)max / count;
		float f1 = Math.max(r, Math.max(g, b));
		r = (int) (r * f0 / f1);
		g = (int) (g * f0 / f1);
		b = (int) (b * f0 / f1);
		int rgb = ColorHelper.Argb.getArgb(0, r, g, b);
		boolean showInTooltip = colorComponent == null || colorComponent.showInTooltip();
		return new DyedColorComponent(rgb, showInTooltip);
	}
}