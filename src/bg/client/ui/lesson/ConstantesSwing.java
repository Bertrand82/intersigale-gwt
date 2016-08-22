package bg.client.ui.lesson;

import com.google.gwt.user.client.ui.Widget;

public class ConstantesSwing {

	public static String color_start = "white";
	public static String color_ok = "green";
	public static String color_err = "red";

	static public void setColor(Widget widget, String color) {
		widget.getElement().getStyle().setColor(color);
	}

	static public void setColorBackground(Widget widget, String color) {
		widget.getElement().getStyle().setBackgroundColor(color);
		;
	}
}
