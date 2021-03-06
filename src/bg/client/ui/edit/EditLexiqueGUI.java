package bg.client.ui.edit;

import bg.client.inter.cicada.model.Lexique;
import bg.client.inter.cicada.model.LexiqueFactory;
import bg.client.inter.cicada.model.Phrase;
import bg.client.inter.cicada.model.UniteLexicale;
import bg.client.ui.util.popup.IPopupListener;
import bg.client.ui.util.popup.PopupDialogOption;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditLexiqueGUI extends Composite {

	private static EditLexiqueGUIUiBinder uiBinder = GWT.create(EditLexiqueGUIUiBinder.class);

	interface EditLexiqueGUIUiBinder extends UiBinder<Widget, EditLexiqueGUI> {
	}

	private static EditLexiqueGUI instance;

	@UiField
	Button buttonRecord;

	@UiField
	Button buttonNext;

	@UiField
	Button buttonPrevious;

	@UiField
	Button buttonNew;

	@UiField
	TextBox textBoxReponse;

	@UiField
	TextBox textBoxQuestion;

	public static EditLexiqueGUI getInstance() {
		if (instance == null) {
			instance = new EditLexiqueGUI();
		}
		return instance;
	}

	private EditLexiqueGUI() {
		initWidget(uiBinder.createAndBindUi(this));
		buttonRecord.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				recordAction();
			}
		});
		buttonNext.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				nextPhrase(1);
			}
		});
		buttonPrevious.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				nextPhrase(-1);
			}
		});
		buttonNew.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				createNew();
			}
		});
		displayUniteLexicaleCourante();
	}

	private Lexique getLexique() {
		return LexiqueFactory.getInstance().getLexique();
	}

	private void nextPhrase(int i) {
		System.out.println("NextPhrase ");
		getLexique().next(i);
		displayUniteLexicaleCourante();

	}

	UniteLexicale item;

	void displayUniteLexicaleCourante() {
		UniteLexicale ul = getLexique().getUniteLexicaleCourante();
		displayUniteLexicaleCourante(ul);
	}

	void displayUniteLexicaleCourante(UniteLexicale ul) {
		this.item = ul;
		if (ul == null) {
			textBoxQuestion.setText("");
			textBoxReponse.setText("");
		} else {
			textBoxQuestion.setText(ul.getPhrase_0().getText());
			Phrase pr = ul.getPhrase_1();
			textBoxReponse.setText(pr.getText());
			int start = pr.getwStartVisible(0);
			int end = pr.getEndVisible(0);
			int range = Math.abs(end - start);

			textBoxReponse.setSelectionRange(start, range);
		}
	}

	private void record() {
		if (this.item == null) {
			this.item = new UniteLexicale(new Phrase(), new Phrase());
			getLexique().getListUniteLexicale().add(item);

		}
		this.item.getPhrase_0().setText(textBoxQuestion.getText());
		this.item.getPhrase_1().setText(textBoxReponse.getText());
		String textReponse = textBoxReponse.getText();
		String selectedText = textBoxReponse.getSelectedText();
		int selectedLength = textBoxReponse.getSelectionLength();

		// int selectionStart = textBoxReponse.getCursorPos();
		int selectionStart = textReponse.indexOf(selectedText);
		GWT.log("Start " + selectionStart + " Length :" + selectedLength + "  text : " + selectedText + "  ");
		item.getPhrase_1().setSelected(selectionStart, selectionStart + selectedLength);
		LexiqueFactory.getInstance().saveItem(this.item);

	}

	/**
	 * 
	 * @return
	 */
	private void createNew() {
		boolean isTheSame = isTheSame();
		System.out.println("  isTheSame " + isTheSame);
		if (isTheSame) {
			item = null;
			displayUniteLexicaleCourante(null);
		} else {
			IPopupListener listener = new IPopupListener() {

				@Override
				public void actionPerformed(int n, String s) {
					if (n == PopupDialogOption.YES_OPTION) {
						record();
						;
					} else if (n == PopupDialogOption.CANCEL_OPTION) {
						;
					} else {// No_Option
						;
						;
					}
					item = null;
					displayUniteLexicaleCourante(null);
				}
			};
			PopupDialogOption.getInstance().showConfirmDialog("Save modifications ?", "NO", "Yes", listener);

		}

	}

	/**
	 * 
	 * @return
	 */
	private void recordAction() {
		boolean isTheSame = isTheSame();
		if (!isTheSame) {
			IPopupListener listener = new IPopupListener() {

				@Override
				public void actionPerformed(int n, String s) {
					if (n == PopupDialogOption.YES_OPTION) {
						record();
						;
					}
				}
			};
			PopupDialogOption.getInstance().showConfirmDialog("Save modifications ?", "NO", "Yes", listener);
		}

	}

	private boolean isTheSame() {
		UniteLexicale ul_1 = this.item;
		if (ul_1 == null) {
			ul_1 = new UniteLexicale(new Phrase(""), new Phrase(""));
		}
		UniteLexicale ul_2 = new UniteLexicale(textBoxQuestion.getText(), textBoxReponse.getText());
		boolean isTheSame = ul_1.equals(ul_2);
		return isTheSame;

	}

	public void notifyNewLexique() {
		displayUniteLexicaleCourante();
	}

}
