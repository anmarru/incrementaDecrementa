public class IncrementaDecrementa {

	private static final int NUM_HILOS_INC = 10;
	private static final int NUM_HILOS_DEC = 10;
	private static final int LIMITE_SUPERIOR = 10; // Límite máximo para el contador

	public static void main(String[] args) {
		Contador c = new Contador(0, LIMITE_SUPERIOR); // Iniciar el contador con el límite superior
		Thread[] hilosInc = new Thread[NUM_HILOS_INC];
		for (int i = 0; i < NUM_HILOS_INC; i++) {
			Thread h = new Thread(new HiloInc("INC" + i, c));
			hilosInc[i] = h;
		}
		Thread[] hilosDec = new Thread[NUM_HILOS_DEC];
		for (int i = 0; i < NUM_HILOS_DEC; i++) {
			Thread h = new Thread(new HiloDec("DEC" + i, c));
			hilosDec[i] = h;
		}

		for (int i = 0; i < NUM_HILOS_INC; i++) {
			hilosInc[i].start();
		}
		for (int i = 0; i < NUM_HILOS_DEC; i++) {
			hilosDec[i].start();
		}
	}
}

class Contador {
	private int cuenta;
	private final int limiteSuperior; // Límite máximo para el contador

	Contador(int valorInicial, int limiteSuperior) {
		cuenta = valorInicial;
		this.limiteSuperior = limiteSuperior;
	}

	synchronized public int getCuenta() {
		return cuenta;
	}

	// Incrementa solo si no supera el límite superior
	synchronized int incrementa() {
		if (cuenta < limiteSuperior) {
			cuenta++;
		}
		return cuenta;
	}

	// Decrementa solo si no baja de 0
	synchronized int decrementa() {
		if (cuenta > 0) {
			cuenta--;
		}
		return cuenta;
	}
}

class HiloInc implements Runnable {

	private final String id;
	private final Contador cont;

	HiloInc(String id, Contador c) {
		this.id = id;
		cont = c;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (this.cont) {
				int valor = this.cont.incrementa();
				System.out.println("Hilo " + id + " incrementa, valor contador: " + valor);
			}
			try {
				Thread.sleep(100); // Pausa para simular el tiempo de ejecución
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class HiloDec implements Runnable {

	private final String id;
	private final Contador cont;

	HiloDec(String id, Contador c) {
		this.id = id;
		cont = c;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (this.cont) {
				int valor = this.cont.decrementa();
				System.out.println("Hilo " + id + " decrementa, valor contador: " + valor);
			}
			try {
				Thread.sleep(100); // Pausa para simular el tiempo de ejecución
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
