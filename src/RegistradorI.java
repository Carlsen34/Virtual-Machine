
public class RegistradorI {

	int regI = 0;
	

	public void setRegI(int regI) {
		this.regI = regI;
	}
	
	public int incrementarRegI() {
		
		regI = regI + 1;
		setRegI(regI);
		
		return regI;
		
		
	}
	
	public int decrementarRegI() {
		regI = regI - 1;
		setRegI(regI);
		
		return regI;
		
	}
	
}
