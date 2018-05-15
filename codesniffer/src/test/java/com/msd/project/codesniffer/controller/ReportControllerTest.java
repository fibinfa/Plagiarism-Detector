package com.msd.project.codesniffer.controller;
/**
   @author fibin
   @version 1.0
   @since 15-Apr-2018
*/

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.msd.project.codesniffer.CodesnifferApplication;
import com.msd.project.codesniffer.model.Report;
import com.msd.project.codesniffer.service.ReportService;

@RunWith(SpringRunner.class)
@WebMvcTest(ReportController.class)
@ContextConfiguration(classes=CodesnifferApplication.class)
public class ReportControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReportService reportService;

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.ReportController#findReportsByAsgmtId(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindReportsByAsgmtId() throws Exception {
		List<Report> reports = new ArrayList<>();
		Report mockReport = new Report();
		mockReport.setSimilarityScore(98.00);
		mockReport.setReportLink("abc");
		reports.add(mockReport);
		Mockito.when(reportService.findReportsByAsgmtId(Mockito.anyString()))
		.thenReturn(reports);
		this.mockMvc.perform(get("/api/assignment/1/findReportsByAsgmtId"))
		.andExpect(status().isOk());
		
	}
	
	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.ReportController#findReportsByAsgmtId(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindReportsByAsgmtIdFail() throws Exception {
		List<Report> reports = new ArrayList<>();
		Report mockReport = new Report();
		mockReport.setSimilarityScore(98.00);
		mockReport.setReportLink("abc");
		reports.add(mockReport);
		Mockito.when(reportService.findReportsByAsgmtId(Mockito.anyString()))
		.thenReturn(null);
		this.mockMvc.perform(get("/api/assignment/1/findReportsByAsgmtId"))
		.andExpect(status().is5xxServerError());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.ReportController#findReportsByAsgmtId(java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testFindReportsByAsgmtIdFailEmpty() throws Exception {
		List<Report> reports = new ArrayList<>();
		Report mockReport = new Report();
		mockReport.setSimilarityScore(98.00);
		mockReport.setReportLink("abc");
		Mockito.when(reportService.findReportsByAsgmtId(Mockito.anyString()))
		.thenReturn(reports);
		this.mockMvc.perform(get("/api/assignment/1/findReportsByAsgmtId"))
		.andExpect(status().is5xxServerError());
	}

	/**
	 * Test method for {@link com.msd.project.codesniffer.controller.ReportController#shareReport(long, long)}.
	 * @throws Exception 
	 */
	@Test
	public void testShareReport() throws Exception {
		doNothing().when(reportService).shareReport(Mockito.anyLong(), Mockito.anyLong());
		this.mockMvc.perform(post("/api/assignment/1/report/1/share"));
		
	}

}
