<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="data.Candidate"%>
<%@ page errorPage="error.jsp" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="/css/style2.css">
    <title>Edit info</title>
</head>

<body id="profile">

	<div class="col" style="position: absolute; left: 0; margin-top:0px;"><a href="/index.html" class="btnhome">HOME</a></div>
    <main class="main_profile">
    

        <h2 class="h2prof">Your profile</h2>

        <div class="container">
			
            <div class="rowcan1">
                <div class="col">
                
          	
                    <image><img class="profile_cand" src="/pics/${requestScope.candform.pic}"></image>
                        <p class="candnumber">${requestScope.candform.candi_id}</p>
                        <h2 class="candnameprof">${requestScope.candform.firstname} ${requestScope.candform.lastname}</h2>
                </div>
            </div>

            <div class="row" id="rowprof-info">

                <div class="col-12">
                <form action="ReadFormToUpdate" method="POST" class="forminfo">	
                
                <div class="form-group">
             
                    <input type="hidden" class="infoper" id="pic" name="pic" value="${requestScope.candform.pic}">
                </div>
                <div class="form-group">
                    <label for="id" id="label">Number</label><br>
                    <input type="text" class="infoper" id="id" name="id" value="${requestScope.candform.candi_id}" readonly>
                </div>
            
                <div class="form-group">
                    <label for="fname" id="label">First name</label><br>
                    <input type="text" class="infoper" id="fname" name="fname" value="${requestScope.candform.firstname}" required>
                </div>

                <div class="form-group">
                    <label for="lname" id="label">Last name</label><br>
                    <input type="text" class="infoper" id="lname" name="lname" value="${requestScope.candform.lastname}" required>
                </div>

                <div class="form-group">
                    <label for="party" id="label">Party</label><br>
                    <input type="text" class="infoper" id="party" name="party" value="${requestScope.candform.party}" required>
                </div>

                <div class="form-group">
                    <label for="munic" id="label">Municipality</label><br>
                    <input type="text" class="infoper" id="munic" name="munic" value="${requestScope.candform.municipality}" required>
                </div>

                <div class="form-group">
                    <label for="age" id="label">Age</label><br>
                    <input type="text" class="infoper" id="age" name="age" value="${requestScope.candform.age}" required>
                </div>
 
                <div class="form-group">
                    <label for="prof" id="label">Profession</label><br>
                    <input type="text" class="infoper" id="prof" name="prof" value="${requestScope.candform.profession}" required>
                </div>

                <div class="form-group">
                    <label for="promo" id="label">Promote</label><br>
                    <textarea type="text" class="infoperarea" id="promo" name="promo" required>${requestScope.candform.promo}</textarea>
                
                </div>
                
                <div class="row justify-content-center">
              	<div class="col-2">
				<button type="button" class="btn btn-primary" name="back" onclick="history.back()">BACK</button>
				</div>
				<div class="col-2">
				<input type="submit" class="btn btn-primary" name="ok" value="SAVE">
				</div>
			
				<div class="col-2"><a href="/ShowAnswersToCandidate?id=${candform.candi_id}" class="btn btn-primary">CHECK YOUR ANSWERS</a></div>
				<!-- 4.4 10.38 -->		
			
				</div>
			
            	</form>
                </div>
        </div>
        
             
		
      
    </main>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous">
    </script>
</body>

</html>