package io.gpm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/***
 * @author George
 * @since 21-Dec-17
 */
public class FileUploadEvent {


    protected static void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("");//replace with image

        //general setting up shit

        final String path = ""; //replace with the path
        final Part filePart = request.getPart("file");

    }
}
