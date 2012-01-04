package se.gustavkarlsson.snap.persistance;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class PersistanceManager {

	private final EntityManager em;

	public PersistanceManager(String pathToDb) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(pathToDb);
		em = emf.createEntityManager();
		emf.close();
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

}
