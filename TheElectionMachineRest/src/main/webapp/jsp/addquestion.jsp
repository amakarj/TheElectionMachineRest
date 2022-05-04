<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
    <title>Add new question</title>
</head>

<body id="profile">

    <div class="col" style="position: absolute; left: 0; margin-top:0px;"><a href="/jsp/adminhome.jsp" class="btnhome">HOME</a>
    </div>
    <main class="main_profile">
    <div class="adminlogin"><a href="/../Logout"><img src="/pics/icons8-logout-32.png">Log out</a></div>


        <h2>Add new question</h2>

        <div class="container">


            <div class="row" id="rowprof-info">

                <form action="/rest/questions/addquestion" method="POST" class="forminfo">
                    <div class="form-group">
                        <label for="question" id="label">Question</label><br>
                        <textarea type="text" class="infoperarea" id="ques" name="question" required></textarea>

                    </div>

                    <div class="row justify-content-center">
                        <div class="col-2">
                            <button type="button" class="btn btn-primary" name="back"
                                onclick="history.back()">BACK</button>
                        </div>
                        <div class="col-2">
                            <input type="submit" class="btn btn-primary" name="ok" value="ADD QUESTION" style="max-width: 200px;">
                        </div>
                        
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