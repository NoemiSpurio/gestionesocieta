package it.prova.gestionesocieta.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.exception.SocietaConDipendentiException;
import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;

@Service
public class BatteriaDiTestService {

	@Autowired
	private SocietaService societaService;

	@Autowired
	private DipendenteService dipendenteService;

	public void testInserisciNuovaSocieta() {

		Societa nuovaSocieta = new Societa("Ragione sociale 1", "via Colombo 33", new Date());

		societaService.inserisciNuovo(nuovaSocieta);

		if (nuovaSocieta.getId() != 1) {
			throw new RuntimeException("testInserisciNuovaSocieta failed!");
		}

		societaService.rimuovi(nuovaSocieta);
		System.out.println("testInserisciNuovaSocieta PASSED!");
	}

	public void testFindByExampleSocieta() throws ParseException {

		Societa nuovaSocieta1 = new Societa("Ragione sociale 1", "via Colombo 33", new Date());
		societaService.inserisciNuovo(nuovaSocieta1);

		Societa nuovaSocieta2 = new Societa("Ragione sociale 2", "via Brubru 33", new Date());
		societaService.inserisciNuovo(nuovaSocieta2);

		Societa nuovaSocieta3 = new Societa("Ragione sociale 3", "via Brubru 33",
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1997"));
		societaService.inserisciNuovo(nuovaSocieta3);

		Societa example = new Societa("", "bru", new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000"));

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

	public void testRimuoviSocieta() {
		Societa nuovaSocieta1 = new Societa("Ragione sociale 1", "via Colombo 33", new Date());
		societaService.inserisciNuovo(nuovaSocieta1);

		societaService.rimuovi(nuovaSocieta1);

		if (societaService.listAllSocieta().size() != 0) {
			throw new RuntimeException("testRimuoviSocieta FAILED!");
		}

		Societa nuovaSocieta2 = new Societa("Ragione sociale 1", "via Colombo 33", new Date());
		societaService.inserisciNuovo(nuovaSocieta2);

		Dipendente dipendente1 = new Dipendente("nome1", "cognome1", new Date(), 1000, nuovaSocieta2);
		dipendenteService.inserisciNuovo(dipendente1);

		try {
			societaService.rimuovi(nuovaSocieta2);
		} catch (SocietaConDipendentiException e) {

		}

		dipendenteService.rimuovi(dipendente1);
		societaService.rimuovi(nuovaSocieta2);
		System.out.println("testRimuoviSocieta PASSED!");
	}

	public void testInserisciDipendente() {

		Societa nuovaSocieta1 = new Societa("Ragione sociale 1", "via Colombo 33", new Date());
		societaService.inserisciNuovo(nuovaSocieta1);
		Dipendente dipendente1 = new Dipendente("nome1", "cognome1", new Date(), 1000, nuovaSocieta1);
		dipendenteService.inserisciNuovo(dipendente1);

		if (dipendenteService.listAllDipendenti().size() != 1) {
			throw new RuntimeException("testInserisciDipendente failed! Numero record inatteso.");
		}

		dipendenteService.rimuovi(dipendente1);
		societaService.rimuovi(nuovaSocieta1);
		System.out.println("testInserisciDipendente PASSED!");
	}

	public void testModificaDipendente() {

		Societa nuovaSocieta1 = new Societa("Ragione sociale 1", "via Colombo 33", new Date());
		societaService.inserisciNuovo(nuovaSocieta1);
		Dipendente dipendente1 = new Dipendente("nome1", "cognome1", new Date(), 1000, nuovaSocieta1);
		dipendenteService.inserisciNuovo(dipendente1);

		Dipendente dipendente1Mod = new Dipendente("nome2", "cognome2", new Date(), 2000, nuovaSocieta1);
		dipendente1Mod.setId(dipendente1.getId());

		dipendenteService.aggiorna(dipendente1Mod);

		if (dipendenteService.caricaSingoloDipendente(dipendente1.getId()).getRedditoAnnuoLordo() != 2000) {
			throw new RuntimeException("testModificaDipendente failed! Modifica non avvenuta.");
		}

		dipendenteService.rimuovi(dipendente1);
		societaService.rimuovi(nuovaSocieta1);
		System.out.println("testModificaDipendente PASSED!");
	}

	public void testFindAllDistinctByDipendenti_RedditoAnnuoLordoGreaterThan() {

		Societa nuovaSocieta1 = new Societa("Ragione sociale 1", "via Colombo 33", new Date());
		societaService.inserisciNuovo(nuovaSocieta1);
		Dipendente dipendente1 = new Dipendente("nome1", "cognome1", new Date(), 1000, nuovaSocieta1);
		dipendenteService.inserisciNuovo(dipendente1);
		Societa nuovaSocieta2 = new Societa("Ragione sociale 1", "via Colombo 33", new Date());
		societaService.inserisciNuovo(nuovaSocieta1);
		Dipendente dipendente2 = new Dipendente("nome1", "cognome1", new Date(), 30001, nuovaSocieta1);
		dipendenteService.inserisciNuovo(dipendente2);

		if (societaService.cercaAllDistinctByDipendenti_RedditoAnnuoLordoGreaterThan(30000).size() != 1) {
			throw new RuntimeException(
					"testFindAllDistinctByDipendenti_RedditoAnnuoLordoGreaterThan failed! Numero record inatteso.");
		}

		dipendenteService.rimuovi(dipendente1);
		dipendenteService.rimuovi(dipendente2);
		societaService.rimuovi(nuovaSocieta1);
		societaService.rimuovi(nuovaSocieta2);
		System.out.println("testFindAllDistinctByDipendenti_RedditoAnnuoLordoGreaterThan PASSED!");
	}

	public void testDipendentePiuAnzianoDiSocietaFondatePrimaDi() throws ParseException {

		Date dataFondazione = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1850");
		Date dataInput = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1900");

		Societa nuovaSocieta1 = new Societa("Ragione sociale 1", "via Colombo 33", dataFondazione);
		societaService.inserisciNuovo(nuovaSocieta1);
		Dipendente dipendente1 = new Dipendente("nome1", "cognome1", new Date(), 1000, nuovaSocieta1);
		dipendenteService.inserisciNuovo(dipendente1);
		Dipendente dipendente2 = new Dipendente("nome1", "cognome1",
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-1950"), 1000, nuovaSocieta1);
		dipendenteService.inserisciNuovo(dipendente2);
		
		Dipendente result = dipendenteService.dipendentePiuAnzianoDiSocietaFondatePrimaDi(dataInput);
		
		if (result.getId() != dipendente2.getId()) {
			throw new RuntimeException("testDipendentePiuAnzianoDiSocietaFondatePrimaDi failed!");
		}
		
		dipendenteService.rimuovi(dipendente1);
		dipendenteService.rimuovi(dipendente2);
		societaService.rimuovi(nuovaSocieta1);
		System.out.println("testDipendentePiuAnzianoDiSocietaFondatePrimaDi PASSED!");
	}
}
