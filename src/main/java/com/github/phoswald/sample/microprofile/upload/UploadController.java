package com.github.phoswald.sample.microprofile.upload;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.ibm.websphere.jaxrs20.multipart.IAttachment;
import com.ibm.websphere.jaxrs20.multipart.IMultipartBody;

@RequestScoped
@Path("/pages")
public class UploadController {

    private @Context HttpServletRequest request;

    @GET
    @Path("/upload")
    @Produces(MediaType.TEXT_HTML)
    public Response getUploadPage() {
        return Response.ok(new UploadView().render(new UploadViewModel())).build();
    }

    @POST
    @Path("/upload-form-single")
    @Produces(MediaType.TEXT_HTML)
    public Response postUploadSinglePage(IMultipartBody multipartBody) throws IOException, ServletException {
        StringBuilder message = new StringBuilder();
        message.append("Single attachment:\n");
        dumpServletRequest(message);
        dumpMultipartBody(message, multipartBody);
        return Response.ok(new UploadView().render(new UploadViewModel() //
                .withMessageSuccess(message.toString()))).build();
    }

    @POST
    @Path("/upload-form-multiple")
    @Produces(MediaType.TEXT_HTML)
    public Response postUploadMultiplePage(IMultipartBody multipartBody) throws IOException, ServletException {
        StringBuilder message = new StringBuilder();
        message.append("Multiple attachments:\n");
        dumpServletRequest(message);
        dumpMultipartBody(message, multipartBody);
        return Response.ok(new UploadView().render(new UploadViewModel() //
                .withMessageSuccess(message.toString()))).build();
    }

    private void dumpServletRequest(StringBuilder message) {
        message.append("CT=" + request.getContentType() + "\n");
        message.append("CL=" + request.getContentLength() + "\n");
//      for(Part part : request.getParts()) {
//          message.append("Part " + part.getName() + ": CT=" + part.getContentType() + ", S=" + part.getSize() + "\n");
//      }
    }

    private void dumpMultipartBody(StringBuilder message, IMultipartBody multipartBody) throws IOException {
        for (IAttachment attachment : multipartBody.getAllAttachments()) {
            message.append("- Attachment " + attachment.getContentId() + ", CT=" + attachment.getContentType() + "\n");
            message.append("  H=" + attachment.getHeaders() + "\n");
            try(InputStream stream = attachment.getDataHandler().getInputStream()) {
                Hasher hasher = Hashing.sha256().newHasher();
                int size = 0;
                int b;
                while((b = stream.read()) != -1) {
                    hasher.putByte((byte) b);
                    size++;
                }
                message.append("  Size=" + size + ", SHA256=" + hasher.hash().toString() + "\n");
            }
        }
    }
}
