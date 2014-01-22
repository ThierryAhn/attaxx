package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

@SuppressWarnings("serial")
public class About extends JDialog{

	/**
	 * Zone de texte
	 */
	private JTextPane textPane = new JTextPane();
	private JScrollPane scrollPane = new JScrollPane(textPane);

	public About(JFrame owner){
		super(owner, "A propos !", true);


		Dimension ownerSize = owner.getSize();
		Point p = owner.getLocation();
		setLocation(p.x + ownerSize.width / 4, p.y + ownerSize.height / 4);

		JPanel panelNorth = new JPanel();

		JPanel panelInfo = new JPanel(new BorderLayout());


		String text ="<html><body>";

		text += "<h3> Université de Rouen </h3><br/>";

		text += "<h3> Développé par : </h3>";
		text += "&nbsp;&nbsp;&nbsp;&nbsp; &copy;ABALINE &amp; AHOUNOU";

		text +="</body></html>";

		textPane.setContentType("text/html");
		// css textPane
		HTMLEditorKit kit = new HTMLEditorKit();
		textPane.setEditorKit(kit);
		StyleSheet styleSheet = kit.getStyleSheet();
		styleSheet.addRule("body {margin : 22px;}");
		styleSheet.addRule("body {color:#000000; font-family:Verdana,sans-serif;}");
		styleSheet.addRule("caption{color : red;}");
		styleSheet.addRule("h2 {text-decoration :underline;}");
		styleSheet.addRule("td {text-align :center;}");
		Document doc = kit.createDefaultDocument();
		textPane.setDocument(doc);



		textPane.setEditable(false);
		textPane.setText(text);
		panelInfo.add(textPane);


		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		JPanel panelButton = new JPanel();
		panelButton.add(button);


		getContentPane().add(panelNorth, BorderLayout.NORTH);
		getContentPane().add(panelInfo);
		getContentPane().add(panelButton, BorderLayout.SOUTH);


		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		pack();
		setVisible(true);
	}
}
