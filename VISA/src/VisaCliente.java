package visa;

import javax.xml.rpc.Stub;

public class VisaCliente {

	public static void main(String[] args) {
		String[] tarjeta= {
			"1234 5678 9012 3456", 
			"John Doe", 
			"10/10", 
			"10/12"};
		
		try {
			Stub stub = createProxy();
			VisaIF visa = (VisaIF) stub;
			
			System.out.println("Comprobando tarjeta con los datos siguientes:");
			System.out.println("Numero:\t"+tarjeta[0]);
			System.out.println("Titular:\t"+tarjeta[1]);
			System.out.println("Validez:\tde "+tarjeta[2]+" hasta "+tarjeta[3]);
			if(visa.compruebaTarjeta(tarjeta[0],tarjeta[1],tarjeta[2],tarjeta[3])) 
				System.out.println("OK: La tarjeta es correcta");
			else
				System.out.println("ERROR: La tarjeta es incorrecta");
				
			visa.carga("1234 5678 9012 3456",30,"sede1","1/2/2010");
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	
	}

	private static Stub createProxy() {
		return (Stub) (new VisaService_Impl().getVisaIFPort());
	}
}
