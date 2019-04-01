package escalonador.aps.model;

public class Processo {

	private String nome;
	private Status status;
	private int tickAtual;
	private int tickInicial;

	public Processo(String nome, Status status, int tickAtual, int tickInicial) {

		this.nome = nome;
		this.status = status;
		this.tickAtual = tickAtual;
		this.tickInicial = tickInicial;

	}

	public Processo(String nome, int tick) {
		this.nome = nome;
		this.status = Status.Esperando;
		this.tickInicial = tick;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getTickAtual() {
		return tickAtual;
	}

	public void setTickAtual(int tickAtual) {
		this.tickAtual = tickAtual;
	}

	public int getTickInicial() {
		return tickInicial;
	}

	public void setTickInicial(int tickInicial) {
		this.tickInicial = tickInicial;
	}

}
