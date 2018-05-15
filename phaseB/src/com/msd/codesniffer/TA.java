package com.msd.codesniffer;

/**
 * The type Teaching Assistant.
 *
 * @author sidharththapar
 * @since 2/11/18.
 */
public class TA extends User implements Faculty {
    @Override
    public Report checkPlag(Project p1, Project p2) {
        return null;
    }

    @Override
    public void downloadReport(Report report) {

    }

    @Override
    public void uploadURL() {

    }

    @Override
    public void uploadFiles() {

    }

    /**
     * opens an email client with the pdf attached to the mail and authority
     * email entered with a subject as the Subject of the email.
     *
     * @param report  the report
     * @param email   the email
     * @param subject the subject
     */
    public void mailTo(Report report, String email, String subject){};
}
