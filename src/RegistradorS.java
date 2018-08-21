
public class RegistradorS {
	
	int regS = 0;
	

	public void setRegS(int regS) {
		this.regS = regS;
	}
	
	public int incrementarRegS() {
		
		regS = regS + 1;
		setRegS(regS);
		
		return regS;
		
	}
	
	public int decrementarRegS() {
		
		regS = regS - 1;
		setRegS(regS);
		
		return regS;
		
	}

}
