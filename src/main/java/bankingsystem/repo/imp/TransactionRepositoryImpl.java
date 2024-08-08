package bankingsystem.repo.imp;

import org.springframework.orm.hibernate5.HibernateTemplate;

import bankingsystem.entities.Transaction;
import bankingsystem.repo.TransactionRepository;

public class TransactionRepositoryImpl implements TransactionRepository {

    private final HibernateTemplate hibernateTemplate;


    public TransactionRepositoryImpl(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }


    @Override
    public void save(Transaction transaction) {
        hibernateTemplate.saveOrUpdate(transaction);
    }

    @Override
    public int countByTransactionIdPrefix(String prefix) {
        String hql = "SELECT COUNT(*) FROM Transaction WHERE id LIKE :prefix";
        Long count = (Long) hibernateTemplate.execute(session -> session.createQuery(hql)
                .setParameter("prefix", prefix + "%")
                .uniqueResult());
        return count.intValue();
    }

    @Override
    public Transaction findById(String id) {
        return hibernateTemplate.get(Transaction.class, id);
    }

}
