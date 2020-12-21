## 1. Introduction
Issue tracker is a web application that can handle and track issues.
### 1.1 Purpose
You can manage issue activities from start to end. Users with specific permissions can manage these issues.
### 1.2 Intended Audience
Managers
### 1.3 Intended Use
Users and managers.
### 1.4 Scope
-better, more organized issue management  
-ability to get up to date information
### 1.5 Definitions and Acronyms
Once this system is used, it will be difficult afterwards to handle issues if the system is down.
## 2. Overall Description
This will be a new system implementation.
### 2.1 User Needs
agent: who works on the issue
manager: who monitors the issues
### 2.2 Assumptions and Dependencies

## 3. System Features and Requirements
### 3.1 Functional Requirements
-Security: login and authentication
-Database:  
	repository method: Hibernate  
	DB name: issue_tracker_db  
	User DB  
-CRUD functions  
-Nice UI  
-GIT  

### 3.2 External Interface Requirements
### 3.3 System Features
Server side code: Spring Boot  
Back end server: capable of running JavaSE-1.8  
Client side resources: JavaScript, Bootstrap 4  
### 3.4 Nonfunctional Requirements
TDT (Test Driven Development)


### Progress of development
1. Login page, authentication on auth0, store token  
2. Test environment  
3. Database  
4. Tickets page  

### The following improvements were made using this issue tracker by creating tickets and resolving them:
    • add status field to ticket
    • ticket update function
    • insert logged in user name automatically
    • delete ticket function
    • select status from radio buttons
    • ticket activities
    • sign-in sign-out button
    • user not displayed
    • fix navbar
    • dashboard
    • admin page
    • rearrange ticket fields
    • show signed in user in navbar
    • ticket search
    • in ticket view change delete ticket to close ticket
    • delete ticket page redirect
    • inactivate fields in closed ticket
    • correct admin sort
    • ticket head fixed
    • are you sure to delete? function
    • admin modify ticket
    • dark mode
    • close ticket confirmation
    • save dark mode to user profile
    • correct tables in dark mode
    • add dark mode to ticket open view
    • go back to the same window scroll position
    • format ticket number
    • bottom screen notification adjust
    • title accepts minimum characters
    • make sort ascending, descending; show status
    • move notifications from bottom screen
    • add created, updated fields
    • add filters by last week, last 30 days etc, by status
    • correct search with INC - show only ID results
    • split screen for more than 50 results
    • keep query parameters after ticket view
    • correct admin page
    • add/remove table columns
    • user selection from list
    • correct responsive navbar
    • dashboard ticket links
    • add updated column
    • make title removable
    • resizable table columns