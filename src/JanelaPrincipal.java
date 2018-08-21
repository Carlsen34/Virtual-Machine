
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
	JPanel titulo = new JPanel();
	JLabel label = new JLabel();
	JPanel titulo1 = new JPanel();
	JLabel label1 = new JLabel();
	JPanel titulo2 = new JPanel();
	JLabel label2 = new JLabel();
	JPanel titulo3 = new JPanel();
	JLabel label3 = new JLabel();
	JPanel botoes = new JPanel();
	JLabel rgi = new JLabel();
	JLabel rgs = new JLabel();
	JLabel pilhaM = new JLabel();
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


		
		titulo.setPreferredSize(new Dimension(490,30));
		label.setText("INSTRUÇÕES P[i]");
		titulo.add(label);
		panel_esq.add(titulo);
		
		panel_dir.setPreferredSize(new Dimension(500, 250));
		panel_dir.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_dir.setBackground(Color.white);
		
		titulo1.setPreferredSize(new Dimension(490,30));
		label1.setText("CONTEUDO M[s]");
		titulo1.add(label1);
		panel_dir.add(titulo1);

		panel_bot_esq.setPreferredSize(new Dimension(500, 250));
		panel_bot_esq.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_bot_esq.setBackground(Color.white);
		
		
		titulo2.setPreferredSize(new Dimension(490,30));
		label2.setText("JANELA SAIDA");
		titulo2.add(label2);
		panel_bot_esq.add(titulo2);

		panel_bot_dir.setPreferredSize(new Dimension(500, 250));
		panel_bot_dir.setBorder(BorderFactory.createLineBorder(Color.black));
		panel_bot_dir.setBackground(Color.white);
		
		titulo3.setPreferredSize(new Dimension(490,30));
		label3.setText("BREAK POINT");
		titulo3.add(label3);
		panel_bot_dir.add(titulo3);
		
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
		
		
//		for(int i = 0; i<pilha.size();i++) {
//			JPanel panel = new JPanel();
//			JLabel label = new JLabel();
//		
//			panel.setPreferredSize(new Dimension(480,20));
//			panel.setBackground(Color.white);
//			panel_dir.add(panel);
//			label.setText(pilha.get(i).toString());
//			panel.add(label);
//
//			
//		}

	}

	public void exibirPilhaP(Stack pilha) {

	
		String pilhaAux = "";
		String instrucao;
		String a;
		String b;

		for(int i = 0; i<pilha.size();i++) {
		
			JPanel panel = new JPanel();
			JLabel pilhaP = new JLabel();
			JLabel paramA = new JLabel();
			JLabel paramB = new JLabel();
			
		Stack AuxInstrucao = (Stack) pilha.get(i);

		if (AuxInstrucao.size() < 1) {
			instrucao = "";
		} else {
			instrucao = (String) AuxInstrucao.get(0);
		}

		if (AuxInstrucao.size() < 2) {
			a = "";
		} else {
			a = (String) AuxInstrucao.get(1);
		}

		if (AuxInstrucao.size() < 3) {
			b = "";
		} else {
			b = (String) AuxInstrucao.get(2);
		}

		
		panel.setPreferredSize(new Dimension(480,20));
		panel.setBackground(Color.white);
		panel_esq.add(panel);
		
		pilhaP.setText(i+ " " + instrucao);
		paramA.setText(a);
		paramB.setText(b);

		panel.add(pilhaP);
		panel.add(paramA);
		panel.add(paramB);
		
		
		}
	


	}

	public void exibirRegistradorI(int rgI) {
		rgi.setText("Registrador I = " + Integer.toString(rgI));
		panel_esq.add(rgi);
		panel_bot_dir.add(rgi);
	}

	public void exibirRegistradorS(int rgS) {

		rgs.setText("Registrador S = " + Integer.toString(rgS));
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