package it.prova.gestionesocieta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.exception.SocietaConDipendentiException;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.SocietaRepository;

@Service
public class SocietaServiceImpl implements SocietaService {

	@Autowired
	private SocietaRepository societaRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<Societa> listAllSocieta() {
		return (List<Societa>) societaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Societa caricaSingolaSocieta(Long id) {
		return societaRepository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Societa societaInstance) {
		societaRepository.save(societaInstance);
	}

	@Transactional
	public void inserisciNuovo(Societa societaInstance) {
		societaRepository.save(societaInstance);
	}

	@Transactional
	public void rimuovi(Societa societaInstance) {
		TypedQuery<Societa> query = entityManager
				.createQuery("select s from Societa s join fetch s.dipendenti d where s.id = ?1", Societa.class);
		query.setParameter(1, societaInstance.getId());
		if (query.getResultList().size() != 0) {
			throw new SocietaConDipendentiException("Impossibile eliminare una societa' con dei dipendenti!!!");
		}

		societaRepository.delete(societaInstance);
	}

	@Override
	public List<Societa> findByExample(Societa example) {
		String query = "select s from Societa s where s.id = s.id";

		if (StringUtils.isNotBlank(example.getRagioneSociale()))
			query += " and s.ragioneSociale like '%" + example.getRagioneSociale() + "%'";
		if (StringUtils.isNotBlank(example.getIndirizzo()))
			query += " and s.indirizzo like '%" + example.getIndirizzo() + "%'";
		if (example.getDataFondazione() != null)
			query += " and s.dataFondazione > '" + example.getDataFondazione().toInstant() + "'";

		return entityManager.createQuery(query, Societa.class).getResultList();
	}

	@Override
	public List<Societa> cercaAllDistinctByDipendenti_RedditoAnnuoLordoGreaterThan(int valueRAL) {
		return societaRepository.findAllDistinctByDipendenti_RedditoAnnuoLordoGreaterThan(valueRAL);
	}

}
