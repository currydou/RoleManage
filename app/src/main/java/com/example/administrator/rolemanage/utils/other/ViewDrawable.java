package com.example.administrator.rolemanage.utils.other;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * @author xmh
 *	ViewDrawable
 *         2016年7月20日
 */
public class ViewDrawable {

	private static Resources resources;

	public static Drawable getDrawable(Context context, int id) {
		if (resources == null) {
			resources = context.getResources();
		}
		Drawable imgOff = resources.getDrawable(id);
		imgOff.setBounds(0, 0, imgOff.getMinimumWidth(),
				imgOff.getMinimumHeight());
		return imgOff;
	}
}
