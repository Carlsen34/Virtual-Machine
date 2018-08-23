import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JOptionPane;

public class Vm {

	static Boolean bp = false;

	// Contem o endereço da proxima instrução a ser executada.
	public static RegistradorI registradorI = new RegistradorI();
	// Representa o elemento do topo da pilha M.
	public static RegistradorS registradorS = new RegistradorS();
	// Constem instruçõeps para a execução de um programa.
	public static Stack pilhaP = new Stack();
	// Contem os valores manipulados dinamicamente.
	public static Stack pilhaM = new Stack();

	public static Stack AuxInstrucao = new Stack();
	
	public static Stack bpoints = new Stack();
	public static Stack auxbp = new Stack();


	public static JanelaPrincipal tela = new JanelaPrincipal();

	public Vm() {
		criarPilhaP();
		tela.exibirRegistradorI(registradorI.regI);
		tela.exibirRegistradorS(registradorS.regS);
		tela.exibirPilhaP(pilhaP);
		tela.exibirBP(bpoints);
	}

	public static void debug() {
		if(bp == false) {
		bp = true;
		tela.debugLabel.setText("DEBUG MODE");
		tela.bpoint.setVisible(true);
		tela.botoes.setPreferredSize(new Dimension(400, 100));
		}else {
			bp = false;
			tela.debugLabel.setText("NORMAL MODE");
			tela.bpoint.setVisible(false);
			tela.botoes.setPreferredSize(new Dimension(300, 100));




		}
		
	}

	public static void executar() {

		if (bp == true) {
			executarDebug();
		} else {

		for(int i = 0; i<pilhaP.size();i++) {
			executarPilhaP();
			tela.exibirPilhaM(pilhaM);

			System.out.println("Pilha P  = " + pilhaP);
			System.out.println("Pilha M  = " + pilhaM);
			System.out.println("Registrador S =  " + registradorS.regS);
			System.out.println("Registrador I =  " + registradorI.regI);
		}

		}
	}
	
	
	
	public static Stack addBP() {
		String bp = JOptionPane.showInputDialog("Adicione um BreakPoint: ");
		bpoints.push(bp);
		tela.exibirBP(bpoints);
		return bpoints;
	}

	public static int executarDebug() {
		int i = 0;
		if(bpoints.isEmpty()==false) {
			String aux = bpoints.get(0).toString();
			bpoints.remove(0);
			i = Integer.parseInt(aux);
		}
		
		tela.exibirBP(bpoints);

		if(i != 0 ) {
			while(registradorI.regI != i) {
				if(pilhaP.get(registradorI.regI).equals("hlt")) return 0;
				else executarPilhaP();
			}
		}
		
		if (!pilhaP.get(registradorI.regI).equals("hlt")) {
			executarPilhaP();
			tela.exibirPilhaM(pilhaM);
			System.out.println("Pilha P  = " + pilhaP);
			System.out.println("Pilha M  = " + pilhaM);
			System.out.println("Registrador S =  " + registradorS.regS);
			System.out.println("Registrador I =  " + registradorI.regI);
		}
		return 0;
	}

	public static void executarPilhaP() {
		String pilha;
		String instrucao;
		String a;
		String b;

		AuxInstrucao = (Stack) pilhaP.get(registradorI.regI);

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

		System.out.println(AuxInstrucao);
		executarInstrucao(instrucao, a, b);
		tela.exibirRegistradorI(registradorI.regI);
		tela.exibirRegistradorS(registradorS.regS);


	}

	public static void criarPilhaP() {

		try {
			// Cria arquivo
			File file = new File("arquivo.txt");

			// Le o arquivo
			FileReader ler = new FileReader("arquivo.txt");
			BufferedReader reader = new BufferedReader(ler);
			String linha;
			while ((linha = reader.readLine()) != null) {
				AuxInstrucao = new Stack();
				String[] arrayValores = linha.split(" ");
				for (String s : arrayValores) {
					AuxInstrucao.push(s);
				}

				pilhaP.push(AuxInstrucao);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void executarInstrucao(String instrucao, String parametroA, String parametroB) {

		// Inicia Programa Principal
		if (instrucao.equals("start") && parametroA == "" && parametroB == "") {
			registradorS.setRegS(-1);// Setar Regs = -1
			registradorI.incrementarRegI(); // Incrementar RegI

		}

		// Para a execução MVD
		if (instrucao.equals("hlt") && parametroA == "" && parametroB == "") {
			JOptionPane.showMessageDialog(null, "FIM DE PROGRAMA !");
			tela.botoes.setVisible(false);
		}

		// Instrucao de escrita
		if (instrucao.equals("rd") && parametroA == "" && parametroB == "") {
			registradorS.incrementarRegS(); // S:=s + 1;

			String input = JOptionPane.showInputDialog("Digite um valor: ");

			if (pilhaM.size() == registradorS.regS) {
				pilhaM.push(Integer.parseInt(input));// M[s]:= “próximo valor de entrada”.
			} else {
				pilhaM.set(registradorS.regS, Integer.parseInt(input));
			}
			registradorI.incrementarRegI(); // I:=i + 1;

		}
		// Impressao
		if (instrucao.equals("prn") && parametroA == "" && parametroB == "") {

			System.out.println(pilhaM.get(registradorS.regS));
			tela.printPrn(pilhaM.get(registradorS.regS).toString());
			registradorS.decrementarRegS(); // S:=s - 1;
			registradorI.incrementarRegI(); // I:=i + 1;

		}

		// Carregar constante
		if (instrucao.equals("ldc") && parametroA != "" && parametroB == "") {

			registradorS.incrementarRegS();// Incrementar S
			if (pilhaM.size() == registradorS.regS) {
				pilhaM.push(parametroA);
			} else {
				pilhaM.set(registradorS.regS, parametroA);
			}

			registradorI.incrementarRegI();// Incrementar I

		}
		// Carregar valor
		if (instrucao.equals("ldv") && parametroA != "" && parametroB == "") {

			int a = Integer.parseInt(parametroA);

			if (pilhaM.size() < a) {

				System.out.println("paramentroA não pode ser maior que o tamanho da pilha");
			}

			if (pilhaM.size() == registradorS.regS) {
				pilhaM.push(a);// M[s]:= “próximo valor de entrada”.
			} else {
				pilhaM.set(registradorS.regS, a);
			}

			registradorS.incrementarRegS();// Incrementar S
			registradorI.incrementarRegI();// Incrementar I

		}

		// Somar
		if (instrucao.equals("add") && parametroA == "" && parametroB == "") {
			int a = (int) pilhaM.get(registradorS.regS - 1); // M[s-1] = M[s-1] + M[s]
			int b = (int) pilhaM.get(registradorS.regS);
			pilhaM.set(registradorS.regS - 1, a + b);

			registradorI.incrementarRegI(); // I = i + 1
			registradorS.decrementarRegS();// S = s - 1

		}

		// Subtrair
		if (instrucao.equals("sub") && parametroA == "" && parametroB == "") {
			int a = (int) pilhaM.get(registradorS.regS - 1);// M[s-1] = M[s-1] - M[s]
			int b = (int) pilhaM.get(registradorS.regS);
			pilhaM.set(registradorS.regS - 1, a - b);
			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Multiplicar
		if (instrucao.equals("mult") && parametroA == "" && parametroB == "") {
			int a = (int) pilhaM.get(registradorS.regS - 1);// M[s-1] = M[s-1] * M[s]
			int b = (int) pilhaM.get(registradorS.regS);
			pilhaM.set(registradorS.regS - 1, a * b);

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Dividir
		if (instrucao.equals("divi") && parametroA == "" && parametroB == "") {

			int a = (int) pilhaM.get(registradorS.regS - 1);// M[s-1] = M[s-1] / M[s]
			int b = (int) pilhaM.get(registradorS.regS);
			pilhaM.set(registradorS.regS - 1, a / b);

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Inverter Sinal

		if (instrucao.equals("inv") && parametroA == "" && parametroB == "") {
			int a = (int) pilhaM.get(registradorS.regS);// M[s] = - M[s]
			pilhaM.set(registradorS.regS, a * -1);
			System.out.println(pilhaM);

			registradorI.incrementarRegI();// I = i + 1

		}

		// Conjunção
		if (instrucao.equals("and") && parametroA == "" && parametroB == "") {

			// se M [s-1] = 1 e M[s] = 1 então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
			int a = (int) pilhaM.get(registradorS.regS - 1);
			int b = (int) pilhaM.get(registradorS.regS);

			if (a == 1 && b == 1) {
				pilhaM.set(1, registradorS.regS - 1);
			} else {
				pilhaM.set(0, registradorS.regS - 1);

			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Disjunção

		if (instrucao.equals("or") && parametroA == "" && parametroB == "") {
			// se M[s-1] = 1 ou M[s] = 1 então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
			int a = (int) pilhaM.get(registradorS.regS - 1);
			int b = (int) pilhaM.get(registradorS.regS);

			if (a == 1 || b == 1) {
				pilhaM.set(1, registradorS.regS - 1);
			} else {
				pilhaM.set(0, registradorS.regS - 1);

			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Negação
		if (instrucao.equals("ned") && parametroA == "" && parametroB == "") {
			int a = (int) pilhaM.get(registradorS.regS);// M[s] = 1 - M[s]
			pilhaM.set(registradorS.regS, 1 - a);

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1

		}

		// Comparar Menor
		if (instrucao.equals("cme") && parametroA == "" && parametroB == "") {
			int a = (int) pilhaM.get(registradorS.regS - 1);// se M[s-1] < M[s] então M[s-1] =1 senão M[s-1]=0
			int b = (int) pilhaM.get(registradorS.regS);
			if (a < b) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1

		}

		// Comparar Maior
		if (instrucao.equals("cma") && parametroA == "" && parametroB == "") {

			int a = (int) pilhaM.get(registradorS.regS - 1);// se M[s-1] > M[s] então M[s-1] =1 senão M[s-1]=0
			int b = (int) pilhaM.get(registradorS.regS);
			if (a > b) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Comparar Igual
		if (instrucao.equals("ceq") && parametroA == "" && parametroB == "") {

			int a = (int) pilhaM.get(registradorS.regS - 1);// se M[s-1] == M[s] então M[s-1] =1 senão M[s-1]=0
			int b = (int) pilhaM.get(registradorS.regS);
			if (a == b) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1

		}

		// Comarar Desigual
		if (instrucao.equals("cdif") && parametroA == "" && parametroB == "") {
			int a = (int) pilhaM.get(registradorS.regS - 1);// se M[s-1] != M[s] então M[s-1] =1 senão M[s-1]=0
			int b = (int) pilhaM.get(registradorS.regS);
			if (a != b) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Comparar Menor ou Igual
		if (instrucao.equals("cmeq") && parametroA == "" && parametroB == "") {
			int a = (int) pilhaM.get(registradorS.regS - 1);// se M[s-1] <= M[s] então M[s-1] =1 senão M[s-1]=0
			int b = (int) pilhaM.get(registradorS.regS);
			if (a <= b) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Comparar Maior ou Igual
		if (instrucao.equals("cmaq") && parametroA == "" && parametroB == "") {
			int a = (int) pilhaM.get(registradorS.regS - 1);// se M[s-1] >= M[s] então M[s-1] =1 senão M[s-1]=0
			int b = (int) pilhaM.get(registradorS.regS);
			if (a >= b) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}
		// Armazenar Valor
		if (instrucao.equals("str") && parametroA != "" && parametroB == "") {

			int a = Integer.parseInt(parametroA);

			if (pilhaM.size() < a) {
				System.out.println("Tamanho da pilha não devera ser menor que paramentroA ");
			}

			pilhaM.set(a, pilhaM.get(registradorS.regS));// M[n]:=M[s]; s:=s-1
			registradorS.decrementarRegS();
			registradorI.incrementarRegI();

		}

		// Desvios não intrementa RegI
		// Desviar Sempre
		if (instrucao.equals("jmp") && parametroA != "" && parametroB == "") {

			registradorI.setRegI(Integer.parseInt(parametroA));

		}

		// Desviar se falso
		if (instrucao.equals("jmpf") && parametroA != "" && parametroB == "") {
			int a = (int) pilhaM.get(registradorS.regS);
			if (a == 0) {// se M[s] = 0 então i = parametroA
				registradorI.setRegI(Integer.parseInt(parametroA));
			} else {// senão i:=i + 1;
				registradorI.incrementarRegI();
			}
			registradorS.decrementarRegS();

		}

		// Operacao Nula
		if (instrucao.equals("null") && parametroA == "" && parametroB == "") {
			registradorI.incrementarRegI();

		}

		// Alocar Memoria
		if (instrucao.equals("alloc") && parametroA != "" && parametroB != "") {

			int m = Integer.parseInt(parametroA);
			int n = Integer.parseInt(parametroB);
			int k;

			for (k = 0; k < n; k++) {
				registradorS.incrementarRegS();
		
					pilhaM.set(registradorS.regS,pilhaM.get( m + k));
					
					

			}

			registradorI.incrementarRegI();

		}

		// Desalocar Memoria
		if (instrucao.equals("dalloc") && parametroA != "" && parametroB != "") {

			int m = Integer.parseInt(parametroA);
			int n = Integer.parseInt(parametroB);
			int k;

			for (k = n; k > 0; k--) {
				pilhaM.set(m+k, pilhaM.get(registradorS.regS));
				registradorS.decrementarRegS();
			}
			registradorI.incrementarRegI();

		}

		// Chamar Procedimento ou Função
		if (instrucao.equals("call") && parametroA != "" && parametroB == "") {

			int t = Integer.parseInt(parametroA);
			registradorS.incrementarRegS();

			if (pilhaM.size() == registradorS.regS) {
				pilhaM.push(registradorI.regI + 1);// M[s]:= “próximo valor de entrada”.
			} else {
				pilhaM.set(registradorS.regS, registradorI.regI + 1);
			}

			registradorI.setRegI(t);

		}

		// Retornar de Procedimento ou Função
		if (instrucao.equals("return") && parametroA == "" && parametroB == "") {

			registradorI.setRegI((int) pilhaM.get(registradorS.regS));
			registradorS.decrementarRegS();

		}
	}

}
