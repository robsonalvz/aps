package escalonador.aps.entities;

import java.util.List;
import java.util.ArrayList;
import escalonador.aps.exceptions.SemPrioridadeException;

public class EscalonadorComPrioridade {
	
	private List<Processo> processos;
	private boolean prioridade;
	private String status;
	private int tick;
	private int quantum;
	
	

	public void adicionarProcessoComPrioridade(Processo processo) throws SemPrioridadeException {
		
		if (processo.getPrioridade()==-1) {
				throw new SemPrioridadeException("Processo adicionado está sem prioridade.");
			}
		
		this.processos.add(processo);
	}
	
	public void ordenaPorPrioridade() {
		int cont = 0;
		List<Processo> lista = new ArrayList<Processo>();	
		while(cont < this.processos.size()){
			Processo po = this.processos.get(0);
			for(Processo p : this.processos) {
				if(p.getPrioridade() < po.getPrioridade()) {
					po = p;
				}
			}
			lista.add(po);
			this.processos.remove(po);
			cont += 1;		
		}
		lista.add(processos.get(0));
		this.processos = lista;
	}
}
