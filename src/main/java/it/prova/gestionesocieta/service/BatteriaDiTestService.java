package it.prova.gestionesocieta.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	
	public void testFindByExampleSocieta() throws ParseException {
		
		Societa nuovaSocieta1 = new Societa("Ragione sociale 1","via Colombo 33", new Date());
		societaService.inserisciNuovo(nuovaSocieta1);
		
		Societa nuovaSocieta2 = new Societa("Ragione sociale 2","via Brubru 33", new Date());
		societaService.inserisciNuovo(nuovaSocieta2);
		
		Societa nuovaSocieta3 = new Societa("Ragione sociale 3","via Brubru 33", new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1997"));
		societaService.inserisciNuovo(nuovaSocieta3);
		
		Societa example = new Societa("","bru", new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000"));
		
		List<Societa> result = societaService.findByExample(example);
		
		System.out.println(result.size());
		
		if (result.size() != 1) {
			throw new RuntimeException("testFindByExampleSocieta failed! Numero record inatteso.");
		}
		
		societaService.rimuovi(nuovaSocieta1);
		societaService.rimuovi(nuovaSocieta2);
		societaService.rimuovi(nuovaSocieta3);
		System.out.println("testFindByExampleSocieta PASSED!");
	}
}
