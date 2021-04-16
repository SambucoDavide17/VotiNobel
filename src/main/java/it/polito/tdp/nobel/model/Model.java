package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	
	private List<Esame> partenza;
	private Set<Esame> soluzioneMigliore;
	private double mediaSoluzioneMigliore;

	public Model() {
		EsameDAO dao = new EsameDAO();
		partenza = dao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		Set<Esame> parziale = new HashSet<>();
		soluzioneMigliore = new HashSet<>();
		mediaSoluzioneMigliore = 0.0;
		//cerca1(parziale, 0, numeroCrediti);
		cerca2(parziale, 0, numeroCrediti);
		return soluzioneMigliore;	
	}

	private void cerca2(Set<Esame> parziale, int L, int m) {
		// casi terminali
		
		int crediti = sommaCrediti(parziale);
		if(crediti > m)
			return;		
		if(crediti == m) {
			double media = calcolaMedia(parziale);
		if(media > mediaSoluzioneMigliore) {
			soluzioneMigliore = new HashSet<>(parziale);
			mediaSoluzioneMigliore = media;
		}
		return;
		}
		if(L == partenza.size()) {
			return;
		}
		// generazione sottoproblemi
		parziale.add(partenza.get(L));
		cerca2(parziale, L+1, m);
		parziale.remove(partenza.get(L));
		cerca2(parziale, L+1, m);
		
	}
	
	private void cerca1(Set<Esame> parziale, int L, int m) {
		// casi terminali
		
		int crediti = sommaCrediti(parziale);
		if(crediti > m)
			return;
		if(crediti == m) {
			double media = calcolaMedia(parziale);
			if(media > mediaSoluzioneMigliore) {
				soluzioneMigliore = new HashSet<>(parziale);
				mediaSoluzioneMigliore = media;
			}
			return;
		}
		if(L == partenza.size()) {
			return;
		}
		// genero i sotto-problemi
		for(Esame e: partenza) {
			if(parziale.contains(e) == false) {
				parziale.add(e);
				cerca1(parziale, L+1, m);
				parziale.remove(e);
			}
		}
	}

	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
