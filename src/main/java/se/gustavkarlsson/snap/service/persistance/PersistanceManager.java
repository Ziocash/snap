package se.gustavkarlsson.snap.service.persistance;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import se.gustavkarlsson.snap.domain.FolderNode;

public class PersistanceManager {

	private final EntityManagerFactory emf;
	private final EntityManager em;

	public PersistanceManager(String pathToDb) {
		emf = Persistence.createEntityManagerFactory(pathToDb);
		em = emf.createEntityManager();
	}

	public <T> Object save(T object) {
		T ret;

		em.getTransaction().begin();
		if (em.contains(object)) {
			ret = em.merge(object);
		} else {
			em.persist(object);
			ret = object;
		}
		em.getTransaction().commit();

		return ret;
	}

	public <T> Object getSingleObject(String queryString, Class<T> clazz) {
		TypedQuery<T> query = em.createQuery(queryString, clazz);
		return query.getSingleResult();
	}

	public FolderNode getRoot() { // FIXME get real root
		TypedQuery<FolderNode> query = em.createQuery(
				"SELECT f FROM Folders f", FolderNode.class);
		List<FolderNode> result = query.getResultList();
		return result.get(0);
	}

	public void close() {
		em.close();
		emf.close();
	}
}
