# Election Machine
**This is our school project for Web Services course that uses REST and JPA.**  
**You can find the first part of the project from [here](https://github.com/amakarj/TheElectionMachine).**
  
**Team: [Jenna Hakkarainen](https://github.com/jenhakk), [Amanda Karjalainen](https://github.com/amakarj), [Anna-Maria Palm](https://github.com/A-d-f)**

## Description
Our task was to implement **REST** and **JPA** to the Election Machine web application we had created as a first part of the project (see the link above).
In the first part, we had already used **JSP**, **MySQL database**, as well as **HTML** and **CSS**.

#### In the previous version, implemented features were:

* the voter to find suitable candidates
* the candidate to answer questions, as well as edit and delete their answers.

For the second part, **we wanted to add features for the admin side**. The task description required only one feature but we wanted to add two.  

#### New features are:

* admin can add, edit and delete *questions*
* admin can add, edit and delete *candidates*
* data is saved in a local MySQL database and queries are handled with JPA. The methods for JPA are located in `Daojpa.java` and they are called from `Rest.java`. Data is transferred between the REST and JSP files with `RequestDispatcher`.

#### Some extra features are: 

* login for the admin using `AuthFilter`
* pop-up window when deleting data
* adding commenting opportunity for the voter when answering questions

## Conclusion
The project was fun to work with and we learned a lot. It was demanding and helped us to develop our problem solving skills. We only had a few weeks to work with and there was much to take in. The fact that we had to connect the first part of the project with this one added extra challenges.
For example, we hadn’t thought about how deleting questions and candidates would affect the functionality. In addition, we had to re-create the database because we had used two primary keys instead of foreign keys.

All in all, the second part came together quickly after we figured out how the data is being transferred. We succeeded in creating a functioning site and added **a few extra features like:**

* adding candidate’s picture from local project folder
* admin login
* *customized* confirmation window for deleting

If we had more time we would have wanted to create filtering for the candidates by party or municipality, for instance.
