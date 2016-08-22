package bg.client.ui.edit;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LexiqueChooser extends Composite {

	private static LexiqueChooserUiBinder uiBinder = GWT
			.create(LexiqueChooserUiBinder.class);

	private static LexiqueChooser instance ;
	interface LexiqueChooserUiBinder extends UiBinder<Widget, LexiqueChooser> {
	}
	
	@UiField
	Label labelComment;
	
	@UiField
	FlexTable tableLexiques;
	
	private  LexiqueChooser() {
		tableLexiques = new FlexTable();
		initWidget(uiBinder.createAndBindUi(this));
	}

	public static LexiqueChooser getInstance() {
		if (instance ==null){
			instance =new LexiqueChooser();
		}
		
		return instance;
	}

	public void setLexiques(List<String> list) {
		labelComment.setText("size : "+list.size()+"  "+list);
		/*tableLexiques.removeAllRows();
		int i=0;
		for(String lexique : list){
			tableLexiques.setText(i, 0, lexique);
			i++;
		}*/
	}
	
	

}
