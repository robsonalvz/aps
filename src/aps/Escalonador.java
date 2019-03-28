package aps;

import java.util.List;
import java.util.ArrayList;

public class Escalonador {
	private int tick;
	private int quantum;
	private List<Processo> processos;
	
	public Escalonador() {
		this.tick = 0;
		this.quantum = 0;
		this.processos = new ArrayList<>();
	}
	
	public void adicionarProcesso() {
		
	}
	
	public void finalizarProcesso() {
		
	}
	
	public int Tick(){
		return this.tick+=1;
	}
	
	public String Status() {
		String status = (""+this.tick) + this.quantum; 
		return status;
		
	}
	
	
}

