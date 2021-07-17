/**
 *
 * @author Kori Isabella H
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class todolist {

    // deklarasi variabel global
    static String fileName;
    static ArrayList<String> todoList;
    static boolean isEditing = false;
    static Scanner input;

    public static void main(String[] args) {
     // initialize
    todoList = new ArrayList<>();
    input = new Scanner(System.in);

    String filePath = System.console() == null ? "/src/todolist.txt" : "/todolist.txt";
    fileName = System.getProperty("user.dir") + filePath;

    System.out.println("FILE: " + fileName);

    // run the program (main loop)
    while (true) {
        showMenu();
    }
}

    
    static void clearScreen() {
    try {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            // clear screen untuk windows
            new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
        } else {
            // clear screen untuk Linux, Unix, Mac
            Runtime.getRuntime().exec("clear");
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    } catch (final IOException | InterruptedException e) {
        System.out.println("Error because: " + e.getMessage());
    }
}
String filePath = System.console() == null ? "/src/todolist.txt" : "/todolist.txt";



    static void showMenu() {
    System.out.println("=== TODO LIST ===");
    System.out.println("[1] Show Todo List");
    System.out.println("[2] Add Todo List");
    System.out.println("[3] Edit Todo List");
    System.out.println("[4] Delete Todo List");
    System.out.println("[0] Exit");
    System.out.println("---------------------");
    System.out.print("Choose Menu> ");

    String selectedMenu = input.nextLine();

        switch (selectedMenu) {
            case "1":
                showTodoList();
                break;
            case "2":
                addTodoList();
                break;
            case "3":
                edittodolist();
                break;
            case "4":
                deleteTodoList();
                break;
            case "0":
                System.exit(0);
            default:
                System.out.println("Incorrect Menu!");
                backToMenu();
                break;
        }
    }

    static void backToMenu() {
    System.out.println("");
    System.out.print("Press [Enter] to return!");
    input.nextLine();
    clearScreen();
    }

    static void readtodolist() {
    try {
        File file = new File(fileName);
        Scanner fileReader = new Scanner(file);

        // load isi file ke dalam array todoLists
        todoList.clear();
        while (fileReader.hasNextLine()) {
            String data = fileReader.nextLine();
            todoList.add(data);
        }

    } catch (FileNotFoundException e) {
        System.out.println("Error because: " + e.getMessage());
    }
    }

    static void showTodoList() {
    clearScreen();
    readtodolist();
    if (todoList.size() > 0) {
        System.out.println("TODO LIST:");
        int index = 0;
        for (String data : todoList) {
            System.out.println(String.format("[%d] %s", index, data));
            index++;
        }
    } else {
        System.out.println("There is no data!");
    }

    if (!isEditing) {
        backToMenu();
    }
    }

    static void addTodoList() {
    clearScreen();

    System.out.println("What do you want to do?");
    System.out.print("Answer : ");
    String newtodolist = input.nextLine();

    try {
        try ( // tulis file
                FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.append(String.format("%s%n", newtodolist));
        }
        System.out.println("Added successfully!");
    } catch (IOException e) {
        System.out.println("Error occurred because : " + e.getMessage());
    }

    backToMenu();
    }

    static void edittodolist() {
    isEditing = true;
    showTodoList();

    try {
        System.out.println("-----------------");
        System.out.print("Select Index > ");
        int index = Integer.parseInt(input.nextLine());

        if (index > todoList.size()) {
            throw new IndexOutOfBoundsException("The data entered is incorret!");
        } else {

            System.out.print("New data : ");
            String newData = input.nextLine();

            // update data
            todoList.set(index, newData);

            System.out.println(todoList.toString());

            try {
                // write new data
                try (FileWriter fileWriter = new FileWriter(fileName, false)) {
                    // write new data
                    for (String data : todoList) {
                        fileWriter.append(String.format("%s%n", data));
                    }
                }

                System.out.println("Changed successfully!");
            } catch (IOException e) {
                System.out.println("Error ocurred because : " + e.getMessage());
            }
        }
    } catch (NumberFormatException | IndexOutOfBoundsException e) {
        System.out.println(e.getMessage());
    }

    isEditing = false;
    backToMenu();
    }

    static void deleteTodoList() {
    isEditing = true;
    showTodoList();

    System.out.println("-----------------");
    System.out.print("Select Index > ");
    int index = Integer.parseInt(input.nextLine());

    try {
        if (index > todoList.size()) {
            throw new IndexOutOfBoundsException("The data entered is incorret!");
        } else {

            System.out.println("You will delete : ");
            System.out.println(String.format("[%d] %s", index, todoList.get(index)));
            System.out.println("Are you sure?");
            System.out.print("Answer Y/N): ");
            String jawab = input.nextLine();

            if (jawab.equalsIgnoreCase("Y")) {
                // hapus data
                todoList.remove(index);

                // tulis ulang file
                try {
                    // write new data
                    try (FileWriter fileWriter = new FileWriter(fileName, false)) {
                        // write new data
                        for (String data : todoList) {
                            fileWriter.append(String.format("%s%n", data));
                        }
                    }

                    System.out.println("Deleted successfully!");
                } catch (IOException e) {
                    System.out.println("Error ocurred because : " + e.getMessage());
                }
            }
        }
    } catch (IndexOutOfBoundsException e) {
        System.out.println(e.getMessage());
    }

    isEditing = false;
    backToMenu();
    }

}