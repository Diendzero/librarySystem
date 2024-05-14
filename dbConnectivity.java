/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;


public class dbConnectivity {

    private Connection con;
    private Statement stmt;

    public dbConnectivity() {
        try {
            String s = "jdbc:sqlserver://localhost:49862;databaseName=Library";
            con = DriverManager.getConnection(s, "new_user", "123");
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Books getBookById(int book_id) {
        String query = "SELECT * FROM Books WHERE book_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, book_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Books(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Books> loadAllBooks() {
        ArrayList<Books> booksList = new ArrayList<>();
        String query = "SELECT * FROM Books";
        try (ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                booksList.add(new Books(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksList;
    }

    public String getAuthorOfBook(int book_id) {
        return getStringFieldById("author", book_id);
    }

    public String getTitleOfBook(int book_id) {
        return getStringFieldById("title", book_id);
    }

    public String getSubjectOfBook(int book_id) {
        return getStringFieldById("subject", book_id);
    }

    public int getQuantityOfBook(int book_id) {
        return getIntFieldById("quantity", book_id);
    }

    private String getStringFieldById(String fieldName, int book_id) {
        String query = "SELECT " + fieldName + " FROM Books WHERE book_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, book_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private int getIntFieldById(String fieldName, int book_id) {
        String query = "SELECT " + fieldName + " FROM Books WHERE book_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, book_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateBookQuantity(int new_quantity, int id) {
        String query = "UPDATE Books SET quantity = ? WHERE book_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, new_quantity);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeBookInfo(int book_id, String updatedInfo, int type) {
        String[] fields = {"title", "author", "subject"};
        if (type < 1 || type > 3) return;
        String query = "UPDATE Books SET " + fields[type - 1] + " = ? WHERE book_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, updatedInfo);
            pstmt.setInt(2, book_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Borrower getBorrowerById(int id) {
        String query = "SELECT * FROM Borrower WHERE borrower_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Borrower(rs.getInt(1), rs.getString(2), rs.getString(3).charAt(0), rs.getString(4),
                            rs.getString(5), rs.getBoolean(6), rs.getFloat(7));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Users> loadAllBorrowers() {
        ArrayList<Users> usersList = new ArrayList<>();
        String query = "SELECT * FROM Borrower";
        try (ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Borrower borrower = new Borrower(rs.getInt(1), rs.getString(2), rs.getString(3).charAt(0), rs.getString(4),
                        rs.getString(5), rs.getBoolean(6), rs.getFloat(7));
                borrower.setLoans(loadLoanListOfSpecificUser(borrower.getId()));
                usersList.add(borrower);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public boolean setFineStatus(int borrower_id, boolean fine_defaulter) {
        return updateBorrowerField(borrower_id, "fine_defaulter", fine_defaulter);
    }

    public boolean setTelephone(int borrower_id, String telnum) {
        return updateBorrowerField(borrower_id, "borrower_number", telnum);
    }

    public boolean setAddress(int borrower_id, String address) {
        return updateBorrowerField(borrower_id, "borrower_address", address);
    }

    public boolean setName(int borrower_id, String name) {
        return updateBorrowerField(borrower_id, "borrower_name", name);
    }

    public boolean setGender(int borrower_id, char gender) {
        return updateBorrowerField(borrower_id, "borrower_gender", gender);
    }

    public boolean setFineAmount(int borrower_id, double fine_amount) {
        return updateBorrowerField(borrower_id, "fine", fine_amount);
    }

    private boolean updateBorrowerField(int borrower_id, String fieldName, Object value) {
        String query = "UPDATE Borrower SET " + fieldName + " = ? WHERE borrower_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setObject(1, value);
            pstmt.setInt(2, borrower_id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getFineStatus(int borrower_id) {
        return getBooleanFieldById("fine_defaulter", borrower_id);
    }

    public double getFineAmount(int borrower_id) {
        return getDoubleFieldById("fine", borrower_id);
    }

    private boolean getBooleanFieldById(String fieldName, int borrower_id) {
        String query = "SELECT " + fieldName + " FROM Borrower WHERE borrower_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, borrower_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private double getDoubleFieldById(String fieldName, int borrower_id) {
        String query = "SELECT " + fieldName + " FROM Borrower WHERE borrower_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, borrower_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public ArrayList<Librarian> loadAllLibrarians() {
        ArrayList<Librarian> librariansList = new ArrayList<>();
        String query = "SELECT * FROM Staff WHERE rank = 'librarian'";
        try (ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                librariansList.add(new Librarian(rs.getInt(1), rs.getString(2), rs.getString(3).charAt(0)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librariansList;
    }

    public boolean addBorrower(int user_id, String user_name, char user_gender, String address, String telnum) {
        String query = "INSERT INTO Borrower (borrower_id, borrower_name, borrower_gender, borrower_address, borrower_number, fine_defaulter, fine) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, user_id);
            pstmt.setString(2, user_name);
            pstmt.setString(3, String.valueOf(user_gender));
            pstmt.setString(4, address);
            pstmt.setString(5, telnum);
            pstmt.setBoolean(6, false);
            pstmt.setDouble(7, 0.0);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Clerk> loadAllClerks() {
        ArrayList<Clerk> clerksList = new ArrayList<>();
        String query = "SELECT * FROM Staff WHERE rank = 'clerk'";
        try (ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                clerksList.add(new Clerk(rs.getInt(1), rs.getString(2), rs.getString(3).charAt(0)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clerksList;
    }

    public ArrayList<Loan> loadLoanList() {
        return loadLoans("SELECT * FROM (Borrower JOIN Loan AS L ON Borrower.borrower_id = L.borrower_id) JOIN Books ON Books.book_id = L.book_id");
    }

    public ArrayList<Loan> loadLoanListOfSpecificUser(int user_id) {
        return loadLoans("SELECT * FROM (Borrower JOIN Loan AS L ON Borrower.borrower_id = L.borrower_id) JOIN Books ON Books.book_id = L.book_id WHERE Borrower.borrower_id = " + user_id);
    }

    private ArrayList<Loan> loadLoans(String query) {
        ArrayList<Loan> loanList = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Borrower loanee = new Borrower(rs.getInt(1), rs.getString(2), rs.getString(3).charAt(0), rs.getString(4),
                        rs.getString(5), rs.getBoolean(6), rs.getFloat(7));
                Books loanedBook = new Books(rs.getInt(16), rs.getString(17), rs.getString(18), rs.getString(19), rs.getInt(20));
                Loan loan = new Loan(rs.getInt(8), loanee, loanedBook, rs.getString(12), rs.getBoolean(13), rs.getDate(9),
                        rs.getDate(10), rs.getDate(11));
                loanList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanList;
    }

    public boolean addNewLoan(Loan loan) {
        String query = "INSERT INTO Loan (loanid, issue_date, due_date, return_date, fine_status, returned_status, borrower_id, book_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, loan.getLoanId());
            pstmt.setDate(2, new java.sql.Date(loan.getIssueDate().getTime()));
            pstmt.setDate(3, new java.sql.Date(loan.getDueDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(loan.getReturnDate().getTime()));
            pstmt.setString(5, loan.getFineStatus());
            pstmt.setBoolean(6, loan.getStatus());
            pstmt.setInt(7, loan.getBorrowerId());
            pstmt.setInt(8, loan.getBookId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getLoanedBookId(int loanId) {
        return getIntFieldById("book_id", loanId);
    }

    public String getLoanFineStatus(int loanId) {
        return getStringFieldById("fine_status", loanId);
    }

    public Date getReturnDate(int loanId) {
        return getDateFieldById("return_date", loanId);
    }

    public Date getIssueDate(int loanId) {
        return getDateFieldById("issue_date", loanId);
    }

    public Date getDueDate(int loanId) {
        return getDateFieldById("due_date", loanId);
    }

    private Date getDateFieldById(String fieldName, int loanId) {
        String query = "SELECT " + fieldName + " FROM Loan WHERE loanid = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, loanId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDate(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Books getLoanedBook(int loanId) {
        return getBookById(getLoanedBookId(loanId));
    }

    public int getLoaneeId(int loanId) {
        return getIntFieldById("borrower_id", loanId);
    }

    public boolean getLoanReturnedStatus(int loanId) {
        return getBooleanFieldById("returned_status", loanId);
    }

    public void setLoanReturnedDate(int loanId, Date ret_date) {
        updateLoanField(loanId, "return_date", new java.sql.Date(ret_date.getTime()));
    }

    public void setLoanedBook(int loanId, int book_id) {
        updateLoanField(loanId, "book_id", book_id);
    }

    public void setLoanee(int loanId, int borrower_id) {
        updateLoanField(loanId, "borrower_id", borrower_id);
    }

    public void setReturnStatus(int loanId, boolean status) {
        updateLoanField(loanId, "returned_status", status);
    }

    public void setLoanFineStatus(int loanId, String status) {
        updateLoanField(loanId, "fine_status", status);
    }

    private void updateLoanField(int loanId, String fieldName, Object value) {
        String query = "UPDATE Loan SET " + fieldName + " = ? WHERE loanid = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setObject(1, value);
            pstmt.setInt(2, loanId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLoan(int loanID, Loan loan) {
        String query = "UPDATE Loan SET issue_date = ?, due_date = ?, return_date = ?, fine_status = ?, returned_status = ?, borrower_id = ?, book_id = ? WHERE loanid = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setDate(1, new java.sql.Date(loan.getIssueDate().getTime()));
            pstmt.setDate(2, new java.sql.Date(loan.getDueDate().getTime()));
            pstmt.setDate(3, new java.sql.Date(loan.getReturnDate().getTime()));
            pstmt.setString(4, loan.getFineStatus());
            pstmt.setBoolean(5, loan.getStatus());
            pstmt.setInt(6, loan.getBorrowerId());
            pstmt.setInt(7, loan.getBookId());
            pstmt.setInt(8, loanID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewBook(int book_id, String title, String author, String subject, int quantity) {
        String query = "INSERT INTO Books (book_id, author, title, subject, quantity) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, book_id);
            pstmt.setString(2, author);
            pstmt.setString(3, title);
            pstmt.setString(4, subject);
            pstmt.setInt(5, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteBook(int book_id) {
        if (!isBookLoaned(book_id)) {
            String query = "DELETE FROM Books WHERE book_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, book_id);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ArrayList<Books> searchBooksByTitle(String title) {
        return searchBooks("title", title);
    }

    public ArrayList<Books> searchBooksByAuthor(String author) {
        return searchBooks("author", author);
    }

    public ArrayList<Books> searchBooksBySubject(String subject) {
        return searchBooks("subject", subject);
    }

    private ArrayList<Books> searchBooks(String field, String value) {
        ArrayList<Books> booksList = new ArrayList<>();
        String query = "SELECT book_id FROM Books WHERE " + field + " = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, value);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    booksList.add(getBookById(rs.getInt(1)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksList;
    }
    
    
    
    boolean CheckUserId (int ID)
    {
        boolean flag=false;
    
        try {

            ResultSet rs = stmt.executeQuery("select * from Borrower where borrower_id='" + ID+ "'");
            if(rs.next())
            {
                
                   flag=true;
            }
         

        } catch (Exception e) {
            System.out.println(e);
        }
        
        return flag;
    
    }
 
    boolean CheckIsBookLoaned(int book_id)
    {
    
        boolean flag=false;
    
        try {

            ResultSet rs = stmt.executeQuery("select * from Loan where book_id='" +book_id+ " 'and returned_status ='" +0+"'");
            if(rs.next())
            {
                
                   flag=true;
            }
         

        } catch (Exception e) {
            System.out.println(e);
        }
        
        return flag;
    
    }
    
}
