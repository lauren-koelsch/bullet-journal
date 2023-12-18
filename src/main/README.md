Hi welcome to our application. In this Java Journal we created many features!
Firstly, we have a helpful weekview that helps the user see whole week displayed like a calendar. Then there's the option
of creating an event or task and all the details of each to add to a specific day on the calendar. Next we let the user
click Save to save that data in that week. When the program first opens,
the user choose a `.bujo` file to open and display the contents. A “Save to File” and “Open File” are also very helpful
features. Also, the user can set a maximum number of events and a maximum number of tasks for each day. Each day of the 
week has the same restriction. If a user’s number of tasks or events exceed their respective maximums, a warning will 
show up. There is a helpful sidebar containing all the tasks for the week. Also, there is a week overview that shows the 
statistics for the Week in the calendar. There is a notes and quotes feature that allows the user a small space to write 
whatever notes/ideas/quotes they need above the weekview. There are also preset themes that you can choose from like 
darkmode! There's a helpful miniview that enables the user to tap on a task/event and see a small popup of the details. 
We also parsed a HTTP/HTTPS link in the description and made it clickable from our program’s Event/Task detail views. 
Finally. you can create your own theme by customizing background color!

![Screen Shot 2023-06-22 at 12.17.00 PM.png](..%2F..%2F..%2F..%2FDesktop%2FScreen%20Shot%202023-06-22%20at%2012.17.00%20PM.png)
![Screen Shot 2023-06-22 at 12.17.28 PM.png](..%2F..%2F..%2F..%2FDesktop%2FScreen%20Shot%202023-06-22%20at%2012.17.28%20PM.png)
![Screen Shot 2023-06-22 at 12.17.15 PM.png](..%2F..%2F..%2F..%2FDesktop%2FScreen%20Shot%202023-06-22%20at%2012.17.15%20PM.png)

Single Responsibility Principle (SRP):
- The Single Responsibility Principle states that a class should have only one reason to change. In the code, there is 
an Events class responsible for handling calendar events. To adhere to SRP, it should has e a single responsibility 
related to calendar events, such as storing event details or retrieving event data. By keeping the class focused on a 
single responsibility, it becomes easier to maintain, test, and extend. 
Open-Closed Principle (OCP):
- As mentioned before, the code snippet does not explicitly demonstrate the Open-Closed Principle. However, let's assume
that there is a CalendarItem base class, and it has derived classes like Meeting, Reminder, and Event. To apply OCP, 
the base class can provide an abstract or virtual method (e.g., execute()) that each derived class implements 
differently based on its specific behavior. This way, you can extend the behavior of the calendar items by creating new 
derived classes without modifying the existing code. 
Liskov Substitution Principle (LSP):
- The Liskov Substitution Principle states that objects of a superclass should be replaceable with objects of its 
subclasses without affecting the correctness of the program. In the codeCalendar item is open for extension (you could
make a new example of a calendar item like events and tasks), and its closed for modification since its already set. 
Interface Segregation Principle (ISP):
- The Interface Segregation Principle states that clients should not be forced to depend on interfaces they do not use. 
The code defines common operations for calendar items, it's important to ensure that the Controller interface is not
bloated with unnecessary methods that specific calendar items don't need. Instead, interfaces can be segregated into 
more focused and specialized interfaces to provide specific functionality to clients. 
Dependency Inversion Principle (DIP):
- The Dependency Inversion Principle states that high-level modules should not depend on low-level modules. Both 
should depend on abstractions. In the code, if there are dependencies between higher-level modules (e.g., a Calendar 
class) and lower-level modules (e.g., Database), DIP can be applied by introducing abstractions (e.g., interfaces) that 
both modules depend on. This allows for more flexibility, easier testing, and the ability to substitute different 
implementations without modifying the high-level modules. 


One feature we could’ve extended is giving the user some way to delete tasks and events. 
Deleted items could be removed from the GUI immediately and .bujo when saved.