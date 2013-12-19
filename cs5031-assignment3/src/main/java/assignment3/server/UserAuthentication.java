package assignment3.server;

import java.security.Principal;
import java.util.Vector;

import javax.ws.rs.WebApplicationException;

import assignment3.Interfaces.Model.User;
import assignment3.Interfaces.Model.DataBase.UserResponder;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.tmatesoft.sqljet.core.SqlJetException;

import com.sun.jersey.core.util.Base64;

public class UserAuthentication implements ContainerRequestFilter
{
    
//    private DBManipulator dbHandle;
    Vector<User>          users;
    // Exception thrown if user is unauthorized.
    private final static WebApplicationException unauthorized = new WebApplicationException(Response.status(Status.UNAUTHORIZED)
                                                                  .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"realm\"")
                                                                  .entity("Page requires login.")
                                                                  .build());    
    public UserAuthentication() throws SqlJetException
    {
//        dbHandle = new DBManipulator();
        UserResponder resp = new UserResponder();
        users              = resp.getAllUserInfo();
    }
    
    @Override
    public ContainerRequest filter(ContainerRequest request)
    {
        String auth = request.getHeaderValue("authorization");
        if (null == auth)
        {
            throw unauthorized;
        }
        auth = auth.replaceFirst("[Bb]asic ", "");
        String userCreds = Base64.base64Decode(auth);
        System.out.println(userCreds);
        
        for (User user : users)
        {
            String credential = user.getName() + ":" + user.getPasswordString();
            if (0 == userCreds.compareTo(credential)) 
            { 
                SecurityContext sc = new MySecurityContext(user.getName());
                request.setSecurityContext(sc);
                return request;
            }
        }
        throw unauthorized;
    }
        class MyUserPrincipal implements Principal
        {
            
            String name = null;
            
            public MyUserPrincipal(String principalName)
            {
                this.name = principalName;
            }
            
            @Override
            public String getName()
            {
                return name;
            }
        }
        
        class MySecurityContext implements SecurityContext
        {
            
            Principal principal = null;
            
            public MySecurityContext(String principalName)
            {
                this.principal = new MyUserPrincipal(principalName);
            }
            
            @Override
            public Principal getUserPrincipal()
            {
                return this.principal;
            }
            
            @Override
            public boolean isUserInRole(String role)
            {
                return false;
            }
            
            @Override
            public boolean isSecure()
            {
                return false;
            }
            
            @Override
            public String getAuthenticationScheme()
            {
                return SecurityContext.BASIC_AUTH;
            }
        }
}