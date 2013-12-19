package assignment3.Controller.Interfaces;

import assignment3.server.Interfaces.IFactory;

public abstract class AbstractTest {
    
    private static final String implementation = "uk.ac.standrews.cs.cs2001.practical02.edwin.impl.Factory";
    
    private static IFactory factory;
    
    static {
        try {
            factory = (IFactory) Class.forName(implementation).newInstance();
        }
        catch (Exception e) {
            System.err.println("cannot load factory class: " + e.getMessage());
        }
    }

    protected static IFactory getFactory() {
        return factory;
    }
}
