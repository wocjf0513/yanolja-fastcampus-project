package kdt_y_be_toy_project1.view;


/**
 * This interface for console apps while iterating. It becomes possible to configure a chain of logic
 * in which a processor performs its own logic and calls the next processor.
 *
 * <pre>{@code
 * class StartProcessor implements Processor {
 *
 *     @Override
 *     public Processor run(String input) {
 *         System.out.println("Start the app!");
 *         return new NextProcessor();
 *     }
 * }
 *
 * class NextProcessor implements Processor {
 *
 *     @Override
 *     public Processor run(String input) {
 *         System.out.println("do app's main logic");
 *         return new EndProcessor();
 *     }
 * }
 *
 * class EndProcessor implements Processor {
 *
 *     @Override
 *     public Processor run(String input) {
 *         System.out.println("shut down app!");
 *         return null; // if you want to stop loop return null
 *     }
 * }
 * }
 * </pre>
 * <p>
 */
public interface Processor {

    /**
     * return the Processor after process with input argument.
     *
     * @param input part of InputStream. {@link java.io.InputStream}
     * @return the Processor, it is nullable to stop the processor chain
     */
    Processor run(String input);
}
