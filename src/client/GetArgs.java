package client;

import com.beust.jcommander.Parameter;

public class GetArgs {
    @Parameter(names={"-t"})
    String type;
    @Parameter(names={"-k"})
    String key;
    @Parameter(names={"-v"})
    String value;
    @Parameter(names={"-in"})
    String fileName;
}
