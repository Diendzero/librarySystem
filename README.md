# Library-Management-System-Java

:books:A library management system implement using concepts of OOP in JAVA, GUI desgined using Java Swing and MS SQL as database.

**System Users/Actors**
- Librarian
- Clerk
- Borrower


**Functionalities**

**-Librarian**
   - can search book by title
   - can search book by subject
   - can search book by author name
   - can view loan history of borrowers
   - can add borrower
   - can update personal information of borrower
   - can checkout an item 
   - can checkin an item
   - can record fine of borrower
   - can increase quantity of book
   - can decrease quantity of book
   - can add new book 
   - can delete a book 
   - can update book info
   
   
   **-Clerk**
   - can search book by title
   - can search book by subject
   - can search book by author name
   - can view loan history of borrowers
   - can add borrower
   - can update personal information of borrower
   - can checkout an item 
   - can checkin an item
   - can record fine of borrower
   - can increase quantity of book
   - can decrease quantity of book
   
   **-Borrower**
   - can search book by title
   - can search book by subject
   - can search book by author name
   - can view loan history of borrowers
   - request a loan
   
   
  **Run this project**
  
  -For running this project , you must have installed JDK and NetBeans.
  
  
  -Create a new project using netbeans and include these files in source code folder.
  
  
  -The include sqljdc driver.
  
  
  -Change the connection string in **dbConnectivity.java** according to your system.
  
  
  -Select **LoginUI.java** and click on run file.
  
  
  
  
  For getting in touch or any help follow [Minahil Imtiaz](https://www.linkedin.com/in/minahilimtiaz/)
  
  If this project helped you, then follow me on [@minaahilimtiaz](https://github.com/minaahilimtiaz/) and 🌟 [this repository](https://github.com/minaahilimtiaz/Library-Management-System-Java/)
  
  
 **Bugs Report**
 
   -Introduction

   Welcome to the Bug Report section of our Library Management System (LMS). This part provides guidelines on reporting bugs effectively and 
   outlines our process to address them. We aim to maintain a robust and error-free system by collaborating openly with our community of users and 
   developers.

   To report a bug, please follow these steps:

   Check Existing Reports: Before submitting a new bug report, check if the issue has already been reported. This helps avoid duplicate efforts and 
   ensures that we can focus on new problems.

   Use the Bug Report Template: When reporting a bug, use the template provided below. This ensures that all necessary information is included, 
   which helps us in diagnosing and fixing the bug more efficiently.

   -Bug Report Template:

      Title: Provide a brief, descriptive title.
      Environment: Specify the software version, along with details about the operating system.
      Reproduction Steps: Clearly outline the steps to reproduce the bug.
      Expected Behavior: Describe what you expect to happen when following the steps above.
      Observed Behavior: Describe what actually happens.
      Screenshots/Logs: Attach any relevant screenshots or logs.
      Severity: Rate the severity of the bug (e.g., minor, major, critical).
      Submit Through GitHub: Use our GitHub issues page to submit your bug report. This centralizes our tracking and allows for community discussion 
      and contribution.

   -Bug Fixing Process

      Our team is committed to addressing all reported bugs as follows:

      Review and Assessment: Each bug report is reviewed by a team member to assess its validity and impact. We may reach out for additional 
      information.

      Prioritization: Bugs are prioritized based on their severity, frequency, and impact on the overall system. Critical bugs are handled I 
      mmediately, while less severe bugs are scheduled for future releases.

      Fixing: Our developers work to fix the bug. We aim to keep the community updated on our progress through comments in the GitHub issue.

      Testing: Once a fix is implemented, it undergoes thorough testing in both development and staging environments to ensure that the issue is 
      fully resolved.

      Release: Fixed bugs are included in the next software release. Each release is accompanied by release notes that detail the changes, including 
      bug fixes.

   -Versioning and Releases

      We release updates regularly to ensure that the system remains reliable and secure. Each release includes a version number, and we adhere to 
      semantic versioning to reflect the nature of changes made:

      Major releases (e.g., 1.0, 2.0) include significant changes and new features.
      Minor releases (e.g., 1.1, 1.2) typically include improvements and minor feature additions.
      Patch releases (e.g., 1.0.1, 1.0.2) focus primarily on bug fixes and minor enhancements.
   
   -Conclusion
   
    We greatly appreciate your contributions to identifying and reporting bugs. Your efforts help improve the quality and reliability of the LMS, 
    benefiting the entire community. For any further questions, please get in touch with us through our GitHub page.


   **Versions**

    --Version 1.1
    
       Bug: Book Search Function Returns No Results

         Title: Book search function fails to find an existing book
         Environment: LMS version 2.3.7, running on Windows 10
         Steps to Reproduce:
         Enter a known existing book ISBN "1234567890" into the search bar.
         Click the search button.
         Expected Behavior: The system should display the information for the matching book.
         Observed Behavior: The search results are empty, and no books are displayed.
         Severity: Major

    --Version 1.2
    
        Bug: Incorrect Book Return Date Calculation
         
         Title: Book return date shows as negative
         Environment: LMS version 2.4.1, running on macOS Catalina
         Steps to Reproduce:
         Log in as a library administrator.
         Return any borrowed book.
         Expected Behavior: Display the correct number of remaining days or return confirmation.
         Observed Behavior: Return date shows as "-3 days," even though the book is returned on the same day.
         Severity: Moderate

     --Version 1.3
     
         Bug: Inconsistent User Interface Language
         
         Title: Parts of the user interface text are not translated
         Environment: LMS version 2.5.3, running on Ubuntu 18.04
         Steps to Reproduce:
         Set the system language to English.
         Access the user settings page.
         Expected Behavior: All text should be displayed in English.
         Observed Behavior: Some text remains in Chinese.
         Severity: Minor

