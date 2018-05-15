package com.msd.codesniffer;

/**
 * The type Professor.
 *
 * @author sidharththapar
 * @since 2/11/18.
 */
public class Professor extends User implements Faculty {
	
	/**
     * upload github url where files have been uploaded
    */
    @Override
    public void uploadURL() { }
    
    /**
     * upload files to be checked
    */
    @Override
    public void uploadFiles() { }
    
    /**
     * report to regulatory committee
     * downloads the report in correct format
     *
     * @param Two projects
     */
    @Override
    public Report checkPlag(Project p1, Project p2) {
        return null;
    }
    
    /**
     * download report.
     * downloads the report in correct format
     *
     * @param report the report
     */
    @Override
    public void downloadReport(Report report) {

    }

    /**
     * Inform authoritiy.
     * opens an email client with the pdf-report attached to the mail
     * and authority email address
     *
     * @param report the report
     */
    public void informAuthoritiy(Report report){};
}
