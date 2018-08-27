
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
	JPanel bot_dirAux = new JPanel();
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
	JLabel prn = new JLabel();
	JLabel debugLabel = new JLabel();
	JLabel bpLabel = new JLabel();
	JLabel instrucaoBP = new JLabel();
	JPanel rodape = new JPanel();

	JButton debug = new JButton();
	JButton continuar = new JButton();
	JButton bpoint = new JButton();


	public JanelaPrincipal() {
		
		tela.add(panel_esq);
		final JScrollPane scrollEsq = new JScrollPane(panel_esq, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//this is for the main panel 		
		scrollEsq.setPreferredSize(new Dimension(500,250));
		tela.add(scrollEsq);
		tela.add(panel_dir);
		final JScrollPane scrollDir = new JScrollPane(panel_dir, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//this is for the main panel 		
		scrollDir.setPreferredSize(new Dimension(500,250));
		tela.add(scrollDir);
		tela.add(panel_bot_esq);
		final JScrollPane scrollBotEsq = new JScrollPane(panel_bot_esq, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//this is for the main panel 		
		scrollBotEsq.setPreferredSize(new Dimension(500,250));
		tela.add(scrollBotEsq);
		
		tela.add(panel_bot_dir);
		final JScrollPane scrollBotDir = new JScrollPane(panel_bot_dir, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//this is for the main panel 		
		scrollBotDir.setPreferredSize(new Dimension(500,250));
		tela.add(scrollBotDir);
		tela.add(botoes);
		tela.add(rodape);
		
		tela.setLayout(new FlowLayout());

		System.out.println(Vm.pilhaP.size()*10);
		panel_esq.setPreferredSize(new Dimension(500,250));
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

		rodape.setPreferredSize(new Dimension(200, 150));
	

		tela.setSize(1300, 700);
		tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tela.setVisible(true);
		
		
		debug.setText("Debuggar");
		debug.setBackground(Color.RED);
		botoes.add(debug);
		
		bpoint.setText("BreakPoint");
		bpoint.setBackground(Color.WHITE);
		botoes.add(bpoint);
		bpoint.setVisible(false);
		
		
		continuar.setText("Continuar");
		continuar.setBackground(Color.GREEN);
		botoes.add(continuar);
		
		debugLabel.setText("NORMAL MODE");
		botoes.add(debugLabel);
		

		continuar.addActionListener(this);
		debug.addActionListener(this);
		bpoint.addActionListener(this);


	}
	
	
	public void exibiInstrucaoBP(String instrucao) {
		instrucaoBP.setText("Instrucao:" +  instrucao);
		rodape.add(instrucaoBP);
	}
	
	
	public void printPrn(Stack pilha) {
//		JLabel prnInstruction = new JLabel();
//		prnInstruction.setText("<html>"+ pilha + "<br/>"+"</html>");
//		panel_bot_esq.add(prnInstruction);
		
		
		String pilhaAux = "";
		for (int i = 0; i < pilha.size(); i++) {
			
			pilhaAux = pilhaAux + pilha.get(i).toString() + "<br/>";
		}
		
		prn.setText("<html>"+ pilhaAux + "<br/>"+"</html>");
		panel_bot_esq.add(prn);
		
		if(pilha.size()*18>250) panel_bot_esq.setPreferredSize(new Dimension(500,pilha.size()*18));
		
		
		
	}

	public void exibirPilhaM(Stack pilha) {

		String pilhaAux = "";
		for (int i = 0; i < pilha.size(); i++) {
			
			pilhaAux = pilhaAux + "M[" + i + "]" + pilha.get(i).toString() + "<br/>";
		}
		
		pilhaM.setText("<html>"+ pilhaAux + "<br/>"+"</html>");
		panel_dir.add(pilhaM);
		
		if(pilha.size()*18>250) panel_dir.setPreferredSize(new Dimension(500,pilha.size()*18));
		

	}

	public void exibirPilhaP(Stack pilha) {
		String pilhaAux = "";
		String instrucao;
		String a;
		String b;
		JLabel label = new JLabel();


		for(int i = 0; i<pilha.size();i++) {
		
			
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

		
		pilhaAux = pilhaAux +"P[" + i + "] " + instrucao + " " + a + " " + b + "<br/>";
		

		}
		
		label.setText("<html>"+ pilhaAux + "<br/>"+"</html>");
		panel_esq.add(label);
		panel_esq.setPreferredSize(new Dimension(500,pilha.size()*18));
	}

	public void exibirRegistradorI(int rgI) {
		String aux = "Registrador I = " + Integer.toString(rgI) ;
		rgi.setText("<html>"+ aux + "<br/>"+"</html>");
		panel_esq.add(rgi);
		rodape.add(rgi);
	}

	public void exibirRegistradorS(int rgS) {
		rgs.setText("Registrador S = " + Integer.toString(rgS));
		rodape.add(rgs);

	}
	
	public void exibirBP(Stack bp) {

		String aux = "";
		
		for(int i = 0;i < bp.size();i++) {
			int x = i + 1;
			aux = aux + "BP[" + x +"] = " + bp.get(i) + "<br/>";
		}
		
		bpLabel.setText("<html>" + aux + "<br/>"+"</html>");
		panel_bot_dir.add(bpLabel);
		if(bp.size()*18>250)panel_bot_dir.setPreferredSize(new Dimension(500,bp.size()*18));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == continuar) {
			Vm.executar();
		}
		
		if(e.getSource() == debug) {
			Vm.debug();
		}
		
		if(e.getSource() == bpoint ) {

			Stack bp = Vm.addBP();
	
		}

	}
}