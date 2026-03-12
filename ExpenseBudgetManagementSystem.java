import java.util.*;

public class ExpenseBudgetManagementSystem {

    /* Expense Node (Linked List) */
    static class Expense {
        int id;
        String category;
        double amount;
        String date;
        Expense next;

        Expense(int id, String category, double amount, String date) {
            this.id = id;
            this.category = category;
            this.amount = amount;
            this.date = date;
            this.next = null;
        }
    }

    /* Linked List to store expenses */
    static class ExpenseList {

        Expense head;

        void addExpense(int id, String category, double amount, String date) {

            Expense newExpense = new Expense(id, category, amount, date);

            if (head == null) {
                head = newExpense;
                return;
            }

            Expense temp = head;

            while (temp.next != null)
                temp = temp.next;

            temp.next = newExpense;
        }

        Expense searchExpense(int id) {

            Expense temp = head;

            while (temp != null) {

                if (temp.id == id)
                    return temp;

                temp = temp.next;
            }

            return null;
        }

        void deleteExpense(int id) {

            if (head == null)
                return;

            if (head.id == id) {
                head = head.next;
                return;
            }

            Expense prev = head;
            Expense curr = head.next;

            while (curr != null) {

                if (curr.id == id) {
                    prev.next = curr.next;
                    return;
                }

                prev = curr;
                curr = curr.next;
            }
        }

        List<Expense> getAllExpenses() {

            List<Expense> list = new ArrayList<>();

            Expense temp = head;

            while (temp != null) {
                list.add(temp);
                temp = temp.next;
            }

            return list;
        }

        void displayExpenses() {

            Expense temp = head;

            System.out.println("\nExpense Records:");

            while (temp != null) {

                System.out.println(
                        temp.id + " | "
                                + temp.category + " | Amount: "
                                + temp.amount + " | Date: "
                                + temp.date);

                temp = temp.next;
            }
        }
    }

    /* Main Expense Management System */
    static class ExpenseManager {

        ExpenseList expenseList = new ExpenseList();

        HashMap<Integer, Expense> expenseMap = new HashMap<>();

        HashMap<String, Double> categoryTotal = new HashMap<>();

        Queue<Integer> processingQueue = new LinkedList<>();

        Scanner sc = new Scanner(System.in);

        double monthlyBudget = 0;

        void setBudget() {

            System.out.print("Enter Monthly Budget: ");
            monthlyBudget = sc.nextDouble();

            System.out.println("Budget Set Successfully");
        }

        void addExpense() {

            System.out.print("Expense ID: ");
            int id = sc.nextInt();

            System.out.print("Category: ");
            String category = sc.next();

            System.out.print("Amount: ");
            double amount = sc.nextDouble();

            System.out.print("Date: ");
            String date = sc.next();

            expenseList.addExpense(id, category, amount, date);

            Expense e = expenseList.searchExpense(id);

            expenseMap.put(id, e);

            categoryTotal.put(category,
                    categoryTotal.getOrDefault(category, 0.0) + amount);

            processingQueue.add(id);

            System.out.println("Expense Added Successfully");
        }

        void searchExpense() {

            System.out.print("Enter Expense ID: ");
            int id = sc.nextInt();

            Expense e = expenseMap.get(id);

            if (e == null) {
                System.out.println("Expense Not Found");
                return;
            }

            System.out.println(
                    e.id + " | "
                            + e.category + " | Amount: "
                            + e.amount + " | Date: "
                            + e.date);
        }

        void deleteExpense() {

            System.out.print("Enter Expense ID: ");
            int id = sc.nextInt();

            Expense e = expenseMap.get(id);

            if (e == null) {
                System.out.println("Expense Not Found");
                return;
            }

            expenseList.deleteExpense(id);

            expenseMap.remove(id);

            System.out.println("Expense Deleted");
        }

        void processExpenses() {

            if (processingQueue.isEmpty()) {
                System.out.println("No Expenses to Process");
                return;
            }

            int id = processingQueue.poll();

            Expense e = expenseMap.get(id);

            if (e != null)
                System.out.println("Processed Expense: " + e.category);
        }

        void sortExpensesByAmount() {

            List<Expense> list = expenseList.getAllExpenses();

            list.sort(Comparator.comparingDouble(exp -> exp.amount));

            System.out.println("\nExpenses Sorted by Amount:");

            for (Expense e : list) {

                System.out.println(
                        e.id + " | "
                                + e.category + " | Amount: "
                                + e.amount);
            }
        }

        void showCategorySummary() {

            System.out.println("\nCategory Wise Spending:");

            for (String category : categoryTotal.keySet()) {

                System.out.println(
                        category + " : " + categoryTotal.get(category));
            }
        }

        void checkBudget() {

            double total = 0;

            for (double value : categoryTotal.values())
                total += value;

            System.out.println("Total Expenses: " + total);

            if (total > monthlyBudget)
                System.out.println("Budget Exceeded!");
            else
                System.out.println("Within Budget");
        }

        void menu() {

            while (true) {

                System.out.println("\n===== Expense Tracking System =====");

                System.out.println("1 Set Monthly Budget");
                System.out.println("2 Add Expense");
                System.out.println("3 Search Expense");
                System.out.println("4 Delete Expense");
                System.out.println("5 Display Expenses");
                System.out.println("6 Process Expense Queue");
                System.out.println("7 Sort Expenses by Amount");
                System.out.println("8 Category Summary");
                System.out.println("9 Check Budget");
                System.out.println("10 Exit");

                System.out.print("Enter Choice: ");

                int ch = sc.nextInt();

                switch (ch) {

                    case 1:
                        setBudget();
                        break;

                    case 2:
                        addExpense();
                        break;

                    case 3:
                        searchExpense();
                        break;

                    case 4:
                        deleteExpense();
                        break;

                    case 5:
                        expenseList.displayExpenses();
                        break;

                    case 6:
                        processExpenses();
                        break;

                    case 7:
                        sortExpensesByAmount();
                        break;

                    case 8:
                        showCategorySummary();
                        break;

                    case 9:
                        checkBudget();
                        break;

                    case 10:
                        System.out.println("System Closed");
                        return;

                    default:
                        System.out.println("Invalid Choice");
                }
            }
        }
    }

    public static void main(String[] args) {

        ExpenseManager manager = new ExpenseManager();

        manager.menu();
    }
}