# ContactTracer

## Designed to help your business simplify contact tracing

ContactTracer is an easy to use application that is designed to help business owners 
keep track of their customer's contact information. Collected information can be used 
for contact tracing in the event that someone who has visited the establishment or someone
who has attended an event, is diagnosed with COVID-19. ContactTracer is designed to make the
management of large quantities of contact data easy, and at the same time minimize the spread 
of COVID-19.


Some features will include:

- Stores a person's contact information such as **name** and **phone number**
- Stores the time, and the date of when the person was present
- Retrieve a list of people who were present at a specified time and date
- Can be used by customers to enter their information *or* by business owners to perform
contact tracing
- Can be used by business owners to keep track of multiple business locations

This project is of interest to me since I often myself in a lineup at restaurants, waiting to fill out a form
about my contact information. This process is often slower than it needs to be, and often requires everyone to
use the same writing instruments. ContactTracer's intention is to streamline this process and ease the information
data burden on business owners. 


## User stories

- As a user, I want to be able to enter a persons' name and phone number and store it in a visitor's list
- As a user, I want to be able to retrieve the names & phone number of people in my visitor's list that came in on a certain time or date
- As a user, I want to be able to remove people from my visitor's list
- As a user, I want to be able to retrieve the names of people who have screened positive from my visitor's list
- As a user, I want to be able to change the date or time of people in my visitor's list
- As a user, I want to be able to change the status of a person in my visitor's list
- As a user, I want to be able to save my visitor's list
- As a user, I want to be able to reload my visitor's list and resume exactly where I left off at some earlier time

Phase 4: Task 2

I have chosen the option: test and design a class in your model package that is robust.
This is seen in the Person class with the methods, "setTime".

Phase 4: Task 3

If I had more time, I would fix some of the cohesion issues in ContactGUI, since it has many responsibilities including,
the layout of the GUI, creating buttons, actionlisteners, and playing sound. If I had more time I would refactor
this class and add more separate classes such as a Button Class which would be extended by the save, load, and clear
button. There would also be a class that would be dedicated to handling media files to produce sounds. I believe that 
would improve the cohesion in this project.