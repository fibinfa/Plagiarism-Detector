package com.msd.project.codesniffer.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.msd.project.codesniffer.model.Assignment;
import com.msd.project.codesniffer.model.Course;
import com.msd.project.codesniffer.model.Report;
import com.msd.project.codesniffer.model.Submission;
import com.msd.project.codesniffer.model.User;
import com.msd.project.codesniffer.service.AssignmentService;
import com.msd.project.codesniffer.service.CourseService;
import com.msd.project.codesniffer.service.ReportService;
import com.msd.project.codesniffer.service.S3Service;
import com.msd.project.codesniffer.service.SubmissionService;
import com.msd.project.codesniffer.service.UserService;

import jplag.ExitException;
import jplag.Program;
import jplag.options.CommandLineOptions;
import jplag.options.Options;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author wuhao
 *
 */
@Service
public class StorageServiceImpl {
	@Autowired
	AssignmentService asgmtService;

	@Autowired
	S3Service s3Service;

	@Autowired
	SubmissionService subService;

	@Autowired
	ReportService reportService;

	@Autowired
	UserService userService;

	@Autowired
	CourseService courseService;

	@Autowired
	private JavaMailSender sender;

	private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);

	/**
	 * store the zip file in the local file storage (server)
	 * @param file
	 * @param userId
	 * @param semId
	 * @param courseId
	 * @param asgmtId
	 * @param subId
	 * @throws IOException
	 */
	public void store(MultipartFile file, String userId, String semId, String courseId, String asgmtId, String subId)
			throws IOException {
		String root = System.getProperty("user.dir");
		Assignment currentAsgmtObject = asgmtService.findById(Long.parseLong(asgmtId));
		String parToBeAppended = "/user" + userId + "_sem" + semId + "_course" + courseId + "_asgmt" + currentAsgmtObject.getName() + "_sub"
				+ subId;
		File homeworkDirectory = new File(root + "/homework");

		if (!homeworkDirectory.exists()) {
			homeworkDirectory.mkdir();
		}

		File convFile = new File(homeworkDirectory + parToBeAppended + ".zip");
		final FileOutputStream outputStream = new FileOutputStream(convFile);
		try {

			outputStream.write(file.getBytes());

		} catch (Exception e) {
			logger.debug("Exception in unzipping");
		} finally {
			outputStream.close();
		}
	}

	/**
	 * compare two submissions
	 * @param userId
	 * @param semId
	 * @param courseId
	 * @param asgmtId
	 * @param subId
	 * @throws Exception
	 */
	public void compare(String userId, String semId, String courseId, String asgmtId, String subId) throws Exception {
		String root = System.getProperty("user.dir");
		File subDir = new File(root + "/submission");
		File hwDir = new File(root + "/homework");
		File historyDirectory = new File(root + "/history");
		Assignment currentAsgmtObject = asgmtService.findById(Long.parseLong(asgmtId));
		String parToBeAppended = "/user" + userId + "_sem" + semId + "_course" + courseId + "_asgmt"
				+ currentAsgmtObject.getName() + "_sub" + subId;
		double threshold = currentAsgmtObject.getThreshold();

		if (!hwDir.exists()) {
			hwDir.mkdir();
		}
		if (!subDir.exists()) {
			subDir.mkdir();
		}
		if (!historyDirectory.exists()) {
			historyDirectory.mkdir();
		}

		File[] historyFiles = historyDirectory.listFiles();
		if (historyFiles.length > 0) {
			for (File file : historyFiles) {
				// sems are supposed to be in order
				if (file.getName().contains("asgmt" + currentAsgmtObject.getName())
						&& !file.getName().contains("user" + userId) && file.getName().contains("course" + courseId)
						&& checkforPrevSem(file.getName(), Integer.parseInt(semId),
								currentAsgmtObject.getNumOfPrevSemsToCompare())) {
					String id2 = file.getName().split("_")[0].split("user")[1];

					User user1 = userService.findById(userId);
					User user2 = userService.findById(id2);

					ZipFile zipFile = new ZipFile(hwDir + parToBeAppended + ".zip");
					zipFile.extractAll(subDir+"/" + currentAsgmtObject.getName()+"-"+user1.getfName());   //yahan

					ZipFile zipFileTemp = new ZipFile(historyDirectory + "/" + file.getName());
					zipFileTemp.extractAll(subDir + "/" + currentAsgmtObject.getName()+"-"+user2.getfName()); //yahan
					File resHomeDirectory = new File(root + "/res");
					File tempResDirectory = new File(
							root + "/res" + parToBeAppended + "_" + file.getName().split(".zip")[0]);
					if (!resHomeDirectory.exists()) {
						resHomeDirectory.mkdir();
					}

					if (!tempResDirectory.exists()) {
						tempResDirectory.mkdir();
					}

					// run jplag
					runJplagComparison(tempResDirectory, root, currentAsgmtObject.getLanguage());

					// update the flag in asssignment
					File[] resFiles = tempResDirectory.listFiles();
					double simScore = 0.0;
					for (File tempFile : resFiles) {
						if (tempFile.getName().contains("link")) {
							Document doc = Jsoup.parse(tempFile, "UTF-8", "");
							Element sim = doc.select("H1").first();
							simScore = Double.parseDouble(sim.text().split("%")[0]);
							if (simScore > threshold) {
								String st1 = userId;
								String st2 = file.getName().split(".zip")[0].split("_")[0].split("user")[1];
								String asgmtName = currentAsgmtObject.getName();
								sendEmail(st1, st2, asgmtName, simScore, courseId,
										parToBeAppended.split("/")[1] + "_" + file.getName().split(".zip")[0]);
								currentAsgmtObject.setFlag(true);
								asgmtService.update(currentAsgmtObject);
								// call email service

							}
						}
					}
					// upload to s3

					s3Service.uploadReportFolderS3(
							parToBeAppended.split("/")[1] + "_" + file.getName().split(".zip")[0],
							root + "/res" + parToBeAppended + "_" + file.getName().split(".zip")[0]);

					FileUtils.cleanDirectory(subDir);

					// create report object
					Report report = new Report();
					report.setSimilarityScore(simScore);
					report.setReportLink(parToBeAppended.split("/")[1] + "_" + file.getName().split(".zip")[0]);
					String subId1 = parToBeAppended.split("/")[1].split("_")[4].split("sub")[1];
					String subId2 = file.getName().split(".zip")[0].split("_")[4].split("sub")[1];
					createReport(report, subId1, subId2);

					// delete hw and sum

				}
			}
		}

		File currentFileInHistory = new File(historyDirectory + parToBeAppended + ".zip");
		File currentFileInHw = new File(hwDir + parToBeAppended + ".zip");
		FileUtils.copyFile(currentFileInHw, currentFileInHistory);
		FileUtils.cleanDirectory(hwDir);

	}

	/**
	 * call the jplag library
	 * @param tempResDirectory
	 * @param root
	 * @param language
	 * @throws ExitException
	 */
	private void runJplagComparison(File tempResDirectory, String root, String language) throws ExitException {
		String result = "-l " + language + " -r " + tempResDirectory + " -s " + root + "\\submission";
		result = result.replace("SEM 3", "SEM3");
		result = result.replace("\\", "/");

		String[] args = result.split(" ");

		Options options = new CommandLineOptions(args);
		Program p = new Program(options);
		p.run();
	}

	/**
	 * pull code from GitHub repo instead of zip file
	 * @param gitRepo
	 * @param userid
	 * @param assignmentid
	 * @param string
	 * @param assignmentid2
	 * @param valueOf
	 * @return
	 * @throws ZipException
	 * @throws IOException
	 */
	public boolean cloneGit(String gitRepo, String userid, String semId, String courseId, String assignmentid,
			String subId) throws ZipException, IOException {
		String root = System.getProperty("user.dir");
		Assignment asgmt = asgmtService.findById(Long.parseLong(assignmentid));
		String parToBeAppended = "/user" + userid + "_sem" + semId + "_course" + courseId + "_asgmt" + asgmt.getName()
		+ "_sub" + subId;
		File homeworkDirectory = new File(root + "/homework");

		if (!gitRepo.contains(".git"))
			return false;

		if (!homeworkDirectory.exists()) {
			homeworkDirectory.mkdir();
		}

		File clonePath = new File(homeworkDirectory + parToBeAppended);
		try {
			Git git = Git.cloneRepository().setURI(gitRepo).setDirectory(clonePath).call();
			git.getRepository().close();
			git.close();

			File[] resFiles = clonePath.listFiles();
			boolean hwFolderPresent = false;
			for (File tempFile : resFiles) {
				if (tempFile.getName().equals(asgmt.getName())) {
					hwFolderPresent = true;
					break;
				}
			}

			if (!hwFolderPresent)
				return false;
			ZipFile zipFile = new ZipFile(clonePath + ".zip");
			ZipParameters parameters = new ZipParameters();

			// set compression method to store compression
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			zipFile.addFolder(clonePath + "/" + asgmt.getName(), parameters);
			FileUtils.deleteDirectory(clonePath);

		} catch (GitAPIException e) {
			logger.debug("github exception thrown in storage service");
		}
		return true;
	}

	/**
	 * compare the submission with previous semesters
	 * @param fileName
	 * @param currentSem
	 * @param numOfSems
	 * @return
	 */
	private boolean checkforPrevSem(String fileName, int currentSem, int numOfSems) {
		boolean flag = false;
		switch (numOfSems) {
		case 1:
			flag = fileName.contains("sem" + currentSem) || fileName.contains("sem" + (currentSem - 1));
			break;
		case 2:
			flag = fileName.contains("sem" + currentSem) || fileName.contains("sem" + (currentSem - 1))
			|| fileName.contains("sem" + (currentSem - 2));
			break;
		case 3:
			flag = fileName.contains("sem" + currentSem) || fileName.contains("sem" + (currentSem - 1))
			|| fileName.contains("sem" + (currentSem - 2)) || fileName.contains("sem" + (currentSem - 3));
			break;
		case 4:
			flag = fileName.contains("sem" + currentSem) || fileName.contains("sem" + (currentSem - 1))
			|| fileName.contains("sem" + (currentSem - 2)) || fileName.contains("sem" + (currentSem - 3))
			|| fileName.contains("sem" + (currentSem - 4));
			break;
		default:
			flag = fileName.contains("sem" + currentSem);
			break;

		}
		return flag;
	}

	/**
	 * create a report based on two submissions
	 * @param report
	 * @param subId1
	 * @param subId2
	 */
	private void createReport(Report report, String subId1, String subId2) {
		Submission sub1 = subService.findById(Long.parseLong(subId1));
		Submission sub2 = subService.findById(Long.parseLong(subId2));
		List<Report> repList1 = sub1.getReports();
		repList1.add(report);
		sub1.setReports(repList1);
		List<Report> repList2 = sub2.getReports();
		repList2.add(report);
		sub2.setReports(repList2);
		List<Submission> subList = new ArrayList<>();
		subList.add(sub1);
		subList.add(sub2);
		report.setSubmissions(subList);
		Report result =reportService.createReport(report);
	}

	/**
	 * send an email to given email address
	 * @param userId1
	 * @param userId2
	 * @param asgmtName
	 * @param score
	 * @param recipientMail
	 * @param reportLink
	 * @throws Exception
	 */
	public void sendEmail(String userId1, String userId2, String asgmtName, double score, String recipientMail,
			String reportLink) throws Exception {

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		String stud1 = userService.findById(userId1).getfName();
		String stud2 = userService.findById(userId2).getfName();
		helper.setTo("anubhuti.vyas.28@gmail.com");
		helper.setText("Codesniffer found plagiarised submission with similarity score" + score
				+ "Click the below link to view the full report+\n" + "https://s3.amazonaws.com/codesniffer-reports/"
				+ reportLink+ "/match0.html");
		// to do add link in email
		helper.setSubject("Plag detected in " + asgmtName + " between " + stud1 + " and " + stud2);
		sender.send(message);
	}
}