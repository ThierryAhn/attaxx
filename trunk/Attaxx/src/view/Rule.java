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

/**
 * Classe Rule pour afficher les règles du jeu
 * @author Folabi
 *
 */
public class Rule extends JDialog{

	/**
	 * Zone de texte
	 */
	private JTextPane textPane = new JTextPane();
	private JScrollPane scrollPane = new JScrollPane(textPane);


	public Rule(JFrame owner){
		super(owner, "Règle du jeu", true);

		Dimension ownerSize = owner.getSize();
		Point p = owner.getLocation();
		setLocation(p.x + ownerSize.width / 4, p.y + ownerSize.height / 4);

		JPanel panelInfo = new JPanel(new BorderLayout());


		String text ="<html><body>\n";

		text += "<h2>Règle du jeu </h2>\n";
		text += "<ul>";

		text +="<li> Le jeu se joue sur un plateau carré, subdivisé en cases de taille égale, </li>";
		text +="<li> Deux joueurs s'affrontent avec des pions portant la couleur du joueur, </li>";
		text +="<li> Chaque joueur démarre avec deux pions , les quatre étant placés aux quatre coins du plateau, </li>";
		text +="<li> En utilisant les déplacements horizontaux, verticaux et en biais, chaque pion peut aller <br/>"
				+ "dans une case adjacente libre, en se dédoublant, ou peut sauter une seule case pour aller <br/>"
				+ "dans une case libre (sans se dédoubler).  </li>";
		text +="Illustration ci dessous avec, en vert, la position initiale, en jaune les 8 destinations provoquant le <br/>"
				+ "dédoublement du pion, et en gris les 16 destinations provoquant un saut du pion, qui disparaît alors de <br/>"
				+ "sa place initiale. ";
		
		text +="</ul>";
		
		
		text +="<table>";
		for(int i = 0; i < 7; i++){
			text +="<tr>";
			for(int j = 0; j < 7; j++){
				if( ((i == 0) || (i == 4)) && ((j > 0) && (j < 6)) ){
					text +="<td class=\"back-color1\"></td>";
				}else{
					if( ((i > 0) && (i < 4)) ){
						if( ((j == 1) || (j == 5)) ){
							text +="<td class=\"back-color1\"></td>";
						}else{
							if( ((j > 1) && (j < 5)) ){
								if(i == 2 && j == 3){
									text +="<td class=\"back-color3\"></td>";
								}else{
									text +="<td class=\"back-color2\"></td>";
								}
							}else{
								text +="<td></td>";
							}
						}
					}else{
						text +="<td></td>";
						
					}
				}
			}
			text +="</tr>";
		}
		text +="</table>";
		//text +="<"
		
		
		text += "<ul>";
		text +="Chaque pion adverse touchant la case de destination change alors de couleur <br/>"
				+ "(dans le sens horizontal, vertical ou diagonal, jusqu'à, 8 pions peuvent ainsi être touchés).";

		text +="<li> Chacun joue alternativement son tour en effectuant ainsi un dédoublement ou un saut. </li>";
		text +="<li> On ne peut passer son tour que lorsque l'on y est obligé (n'ayant aucune possibilité de mouvement). </li>";
		text +="<li> Le gagnant est celui qui a le plus de pions de sa couleur quand le plateau est rempli. <br/>"
				+ "La partie peut aussi se terminer plus tôt quand un joueur n'a plus de pion ou ne peut <br/>"
				+ "plus jouer, même dans ses mouvements futurs (toutefois, si un joueur occupe plus de la <br/>"
				+ "moitié des cases, s'il peut remplir les autres sans libérer une case accessible à son <br/>"
				+ "adversaire, la partie peut se terminer à ce moment là). </li>";



		text +="</ul>";


		text +="</body></html>";

		textPane.setContentType("text/html");
		// css textPane
		HTMLEditorKit kit = new HTMLEditorKit();
		textPane.setEditorKit(kit);
		StyleSheet styleSheet = kit.getStyleSheet();
		styleSheet.addRule("body {text-align:justify; text-justify:inter-word;}");
		styleSheet.addRule("body {color:#000000; font-family:Verdana,sans-serif;}");
		styleSheet.addRule("caption{color : red;}");
		styleSheet.addRule("h2{text-decoration :underline;}");
		styleSheet.addRule("table {text-align :center; border:1px solid black; width:40%;}");
		styleSheet.addRule("td {border-width:1px; border-style:solid;}");
		styleSheet.addRule(".back-color1{background-color : gray;}");
		styleSheet.addRule(".back-color2{background-color : yellow;}");
		styleSheet.addRule(".back-color3{background-color : green;}");
		
		
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

		getContentPane().add(panelInfo);
		getContentPane().add(panelButton, BorderLayout.SOUTH);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		pack();
		setVisible(true);
	}

}
