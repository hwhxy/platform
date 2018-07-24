package com.fulu.game.point.config.editor;


import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

public class CustomFloatEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text)
			throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text)) {
			text=text.replaceAll(",", "");
			super.setValue(Float.valueOf(text));
		} else {
			super.setValue(null);
		}
	}

	@Override
	public String getAsText() {
		Object obj=super.getValue();
		String text=null;
		if (obj != null && obj instanceof String ) {
			text=obj.toString().replaceAll(",", "");
		}
		return text;
	}

}
