import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HibernateTest {
	
	private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

	public static void main(String[] args) {
		try (
				SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
				Session session = factory.openSession();
				)
		{
			/*
			session.beginTransaction();
			
			User u = new User();
			u.setAge(75);
			u.setId(14);
			u.setName("Paskuda");
			u.setPosition("director");
			
			session.save(u);
			
			session.getTransaction().commit();
			*/
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> query = builder.createQuery(User.class);
			
			Root<User> root = query.from(User.class);
			
			//query.select(root);
			query.select(root).where(
					builder.between(root.get("age"), 10, 34)
			);
			
			Query<User> q  = session.createQuery(query);
			List<User> users = q.getResultList();
			for(User u : users) {
				System.out.println("user: " + u.getName());
			}
		}
	}

}
