package assignment3.server.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import assignment3.server.Interfaces.IInterestRequests;

import com.sun.jersey.api.core.ResourceConfig;

public class TheServerShouldBeAbleTo
{

        Mockery context = new JUnit4Mockery();

        @Before
        public void setUp() throws Exception 
        {
            context.mock(IInterestRequests.class);
        }

        /**
         * test configuring resources
         */
        @Test
        public void configuringResources() {
//
//            final String id = "11";
//            context.checking(new Expectations() {
//                {
//                    oneOf(gSRI).configureResources(sNC.package_name);
//                    will(returnValue(rc));
//                }
//            });
//
//            assertEquals(sNC.configureResources(), rc);
//        }




    }    
}
