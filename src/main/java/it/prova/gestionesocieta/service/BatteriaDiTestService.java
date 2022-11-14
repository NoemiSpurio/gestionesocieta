package it.prova.gestionesocieta.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.model.Societa;

@Service
public class BatteriaDiTestService {

	@Autowired
	private SocietaService societaService;
	
	@Autowired
	private DipendenteService dipendenteService;
	
	public void testInserisciNuovaSocieta() {
		
		Societa nuovaSocieta = new Societa("Ragione sociale 1","via Colombo 33", new Date());
		
		societaService.inserisciNuovo(nuovaSocieta);
		
		if (nuovaSocieta.getId() != 1) {
			throw new RuntimeException("testInserisciNuovaSocieta failed!");
		}
		
		societaService.rimuovi(nuovaSocieta);
		System.out.println("testInserisciNuovaSocieta PASSED!");
	}
}
