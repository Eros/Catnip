package io.gpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Objects;
import java.util.logging.Level;

/***
 * @author George
 * @since 21-Dec-17
 */
public class FileUploadEvent {

    private static final Logger log = LoggerFactory.getLogger("[Catnip-Log] ");
    
    protected static void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String contentType[] = {"jpg", "png", "gif", "bmp"};//half of this isn't used but still
        for(String s : contentType.clone()) {
            if(Objects.equals(request.getContentType(), s))
            response.setContentType(s);
        }
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
            writer.println("New file has been uploaded with the name : " + fileName + " in directory : " + path);
            log.info(Level.FINE + "File {0} is attempting to upload to {1}", new Object[]{fileName, path});
        } catch (FileNotFoundException e){
            log.info(Level.SEVERE + "Exception caught: " + e);
            writer.print("Something went wrong. Either the file uploaded \n" +
                    "is protected, nonexistent or the location is not the one specified");
        } finally {
            if(out != null){
                out.close();
                log.info(Level.WARNING + " Output stream has been closed!");
            }
            if(in != null){
                in.close();
                log.info(Level.WARNING + " Input stream has been closed!");
            }
            if(writer != null){
                writer.close();
                log.info(Level.WARNING + " Writer has been closed!");
            }
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
