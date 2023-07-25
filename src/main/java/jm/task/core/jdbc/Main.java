package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan1", "LastName1", (byte) 10);
        userService.saveUser("Ivan2", "LastName2", (byte) 45);
        userService.saveUser("Ivan3", "LastName3", (byte) 12);
        userService.saveUser("Ivan4", "LastName4", (byte) 22);

        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
