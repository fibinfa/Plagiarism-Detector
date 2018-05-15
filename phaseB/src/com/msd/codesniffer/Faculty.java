package com.msd.codesniffer;

/**
 * The interface Faculty.
 *
 * @author sidharththapar
 * @since 2/11/18.
 */
public interface Faculty extends UploadCapability{
    /**
     * Checks the plagiarism between the 2 projects.
     *
     * @param p1 the p 1
     * @param p2 the p 2
     * @return the report
     */
    Report checkPlag(Project p1, Project p2);

    /**
     * Downloads a pdf format report file.
     *
     * @param report the report
     */
    void downloadReport(Report report);
}
