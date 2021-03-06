package bg.client.ui.lesson;

import bg.client.inter.cicada.model.Lexique;
import bg.client.inter.cicada.model.LexiqueFactory;
import bg.client.inter.cicada.model.UniteLexicale;
import bg.client.ui.lesson.lessonStat.StatistiquesPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Lesson extends Composite {
	private static String STR_VIDE = " ";
	private static LessonUiBinder uiBinder = GWT.create(LessonUiBinder.class);

	String colorNeutre = ConstantesUI.color_start;
	String colorOK = ConstantesUI.color_ok;
	String colorKO = ConstantesUI.color_err;;

	interface LessonUiBinder extends UiBinder<Widget, Lesson> {
	}

	@UiField
	Button button_OK_NEXT;

	
	@UiField
	TextBox textBoxReponse;

	@UiField
	Label labelCorrection;

	@UiField
	Label labelQuestion;

	@UiField
	Label labelStat;

	@UiField
	SimplePanel panelStat;

	public Lesson() {
		initWidget(uiBinder.createAndBindUi(this));
		button_OK_NEXT.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ok_next();
			}

		
		});
		
		textBoxReponse.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				int cursorPosition = textBoxReponse.getCursorPos();
				String text = textBoxReponse.getText();
				if (text.length() > cursorPosition) {
					String text1 = text.substring(0, cursorPosition);
					String text2 = text.substring(cursorPosition + 1);
					String replace = text1 + text2;
					textBoxReponse.setText(replace);
					textBoxReponse.setCursorPos(cursorPosition);
				}

			}
		});

		displayUniteLexicaleCourante();
		panelStat.add(StatistiquesPanel.getInstance());
	}

	
	private static Lesson instance = new Lesson();

	public static Lesson getInstance() {
		return instance;
	}

	private Lexique getLexique() {
		return LexiqueFactory.getInstance().getLexique();
	}
	private void ok_next() {
		if(button_OK_NEXT.getText().equalsIgnoreCase("OK")){
			validResult();
			button_OK_NEXT.setText("Next");
		}else {
			nextPhrase();
			button_OK_NEXT.setText("OK");
		}
	}
	private void nextPhrase() {
		getLexique().next();
		displayUniteLexicaleCourante();
		labelCorrection.setText(STR_VIDE);
		displayResult(null, " ");
		
		this.textBoxReponse.setText(reponseStr);
		this.textBoxReponse.setCursorPos(this.positionCuror);
		this.textBoxReponse.setFocus(true);
		StatistiquesPanel.getInstance().removeStat();
	}

	

	private void displayResult(Boolean ok, String stat) {
		String color;
		if (ok == null) {
			color = colorNeutre;
		} else if (ok) {
			color = colorOK;
		} else {
			color = colorKO;
		}
		ConstantesUI.setColorBackground(textBoxReponse, color);
		ConstantesUI.setColorBackground(labelStat, color);
		labelStat.setText(stat);
	}

	private void validResult() {
		UniteLexicale ul = getLexique().getUniteLexicaleCourante();
		if (ul != null) {
			UniteLexicale ulCourrante = getLexique().getUniteLexicaleCourante();
			boolean ok = ulCourrante.resultProcess(this.textBoxReponse.getText());
			String stat = " ? ";
			StatistiquesPanel.getInstance().updateStat(ulCourrante, ok);
			System.out.println("ValidResult " + ok);
			// this.textFieldResponse.setText(text);
			if (ok) {
				stat = " ok ";
			} else {
				stat = " ko ";
				labelCorrection.setText(getLexique().getUniteLexicaleCourante().getPhrase_1().getText());
			}

			displayResult(ok, stat);
		}
	}

	private int positionCuror = 0;
	private String reponseStr = "";

	void displayUniteLexicaleCourante() {
		UniteLexicale ul = getLexique().getUniteLexicaleCourante();
		if (ul != null) {
			labelQuestion.setText(ul.getPhrase_0().getText());
			reponseStr = ul.getPhrase_1().getTextVisible();
			textBoxReponse.setText(reponseStr);
			positionCuror = ul.getPhrase_1().getEndVisible();
		}
	}

	public void notifyNewLexique() {
		displayUniteLexicaleCourante();
	}
}
