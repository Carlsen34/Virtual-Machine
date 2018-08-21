
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.*;

public class JanelaPrincipal extends JFrame implements ActionListener {

	JFrame tela = new JFrame("Maquina Virtual");
	JPanel panel_esq = new JPanel();
	JPanel panel_dir = new JPanel();
	JPanel panel_bot_esq = new JPanel();
	JPanel panel_bot_dir = new JPanel();
	JPanel botoes = new JPanel();
	JLabel rgi = new JLabel();
	JLabel rgs = new JLabel();
	JLabel pilhaM = new JLabel();
	JLabel pilhaP = new JLabel();
	JLabel prnInstruction = new JLabel();
	JLabel debugLabel = new JLabel();

	JButton debug = new JButton();
	JButton continuar = new JButton();

	public JanelaPrincipal() {

		tela.add(panel_esq);
		tela.add(panel_dir);
		tela.add(panel_bot_esq);
		tela.add(panel_bot_dir);
		tela.add(botoes);
		tela.setLayout(new FlowLayout());

		panel_esq.setPreferredSize(new Dimension(500, 250));
		panel_esq.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_esq.setBackground(Color.white);

		panel_dir.setPreferredSize(new Dimension(500, 250));
		panel_dir.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_dir.setBackground(Color.white);

		panel_bot_esq.setPreferredSize(new Dimension(500, 250));
		panel_bot_esq.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_bot_esq.setBackground(Color.white);

		panel_bot_dir.setPreferredSize(new Dimension(500, 250));
		panel_bot_dir.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_bot_dir.setBackground(Color.white);
		
		botoes.setPreferredSize(new Dimension(300, 100));


		tela.setSize(1300, 700);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.setVisible(true);
		
		
		debug.setText("Debuggar");
		debug.setBackground(Color.RED);
		botoes.add(debug);
		
		continuar.setText("Continuar");
		continuar.setBackground(Color.GREEN);
		botoes.add(continuar);
		
		debugLabel.setText("NORMAL MODE");
		botoes.add(debugLabel);
		
		continuar.addActionListener(this);
		debug.addActionListener(this);


	}
	
	
	
	public void printPrn(String prn) {
	
		prnInstruction.setText(prn);
		panel_bot_esq.add(prnInstruction);
	}

	public void exibirPilhaM(Stack pilha) {

		String pilhaAux = "";
		for (int i = 0; i < pilha.size(); i++) {
			pilhaAux = pilhaAux + pilha.get(i).toString();
		}
		pilhaM.setText(pilhaAux);
		panel_dir.add(pilhaM);

	}

	public void exibirPilhaP(Stack pilha) {

		String pilhaAux = "";
		for (int i = 0; i < pilha.size(); i++) {
			pilhaAux = pilhaAux + pilha.get(i).toString();
		}
		pilhaP.setText(pilhaAux);
		panel_esq.add(pilhaP);

	}

	public void exibirRegistradorI(int rgI) {
		rgi.setText("Registrador I = " + Integer.toString(rgI));
		panel_esq.add(rgi);
		panel_bot_dir.add(rgi);
	}

	public void exibirRegistradorS(int rgS) {
		rgs.setText(Integer.toString(rgS));
		panel_esq.add(rgs);
		panel_bot_dir.add(rgs);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == continuar) {
			Vm.executar();
		}
		
		if(e.getSource() == debug) {
			Vm.debug();
		}

	}
}