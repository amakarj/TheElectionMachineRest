package rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import dao.Daojpa;
import datarest.Candidates;
import datarest.Questions;
/**
 * Class for REST methods
 * 
 *Date: May 4-2022
 * @author jenna hakkarainen, amanda karjalainen, anna-maria palm
 *
 */
@Path("/questions")
public class Rest {

	// Method for getting all of the questions into a list by Daojpa method
	// "getQuestions".
	// Method sends list of questions to browsequestions.jsp.
	/**
	 * Method for getting all of the questions into a list by Daojpa method
	 * "getQuestions".
	 * Method sends list of questions to browsequestions.jsp
	 * @param request
	 * @param response
	 */
	@GET
	@Path("/getquestions")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void getQuestions(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		List list = Daojpa.getQuestions();
		request.setAttribute("questions", list);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/browsequestions.jsp");

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ************************Methods for editing
	// questions*******************************
	// Gets question-to-edit id from browsequestions.jsp, sends it to Daojpa and
	// gets the question object back. Sends it to editquestion.jsp in to a textfield
	
	/**
	 * Gets question-to-edit id from browsequestions.jsp, sends it to Daojpa and
	 * gets the question object back. Sends it to editquestion.jsp in to a textfield
	 * @param ques_id
	 * @param request
	 * @param response
	 */
	@GET
	@Path("/showquestion/{question_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void getOneQuestion(@PathParam("question_id") int ques_id, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		Questions quesid = Daojpa.readQuestion(ques_id);
		request.setAttribute("question", quesid);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/editquestion.jsp");

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	//Reads form from editquestion.jsp with POST-method and FormParams, makes a new object from it and sends it to Daojpa for updating the question. 
//	//Gets back all the questions from database into a list and sends it to browsequestions.jsp 
	/**
	 * Reads form from editquestion.jsp with POST-method and FormParams, makes a new object from it and sends it to Daojpa for updating the question.
	 * Gets back all the questions from database into a list and sends it to browsequestions.jsp 
	 * @param quesid
	 * @param question
	 * @param request
	 * @param response
	 */
	@POST
	@Path("/updatequestion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public void updateQuestionWithForm(@FormParam("question_id") int quesid, @FormParam("question") String question,
			@Context HttpServletRequest request, @Context HttpServletResponse response) {

		Questions ques = new Questions(quesid, question);
		List queslist = Daojpa.updateQuestion(ques);

		request.setAttribute("questions", queslist);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/browsequestions.jsp");

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ***************************************************************************************

	// Method adds a question to a database. First creating new question object and
	// add the question
	// inside it and send it to Daojpas method addQuestion. After that method calls
	// Daojpas method which add all candidates answer to zero for the new question. Dao returns the question to a
	// list and method sends the list to browsequestions.jpa.

	/**
	 * Method adds a question to a database. First creating new question object and add the question
	 * inside it and send it to Daojpas method addQuestion. After that method calls
	 * Daojpas method which add all candidates answer to zero for the new question. Dao returns the question to a
	 * list and method sends the list to browsequestions.jpa.
	 * @param question
	 * @param request
	 * @param response
	 */
	@POST
	@Path("/addquestion")
	@Produces(MediaType.APPLICATION_XHTML_XML)
	@Consumes("application/x-www-form-urlencoded") // Method can receive POSTed data from a html form
	public void addQuestion(@FormParam("question") String question, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		Questions q = new Questions(question);
		List questions = Daojpa.addQuestion(q);
		Daojpa.addAnswerZeroToNewQuestion();
		request.setAttribute("questions", questions);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/browsequestions.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Removes a question and answers related to given question id. Refreshes the page by reading questions from the database
	// Gets question_id from browsequestions.jsp and send the refreshed list back to browsequestions.jsp
	/**
	 * Removes a question and answers related to given question id. Refreshes the page by reading questions from the database
	 * Gets question_id from browsequestions.jsp and send the refreshed list back to browsequestions.jsp
	 * @param question_id
	 * @param request
	 * @param response
	 */
	@GET
	@Path("/deletequestion/{question_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteQuestion(@PathParam("question_id") int question_id, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		
		// Creating a list to receive the list getQuestions() returns 
		List<Questions> list = new ArrayList<Questions>();

		// Let's call deleteQuestions() and give question_id to it as a parameter. If it succeeds (returns true), let's call qetQuestions().
		if (Daojpa.deleteQuestion(question_id) == true) {
			list = Daojpa.getQuestions();

		} else {
			System.out.println("Failed to delete the question.");
		}

		// Let's send the received list back to browsequestions.jsp by request via RequestDispatcher
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/browsequestions.jsp");
		request.setAttribute("questions", list);

		try {
			rd.forward(request, response);

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	// ************************Methods for editing
	// candidates*******************************

	// Gets all candidates from database with Daojpa's getCandidates() into a list and sends it to adminbrowse.jsp
	/**
	 * Gets all candidates from database with Daojpa's getCandidates() into a list and sends it to adminbrowse.jsp
	 * @param request
	 * @param response
	 */
	@GET
	@Path("/getcandidates")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void getCandidates(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		List list = Daojpa.getCandidates();
		request.setAttribute("candidates", list);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/adminbrowse.jsp");

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Gets candidate's id by @PathParam from adminbrowse.jsp, gets the candidate's info from database with Daojpa's readCandidate() and sends it forward to adminviewcand.jsp
	/**
	 * Gets candidate's id by @PathParam from adminbrowse.jsp, gets the candidate's info from database with Daojpa's readCandidate() 
	 * and sends it forward to adminviewcand.jsp
	 * @param cand_id
	 * @param request
	 * @param response
	 */
	@GET
	@Path("/showcandidate/{candidate_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void getOneCandidate(@PathParam("candidate_id") int cand_id, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		Candidates candid = Daojpa.readCandidate(cand_id);
		request.setAttribute("candidate", candid);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/adminviewcand.jsp");

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Gets candidate's id by @PathParam from adminviewcand.jsp, gets the candidate's info from database with Daojpa's readCandidate()
	// and sends it forward to editcandidate.jsp
	/**
	 * Gets candidate's id by @PathParam from adminviewcand.jsp, gets the candidate's info from database with Daojpa's readCandidate()
	 * and sends it forward to editcandidate.jsp
	 * @param cand_id
	 * @param request
	 * @param response
	 */
	@GET
	@Path("/editcandidate/{candidate_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void getOneCandidatetoForm(@PathParam("candidate_id") int cand_id, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		Candidates candid = Daojpa.readCandidate(cand_id);

		File f = new File("./pics"); // ../pics jsp:lle
		String[] piclist = f.list();

		request.setAttribute("pics", piclist);
		request.setAttribute("candform", candid);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/editcandidate.jsp");

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Gets candidates edited info by @FormParams from editcandidate.jsp, saves them to new Candidates object and sends it to Daojpa which makes the
	// update to database and returns updated info from database and sends it to adminviewcand.jsp
	/**
	 * Gets candidates edited info by @FormParams from editcandidate.jsp, saves them to new Candidates object and sends it to Daojpa which makes the
	 * update to database and returns updated info from database and sends it to adminviewcand.jsp
	 * @param fileInputStream
	 * @param fileMetaData
	 * @param candid
	 * @param picture
	 * @param fname
	 * @param lname
	 * @param party
	 * @param munic
	 * @param age
	 * @param prof
	 * @param promo
	 * @param request
	 * @param response
	 * @param sc
	 * @throws Exception
	 */
	@POST
	@Path("/updatecandidate")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public void uploadFile(@FormDataParam("file") InputStream fileInputStream,
			// public String uploadFile( @FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData, @FormDataParam("id") int candid,
			@FormDataParam("pictures") String picture, @FormDataParam("fname") String fname,
			@FormDataParam("lname") String lname, @FormDataParam("party") String party,
			@FormDataParam("munic") String munic, @FormDataParam("age") String age, @FormDataParam("prof") String prof,
			@FormDataParam("promo") String promo, @Context HttpServletRequest request,
			@Context HttpServletResponse response, @Context ServletContext sc) throws Exception {

		Candidates cand = new Candidates(candid, lname, fname, picture, party, munic, age, promo, prof);
		cand = Daojpa.updateCandidate(cand);

		request.setAttribute("candidate", cand);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/adminviewcand.jsp");

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Gets new candidates info using FormDataParams from addcandidate.jsp and saves the info
	// to object Candidate. Then sends object to a daoJpa and saves candidates info to a database.
	// After that method calls daojpas method "addAnswersToNewCandidate,
	// finally in the end sends the object by setAttribute to a adminviewcand.jsp.

	/**
	 * Gets new candidates info using FormDataParams from addcandidate.jsp and saves the info
	 * to object Candidate. Then sends object to a daoJpa and saves candidates info to a database.
	 * After that method calls daojpas method "addAnswersToNewCandidate,
	 * finally in the end sends the object by setAttribute to a adminviewcand.jsp.
	 * @param fileInputStream
	 * @param fileMetaData
	 * @param fname
	 * @param lname
	 * @param party
	 * @param munic
	 * @param age
	 * @param prof
	 * @param promo
	 * @param request
	 * @param response
	 * @param sc
	 * @throws Exception
	 */
	@POST
	@Path("/addcandidate")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public void addCandidate(@FormDataParam("file") InputStream fileInputStream,
			// public String uploadFile( @FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData, @FormDataParam("fname") String fname,
			@FormDataParam("lname") String lname, @FormDataParam("party") String party,
			@FormDataParam("munic") String munic, @FormDataParam("age") String age, @FormDataParam("prof") String prof,
			@FormDataParam("promo") String promo, @Context HttpServletRequest request,
			@Context HttpServletResponse response, @Context ServletContext sc) throws Exception {
		
		String UPLOAD_PATH = sc.getRealPath("/pics");

		try {
			int read = 0;
			byte[] bytes = new byte[1024];

			OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + "/" + fileMetaData.getFileName()));

			while ((read = fileInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			out.flush();
			out.close();

		} catch (IOException e) {
			throw new WebApplicationException("Error while uploading file. Please try again !!");
		}

		String picture = fileMetaData.getFileName();

		Candidates c = new Candidates(lname, fname, picture, party, munic, age, promo, prof);
		c = Daojpa.addCandidate(c);
		Daojpa.addAnswersToNewCandidate();

		request.setAttribute("candidate", c);
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/adminviewcand.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated
			e.printStackTrace();
		}

	}

	// Removes a candidate and answers related to given candidate_id. Refreshes the page by reading candidates from the database
	// Gets candidate_id from browsecandidates.jsp and send the refreshed list back to browsequestions.jsp
	/**
	 * 
	 * Removes a candidate and answers related to given candidate_id. Refreshes the page by reading candidates from the database
	 * Gets candidate_id from browsecandidates.jsp and send the refreshed list back to browsequestions.jsp
	 * @param candidate_id
	 * @param request
	 * @param response
	 */
	@GET
	@Path("/deletecandidate/{candidate_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteCandidate(@PathParam("candidate_id") int candidate_id, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		// Creating a list to receive the list getQuestions() returns 
		List<Candidates> list = new ArrayList<Candidates>();

		// Let's call deleteCandidates() and give candidate_id to it as a parameter. If it succeeds (returns true), let's call qetCandidates().
		if (Daojpa.deleteCandidate(candidate_id) == true) {
			list = Daojpa.getCandidates();

		} else {
			System.out.println("Failed to delete the candidate.");
		}

		// Let's send the received list back to adminbrowse.jsp by request via RequestDispatcher
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/adminbrowse.jsp");
		request.setAttribute("candidates", list);

		try {
			rd.forward(request, response);

		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
//********************************************************************************************************************

	

}
