package main.homework.jdbc;

import java.sql.*;

public class JDBCHomework {

            private static final String url = "jdbc:mysql://localhost:3306/test?serverTimezone=Europe/Moscow";
            private static final String user = "root";
            private static final String password = "root";

            public static void main(String[] args) {
                try (Connection connection = DriverManager.getConnection(url, user, password)) {
                    // 1.1 Создать таблицу book
                    String createTableQuery = "CREATE TABLE IF NOT EXISTS book (" +
                            "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                            "name VARCHAR(100), " +
                            "author VARCHAR(100))";
                    try (PreparedStatement statement = connection.prepareStatement(createTableQuery)) {
                        statement.execute();
                    }

                    // 1.2 Добавить в таблицу 10 книг
                    String insertQuery = "INSERT INTO book (name, author) VALUES (?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                        for (int i = 0; i < 10; i++) {
                            statement.setString(1, "Book " + i);
                            statement.setString(2, "Author " + i);
                            statement.addBatch();
                        }
                        statement.executeBatch();
                    }

                    // 1.3 Сделать запрос select from book where author = 'какое-то имя'
                    String selectQuery = "SELECT * FROM book WHERE author = ?";
                    try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
                        statement.setString(1, "Author 5");
                        try (ResultSet resultSet = statement.executeQuery()) {
                            while (resultSet.next()) {
                                long id = resultSet.getLong("id");
                                String name = resultSet.getString("name");
                                String author = resultSet.getString("author");
                                System.out.println("ID: " + id + ", Name: " + name + ", Author: " + author);
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
}

