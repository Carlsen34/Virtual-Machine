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

	public static Boolean bp = false;
	public static Boolean fim = false;
	
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
	public static Stack prnPilha = new Stack();

	public static JanelaPrincipal tela = new JanelaPrincipal();

	public Vm() {
		criarPilhaP();
		tela.exibirPilhaP(pilhaP);
		tela.exibiInstrucaoBP(pilhaP.get(registradorI.regI).toString());
		tela.exibirBP(bpoints);
		tela.exibirRegistradorI(registradorI.regI);
		tela.exibirRegistradorS(registradorS.regS);
	}

	public static void debug() {
		if (bp == false) {
			bp = true;
			tela.debugLabel.setText("DEBUG MODE");
			tela.bpoint.setVisible(true);
			tela.botoes.setPreferredSize(new Dimension(400, 100));
		} else {
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

			for (int i = 0; i < pilhaP.size(); i++) {
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
		String bp = JOptionPane.showInputDialog("Adicionar/Remover BreakPoint: ");
		if(bp != null) {
		if(bpoints.indexOf(bp) != -1) {
			bpoints.remove(bpoints.indexOf(bp));
		}else {
		bpoints.push(bp);
		}		
		tela.exibirBP(bpoints);
		}
		return bpoints;
	}
	

	public static int executarDebug() {
		int i = 0;
		if (bpoints.isEmpty() == false) {
			String aux = bpoints.get(0).toString();
			bpoints.remove(0);
			i = Integer.parseInt(aux);
		}

		tela.exibirBP(bpoints);

		if (i != 0) {
			while (registradorI.regI != i-1) {
				if (pilhaP.get(registradorI.regI).equals("hlt"))
					return 0;
				else
					executarPilhaP();
			}
		}

		if (!pilhaP.get(registradorI.regI).equals("hlt")) {
			executarPilhaP();
			tela.exibiInstrucaoBP(pilhaP.get(registradorI.regI).toString());
			tela.exibirPilhaM(pilhaM);
			System.out.println("Pilha P  = " + pilhaP);
			System.out.println("Pilha M  = " + pilhaM);
			System.out.println("Registrador S =  " + registradorS.regS);
			System.out.println("Registrador I =  " + registradorI.regI);
		}
		return 0;
	}

//	public static int findJump(String paramA) {
//		int num;
//		for(num = registradorI.regI+1;num<pilhaP.size();num++) {
//
//			if(paramA.equals(pilhaP.get(num))) {
//				System.out.println("ACHEI");
//			}
//		}
//		return num;
//	}
	
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
		if (instrucao.equals("START") && parametroA == "" && parametroB == "") {
			registradorS.setRegS(-1);// Setar Regs = -1
			registradorI.incrementarRegI(); // Incrementar RegI

		}

		// Para a execução MVD
		if (instrucao.equals("HLT") && parametroA == "" && parametroB == "") {
			if(fim == false) {
			JOptionPane.showMessageDialog(null, "FIM DE PROGRAMA !");
			tela.botoes.setVisible(false);
			tela.rodape.setVisible(false);
			fim = true;
			}
		}

		// Instrucao de escrita
		if (instrucao.equals("RD") && parametroA == "" && parametroB == "") {
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
		if (instrucao.equals("PRN") && parametroA == "" && parametroB == "") {

			System.out.println(pilhaM.get(registradorS.regS));
			prnPilha.push(pilhaM.get(registradorS.regS));
			tela.printPrn(prnPilha);
			registradorS.decrementarRegS(); // S:=s - 1;
			registradorI.incrementarRegI(); // I:=i + 1;

		}

		// Carregar constante
		if (instrucao.equals("LDC") && parametroA != "" && parametroB == "") {

			registradorS.incrementarRegS();// Incrementar S
			if (pilhaM.size() == registradorS.regS) {
				pilhaM.push(parametroA);
			} else {
				pilhaM.set(registradorS.regS, parametroA);
			}

			registradorI.incrementarRegI();// Incrementar I

		}
		// Carregar valor
		if (instrucao.equals("LDV") && parametroA != "" && parametroB == "") {

			int a = Integer.parseInt(parametroA);
			registradorS.incrementarRegS();// Incrementar S


			if (pilhaM.size() < a) {

				System.out.println("paramentroA não pode ser maior que o tamanho da pilha");
			}

			if (pilhaM.size() == registradorS.regS) {
				pilhaM.push(pilhaM.get(a));// M[s]:= “próximo valor de entrada”.
			} else {
				pilhaM.set(registradorS.regS, pilhaM.get(a));
			}

			registradorI.incrementarRegI();// Incrementar I

		}

		// Somar
		if (instrucao.equals("ADD") && parametroA == "" && parametroB == "") {
			int a = Integer.parseInt(pilhaM.get(registradorS.regS - 1).toString()) ;
			int b = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			pilhaM.set(registradorS.regS - 1, a + b);

			registradorI.incrementarRegI(); // I = i + 1
			registradorS.decrementarRegS();// S = s - 1

		}

		// Subtrair
		if (instrucao.equals("SUB") && parametroA == "" && parametroB == "") {
			int a = Integer.parseInt(pilhaM.get(registradorS.regS - 1).toString()) ;
			int b = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			pilhaM.set(registradorS.regS - 1, a - b);
			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Multiplicar
		if (instrucao.equals("MULT") && parametroA == "" && parametroB == "") {
			int a = Integer.parseInt(pilhaM.get(registradorS.regS - 1).toString()) ;
			int b = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			pilhaM.set(registradorS.regS - 1, a * b);

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Dividir
		if (instrucao.equals("DIVI") && parametroA == "" && parametroB == "") {

			int a = Integer.parseInt(pilhaM.get(registradorS.regS - 1).toString()) ;
			int b = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			pilhaM.set(registradorS.regS - 1, a / b);

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Inverter Sinal

		if (instrucao.equals("INV") && parametroA == "" && parametroB == "") {
			int a = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			pilhaM.set(registradorS.regS, a * -1);

			registradorI.incrementarRegI();// I = i + 1

		}

		// Conjunção
		if (instrucao.equals("AND") && parametroA == "" && parametroB == "") {

			// se M [s-1] = 1 e M[s] = 1 então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
			int a = Integer.parseInt(pilhaM.get(registradorS.regS - 1).toString()) ;
			int b = Integer.parseInt(pilhaM.get(registradorS.regS).toString());

			if (a == 1 && b == 1) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);

			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Disjunção

		if (instrucao.equals("OR") && parametroA == "" && parametroB == "") {
			// se M[s-1] = 1 ou M[s] = 1 então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
			int a = Integer.parseInt(pilhaM.get(registradorS.regS - 1).toString()) ;
			int b = Integer.parseInt(pilhaM.get(registradorS.regS).toString());

			if(a == 1 || b == 1) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Negação
		if (instrucao.equals("NEG") && parametroA == "" && parametroB == "") {
			int a = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			pilhaM.set(registradorS.regS, 1 - a);

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1

		}

		// Comparar Menor
		if (instrucao.equals("CME") && parametroA == "" && parametroB == "") {
			int a = Integer.parseInt(pilhaM.get(registradorS.regS - 1).toString()) ;
			int b = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			if (a < b) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1

		}

		// Comparar Maior
		if (instrucao.equals("CMA") && parametroA == "" && parametroB == "") {
			int a = Integer.parseInt(pilhaM.get(registradorS.regS - 1).toString()) ;
			int b = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			if (a > b) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Comparar Igual
		if (instrucao.equals("CEQ") && parametroA == "" && parametroB == "") {

	
			int a = Integer.parseInt(pilhaM.get(registradorS.regS - 1).toString()) ;
			int b = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			if (a == b) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1

		}

		// Comarar Desigual
		if (instrucao.equals("CDIF") && parametroA == "" && parametroB == "") {
			int a = Integer.parseInt(pilhaM.get(registradorS.regS - 1).toString()) ;
			int b = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			if (a != b) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Comparar Menor ou Igual
		if (instrucao.equals("CMEQ") && parametroA == "" && parametroB == "") {
			int a = Integer.parseInt(pilhaM.get(registradorS.regS - 1).toString()) ;
			int b = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			if (a <= b) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}

		// Comparar Maior ou Igual
		if (instrucao.equals("CMAQ") && parametroA == "" && parametroB == "") {
			int a = Integer.parseInt(pilhaM.get(registradorS.regS - 1).toString()) ;
			int b = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			if (a >= b) {
				pilhaM.set(registradorS.regS - 1, 1);
			} else {
				pilhaM.set(registradorS.regS - 1, 0);
			}

			registradorI.incrementarRegI();// I = i + 1
			registradorS.decrementarRegS();// S = s - 1
		}
		// Armazenar Valor
		if (instrucao.equals("STR") && parametroA != "" && parametroB == "") {

			int n = Integer.parseInt(parametroA);

			if (pilhaM.size() < n) {
				System.out.println("Tamanho da pilha não devera ser menor que paramentroA ");
			}

			pilhaM.set(n, pilhaM.get(registradorS.regS));// M[n]:=M[s]; s:=s-1
			registradorS.decrementarRegS();
			registradorI.incrementarRegI();

		}

		// Desvios não intrementa RegI
		// Desviar Sempre
		if (instrucao.equals("JMP") && parametroA != "" && parametroB == "") {

//			int num = findJump(parametroA);
//			registradorI.setRegI(num);
			registradorI.setRegI(Integer.parseInt(parametroA));

		}

		// Desviar se falso
		if (instrucao.equals("JMPF") && parametroA != "" && parametroB == "") {
			int a = Integer.parseInt(pilhaM.get(registradorS.regS).toString());
			if (a == 0) {// se M[s] = 0 então i = parametroA
				registradorI.setRegI(Integer.parseInt(parametroA));
			} else {// senão i:=i + 1;
				registradorI.incrementarRegI();
			}
			registradorS.decrementarRegS();

		}

		// Operacao Nula
		if (instrucao.equals("NULL") && parametroA == "" && parametroB == "") {
			registradorI.incrementarRegI();

		}

		// Alocar Memoria
		if (instrucao.equals("ALLOC") && parametroA != "" && parametroB != "") {

			int m = Integer.parseInt(parametroA);
			int n = Integer.parseInt(parametroB);
			int k;

			for (k = 0; k < n; k++) {
				registradorS.incrementarRegS();
				// pilhaM.set(registradorS.regS,pilhaM.get( m + k));

				if (pilhaM.size() == registradorS.regS) {
					if(m+k >= pilhaM.size()) {
					pilhaM.push("");
					}else {
					pilhaM.push(pilhaM.get(m + k));// M[s]:= “próximo valor de entrada”.
					}
				} else {
					pilhaM.set(registradorS.regS, pilhaM.get(m + k));
				}

			}

			registradorI.incrementarRegI();

		}

		// Desalocar Memoria
		if (instrucao.equals("DALLOC") && parametroA != "" && parametroB != "") {

			int m = Integer.parseInt(parametroA);
			int n = Integer.parseInt(parametroB);
			int k;

			for (k = n-1; k >= 0; k--) {
				pilhaM.set(m + k, pilhaM.get(registradorS.regS));
				registradorS.decrementarRegS();
			}
			registradorI.incrementarRegI();

		}

		// Chamar Procedimento ou Função
		if (instrucao.equals("CALL") && parametroA != "" && parametroB == "") {

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
		if (instrucao.equals("RETURN") && parametroA == "" && parametroB == "") {
			String i = pilhaM.get(registradorS.regS).toString();
			
			registradorI.setRegI(Integer.parseInt(i));
			registradorS.decrementarRegS();

		}
	}



}
