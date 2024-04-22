/**
 * Provides static methods to print various CLI (Command Line Interface) screens
 * for interacting with the Boolean Books library management system.
 */
public class CLIScreens {
    public static void main(String[] args) {
        printStartCLI();
    }
    public static void printStartCLI() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@Welcome to Boolean Books, a full service Library Management System!@");
        System.out.println("@    To get started, please select one of the following options:    @");
        System.out.println("@                             Login (1)                             @");
        System.out.println("@                       Create an Account (2)                       @");
        System.out.println("@                          Admin Login (3)                          @");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }
    public static void login() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@                 Login                 @");
        System.out.println("@Please enter your username and password@");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
    public static void createUser() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@           Create user           @");
        System.out.println("@   Please enter the following:   @");
        System.out.println("@              Name               @");
        System.out.println("@          Date of Birth          @");
        System.out.println("@            Username             @");
        System.out.println("@            Password             @");
        System.out.println("@         Repeat Password         @");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }
    public static void adminLogin() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@           Admin Login           @");
        System.out.println("@   Please enter the following:   @");
        System.out.println("@            Username             @");
        System.out.println("@            Password             @");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }
    public static void mainUserPage() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@        Login Succesfull         @");
        System.out.println("@    Welcome to Boolean Books!    @");
        System.out.println("@    Please choose an option:     @");
        System.out.println("@        Browse Media (1)         @");
        System.out.println("@        Return Media (2)         @");
        System.out.println("@        User Settings (3)        @");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }

    public static void mainAdminPage() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@                                 @");
        System.out.println("@     Welcome, Administrator!     @");
        System.out.println("@          Add media (1)          @");
        System.out.println("@        View patrons (2)         @");
        System.out.println("@                                 @");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
    public static void addMedia() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@          Add Media             @");
        System.out.println("@          From CSV (1)          @");
        System.out.println("@       Manual Entry (2)         @");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
    public static void chooseMedia() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@         Choose Media           @");
        System.out.println("@           Books (1)            @");
        System.out.println("@            CDs (2)             @");
        System.out.println("@           DVDs (3)             @");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }
    public static void addBooksManual() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@           Add Books            @");
        System.out.println("@  Please enter the following:   @");
        System.out.println("@             Title              @");
        System.out.println("@             Author             @");
        System.out.println("@        Number of Pages         @");
        System.out.println("@             Genre              @");
        System.out.println("@            Summary             @");
        System.out.println("@            Quantity            @");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
    public static void addCDs() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@            Add CDs             @");
        System.out.println("@          Album Name:           @");
        System.out.println("@            Artist:             @");
        System.out.println("@             Genre:             @");
        System.out.println("@             Year:              @");
        System.out.println("@            Quantity:           @");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
    public static void addDVDsManual() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n" +
                "@           Add DVDs              @\n" +
                "@             Title               @\n" +
                "@             Genre               @\n" +
                "@            Length               @\n" +
                "@           Director              @\n" +
                "@             Cast                @\n" +
                "@            Summary              @\n" +
                "@           Quantity              @\n" +
                "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }
    public static void checkout() {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("@        Checkout Book (1)         @");
        System.out.println("@        Checkout CD (2)           @");
        System.out.println("@        Checkout DVD (3)          @");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }
}
