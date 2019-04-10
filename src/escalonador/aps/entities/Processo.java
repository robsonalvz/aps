package escalonador.aps.entities;

public class Processo {

	private String nome;
	private Status status;
	private int tickAtual;
	private int tickInicial;
	private int prioridade;

	/**
	 * 
	 * @param nome
	 * @param tick
	 * @param prioridade
	 */
	public Processo(String nome, int tick, int prioridade){
		this.nome=nome;
		this.prioridade=prioridade;
		this.status = Status.Esperando;
	}
	/**
	 * 
	 * @param nome
	 * @param tick
	 */
	public Processo(String nome, int tick) {
		this.nome = nome;
		this.status = Status.Esperando;
		this.tickInicial = tick;
		this.prioridade = -1;
	}
	/**
	 * 
	 * @param nome
	 * @param status
	 * @param tickAtual
	 * @param tickInicial
	 */
	public Processo(String nome, Status status, int tickAtual, int tickInicial) {

		this.nome = nome;
		this.status = status;
		this.tickAtual = tickAtual;
		this.tickInicial = tickInicial;

	}
	/**
	 * 
	 * @param nome
	 * @param status
	 * @param tickAtual
	 * @param tickInicial
	 * @param prioridade
	 */
	public Processo(String nome, Status status, int tickAtual, int tickInicial, int prioridade) {

		this.nome = nome;
		this.status = status;
		this.tickAtual = tickAtual;
		this.tickInicial = tickInicial;
		this.prioridade = prioridade;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	@Override
	public String toString() {
		return "Processo [nome=" + nome + ", status=" + status + ", tickAtual=" + tickAtual + ", tickInicial="
				+ tickInicial + "]";
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
