package io.gpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.logging.Level;

/***
 * @author George
 * @since 21-Dec-17
 */
public class FileUploadEvent {

    private static final Logger log = LoggerFactory.getLogger("[Catnip-Log] ");

    protected static void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("");//todo replace with image

        //general setting up shit
        final String path = ""; //replace with the path
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);

        //streams and shit
        OutputStream out = null;
        InputStream in = null;
        PrintWriter writer = response.getWriter();

        try {
            out = new FileOutputStream(new File(path + File.separator + fileName));
            in = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while((read = in.read(bytes)) != -1){
                out.write(bytes, 0, read);
            }

        } catch (FileNotFoundException e){
            log.info(Level.SEVERE + "Exception caught: " + e);
        }

    }

    private static String getFileName(final Part part){
        final String partHeader = part.getHeader("content-disposition");
        log.info("Part header = {0}", partHeader.intern());

        for(String c : partHeader.split(";")){
            if(c.trim().startsWith("fileName")){
                return c.substring(
                        c.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
