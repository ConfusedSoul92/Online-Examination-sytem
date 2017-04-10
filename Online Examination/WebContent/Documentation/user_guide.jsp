<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User's Guide</title>
</head>
<body>
<h2><a href="../index.jsp" style="color: blue"> Home.</a> <br><br></h2>
<h2>User's Guide:</h2><br><br>
1.)  As you start the application you are directed to the index page. There you have to login into the database that you will be using for the application. You also have the option to open the user Guide and programmers documentation from this index page<br><br>

<img src="/f16g323/images/1.png">

<br><br> 2.) Then you are directed to a user login page where you have to login using your user credentials. The logout button will invalidate the current session and redirect to the index page. (For demo use username = admin , password = admin for admin login) <br><br>

<img src="/f16g323/images/2.png">

<br><br> 3.) After login you are redirected to respective home page depending on type of user (Admin, Instructor, Student). If you login as admin, admin home page gives you the usability to create, drop and view all the
tables that would be used for the application.  <br><br>

<img src="/f16g323/images/3.png">

<br><br>4.) Create all the tables and then firstly click on View User Table. Then click on insert or upload  data and create the users for the application giving them respective rights by selecting appropriate value for USER_TYPE. (admin, instructor, student)<br><br>

<img src="/f16g323/images/4.png">

<br><br>5.)Then click on insert to create the user. If successful message will be populated as Data inserted Successfully. (Make sure to keep unique USER_ID.)<br><br>

<img src="/f16g323/images/5.png">

<br><br><img src="/f16g323/images/6.png">


<br><br>6.)Similarly create a Course. Then Map the course to the instructor in the Teach table to create a CRN for that Roster. When you login as an Instructor you will only be able to see those courses which you are teaching<br><br>

<br><br><img src="/f16g323/images/7.png">

<br><br><img src="/f16g323/images/8.png">

<br><br>7.) Then Create a Test and Map it to the Teach roster by inserting appropriate value (same as in CRN in Teach table) in the CRN column<br><br>

<br><br><img src="/f16g323/images/9.png">


<br><br>8.) Then click on Question table and map the questions to appropriate test_id (It would be a better option to let the instructor create and upload test and questions.) <br><br>

<br><br>9.) Then Click on the Enroll table and map the student user to the Teach table
CRN that you want the student to enroll in.<br><br>

<img src="/f16g323/images/10.png">

<br><br><img src="/f16g323/images/11.png">

<br><br>10.) Instructor Login : Now click Logout/SwitchUser to login as instructor. When you login, you would see a dropdown listing all the courses you are teaching. Select a course and click on Get Test list.

<br><br><img src="/f16g323/images/12.png">

<br><br><img src="/f16g323/images/13.png">

<br><br>11.) Then you get a dropdown listing all the related test for the respective CRN. Select the test and click submit.<br><br>

<img src="/f16g323/images/14.png">


<br><br>12.) Then you could view all the Tests, questions and Roster for the respective CRN and generate statistical results for the Selected Tests marks as saved in the Roster. <br><br>

<img src="/f16g323/images/15.png">

<br><br><img src="/f16g323/images/16.png">

<br><br>13.) Also sample data for the questions can be uploaded for the selected test and test_id will be auto populated.<br><br>

<img src="/f16g323/images/17.png">

<br><br><img src="/f16g323/images/18.png">


<br><br>14.) Similarly we can Upload sample roster data as  well.<br><br>

<img src="/f16g323/images/19.png">

<br><br><img src="/f16g323/images/20.png">
<br><br>15.) And then we can generate the statistical results for the given data.<br><br>
<br><br><img src="/f16g323/images/21.png">



<br><br>16.) Student Login: Now we can click on Logout/ switch User and Login as student. Based on the Courses for which the student has been Enrolled and availability of the Test, a List of Exams is displayed on the home page. Depending on the availability the student can either take the exam or get the feedback.<br><br>

<img src="/f16g323/images/22.png">


<br><br>17.) Then the student can take the test and submit . The feedback will be available once the due date has lapsed. The student can take the test multiple times before due date.<br><br>

<img src="/f16g323/images/23.png">
<br>
<br>
</body>
</html>