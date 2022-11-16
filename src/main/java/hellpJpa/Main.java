package hellpJpa;

import javax.persistence.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        // DB에 접속하여 데이터를 조작하는 일반적인 단위마다 EM을 생성해주어야한다

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            for (int i = 0; i <100 ; i++) {
                Member member = new Member();
                member.setUsername("member"+i);
                member.setAge(i);

                em.persist(member);

            }

            em.flush();
            em.clear();

          //  List<Team> resultList = em.createQuery("select m.team from Member m join m.team", Team.class).getResultList();

            List<Member> members = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : members) {
                System.out.println("member = " + member.toString());
            }







            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("========================== ee" );
            tx.rollback();
        } finally {
            em.close(); // 트랜잭션 끝나면 클로즈 되어야 리소스가 릴리즈 된다.
        }


        emf.close();



    }
}