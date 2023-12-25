package main.homework.jpa;

import main.homework.Book;

import javax.persistence.*;
import java.util.List;

public class JPAHomework {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my_persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // 2.1 Описать сущность Book из пункта 1.1
        // 2.2 Создать EntityManager и сохранить в таблицу 10 книг
        entityManager.getTransaction().begin();
        for (int i = 0; i < 10; i++) {
            Book book = new Book();
            book.setName("Book " + i);
            book.setAuthor("Author " + i);
            entityManager.persist(book);
        }
        entityManager.getTransaction().commit();

        // 2.3 Выгрузить список книг какого-то автора
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b WHERE b.author = :author", Book.class);
        query.setParameter("author", "Author 5");
        List<Book> books = query.getResultList();
        for (Book book : books) {
            System.out.println(book);
        }

        // 3.1 * Выгрузить список книг и убедиться, что поле author заполнено
        TypedQuery<Book> bookQuery = entityManager.createQuery("SELECT b FROM Book b", Book.class);
        List<Book> allBooks = bookQuery.getResultList();
        for (Book book : allBooks) {
            System.out.println(book);
        }
        // 3.2 ** В классе Author создать поле List<Book>, которое описывает список всех книг этого автора. (OneToMany)
        // Сущность Author уже описана выше

        entityManager.close();
        entityManagerFactory.close();
    }
}

